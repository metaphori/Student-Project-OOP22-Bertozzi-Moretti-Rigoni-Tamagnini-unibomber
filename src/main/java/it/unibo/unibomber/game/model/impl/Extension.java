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
            static BiConsumer<Entity, Entity> collide = (e, c) -> {
                if (c.getType() == Type.POWERUP) {
                    PowerUpType powerUpType = c.getComponent(PowerUpComponent.class).get()
                            .getPowerUpType();
                    PowerUpHandlerComponent powerUpHandlerComponent = e
                            .getComponent(PowerUpHandlerComponent.class).get();
                    powerUpHandlerComponent.addPowerUp(powerUpType);
                    c.getComponent(DestroyComponent.class).get().destroy();
                }
                CollisionComponent collision = c.getComponent(CollisionComponent.class).get();
                if (collision.isSolid() && !collision.isOver()) {
                    float thisX = Math.round(e.getPosition().getX());
                    float thisY = Math.round(e.getPosition().getY());
                    float eX = Math.round(c.getPosition().getX());
                    float eY = Math.round(c.getPosition().getY());
                    boolean isOccupied = e.getGame().getGameField().getField()
                            .entrySet().stream()
                            .anyMatch(entry -> entry.getKey().equals(new Pair<Integer, Integer>(
                                    (Math.round(thisX) + e.getComponent(MovementComponent.class)
                                            .get().getDirection().getX()),
                                    (Math.round(thisY)
                                            + -e.getComponent(MovementComponent.class).get()
                                                    .getDirection().getY()))));
                    if (!isOccupied) {
                        if (thisX != eX || thisY != eY) {
                            e.setPosition(new Pair<Float, Float>(thisX, thisY));
                        }
                    } else {
                        if (thisX == eX && thisY != eY) {
                            e.setPosition(
                                    new Pair<Float, Float>(e.getPosition().getX(), thisY));
                        } else if (thisX != eX && thisY == eY) {
                            e.setPosition(
                                    new Pair<Float, Float>(thisX, e.getPosition().getY()));
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
            static BiConsumer<Entity, Entity> collide = (e, c) -> {
                CollisionComponent collision = c.getComponent(CollisionComponent.class).get();
                if (c.getType() == Type.POWERUP) {
                    c.getComponent(DestroyComponent.class).get().destroy();
                }
                if ((c.getType() == Type.PLAYABLE || c.getType() == Type.BOT)
                        && !e.getComponent(CollisionComponent.class).get().isOver()
                        && c.getComponent(PowerUpHandlerComponent.class).get().getPowerUpList()
                                .contains(PowerUpType.KICKBOMB)) {
                    e.getComponent(SlidingComponent.class).get().setSliding(true,
                            c.getComponent(MovementComponent.class).get().getDirection());
                }
                if (collision.isSolid() && !collision.isOver()) {
                        e.getComponent(SlidingComponent.class).get().setSliding(false, null);
                }
            };

            public static BiConsumer<Entity, Entity> getCollide() {
                return collide;
            }
        }
    }
}
