package it.unibo.unibomber.model;

import org.junit.jupiter.api.Test;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.impl.CollisionComponent;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpListComponent;
import it.unibo.unibomber.game.ecs.impl.SlidingComponent;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Collision Test class.
 */
class CollisionTest {
     private static final float XPLAYERR = 0.6f;
     private static final float XPLAYERL = 0.3f;
     @Test
     void testCollisionsPlayerWall() {

          final int rows = 5;
          final int columns = 5;
          final Game game = new GameImpl(null, rows, columns);
          game.addEntity(game.getFactory().makeIndestructibleWall(new Pair<Float, Float>(0f, 1f)));
          game.addEntity(game.getFactory().makeIndestructibleWall(new Pair<Float, Float>(1f, 0f)));
          final Entity player = game.getFactory().makePlayable(new Pair<Float, Float>(0f, 0f));
          game.addEntity(player);
          assertEquals(player.getPosition(), new Pair<>(0f, 0f));
          moveOneTiles(player);
          assertEquals(new Pair<>(0f, 0f), player.getPosition());
     }

     @Test
     void testCollisionsPlayerWallAngleRight() {
          final int rows = 5;
          final int columns = 5;
          final Game game = new GameImpl(null, rows, columns);
          game.addEntity(game.getFactory().makeIndestructibleWall(new Pair<Float, Float>(0f, 1f)));
          final Entity player = game.getFactory().makePlayable(new Pair<Float, Float>(XPLAYERR, 0f));
          game.addEntity(player);
          assertEquals(player.getPosition(), new Pair<>(XPLAYERR, 0f));
          moveOneTiles(player);
          assertEquals(1, player.getPosition().getX());
     }

     @Test
     void testCollisionsPlayerRisingWall() {

          final int rows = 5;
          final int columns = 5;
          final Game game = new GameImpl(null, rows, columns);
          game.addEntity(game.getFactory().makeRaisingWall(new Pair<Float, Float>(0f, 1f)));
          final Entity player = game.getFactory().makePlayable(new Pair<Float, Float>(0f, 0f));
          game.addEntity(player);
          assertEquals(player.getPosition(), new Pair<>(0f, 0f));
          moveOneTiles(player);
          assertEquals(new Pair<>(0f, 0f), player.getPosition());
     }

     @Test
     void testCollisionsPlayerWallAnglLeft() {
          final int rows = 5;
          final int columns = 5;
          final Game game = new GameImpl(null, rows, columns);
          game.addEntity(game.getFactory().makeIndestructibleWall(new Pair<Float, Float>(0f, 1f)));
          final Entity player = game.getFactory().makePlayable(new Pair<Float, Float>(XPLAYERL, 0f));
          game.addEntity(player);
          assertEquals(player.getPosition(), new Pair<>(XPLAYERL, 0f));
          moveOneTiles(player);
          assertEquals(0, player.getPosition().getX());
     }

     @Test
     void testCollisionsPlayerPowerUP() {
          final int rows = 5;
          final int columns = 5;
          final Game game = new GameImpl(null, rows, columns);
          game.addEntity(game.getFactory().makePowerUp(new Pair<Float, Float>(0f, 1f), PowerUpType.FIREUP));
          final Entity player = game.getFactory().makePlayable(new Pair<Float, Float>(0f, 0f));
          game.addEntity(player);
          assertEquals(player.getPosition(), new Pair<>(0f, 0f));
          moveOneTiles(player);
          assertTrue(player.getComponent(PowerUpListComponent.class).get().getPowerUpList()
                    .contains(PowerUpType.FIREUP));
     }

     @Test
     void testCollisionsPlayerBomb() {
          final int rows = 5;
          final int columns = 5;
          final Game game = new GameImpl(null, rows, columns);
          final Entity player = game.getFactory().makePlayable(new Pair<Float, Float>(0f, 0f));
          game.addEntity(game.getFactory().makeBomb(player, new Pair<Float, Float>(0f, 1f)));
          game.addEntity(player);
          player.getComponent(CollisionComponent.class).get().update();
          assertEquals(player.getPosition(), new Pair<>(0f, 0f));
          moveOneTiles(player);
          assertEquals(new Pair<>(0f, 0f), player.getPosition());
     }

     @Test
     void testCollisionsPlayerBombSliding() {
          final int rows = 5;
          final int columns = 5;
          final Game game = new GameImpl(null, rows, columns);
          final Entity player = game.getFactory().makePlayable(new Pair<Float, Float>(0f, 0f));
          player.getComponent(PowerUpListComponent.class).get().addPowerUpList(PowerUpType.KICKBOMB);
          final Entity bomb = game.getFactory().makeBomb(player, new Pair<Float, Float>(0f, 1f));
          game.addEntity(bomb);
          game.addEntity(player);
          player.getComponent(CollisionComponent.class).get().update();
          assertEquals(player.getPosition(), new Pair<>(0f, 0f));
          moveOneTiles(player);
          bomb.getComponent(CollisionComponent.class).get().update();
          assertEquals(new Pair<>(0f, 0f), player.getPosition());
          assertTrue(bomb.getComponent(SlidingComponent.class).get().isSliding());
     }

     private void moveOneTiles(final Entity player) {
          final MovementComponent movement = player.getComponent(MovementComponent.class).get();
          final CollisionComponent collision = player.getComponent(CollisionComponent.class).get();
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
     }
}
