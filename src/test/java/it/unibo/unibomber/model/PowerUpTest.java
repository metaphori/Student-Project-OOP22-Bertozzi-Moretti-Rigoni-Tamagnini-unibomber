package it.unibo.unibomber.model;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.EntityImpl;
import it.unibo.unibomber.game.ecs.impl.PowerUpHandlerComponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.List;

class PowerUpTest {

    private static final int BOMB_NUMBER_BASE = 1;
    private static final int BOMB_NUMBER_POWERUP = 1;
    private static final int BOMB_FIRE_BASE = 1;
    private static final int BOMB_FIRE_POWERUP = 1;

    private Entity createPlayerEntity() {
        return new EntityImpl(null, null, Type.PLAYABLE)
                .addComponent(new PowerUpHandlerComponent(BOMB_NUMBER_BASE, BOMB_FIRE_BASE, List.of()));
    }

    @Test
    void testBombUpPowerUp() {
        Entity player = this.createPlayerEntity();
        player.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(PowerUpType.BOMBUP);
        assertEquals(BOMB_NUMBER_BASE + BOMB_NUMBER_POWERUP,
                player.getComponent(PowerUpHandlerComponent.class).get().getBombNumber());
    }

    @Test
    void testBombFireUpPowerUp() {
        Entity player = this.createPlayerEntity();
        player.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(PowerUpType.FIREUP);
        assertEquals(BOMB_FIRE_BASE + BOMB_FIRE_POWERUP,
                player.getComponent(PowerUpHandlerComponent.class).get().getBombFire());
    }

}
