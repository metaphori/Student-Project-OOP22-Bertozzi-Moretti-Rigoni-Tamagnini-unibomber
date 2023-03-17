package it.unibo.unibomber.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static it.unibo.unibomber.utilities.Constants.Destroy.getDestructionFrames;

import org.junit.jupiter.api.Test;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpListComponent;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.utilities.Pair;

/**
 * This class tests the entity wall
 */
public class WallTest {

    private static final float WALL_COORD_X = 5.6f;
    private static final float WALL_COORD_Y = 3.4f;
    private static final int FIELD_ROWS = 15;
    private static final int FIELD_COLS = 19;
    private final Game game = new GameImpl(null, FIELD_ROWS, FIELD_COLS);
    private final EntityFactory entityFactory = new EntityFactoryImpl(this.game);

    private Entity createDestructibleWall() {
        return this.entityFactory.makeDestructibleWall(new Pair<Float,Float>(WALL_COORD_X, WALL_COORD_Y));
    }

    private Entity createIndestructibleWall() {
        return this.entityFactory.makeIndestructibleWall(new Pair<Float,Float>(WALL_COORD_X, WALL_COORD_Y));
    }

    @Test
    void testDestructibleWall() {
        var desWall = this.createDestructibleWall();
        var powerUpListComponent = desWall.getComponent(PowerUpListComponent.class);
        var destroyComponent = desWall.getComponent(DestroyComponent.class);
        this.game.addEntity(desWall);
        assertEquals(Type.DESTRUCTIBLE_WALL, desWall.getType());
        assertTrue(destroyComponent.isPresent());
        assertTrue(powerUpListComponent.isPresent());
        assertTrue(this.game.getEntities().contains(desWall));
        assertFalse(destroyComponent.get().isDestroyed());
        destroyComponent.get().destroy();
        assertTrue(destroyComponent.get().isDestroyed());
        for (int i = 0; i < getDestructionFrames(desWall.getType()); i++) {
            destroyComponent.get().update();
        }
        assertFalse(this.game.getEntities().contains(desWall));
    }

    @Test
    void testIndestructibleWall() {
        var indesWall = this.createIndestructibleWall();
        this.game.addEntity(indesWall);
        assertEquals(Type.INDESTRUCTIBLE_WALL, indesWall.getType());
        assertFalse(indesWall.getComponent(DestroyComponent.class).isPresent());
        assertFalse(indesWall.getComponent(PowerUpListComponent.class).isPresent());
        assertTrue(this.game.getEntities().contains(indesWall));
    }
}
