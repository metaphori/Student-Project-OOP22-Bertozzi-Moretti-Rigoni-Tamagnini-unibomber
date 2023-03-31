package it.unibo.unibomber.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static it.unibo.unibomber.utilities.Constants.Destroy.getDestroyFramesPerType;
import static it.unibo.unibomber.utilities.Constants.Destroy.STANDARD_FRAME_DURATION;

import org.junit.jupiter.api.Test;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpListComponent;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Pair;

/**
 * This class tests the entity wall.
 */
class WallTest {

    private static final float WALL_COORD_X = 5.6f;
    private static final float WALL_COORD_Y = 3.4f;
    private static final int FIELD_ROWS = 15;
    private static final int FIELD_COLS = 19;
    private static final int DROPPED_POWERUPS_FROM_WALL = 1;
    private final Game game = new GameImpl(null, FIELD_ROWS, FIELD_COLS);
    private final EntityFactory entityFactory = new EntityFactoryImpl(this.game);

    private Entity createDestructibleWall() {
        return this.entityFactory.makeDestructibleWall(new Pair<>(WALL_COORD_X, WALL_COORD_Y));
    }

    private Entity createIndestructibleWall() {
        return this.entityFactory.makeIndestructibleWall(new Pair<>(WALL_COORD_X, WALL_COORD_Y));
    }

    private int getDestroyFrames(final Type type) {
        return getDestroyFramesPerType().containsKey(type)
                ? getDestroyFramesPerType().get(type)
                : STANDARD_FRAME_DURATION;
    }

    @Test
    void testDestructibleWall() {
        final var desWall = this.createDestructibleWall();
        final var powerUpListComponent = desWall.getComponent(PowerUpListComponent.class);
        final var destroyComponent = desWall.getComponent(DestroyComponent.class);
        new Constants.Destroy();
        this.game.addEntity(desWall);
        assertEquals(Type.DESTRUCTIBLE_WALL, desWall.getType());
        assertTrue(destroyComponent.isPresent());
        assertTrue(powerUpListComponent.isPresent());
        assertTrue(this.game.getEntities().contains(desWall));
        assertFalse(destroyComponent.get().isDestroyed());
        destroyComponent.get().destroy();
        assertTrue(destroyComponent.get().isDestroyed());
        for (int i = 0; i < this.getDestroyFrames(desWall.getType()); i++) {
            destroyComponent.get().update();
        }
        assertFalse(this.game.getEntities().contains(desWall));
        if (!powerUpListComponent.get().getPowerUpList().isEmpty()) {
            assertEquals(DROPPED_POWERUPS_FROM_WALL, this.game.getEntities().size());
        }
    }

    @Test
    void testIndestructibleWall() {
        final var indesWall = this.createIndestructibleWall();
        this.game.addEntity(indesWall);
        assertEquals(Type.INDESTRUCTIBLE_WALL, indesWall.getType());
        assertFalse(indesWall.getComponent(DestroyComponent.class).isPresent());
        assertFalse(indesWall.getComponent(PowerUpListComponent.class).isPresent());
        assertTrue(this.game.getEntities().contains(indesWall));
    }
}
