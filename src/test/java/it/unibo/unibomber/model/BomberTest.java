package it.unibo.unibomber.model;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class BomberTest {

    private static final float PLAYER_STARTING_X = 1.0f;
    private static final float PLAYER_STARTING_Y = 1.0f;
    private static final float PLAYER_EXCEPTED_X = 1.0f;
    private static final float PLAYER_EXCEPTED_Y = 1.0f;
    private static final int FIELD_ROWS = 15;
    private static final int FIELD_COLS = 19;

    private final Game game = new GameImpl(null, FIELD_ROWS, FIELD_COLS);
    private final EntityFactory entityFactory = new EntityFactoryImpl(game);

    private Entity createPlayerEntity() {
        return this.entityFactory.makePlayable(new Pair<Float, Float>(PLAYER_STARTING_X, PLAYER_STARTING_Y));
    }

    @Test
    void testCreateBomber() {
        final Entity player = this.createPlayerEntity();
        assertEquals(new Pair<>(PLAYER_EXCEPTED_X, PLAYER_EXCEPTED_Y), player.getPosition());
    }

    @Test
    void testMovementBomber() {
        final Entity player = this.createPlayerEntity();
        assertEquals(new Pair<>(PLAYER_EXCEPTED_X, PLAYER_EXCEPTED_Y), player.getPosition());
        player.getComponent(MovementComponent.class).get().moveBy(Direction.DOWN);
        player.getComponent(MovementComponent.class).get().update();
        assertNotEquals(new Pair<>(PLAYER_EXCEPTED_X, PLAYER_EXCEPTED_Y), player.getPosition());
    }
}
