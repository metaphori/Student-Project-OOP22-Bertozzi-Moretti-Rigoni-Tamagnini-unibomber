package it.unibo.unibomber.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unibo.unibomber.game.controller.impl.WorldImpl;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.impl.CollisionComponent;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Pair;

public class CollisionTest {
     @Test
    void testCollisions() {

     final int ROWS = 5 ;
     final int COLUMNS = 5 ;
     Game game = new GameImpl(null, ROWS, COLUMNS);
     game.addEntity(game.getFactory().makeIndestructibleWall(new Pair<Float,Float>(0f, 1f)));
     game.addEntity(game.getFactory().makeIndestructibleWall(new Pair<Float,Float>(1f, 0f)));
     Entity player = game.getFactory().makePlayable(new Pair<Float,Float>(0f,0f));
     game.addEntity(player);

     assertEquals(player.getPosition(),new Pair<>(0f,0f));
     MovementComponent movement = player.getComponent(MovementComponent.class).get();
     CollisionComponent collision = player.getComponent(CollisionComponent.class).get();
     float x,y;
     movement.moveBy(new Pair<Float,Float>(0f,Constants.Entity.BASE_SPEED));
     movement.update();
     collision.update();     
     x = player.getPosition().getX();
     y = player.getPosition().getY();
     movement.moveBy(new Pair<Float,Float>(0f,Constants.Entity.BASE_SPEED));
     movement.update();
     collision.update();     
     x = player.getPosition().getX();
     y = player.getPosition().getY();
     movement.moveBy(new Pair<Float,Float>(0f,Constants.Entity.BASE_SPEED));
     movement.update();     
     collision.update();
     x = player.getPosition().getX();
     y = player.getPosition().getY();
     movement.moveBy(new Pair<Float,Float>(0f,Constants.Entity.BASE_SPEED));
     movement.update();     
     collision.update();
     x = player.getPosition().getX();
     y = player.getPosition().getY();
     movement.moveBy(new Pair<Float,Float>(0f,Constants.Entity.BASE_SPEED));
     movement.update();     
     collision.update();
     x = player.getPosition().getX();
     y = player.getPosition().getY();
     assertEquals(new Pair<>(0f,0f),player.getPosition());    
}
}