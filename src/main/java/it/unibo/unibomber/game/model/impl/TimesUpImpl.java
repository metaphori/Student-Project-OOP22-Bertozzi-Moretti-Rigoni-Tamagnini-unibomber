package it.unibo.unibomber.game.model.impl;

import java.awt.Graphics;

import it.unibo.unibomber.game.controller.api.GameLoop;
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
public final class TimesUpImpl implements TimesUp, GameLoop {
     private final Game game;
     private boolean isStarted;
     private boolean isDone;
     private int normalizedFrames;
     private Pair<Integer, Integer> currentPosition;
     private boolean[][] raisedWalls;
     private Direction currentDirection;

     /**
      * Constructor of timesup.
      * 
      * @param game
      */
     public TimesUpImpl(final Game game) {
          normalizedFrames = 0;
          isStarted = false;
          isDone = false;
          currentDirection = Direction.RIGHT;
          currentPosition = new Pair<>(-1, 0);
          this.game = game;
          // TODO
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
          normalizedFrames = (normalizedFrames + 1) % 3;
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
               if (this.game.getGameField().getField().containsKey(newPosition)) {
                    final Entity existingEntity = this.game.getGameField().getField().get(newPosition).getY();
                    this.game.getEntities().remove(existingEntity);
               }
               this.game.addEntity(this.game.getFactory().makeRaisingWall(Utilities.getFloatPair(newPosition)));
               currentPosition = newPosition;
          }
     }

     @Override
     public void draw(final Graphics g) {
     }
}
