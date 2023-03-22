package it.unibo.unibomber.model;

import org.junit.jupiter.api.Test;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.impl.CollisionComponent;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Collision Test class.
 */
public class CollisionTest {
     @Test
     void testCollisions() {

          final int rows = 5;
          final int columns = 5;
          Game game = new GameImpl(null, rows, columns);
          game.addEntity(game.getFactory().makeIndestructibleWall(new Pair<Float, Float>(0f, 1f)));
          game.addEntity(game.getFactory().makeIndestructibleWall(new Pair<Float, Float>(1f, 0f)));
          Entity player = game.getFactory().makePlayable(new Pair<Float, Float>(0f, 0f));
          game.addEntity(player);

          assertEquals(player.getPosition(), new Pair<>(0f, 0f));
          MovementComponent movement = player.getComponent(MovementComponent.class).get();
          CollisionComponent collision = player.getComponent(CollisionComponent.class).get();
          movement.moveBy(new Pair<Float, Float>(0f, Constants.Entity.BASE_SPEED));
          movement.update();
          collision.update();
          movement.moveBy(new Pair<Float, Float>(0f, Constants.Entity.BASE_SPEED));
          movement.update();
          collision.update();
          movement.moveBy(new Pair<Float, Float>(0f, Constants.Entity.BASE_SPEED));
          movement.update();
          collision.update();
          movement.moveBy(new Pair<Float, Float>(0f, Constants.Entity.BASE_SPEED));
          movement.update();
          collision.update();
          movement.moveBy(new Pair<Float, Float>(0f, Constants.Entity.BASE_SPEED));
          movement.update();
          collision.update();
          assertEquals(new Pair<>(0f, 0f), player.getPosition());
     }
}
