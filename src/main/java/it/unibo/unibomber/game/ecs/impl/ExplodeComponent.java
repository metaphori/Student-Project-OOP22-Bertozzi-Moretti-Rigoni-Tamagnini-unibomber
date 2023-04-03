package it.unibo.unibomber.game.ecs.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.model.impl.AbstractComponent;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

import static it.unibo.unibomber.utilities.Constants.Explode.EXPLODE_DURATION;
import static it.unibo.unibomber.utilities.Constants.Explode.EXPIRING_TIME;

/**
 * This component manage the explosion of the bomb.
 */
public class ExplodeComponent extends AbstractComponent {

    private final List<Pair<Integer, Integer>> explonsionsList;
    private final Set<Pair<Integer, Integer>> positionsNotToExplode;
    private final Entity placer;
    private int explodeFrames;
    private int expiringFrames;
    private boolean isExploding;

    /**
     * Constructor.
     * 
     * @param placer the entity that palced the bomb.
     */
    public ExplodeComponent(final Entity placer) {
        this.explonsionsList = new ArrayList<>();
        this.positionsNotToExplode = new HashSet<>();
        this.expiringFrames = 0;
        this.explodeFrames = 0;
        this.placer = placer;
        this.isExploding = false;
    }

    @Override
    public final void update() {
        if (!this.getEntity().getComponent(ThrowComponent.class).get().isThrowing()) {
            if (this.expiringFrames == EXPIRING_TIME) {
                this.explodeFrames++;
                this.explodeBomb();
                if (this.explodeFrames < EXPLODE_DURATION) {
                    this.explonsionsList.clear();
                    explodeEntities(this.getEntity().getGame().getEntities().stream()
                            .filter(e -> e.getType() == Type.BOMB)
                            .filter(e -> e.getComponent(ExplodeComponent.class).get().isExploding())
                            .collect(Collectors.toList()));
                } else if (!this.getEntity().getComponent(DestroyComponent.class).get().isDestroyed()) {
                    this.getEntity().getComponent(DestroyComponent.class).get().destroy();
                    this.explonsionsList.clear();
                    this.placer.getComponent(PowerUpHandlerComponent.class).get().addBombPlaced(-1);
                }
            } else {
                this.expiringFrames++;
            }
        }
    }

    /**
     * A method that supplies the explosions
     * areas of the bomb.
     * 
     * @return a list with explosions areas
     */
    public List<Pair<Integer, Integer>> getExplosions() {
        return new ArrayList<>(this.explonsionsList);
    }

    /**
     * A method that supplies the entity
     * who placed the bomb.
     * 
     * @return the entity
     */
    public Entity getPlacer() {
        return this.placer;
    }

    /**
     * A method to know if the bomb is exploding.
     * 
     * @return true if the bomb is exploding, false otherwise
     */
    public boolean isExploding() {
        return this.isExploding;
    }

    /**
     * A method to get the actual expiring frames
     * 
     * @return the expiring frames
     */
    public int getExpiringFrames() {
        return this.expiringFrames;
    }

