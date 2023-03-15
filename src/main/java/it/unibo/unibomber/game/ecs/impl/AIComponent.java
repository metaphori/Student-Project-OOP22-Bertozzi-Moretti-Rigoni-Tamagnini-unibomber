package it.unibo.unibomber.game.ecs.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.Utilities;

/**
 * This component manage the AI of Bots.
 */
public final class AIComponent extends AbstractComponent {

     @Override
     public void update() {
          Type[][] matrix = this.getEntity().getGame().getGameField().getMatrixTypes();
          if (isSafe(matrix)) {

          } else {
               moveToSafety(matrix);
          }
     }

     private void moveToSafety(Type[][] matrix) {
          Direction moveTo = getDirectionToSafety(matrix);
          this.getEntity().getComponent(MovementComponent.class).get()
                    .moveBy(new Pair<Float, Float>(
                              moveTo.getX() * Constants.Input.POSITIVE_MOVE,
                              moveTo.getY() * Constants.Input.POSITIVE_MOVE));
     }

     private Direction getDirectionToSafety(Type[][] matrix) {
          int currentValue=1;
          int[][] checkedPositions = new int[matrix.length][matrix[0].length];
          Queue<Pair<Integer, Integer>> checkPositions = new LinkedBlockingQueue<>();
          Pair<Integer, Integer> position = new Pair<Integer, Integer>(
                    (int) Math.round(this.getEntity().getPosition().getX()),
                    (int) Math.round(this.getEntity().getPosition().getY()));

          checkPositions.add(new Pair<Integer, Integer>(position.getX(), position.getY()));
          while (checkPositions.size() > 0) {
               Pair<Integer, Integer> current = checkPositions.poll();
               Type cellType = matrix[current.getX()][current.getY()];
               checkedPositions[current.getX()][current.getY()] = currentValue++;
               switch (cellType) {
                    case EXPLOSION:
                    case BOMB:
                    case RISING_WALL:
                    case DESTRUCTIBLE_WALL:
                    case INDESTRUCTIBLE_WALL:
                         checkSides(checkPositions, checkedPositions, current);
                         break;

                    default:
                         return extractFirstMovement(current,checkedPositions);
               }
          }               
          //if this code is reached no safe position can be found, rip
          return Direction.CENTER;
     }

     private Direction extractFirstMovement(Pair<Integer, Integer> current,int[][] checkedPositions) {
          int currentValue=checkedPositions[current.getX()][current.getY()];
          List<Direction> path = new ArrayList<>();
          while(currentValue!=0)
          {
               for(Direction d : Direction.values()){              
                    Pair<Integer,Integer> nextCell = new Pair<Integer,Integer>(current.getX()+d.getX(), current.getY()+d.getY());
                    if(Utilities.isBetween(nextCell.getX(), 0, Constants.UI.Game.TILES_WIDTH) &&
                       Utilities.isBetween(nextCell.getY(), 0, Constants.UI.Game.TILES_HEIGHT)){
                         if(checkedPositions[nextCell.getX()][nextCell.getY()] == currentValue-1){
                              path.add(d);
                              currentValue--;
                              continue;
                         }
                    }
               }
          }
          return path.get(path.size()-1);
     }

     private void checkSides(Queue<Pair<Integer, Integer>> checkPositions, int[][] checkedPositions, Pair<Integer, Integer> current) {
          for(Direction d : Direction.values()){
               Pair<Integer,Integer> nextCell = new Pair<Integer,Integer>(current.getX()+d.getX(), current.getY()+d.getY());
               if(Utilities.isBetween(nextCell.getX(), 0, Constants.UI.Game.TILES_WIDTH) &&
                  Utilities.isBetween(nextCell.getY(), 0, Constants.UI.Game.TILES_HEIGHT)){
                    if(checkedPositions[nextCell.getX()][nextCell.getY()] == 0){
                         checkPositions.add(nextCell);
                    }
               }
          }
     }

     private boolean isSafe(Type[][] matrix) {
          Pair<Float, Float> position = this.getEntity().getPosition();
          Type type = matrix[(int) Math.floor(position.getX())][(int) Math.floor(position.getY())];
          return type == Type.EXPLOSION || type == Type.BOMB ? false : true;
     }

}
