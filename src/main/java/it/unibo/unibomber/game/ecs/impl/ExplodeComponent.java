package it.unibo.unibomber.game.ecs.impl;

import java.util.List;
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

    private int explodeFrames;
    private int expiringFrames;
    private Entity placer;

    /**
     * In the constuctor, set 0 the field explodeFrames
     * and the field expiringFrames.
     * 
     * @param placer
     */
    public ExplodeComponent(final Entity placer) {
        this.expiringFrames = 0;
        this.explodeFrames = 0;
        this.placer = placer;
    }

    @Override
    public final void update() {
        if (this.expiringFrames == EXPIRING_TIME) {
            explode();
            this.explodeFrames++;
            if (this.explodeFrames < EXPLODE_DURATION) {
                explodeEntities(this.getEntity().getGame().getEntities().stream()
                        .filter(e -> e.getType() == Type.BOT || e.getType() == Type.PLAYABLE)
                        .collect(Collectors.toList()));
            } else {
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
     * A method to destroy wall or powerups in the bomb range.
     */
    private void explode() {
        explodeEntities(this.getEntity().getGame().getEntities().stream()
                .filter(e -> e.getType() == Type.BOMB)
                .collect(Collectors.toList()));
    }

    /**
     * General method to control if there are some entities on the bomb range.
     * 
     * @param entitiesList the entities to control
     */
    private void explodeEntities(final List<Entity> entitiesList) {
        final int bombRange = this.getEntity().getComponent(PowerUpListComponent.class).get().getBombFire();
        final var field = this.getEntity().getGame().getGameField().getField();
        Pair<Integer, Integer> checkPos;
        int countPositions;
        for (var entity : entitiesList) {
            for (var dir : Direction.values()) {
                countPositions = 1;
                while (countPositions <= bombRange) {
                    checkPos = new Pair<>(Math.round(entity.getPosition().getX() + (dir.getX() * countPositions)),
                        Math.round(entity.getPosition().getY() + (-(dir.getY()) * countPositions)));
                    if (field.containsKey(checkPos) && checkPos(entity.getPosition(), checkPos, field.get(checkPos).getY())) {
                        if (field.get(checkPos).getX() == Type.BOMB) {
                            explodeEntities(List.of(field.get(checkPos).getY()));
                        } else {
                            field.get(checkPos).getY().getComponent(DestroyComponent.class).get().destroy();
                        }
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
    private boolean checkPos(final Pair<Float, Float> pos, final Pair<Integer, Integer> checkPos, final Entity entity) {
        return (Math.round(pos.getX()) != checkPos.getX()
            || Math.round(pos.getY()) != checkPos.getY())
            && entity.getType() != Type.INDESTRUCTIBLE_WALL;
    }
}
