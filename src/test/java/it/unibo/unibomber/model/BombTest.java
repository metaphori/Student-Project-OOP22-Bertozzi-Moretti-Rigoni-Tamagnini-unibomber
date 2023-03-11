package it.unibo.unibomber.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.EntityImpl;
import it.unibo.unibomber.game.ecs.impl.PowerUpListComponent;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;

/**
 * This class tests the bomb entity.
 */
public class BombTest {
    
    private final EntityFactory entityFactory = new EntityFactoryImpl(null);

    @Test
    void testBomb() {
        
    }
}
