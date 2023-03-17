package it.unibo.unibomber.game.ecs.impl;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
           System.out.println(this.getEntity().getPosition().toString());

          if (isSafe(matrix)) {     
               this.getEntity().getComponent(MovementComponent.class).get()
               .moveBy(new Pair<Float, Float>(0f,0f));

          } else {
               moveToSafety(matrix);
          }
     }

     private void moveToSafety(Type[][] matrix) {
          Direction moveTo = getDirectionToSafety(matrix);
          move(moveTo);
     }

     private void move(Direction moveTo) {
          MovementComponent movementComponent = this.getEntity().getComponent(MovementComponent.class).get();
          movementComponent.moveBy(new Pair<Float, Float>(
               moveTo.getX() * Constants.Input.POSITIVE_MOVE,
               moveTo.getY() * Constants.Input.POSITIVE_MOVE));
     }

     private Direction getDirectionToSafety(Type[][] matrix) {
          int[][] checkedPositions = new int[matrix.length][matrix[0].length];
          Deque<Pair<Integer, Integer>> checkPositions = new LinkedList<>();
          Pair<Integer, Integer> position = new Pair<Integer, Integer>(
                    (int) Math.round(this.getEntity().getPosition().getX()),
                    (int) Math.round(this.getEntity().getPosition().getY()));

          checkedPositions[position.getX()][position.getY()] = 1;
          checkPositions.add(new Pair<Integer, Integer>(position.getX(), position.getY()));
          while (checkPositions.size() > 0) {
               Pair<Integer, Integer> current = checkPositions.poll();
               Type cellType = matrix[current.getX()][current.getY()];
               switch (cellType) {
                    case BOMB:
                    case RISING_WALL:
                    case DESTRUCTIBLE_WALL:
                    case INDESTRUCTIBLE_WALL:
                         continue;
                    case EXPLOSION:
                         checkSides(checkPositions, checkedPositions,matrix, current);
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
          while(currentValue!=1)
          {
               for(Direction d : Direction.valuesNoCenter()){              
                    Pair<Integer,Integer> nextCell = new Pair<Integer,Integer>(current.getX()+d.getX(), current.getY()+d.getY());
                    if(Utilities.isBetween(nextCell.getX(), 0, Constants.UI.Game.TILES_WIDTH) &&
                       Utilities.isBetween(nextCell.getY(), 0, Constants.UI.Game.TILES_HEIGHT)){
                         if(checkedPositions[nextCell.getX()][nextCell.getY()] == currentValue-1){
                              path.add(d);
                              currentValue--;
                              current=nextCell;
                              break;
                         }
                    }
               }
          }
          Direction nextMovement = path.get(path.size()-1);
          //TODO why does it that

          return nextMovement.reverse();
     }

     private void checkSides(Queue<Pair<Integer, Integer>> checkPositions, int[][] checkedPositions, Type[][]typeMatrix, Pair<Integer, Integer> current) {
          for(Direction d : Direction.values()){
               if(d!=Direction.CENTER){
                    int lastValue=checkedPositions[current.getX()][current.getY()];
                    Pair<Integer,Integer> nextCell = new Pair<Integer,Integer>(current.getX()+d.getX(), current.getY()+d.getY());
                    if(Utilities.isBetween(nextCell.getX(), 0, Constants.UI.Game.TILES_WIDTH) &&
                       Utilities.isBetween(nextCell.getY(), 0, Constants.UI.Game.TILES_HEIGHT)){
                         if(checkedPositions[nextCell.getX()][nextCell.getY()] == 0 &&
                            (typeMatrix[nextCell.getX()][nextCell.getY()] != Type.INDESTRUCTIBLE_WALL || typeMatrix[nextCell.getX()][nextCell.getY()] == Type.DESTRUCTIBLE_WALL)){
                              checkPositions.add(nextCell);
                              checkedPositions[nextCell.getX()][nextCell.getY()] = lastValue+1;
                         }
                    }
               }
          }
     }

     private boolean isSafe(Type[][] matrix) {
          Pair<Float, Float> position = this.getEntity().getPosition();
          Type type = matrix[(int) Math.round(position.getX())][(int) Math.round(position.getY())];
          return type == Type.EXPLOSION || type == Type.BOMB ? false : true;
     }

}
