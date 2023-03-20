package it.unibo.unibomber.game.ecs.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.Utilities;

/**
 * This component manage the AI of Bots.
 */
public final class AIComponent extends AbstractComponent {

     List<Direction> followingPath = new ArrayList<>();
     Pair<Float, Float> oldPosition;

     @Override
     public void update() {
          Entity entity = this.getEntity();
          Type[][] typesMatrix = entity.getGame().getGameField().getMatrixTypes();
          //TODO
          if(typesMatrix[Math.round(entity.getPosition().getX())][
          Math.round(entity.getPosition().getY())] == Type.BOMB)
          typesMatrix[Math.round(entity.getPosition().getX())][
          Math.round(entity.getPosition().getY())] = Type.EXPLOSION;
          // TODO
          if (oldPosition == null)
               oldPosition = this.getEntity().getPosition();
          checkPath(typesMatrix);
          move(this.followingPath.get(0));
          updatePath(oldPosition, entity.getPosition());
          System.out.println(oldPosition);
          System.out.println(entity.getPosition());
          oldPosition = entity.getPosition();
     }

     private void checkPath(Type[][] typesMatrix) {
          if (this.followingPath.isEmpty()) {
               this.followingPath = getNextPath(typesMatrix);
               Collections.reverse(followingPath);
                this.followingPath = this.followingPath.stream()
                           .limit(3)
                           .collect(Collectors.toList());
          }
          if(this.followingPath.size()==0)
               this.followingPath = new ArrayList<>(List.of(Direction.CENTER));
     }
     private List<Direction> getNextPath(Type[][] typesMatrix) {
          if (isSafe(typesMatrix)) {
               var towardsPowerup =getDirectionsTowards(List.of(Type.POWERUP), true, typesMatrix);
               if (!towardsPowerup.contains(Direction.CENTER)) {
                    return towardsPowerup;
               }
               else{
                    if(typeLeftExist(Type.DESTRUCTIBLE_WALL)){
                         if(nextTo(Type.DESTRUCTIBLE_WALL,typesMatrix, this.getEntity().getPosition()))
                         {
                              PowerUpListComponent powerups = this.getEntity().getComponent(PowerUpListComponent.class).get();
                              if(powerups.getBombNumber()-powerups.getBombPlaced()>0 && nextTo(Type.AIR,typesMatrix,this.getEntity().getPosition())){
                                   BombPlaceComponent placeBomb = this.getEntity().getComponent(BombPlaceComponent.class).get();
                                   placeBomb.placeBomb();
                              }
                              return new ArrayList<>(List.of(Direction.CENTER));
                         }
                         else{
                              //TODO
                              var r = getDirectionsTowards(List.of(Type.DESTRUCTIBLE_WALL), true, typesMatrix);
                              if(r.size()>0)r.remove(0);
                              return r;
                         }
                    }
                    else{
                         if(((int)(Math.random()*100)) % 100 == 0){
                              PowerUpListComponent powerups = this.getEntity().getComponent(PowerUpListComponent.class).get();
                              if(powerups.getBombNumber()-powerups.getBombPlaced()>0 && nextTo(Type.AIR,typesMatrix,this.getEntity().getPosition())){
                                   BombPlaceComponent placeBomb = this.getEntity().getComponent(BombPlaceComponent.class).get();
                                   placeBomb.placeBomb();
                              }
                         }
                         return new ArrayList<>(List.of(Direction.CENTER));
                    }
               }
          } else {
               return getDirectionsTowards(List.of(Type.EXPLOSION), false, typesMatrix);
          }
     }

     private boolean nextTo(Type searchedType, Type[][] typesMatrix, Pair<Float,Float> position) {
          for(Direction d : Direction.valuesNoCenter()){
               int nextX = Math.round(position.getX()+d.getX());
               int nextY = Math.round(position.getY()+d.getY());
               if(Utilities.isBetween(nextX, 0, Constants.UI.Game.TILES_WIDTH) &&
                  Utilities.isBetween(nextY, 0, Constants.UI.Game.TILES_HEIGHT)){
                    if(typesMatrix[nextX][nextY] == searchedType){
                         return true;
                    }
               }
          }
          return false;
     }

