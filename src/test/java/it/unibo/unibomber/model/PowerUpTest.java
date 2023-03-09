package it.unibo.unibomber.model;

import it.unibo.unibomber.game.controller.impl.WorldImpl;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.EntityImpl;
import it.unibo.unibomber.game.ecs.impl.PowerUpHandlerComponent;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.utilities.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.List;

class PowerUpTest {

    private static final int bombNumberBase = 1;
    private static final int bombNumberPowerUp = 1;
    private static final int bombPowerBase = 1;
    private static final int bombPowerPowerUp = 1;

    private Entity createPlayerEntity() {
        return new EntityImpl(new GameImpl(new WorldImpl()), new Pair<Float, Float>(0f, 0f), Type.PLAYABLE)
                .addComponent(new PowerUpHandlerComponent(bombNumberBase, bombPowerBase, List.of()));
    }

    @Test
    void testBombUpPowerUp() {
        Entity player = this.createPlayerEntity();
        player.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(PowerUpType.BOMBUP);
        assertEquals(bombNumberBase + bombNumberPowerUp,
                player.getComponent(PowerUpHandlerComponent.class).get().getBombNumber());
    }

    @Test
    void testBombPowerUpPowerUp() {
        Entity player = this.createPlayerEntity();
        player.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(PowerUpType.FIREUP);
        assertEquals(bombPowerBase + bombPowerPowerUp,
                player.getComponent(PowerUpHandlerComponent.class).get().getBombPower());
    }
    
}
