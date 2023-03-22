package it.unibo.unibomber.game.model.impl;

import java.util.Optional;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.api.TimesUp;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.Utilities;

public class TimesUpImpl implements TimesUp {
     private Game game;
     private boolean isStarted;
     private boolean isDone;
     private int normalizedFrames;
     private Pair<Integer, Integer> currentPosition;
     private boolean[][] raisedWalls;
     private Direction currentDirection;

     public TimesUpImpl(Game game) {
          normalizedFrames=0;
          isStarted = false;
          isDone=false;
          currentDirection = Direction.RIGHT;
          currentPosition = new Pair<>(-1, 0);
          this.game = game;
          // TODO
          this.start();
     }

     public void start() {
          raisedWalls = new boolean[Constants.UI.Game.TILES_WIDTH][Constants.UI.Game.TILES_HEIGHT];
          isStarted = true;
     }

     public void update() {
          normalizedFrames = (normalizedFrames+1)%3;
          if (isStarted && !isDone && normalizedFrames == 0) {
               Pair<Integer, Integer> newPosition = new Pair<>(currentDirection.getX() + currentPosition.getX(),
                         currentDirection.getY() + currentPosition.getY());
               if (!Utilities.isBetween(newPosition.getX(), 0, Constants.UI.Game.TILES_WIDTH)
                         || !Utilities.isBetween(newPosition.getY(), 0, Constants.UI.Game.TILES_HEIGHT)
                         || this.raisedWalls[newPosition.getX()][newPosition.getY()] ==true) {
                    currentDirection = currentDirection.getNextClockwise();
                    newPosition = new Pair<>(currentDirection.getX() + currentPosition.getX(),
                              currentDirection.getY() + currentPosition.getY());
                    if(this.raisedWalls[newPosition.getX()][newPosition.getY()] ==true){
                         this.isDone=true;
                    }
               }
               raisedWalls[newPosition.getX()][newPosition.getY()] = true;
               if(this.game.getGameField().getField().containsKey(newPosition)){
                    Entity existingEntity = this.game.getGameField().getField().get(newPosition).getY();
                    if(existingEntity.getType() == Type.PLAYABLE||existingEntity.getType() == Type.BOT)
                         {
                              existingEntity.getComponent(DestroyComponent.class).get().destroy();
                         }
                    else
                         this.game.getEntities().remove(existingEntity);
               }
               this.game.addEntity(this.game.getFactory().makeRaisingWall(Utilities.getFloatPair(newPosition)));
               currentPosition=newPosition;
          }
     }
}
