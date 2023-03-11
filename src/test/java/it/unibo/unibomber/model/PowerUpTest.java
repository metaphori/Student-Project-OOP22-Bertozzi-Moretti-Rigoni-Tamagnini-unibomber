package it.unibo.unibomber.model;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.impl.PowerUpHandlerComponent;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PowerUpTest {

    private static final int BOMB_NUMBER_BASE = 1;
    private static final int BOMB_NUMBER_POWERUP = 1;
    private static final int BOMB_NUMBER_MAX = 8;
    private static final int BOMB_FIRE_BASE = 1;
    private static final int BOMB_FIRE_POWERUP = 1;
    private static final int BOMB_FIRE_MAX = 8;
    private static final float SPEED_BASE = 1;
    private static final float SPEED_POWERUP = 0.40f;
    private final EntityFactory entityFactory = new EntityFactoryImpl(null);

    private Entity createPlayerEntity() {
        return this.entityFactory.makePlayable(null);
    }

    @Test
    void testBombUpPowerUp() {
        Entity player = this.createPlayerEntity();
        assertEquals(BOMB_NUMBER_BASE, player.getComponent(PowerUpHandlerComponent.class).get().getBombNumber());
        player.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(PowerUpType.BOMBUP);
        assertEquals(BOMB_NUMBER_BASE + BOMB_NUMBER_POWERUP,
                player.getComponent(PowerUpHandlerComponent.class).get().getBombNumber());
        for (int i = 0; i < 10; i++) {
            player.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(PowerUpType.BOMBUP);
        }
        assertEquals(BOMB_NUMBER_MAX, player.getComponent(PowerUpHandlerComponent.class).get().getBombNumber());
        player.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(PowerUpType.BOMBDOWN);
        assertEquals(BOMB_NUMBER_MAX - BOMB_NUMBER_POWERUP,
                player.getComponent(PowerUpHandlerComponent.class).get().getBombNumber());
    }

    @Test
    void testBombFirePowerUp() {
        Entity player = this.createPlayerEntity();
        assertEquals(BOMB_FIRE_BASE, player.getComponent(PowerUpHandlerComponent.class).get().getBombFire());
        player.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(PowerUpType.FIREUP);
        assertEquals(BOMB_FIRE_BASE + BOMB_FIRE_POWERUP,
                player.getComponent(PowerUpHandlerComponent.class).get().getBombFire());
        for (int i = 0; i < 10; i++) {
            player.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(PowerUpType.FIREUP);
        }
        assertEquals(BOMB_FIRE_MAX, player.getComponent(PowerUpHandlerComponent.class).get().getBombFire());
        player.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(PowerUpType.FIREDOWN);
        assertEquals(BOMB_FIRE_MAX - BOMB_FIRE_POWERUP,
                player.getComponent(PowerUpHandlerComponent.class).get().getBombFire());
        player.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(PowerUpType.FIREFULL);
        assertEquals(BOMB_FIRE_MAX, player.getComponent(PowerUpHandlerComponent.class).get().getBombFire());
    }

    @Test
    void testSpeedUpPowerUp() {
        Entity player = this.createPlayerEntity();
        assertEquals(SPEED_BASE, player.getSpeed());
        player.addSpeed(PowerUpType.SPEEDUP);
        assertEquals(SPEED_BASE + SPEED_POWERUP, player.getSpeed());
    }

}
