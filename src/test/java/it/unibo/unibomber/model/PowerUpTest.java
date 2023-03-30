package it.unibo.unibomber.model;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpHandlerComponent;
import it.unibo.unibomber.game.ecs.impl.ThrowComponent;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private static final float BOMB_EXCEPTED_X = 0;
    private static final float BOMB_EXCEPTED_Y = 3;
    private static final int FIELD_ROWS = 5;
    private static final int FIELD_COLS = 5;
    private final Game game = new GameImpl(null, FIELD_ROWS, FIELD_COLS);
    private final EntityFactory entityFactory = new EntityFactoryImpl(this.game);

    private Entity createPlayerEntity() {
        return this.entityFactory.makePlayable(new Pair<Float, Float>(PLAYER_STARTING_X, PLAYER_STARTING_Y));
    }

    private Entity createBombEntity(final Entity placer) {
        return this.entityFactory.makeBomb(placer, new Pair<Float, Float>(PLAYER_STARTING_X, PLAYER_STARTING_Y));
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
        final PowerUpHandlerComponent powerUpHComponent = player.getComponent(PowerUpHandlerComponent.class).get();
        powerUpHComponent.addPowerUp(PowerUpType.SPEEDUP);
        assertEquals(SPEED_BASE + SPEED_POWERUP, player.getSpeed());
        assertTrue(powerUpHComponent.getPowerUpList().contains(PowerUpType.SPEEDUP));
        powerUpHComponent.addPowerUp(PowerUpType.SPEEDDOWN);
        assertEquals(SPEED_BASE, player.getSpeed());
        assertTrue(powerUpHComponent.getPowerUpList().contains(PowerUpType.SPEEDDOWN));
    }

    @Test
    void testThrowBombPowerUp() {
        final Entity player = this.createPlayerEntity();
        final Direction playerDirection = player.getComponent(MovementComponent.class).get().getDirection();
        final PowerUpHandlerComponent powerUpHComponent = player.getComponent(PowerUpHandlerComponent.class).get();
        powerUpHComponent.addPowerUp(PowerUpType.THROWBOMB);
        assertEquals(new Pair<>(PLAYER_STARTING_X, PLAYER_STARTING_Y), player.getPosition());
        assertEquals(Direction.DOWN, playerDirection);
        assertTrue(powerUpHComponent.getPowerUpList().contains(PowerUpType.THROWBOMB));
        final Entity bomb = createBombEntity(player);
        final ThrowComponent throwComponent = bomb.getComponent(ThrowComponent.class).get();
        final MovementComponent bombMovementComponent = bomb.getComponent(MovementComponent.class).get();
        assertEquals(new Pair<>(PLAYER_STARTING_X, PLAYER_STARTING_Y), bomb.getPosition());
        final Pair<Integer, Integer> playerPositionNormalized = new Pair<>(
                (int) Math.round(player.getPosition().getX()),
                (int) Math.round(player.getPosition().getY()));
        throwComponent.throwBomb(playerPositionNormalized, playerDirection);
        assertTrue(throwComponent.isThrowing());
        while (throwComponent.isThrowing()) {
            throwComponent.update();
            bombMovementComponent.update();
        }
        assertEquals(new Pair<>(BOMB_EXCEPTED_X, BOMB_EXCEPTED_Y), bomb.getPosition());
    }

}
