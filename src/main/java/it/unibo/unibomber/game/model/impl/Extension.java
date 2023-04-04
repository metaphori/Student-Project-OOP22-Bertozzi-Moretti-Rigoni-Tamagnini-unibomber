package it.unibo.unibomber.game.model.impl;

import java.util.Map;
import java.util.function.BiConsumer;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.CollisionComponent;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpHandlerComponent;
import it.unibo.unibomber.game.ecs.impl.SlidingComponent;
import it.unibo.unibomber.game.ecs.impl.ThrowComponent;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

/**
 * Extension class for BiConsumer function for collision.
 */
public final class Extension {
    /**
     * Bomber Collision.
     */
    public static class Bomber {
        /**
         * Collison class.
         */
        public static final class Collision {
            /**
             * Collide BiConsumer for Bomber.
             */
            private static BiConsumer<Entity, Entity> collide = (entity, e) -> {
                if (e.getType() == Type.POWERUP) {
                    final PowerUpType powerUpType = e.getComponent(PowerUpComponent.class).get()
                            .getPowerUpType();
                    final PowerUpHandlerComponent powerUpHandlerComponent = entity
                            .getComponent(PowerUpHandlerComponent.class).get();
                    powerUpHandlerComponent.addPowerUp(powerUpType);
                    e.getComponent(DestroyComponent.class).get().destroy();
                }
                if (e.getType() == Type.RISING_WALL) {
                    entity.getComponent(DestroyComponent.class).get().destroy();
                }
                final CollisionComponent collision = e.getComponent(CollisionComponent.class).get();
                if (collision.isSolid() && !collision.isOver()) {
                    Extension.collisonWall(entity, e);
                }
            };

            /**
             * @return collision of bomber.
             */
            public static BiConsumer<Entity, Entity> getCollide() {
                return collide;
            }

            /**
             * Constructor.
             */
            private Collision() {

            }
        }
    }

    /**
     * Bomb Collision.
     */
    public static class Bomb {
        /**
         * Collison class.
         */
        public static final class Collision {
            /**
             * Collide BiConsumer for Bomb.
             */
            private static BiConsumer<Entity, Entity> collide = (entity, e) -> {
                if (e.getType() == Type.POWERUP && !entity.getComponent(ThrowComponent.class).get().isThrowing()) {
                    e.getComponent(DestroyComponent.class).get().destroy();
                }
                if (e.getType() == Type.BOMBER
                        && !entity.getComponent(CollisionComponent.class).get().isOver()
                        && e.getComponent(PowerUpHandlerComponent.class).get().getPowerUpList()
                                .contains(PowerUpType.KICKBOMB)
                        && checkNextPosition(entity, e)) {
                    entity.getComponent(SlidingComponent.class).get().setSliding(true,
                            e.getComponent(MovementComponent.class).get().getDirection());
                }
                final CollisionComponent collision = e.getComponent(CollisionComponent.class).get();
                if (collision.isSolid() && !collision.isOver()
                        && !entity.getComponent(ThrowComponent.class).get().isThrowing()) {
                    entity.getComponent(SlidingComponent.class).get().setSliding(false, Direction.CENTER);
                    Extension.collisonWall(entity, e);
                }
            };

            /**
             * @return collision of bomb.
             */
            public static BiConsumer<Entity, Entity> getCollide() {
                return collide;
            }

            /**
             * Constructor.
             */
            private Collision() {

            }
        }
    }

    /**
     * Check collision entity with wall.
     * 
     * @param entity
     * @param e
     */
    public static void collisonWall(final Entity entity, final Entity e) {
        final float thisX = Math.round(entity.getPosition().getX());
        final float thisY = Math.round(entity.getPosition().getY());
        final float eX = Math.round(e.getPosition().getX());
        final float eY = Math.round(e.getPosition().getY());
        final boolean isOccupied = entity.getGame().getGameField().getField()
                .entrySet().stream()
                .anyMatch(entry -> entry.getKey().equals(new Pair<Integer, Integer>(
                        Math.round(thisX) + entity.getComponent(MovementComponent.class)
                                .get().getDirection().getX(),
                        Math.round(thisY)
                                + entity.getComponent(MovementComponent.class).get()
                                        .getDirection().getY())));
        if (!isOccupied) {
            if (thisX != eX || thisY != eY) {
                entity.setPosition(new Pair<Float, Float>(thisX, thisY));
            }
        } else {
            if (thisX == eX && thisY != eY) {
                entity.setPosition(
                        new Pair<Float, Float>(entity.getPosition().getX(), thisY));
            } else if (thisX != eX && thisY == eY) {
                entity.setPosition(
                        new Pair<Float, Float>(thisX, entity.getPosition().getY()));
            }
        }
    }

    /**
     * This method check if bomb can be kicked.
     * 
     * @param bomb
     * @param player
     * @return whether the bomb che be kicked or not
     */
    private static boolean checkNextPosition(final Entity bomb, final Entity player) {
        final Map<Pair<Integer, Integer>, Pair<Type, Entity>> fieldMap = bomb.getGame().getGameField().getField();
        final Direction playerDirection = player.getComponent(MovementComponent.class).get().getDirection();
        final Pair<Integer, Integer> nextBombPosition = new Pair<>(
                Math.round(bomb.getPosition().getX()) + playerDirection.getX(),
                Math.round(bomb.getPosition().getY()) + playerDirection.getY());
        return !fieldMap.containsKey(nextBombPosition);
    }

    /**
     * Constructor.
     */
    private Extension() {

    }
}
