package it.unibo.unibomber.model;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;
import it.unibo.unibomber.utilities.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class BomberTest {

    private static final float PLAYER_STARTING_X = 1.0f;
    private static final float PLAYER_STARTING_Y = 1.0f;
    private static final float PLAYER_EXCEPTED_X = 1.0f;
    private static final float PLAYER_EXCEPTED_Y = 1.0f;
    private static final float PLAYER_MOVE_X = 0.0f;
    private static final float PLAYER_MOVE_Y = 4.0f;

    private final EntityFactory entityFactory = new EntityFactoryImpl(null);

    private Entity createPlayerEntity() {
        return this.entityFactory.makePlayable(new Pair<Float, Float>(PLAYER_STARTING_X, PLAYER_STARTING_Y));
    }

    @Test
    void testCreateBomber() {
        Entity player = this.createPlayerEntity();
        assertEquals(new Pair<>(PLAYER_EXCEPTED_X, PLAYER_EXCEPTED_Y), player.getPosition());
    }

    @Test
    void testMovementBomber() {
        Entity player = this.createPlayerEntity();
        assertEquals(new Pair<>(PLAYER_EXCEPTED_X, PLAYER_EXCEPTED_Y), player.getPosition());
        player.getComponent(MovementComponent.class).get().moveBy(new Pair<Float, Float>(PLAYER_MOVE_X, PLAYER_MOVE_Y));
        player.getComponent(MovementComponent.class).get().update();
        assertNotEquals(new Pair<>(PLAYER_EXCEPTED_X, PLAYER_EXCEPTED_Y), player.getPosition());
    }
}
