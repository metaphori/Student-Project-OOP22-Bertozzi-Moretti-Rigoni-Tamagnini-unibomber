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

    private static final int bombNumberBase = 1;
    private static final int bombNumberPowerUp = 1;
    private static final int bombFireBase = 1;
    private static final int bombFirePowerUp = 1;

    private Entity createPlayerEntity() {
        return new EntityImpl(null, null, Type.PLAYABLE)
                .addComponent(new PowerUpHandlerComponent(bombNumberBase, bombFireBase, List.of()));
    }

    @Test
    void testBombUpPowerUp() {
        Entity player = this.createPlayerEntity();
        player.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(PowerUpType.BOMBUP);
        assertEquals(bombNumberBase + bombNumberPowerUp,
                player.getComponent(PowerUpHandlerComponent.class).get().getBombNumber());
    }

    @Test
    void testBombFireUpPowerUp() {
        Entity player = this.createPlayerEntity();
        player.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(PowerUpType.FIREUP);
        assertEquals(bombFireBase + bombFirePowerUp,
                player.getComponent(PowerUpHandlerComponent.class).get().getBombFire());
    }

}
