package it.unibo.unibomber.game.ecs.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

import static it.unibo.unibomber.utilities.Constants.Explode.EXPLODE_DURATION;
import static it.unibo.unibomber.utilities.Constants.Explode.EXPIRING_TIME;

/**
 * This component manage the explosion of the bomb.
 */
public class ExplodeComponent extends AbstractComponent {

    private final List<Entity> entitiesToDestroy;
    private int explodeFrames;
    private int expiringFrames;
    private Entity placer;
    private boolean isPlayerDied;
    private boolean isExploding;

    /**
     * In the constuctor, set 0 the field explodeFrames
     * and the field expiringFrames.
     * 
     * @param placer
     */
    public ExplodeComponent(final Entity placer) {
        this.entitiesToDestroy = new ArrayList<>();
        this.expiringFrames = 0;
        this.explodeFrames = 0;
        this.placer = placer;
        this.isPlayerDied = false;
        this.isExploding = false;
    }

    @Override
    public final void update() {
        if (this.expiringFrames == EXPIRING_TIME) {
            this.explodeFrames++;
            if (this.explodeFrames < EXPLODE_DURATION) {
                explodeEntities(this.getEntity().getGame().getEntities().stream()
                    .filter(e -> e.getType() == Type.BOMB
                            && e.getComponent(ExplodeComponent.class).get().isExploding())
                    .collect(Collectors.toList()));
            } else {
                this.destroyEntities();
                this.getEntity().getComponent(DestroyComponent.class).get().destroy();
                this.explodeFrames = 0;
                this.expiringFrames = 0;
                this.placer.getComponent(PowerUpHandlerComponent.class).get().setBombPlaced(-1);
            }
        } else {
            this.expiringFrames++;
        }
    }

    /**
     * A method that supplies the entity
     * who placed the bomb.
     * @return the entity
     */
    public Entity getPlacer() {
        return this.placer;
    }

    /**
     * A method to know if the bomb is exploding.
     * @return true if the bomb is exploding, false otherwise
     */
    public boolean isExploding() {
        return this.isExploding;
    }

    /**
     * General method to control if there are some entities on the bomb range.
     * 
     * @param entitiesList the entities to control
     */
    private void explodeEntities(final List<Entity> entitiesList) {
        final int bombRange = this.getEntity().getComponent(PowerUpListComponent.class).get().getBombFire();
        final var totalEntities = this.getEntity().getGame().getEntities();
        Optional<Entity> previousEntity;
        Optional<Entity> entitySearched;
        Pair<Float, Float> checkPos;
        int countPositions;
        this.explodeBomb();
        for (var entity : entitiesList) {
            for (var dir : Direction.values()) {
                previousEntity = Optional.empty();
                countPositions = 1;
                while (countPositions <= bombRange) {
                    checkPos = new Pair<>(entity.getPosition().getX() + (dir.getX() * countPositions),
                        entity.getPosition().getY() + (-(dir.getY()) * countPositions));
                    entitySearched = checkContainedInList(checkPos, totalEntities);
                    if (entitySearched.isPresent()
                        && previousEntity.isEmpty()) {
                        if (checkPos(entity.getPosition(), checkPos, entitySearched.get())) {
                            previousEntity = entitySearched;
                            if (entitySearched.get().getType() == Type.BOMB
                                && !entitySearched.get().getComponent(ExplodeComponent.class).get()
                                .isExploding()) {
                                entitySearched.get().getComponent(ExplodeComponent.class).get()
                                    .explodeBomb();
                                explodeEntities(List.of(entitySearched.get()));
                            }
                            this.entitiesToDestroy.add(entitySearched.get());
                        } else if (entitySearched.get().getType() == Type.INDESTRUCTIBLE_WALL) {
                            previousEntity = entitySearched;
                        } else if ((entitySearched.get().getType() == Type.PLAYABLE 
                                    || entitySearched.get().getType() == Type.BOT)
                                    && !this.isPlayerDied
                                    && this.checkRound(entitySearched.get().getPosition(), entity.getPosition())) {
                            this.isPlayerDied = true;
                            this.entitiesToDestroy.add(entitySearched.get());
                        }
                    } else if (previousEntity.isPresent()) {
                        countPositions += bombRange;
                    }
                    countPositions++;
                }
            }
        }
    }

    /**
     * A method that checks if the entity is destructible by the bomb.
     * @param pos the bomb position
     * @param checkPos the entity position
     * @param entity the entity
     * @return true if is destructible, false otherwise
     */
    private boolean checkPos(final Pair<Float, Float> pos, final Pair<Float, Float> checkPos, final Entity entity) {
        return !this.checkRound(pos, checkPos)
            && entity.getType() != Type.INDESTRUCTIBLE_WALL;
    }

    /**
     * A method that checks if the entity is contained in the list.
     * 
     * @param pos the position of the entity
     * @param entities the list of entities
     * @return the entity if is contained, an empty otherwise.
     */
    private Optional<Entity> checkContainedInList(final Pair<Float, Float> pos, final List<Entity> entities) {
        for (var entity : entities) {
            if (this.checkRound(entity.getPosition(), pos)) {
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

    /**
     * A method that destroys the entities exploded
     * by the bomb.
     */
    private void destroyEntities() {
        this.entitiesToDestroy.stream()
            .forEach(e -> e.getComponent(DestroyComponent.class).get().destroy());
        this.entitiesToDestroy.clear();
    }

    /**
     * A method to set the explosion of the bomb.
     */
    private void explodeBomb() {
        this.isExploding = true;
    }

    /**
     * A method to check the rounded position.
     * @param pos the first position
     * @param checkPos the second position
     * @return true if the positions are not equal, false otherwise
     */
    private boolean checkRound(final Pair<Float, Float> pos, final Pair<Float, Float> checkPos) {
        return Math.round(pos.getX()) == Math.round(checkPos.getX())
            && Math.round(pos.getY()) == Math.round(checkPos.getY());
    }
}
