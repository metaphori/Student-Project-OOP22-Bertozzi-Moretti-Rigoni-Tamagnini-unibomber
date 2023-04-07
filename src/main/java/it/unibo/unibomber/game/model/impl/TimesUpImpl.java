package it.unibo.unibomber.game.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.api.TimesUp;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.Utilities;

/**
 * TimesUpImpl class.
 */
public final class TimesUpImpl implements TimesUp {
     private final List<Game> game;
     private boolean isStarted;
     private boolean isDone;
     private int normalizedFrames;
     private Pair<Integer, Integer> currentPosition;
     private boolean[][] raisedWalls;
     private Direction currentDirection;

     /**
      * Constructor of timesup.
      * 
      * @param game actual game
      */
     public TimesUpImpl(final Game game) {
          normalizedFrames = 0;
          isStarted = false;
          isDone = false;
          currentDirection = Direction.RIGHT;
          currentPosition = new Pair<>(-1, 0);
          this.game = new ArrayList<>();
          this.game.add(game);
          this.start();
     }

     /**
      * start.
      */
     @Override
     public void start() {
          raisedWalls = new boolean[Constants.UI.Screen.getTilesWidth()][Constants.UI.Screen.getTilesHeight()];
          isStarted = true;
     }

     @Override
     public void update() {
          if (isStarted && !isDone && normalizedFrames == 0) {
               Pair<Integer, Integer> newPosition = new Pair<>(currentDirection.getX() + currentPosition.getX(),
                         currentDirection.getY() + currentPosition.getY());
               if (!Utilities.isBetween(newPosition.getX(), 0, Constants.UI.Screen.getTilesWidth())
                         || !Utilities.isBetween(newPosition.getY(), 0, Constants.UI.Screen.getTilesHeight())
                         || this.raisedWalls[newPosition.getX()][newPosition.getY()]) {
                    currentDirection = currentDirection.getNextClockwise();
                    newPosition = new Pair<>(currentDirection.getX() + currentPosition.getX(),
                              currentDirection.getY() + currentPosition.getY());
                    if (this.raisedWalls[newPosition.getX()][newPosition.getY()]) {
                         this.isDone = true;
                    }
               }
               raisedWalls[newPosition.getX()][newPosition.getY()] = true;
               if (this.game.get(0).getGameField().getField().containsKey(newPosition)) {
                    final Entity existingEntity = this.game.get(0).getGameField().getField().get(newPosition).getY();
                    this.game.get(0).getEntities().remove(existingEntity);
               }
               this.game.get(0).addEntity(this.game.get(0).getFactory().makeRaisingWall(Utilities.getFloatPair(newPosition)));
               currentPosition = newPosition;
          }
          normalizedFrames = (normalizedFrames + 1) % 3;
     }
}
