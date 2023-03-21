package it.unibo.unibomber.game.model.impl;

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
import it.unibo.unibomber.utilities.Pair;

public class Extension {
    public class Bomber {
        public class Collision {
            static BiConsumer<Entity, Entity> collide = (entity, e) -> {
                if (e.getType() == Type.POWERUP) {
                    PowerUpType powerUpType = e.getComponent(PowerUpComponent.class).get()
                            .getPowerUpType();
                    PowerUpHandlerComponent powerUpHandlerComponent = entity
                            .getComponent(PowerUpHandlerComponent.class).get();
                    powerUpHandlerComponent.addPowerUp(powerUpType);
                    e.getComponent(DestroyComponent.class).get().destroy();
                }
                CollisionComponent collision = e.getComponent(CollisionComponent.class).get();
                if (collision.isSolid() && !collision.isOver()) {
                    float thisX = Math.round(entity.getPosition().getX());
                    float thisY = Math.round(entity.getPosition().getY());
                    float eX = Math.round(e.getPosition().getX());
                    float eY = Math.round(e.getPosition().getY());
                    boolean isOccupied = entity.getGame().getGameField().getField()
                            .entrySet().stream()
                            .anyMatch(entry -> entry.getKey().equals(new Pair<Integer, Integer>(
                                    (Math.round(thisX) + entity.getComponent(MovementComponent.class)
                                            .get().getDirection().getX()),
                                    (Math.round(thisY)
                                            + -entity.getComponent(MovementComponent.class).get()
                                                    .getDirection().getY()))));
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
            };

            public static BiConsumer<Entity, Entity> getCollide() {
                return collide;
            }
        }
    }

    public class Bomb {
        public class Collision {
            static BiConsumer<Entity, Entity> collide = (entity, e) -> {
                if (e.getType() == Type.POWERUP) {
                    e.getComponent(DestroyComponent.class).get().destroy();
                }
                if ((e.getType() == Type.PLAYABLE || e.getType() == Type.BOT)
                        && !entity.getComponent(CollisionComponent.class).get().isOver()
                        && e.getComponent(PowerUpHandlerComponent.class).get().getPowerUpList()
                                .contains(PowerUpType.KICKBOMB)) {
                    entity.getComponent(SlidingComponent.class).get().setSliding(true,
                            e.getComponent(MovementComponent.class).get().getDirection());
                }
                CollisionComponent collision = e.getComponent(CollisionComponent.class).get();
                if (collision.isSolid() && !collision.isOver()) {
                    entity.getComponent(SlidingComponent.class).get().setSliding(false, null);
                    float thisX = Math.round(entity.getPosition().getX());
                    float thisY = Math.round(entity.getPosition().getY());
                    float eX = Math.round(e.getPosition().getX());
                    float eY = Math.round(e.getPosition().getY());
                    boolean isOccupied = entity.getGame().getGameField().getField()
                            .entrySet().stream()
                            .anyMatch(entry -> entry.getKey().equals(new Pair<Integer, Integer>(
                                    (Math.round(thisX) + entity.getComponent(MovementComponent.class)
                                            .get().getDirection().getX()),
                                    (Math.round(thisY)
                                            + -entity.getComponent(MovementComponent.class).get()
                                                    .getDirection().getY()))));
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
            };

            public static BiConsumer<Entity, Entity> getCollide() {
                return collide;
            }
        }
    }
}