     private List<Direction> getDirectionsTowards(List<Type> types, boolean goTowards,Type[][] matrix) {
          List<Type> toAvoid = new ArrayList<>(List.of(Type.RISING_WALL, Type.BOMB, Type.DESTRUCTIBLE_WALL, Type.INDESTRUCTIBLE_WALL,Type.EXPLOSION));
          toAvoid.removeAll(types);
          int[][] checkedPositions = new int[matrix.length][matrix[0].length];
          Deque<Pair<Integer, Integer>> unsafePositions = new LinkedList<>();
          Pair<Float, Float> startingPosition = this.getEntity().getPosition();
          checkedPositions[Math.round(startingPosition.getX())][Math.round(startingPosition.getY())] = 1;
          unsafePositions.add(new Pair<Integer, Integer>(
                    Math.round(startingPosition.getX()),
                    Math.round(startingPosition.getY())));

          while (unsafePositions.size() > 0) {
               Pair<Integer, Integer> current = unsafePositions.poll();
               Type cellType = matrix[current.getX()][current.getY()];
               if (toAvoid.contains(cellType))
                    continue;
               if (types.contains(cellType) ^ goTowards) {
                    checkSides(unsafePositions, checkedPositions, matrix, current, toAvoid);
               } else {
                    return extractPath(current, checkedPositions);
               }
          }
          // if this code is reached no safe position can be found, rip
          return new ArrayList<>(List.of(Direction.CENTER));
     }

     private void checkSides(Queue<Pair<Integer, Integer>> checkPositions, int[][] checkedPositions,
               Type[][] typeMatrix, Pair<Integer, Integer> current, List<Type> toAvoid) {
          for (Direction d : Direction.values()) {
               if (d != Direction.CENTER) {
                    int lastValue = checkedPositions[current.getX()][current.getY()];
                    Pair<Integer, Integer> nextCell = new Pair<Integer, Integer>(current.getX() + d.getX(),
                              current.getY() + d.getY());
                    if (Utilities.isBetween(nextCell.getX(), 0, Constants.UI.Game.TILES_WIDTH) &&
                              Utilities.isBetween(nextCell.getY(), 0, Constants.UI.Game.TILES_HEIGHT)) {
                         if (checkedPositions[nextCell.getX()][nextCell.getY()] == 0 &&
                             !toAvoid.contains(typeMatrix[nextCell.getX()][nextCell.getY()])) {
                              checkPositions.add(nextCell);
                              checkedPositions[nextCell.getX()][nextCell.getY()] = lastValue + 1;
                         }
                    }
               }
          }
     }

     private List<Direction> extractPath(Pair<Integer, Integer> current, int[][] checkedPositions) {
          int currentValue = checkedPositions[current.getX()][current.getY()];
          List<Direction> path = new ArrayList<>();
          while (currentValue != 1) {
               for (Direction d : Direction.valuesNoCenter()) {
                    Pair<Integer, Integer> nextCell = new Pair<Integer, Integer>(current.getX() + d.getX(),
                              current.getY() + d.getY());
                    if (Utilities.isBetween(nextCell.getX(), 0, Constants.UI.Game.TILES_WIDTH) &&
                              Utilities.isBetween(nextCell.getY(), 0, Constants.UI.Game.TILES_HEIGHT)) {
                         if (checkedPositions[nextCell.getX()][nextCell.getY()] == currentValue - 1) {
                              path.add(d);
                              currentValue--;
                              current = nextCell;
                              break;
                         }
                    }
               }
          }
          for (int i = 0; i < path.size(); i++) {
               path.set(i, path.get(i).reverse());
          }               
          return path;
     }

     private boolean isSafe(Type[][] matrix) {
          Pair<Float, Float> position = this.getEntity().getPosition();
          Type type = matrix[(int) Math.round(position.getX())][(int) Math.round(position.getY())];
          return type == Type.EXPLOSION || type == Type.BOMB ? false : true;
     }

     private boolean typeLeftExist(Type type) {
          return (this.getEntity().getGame().getEntities().stream().filter(e -> e.getType().equals(type))
                    .count() > 0);
     }

     private void move(Direction moveTo) {
          MovementComponent movementComponent = this.getEntity().getComponent(MovementComponent.class).get();
          movementComponent.moveBy(new Pair<Float, Float>(
                    moveTo.getX() * Constants.Input.POSITIVE_MOVE,
                    moveTo.getY() * Constants.Input.POSITIVE_MOVE));
     }

     private void updatePath(Pair<Float, Float> oldPosition, Pair<Float, Float> newPosition) {
          if (Math.round(oldPosition.getX()) != Math.round(newPosition.getX()) ||
              Math.round(oldPosition.getY()) != Math.round(newPosition.getY()) ||
              this.followingPath.get(0) == Direction.CENTER) {
               this.followingPath.remove(0);
          }
     }
}