    /**
     * General method to control if there are some entities on the bomb range.
     * 
     * @param entitiesList the entities to control
     */
    private void explodeEntities(final List<Entity> entitiesList) {
        final int bombRange = this.getEntity().getComponent(PowerUpListComponent.class).get().getBombFire();
        final var totalEntities = this.getEntity().getGame().getEntities();
        Optional<Entity> entitySearched;
        Pair<Float, Float> checkPos;
        int countPositions;
        for (final var entity : entitiesList) {
            this.explonsionsList.add(new Pair<>(Math.round(entity.getPosition().getY()),
                    Math.round(entity.getPosition().getX())));
            for (final var dir : Direction.values()) {
                countPositions = 1;
                while (countPositions <= bombRange) {
                    checkPos = new Pair<>(entity.getPosition().getX() + (dir.getX() * countPositions),
                            entity.getPosition().getY() + (dir.getY() * countPositions));
                    entitySearched = checkContainedInList(checkPos, totalEntities);
                    if (entitySearched.isPresent()
                            && !this.positionsNotToExplode.contains(new Pair<>(Math.round(checkPos.getX()),
                                    Math.round(checkPos.getY())))) {
                        if (checkEntity(entity.getPosition(), checkPos, entitySearched.get())) {
                            if (entitySearched.get().getType() == Type.BOMB
                                    && !entitySearched.get().getComponent(ExplodeComponent.class).get()
                                            .isExploding()
                                    && !entitySearched.get().getComponent(ThrowComponent.class).get()
                                            .isThrowing()) {
                                entitySearched.get().getComponent(ExplodeComponent.class).get()
                                        .explodeBomb();
                                explodeEntities(List.of(entitySearched.get()));
                                countPositions += bombRange;
                            } else if (entitySearched.get().getType() != Type.BOMB
                                    && entitySearched.get().getType() != Type.RISING_WALL) {
                                entitySearched.get().getComponent(DestroyComponent.class).get()
                                        .destroy();
                                if (entitySearched.get().getType() != Type.BOMBER
                                        && entitySearched.get().getType() != Type.BOMB) {
                                    this.positionsNotToExplode.add(new Pair<>(Math.round(checkPos.getX()),
                                            Math.round(checkPos.getY())));
                                    countPositions += bombRange;
                                }
                                if (entitySearched.get().getType() != Type.DESTRUCTIBLE_WALL) {
                                    this.explonsionsList.add(new Pair<>(Math.round(checkPos.getY()),
                                            Math.round(checkPos.getX())));
                                    if (entitySearched.get().getType() != Type.BOMBER
                                            && entitySearched.get().getType() != Type.BOMB) {
                                        countPositions += bombRange;
                                    }
                                }
                            }
                        } else if (entitySearched.get().getType() == Type.INDESTRUCTIBLE_WALL) {
                            this.positionsNotToExplode.add(new Pair<>(Math.round(checkPos.getX()),
                                    Math.round(checkPos.getY())));
                            countPositions += bombRange;
                        } else if (entitySearched.get().getType() == Type.BOMBER
                                && this.checkRound(entitySearched.get().getPosition(), entity.getPosition())) {
                            final var checkPosCopy = checkPos;
                            totalEntities.stream()
                                    .filter(e -> e.getType() == Type.BOMBER)
                                    .filter(p -> this.checkRound(p.getPosition(), checkPosCopy))
                                    .forEach(p -> p.getComponent(DestroyComponent.class).get().destroy());
                            this.explonsionsList.add(new Pair<>(Math.round(checkPos.getY()),
                                    Math.round(checkPos.getX())));
                        }
                    } else if (this.positionsNotToExplode.contains(new Pair<>(Math.round(checkPos.getX()),
                            Math.round(checkPos.getY())))) {
                        countPositions += bombRange;
                    } else if (checkBounds(checkPos)) {
                        this.explonsionsList.add(new Pair<>(Math.round(checkPos.getY()),
                                Math.round(checkPos.getX())));
                    }
                    countPositions++;
                }
            }
        }
    }

    /**
     * A method that checks if the entity is destructible by the bomb.
     * 
     * @param pos      the bomb position
     * @param checkPos the entity position
     * @param entity   the entity
     * @return true if is destructible, false otherwise
     */
    private boolean checkEntity(final Pair<Float, Float> pos, final Pair<Float, Float> checkPos, final Entity entity) {
        return !this.checkRound(pos, checkPos)
                && entity.getType() != Type.INDESTRUCTIBLE_WALL;
    }

    /**
     * A method that checks if the entity is contained in the list.
     * 
     * @param pos      the position of the entity
     * @param entities the list of entities
     * @return the entity if is contained, an empty otherwise.
     */
    private Optional<Entity> checkContainedInList(final Pair<Float, Float> pos, final List<Entity> entities) {
        final var entityInPos = entities.stream()
                .filter(e -> this.checkRound(e.getPosition(), pos))
                .collect(Collectors.toList());
        if (!entityInPos.isEmpty()) {
            return Optional.of(entityInPos.get(0));
        }
        return Optional.empty();
    }

    /**
     * A method to set the explosion of the bomb.
     */
    private void explodeBomb() {
        this.isExploding = true;
    }

    /**
     * A method to check the rounded position.
     * 
     * @param pos      the first position
     * @param checkPos the second position
     * @return true if the positions are not equal, false otherwise
     */
    private boolean checkRound(final Pair<Float, Float> pos, final Pair<Float, Float> checkPos) {
        return Math.round(pos.getX()) == Math.round(checkPos.getX())
                && Math.round(pos.getY()) == Math.round(checkPos.getY());
    }

    /**
     * A method that checks the position of the entity.
     * 
     * @param pos the position of the entity
     * @return true if is in the field, false otherwise
     */
    private boolean checkBounds(final Pair<Float, Float> pos) {
        return Math.round(pos.getX()) >= 0 && Math.round(pos.getY()) >= 0
                && Math.round(pos.getX()) < this.getEntity().getGame().getDimensions().getX()
                && Math.round(pos.getY()) < this.getEntity().getGame().getDimensions().getY();
    }
}
