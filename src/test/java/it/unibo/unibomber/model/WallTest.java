package it.unibo.unibomber.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpListComponent;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;
/**
 * Wall Test.
 */
public class WallTest {
    private static final int NUM_POWERUPS_WALL = 1;
    private final EntityFactory entityFactory = new EntityFactoryImpl(null);

    private Entity createDestructibleWall() {
        return this.entityFactory.makeDestructibleWall(null);
    }

    private Entity createIndestructibleWall() {
        return this.entityFactory.makeIndestructibleWall(null);
    }

    @Test
    void testDestructibleWall() {
        var desWall = this.createDestructibleWall();
        var destroyComponent = desWall.getComponent(DestroyComponent.class);
        var powerUpListComponent = desWall.getComponent(PowerUpListComponent.class);
        assertEquals(Type.DESTRUCTIBLE_WALL, desWall.getType());
        assertTrue(destroyComponent.isPresent());
        assertTrue(powerUpListComponent.isPresent());
        assertFalse(powerUpListComponent.get().getPowerUpList().isEmpty());
        assertEquals(NUM_POWERUPS_WALL, powerUpListComponent.get().getPowerUpList().size());
        assertFalse(destroyComponent.get().isDestroyed());
        destroyComponent.get().destroy();
        assertTrue(destroyComponent.get().isDestroyed());
    }

    @Test
    void testIndestructibleWall() {
        var indesWall = this.createIndestructibleWall();
        assertEquals(Type.INDESTRUCTIBLE_WALL, indesWall.getType());
        assertFalse(indesWall.getComponent(DestroyComponent.class).isPresent());
        assertFalse(indesWall.getComponent(PowerUpListComponent.class).isPresent());
    }
}
