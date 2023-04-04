package it.unibo.unibomber.model;

import org.junit.jupiter.api.Test;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.impl.CollisionComponent;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpHandlerComponent;
import it.unibo.unibomber.game.ecs.impl.SlidingComponent;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Collision Test class.
 */
class CollisionTest {
     private static final float XPLAYERR = 0.6f;
     private static final float XPLAYERL = 0.3f;
     private static final int ROWS = 5;
     private static final int COLUMNS = 5;
     private final Game game = new GameImpl(null, ROWS, COLUMNS);

     @Test
     void testCollisionsPlayerWall() {
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
          game.addEntity(game.getFactory().makeIndestructibleWall(new Pair<Float, Float>(0f, 1f)));
          final Entity player = game.getFactory().makePlayable(new Pair<Float, Float>(XPLAYERR, 0f));
          game.addEntity(player);
          assertEquals(player.getPosition(), new Pair<>(XPLAYERR, 0f));
          moveOneTiles(player);
          assertEquals(1, player.getPosition().getX());
     }

     @Test
     void testCollisionsPlayerRisingWall() {
          game.addEntity(game.getFactory().makeRaisingWall(new Pair<Float, Float>(0f, 1f)));
          final Entity player = game.getFactory().makePlayable(new Pair<Float, Float>(0f, 0f));
          game.addEntity(player);
          assertEquals(player.getPosition(), new Pair<>(0f, 0f));
          moveOneTiles(player);
          assertEquals(new Pair<>(0f, 0f), player.getPosition());
     }

     @Test
     void testCollisionsPlayerWallAnglLeft() {
          game.addEntity(game.getFactory().makeIndestructibleWall(new Pair<Float, Float>(0f, 1f)));
          final Entity player = game.getFactory().makePlayable(new Pair<Float, Float>(XPLAYERL, 0f));
          game.addEntity(player);
          assertEquals(player.getPosition(), new Pair<>(XPLAYERL, 0f));
          moveOneTiles(player);
          assertEquals(0, player.getPosition().getX());
     }

     @Test
     void testCollisionsPlayerPowerUP() {
          game.addEntity(game.getFactory().makePowerUp(new Pair<Float, Float>(0f, 1f), PowerUpType.FIREUP));
          final Entity player = game.getFactory().makePlayable(new Pair<Float, Float>(0f, 0f));
          game.addEntity(player);
          assertEquals(player.getPosition(), new Pair<>(0f, 0f));
          moveOneTiles(player);
          assertTrue(player.getComponent(PowerUpHandlerComponent.class).get().getPowerUpList()
                    .contains(PowerUpType.FIREUP));
     }

     @Test
     void testCollisionsPlayerBomb() {
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
          final Entity player = game.getFactory().makePlayable(new Pair<Float, Float>(0f, 0f));
          game.addEntity(player);
          final PowerUpHandlerComponent powerUpHComponent = player.getComponent(PowerUpHandlerComponent.class).get();
          powerUpHComponent.addPowerUp(PowerUpType.KICKBOMB);
          assertTrue(powerUpHComponent.getPowerUpList().contains(PowerUpType.KICKBOMB));
          final Entity bomb = game.getFactory().makeBomb(player, new Pair<Float, Float>(0f, 1f));
          game.addEntity(bomb);
          player.getComponent(CollisionComponent.class).get().update();
          assertEquals(player.getPosition(), new Pair<>(0f, 0f));
          moveOneTiles(player);
          final Direction playerDirection = player.getComponent(MovementComponent.class).get().getDirection();
          assertEquals(Direction.DOWN, playerDirection);
          bomb.getComponent(CollisionComponent.class).get().update();
          assertEquals(new Pair<>(0f, 0f), player.getPosition());
          final SlidingComponent slidingComponent = bomb.getComponent(SlidingComponent.class).get();
          assertTrue(slidingComponent.isSliding());
          slidingComponent.update();
          bomb.getComponent(MovementComponent.class).get().update();
          assertNotEquals(new Pair<Float, Float>(0f, 1f), bomb.getPosition());
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
