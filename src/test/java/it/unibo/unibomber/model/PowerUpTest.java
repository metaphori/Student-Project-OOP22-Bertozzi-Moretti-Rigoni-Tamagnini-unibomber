package it.unibo.unibomber.model;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.impl.PowerUpHandlerComponent;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;
import it.unibo.unibomber.utilities.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PowerUpTest {
    private static final float PLAYER_STARTING_X = 0.0f;
    private static final float PLAYER_STARTING_Y = 0.0f;
    private static final int BOMB_NUMBER_BASE = 1;
    private static final int BOMB_NUMBER_POWERUP = 1;
    private static final int BOMB_NUMBER_MAX = 8;
    private static final int BOMB_FIRE_BASE = 1;
    private static final int BOMB_FIRE_POWERUP = 1;
    private static final int BOMB_FIRE_MAX = 8;
    private static final float SPEED_BASE = 0.3f;
    private static final float SPEED_POWERUP = 0.07f;
    private final EntityFactory entityFactory = new EntityFactoryImpl(null);

    private Entity createPlayerEntity() {
        return this.entityFactory.makePlayable(new Pair<Float, Float>(PLAYER_STARTING_X, PLAYER_STARTING_Y));
    }

    @Test
    void testBombNumberPowerUp() {
        final Entity player = this.createPlayerEntity();
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
        final Entity player = this.createPlayerEntity();
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
        final Entity player = this.createPlayerEntity();
        assertEquals(SPEED_BASE, player.getSpeed());
        player.addSpeed(SPEED_POWERUP);
        assertEquals(SPEED_BASE + SPEED_POWERUP, player.getSpeed());
    }

}
