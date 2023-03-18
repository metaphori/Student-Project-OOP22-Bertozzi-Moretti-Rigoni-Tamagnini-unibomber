package it.unibo.unibomber.game.ecs.impl;

import java.util.ArrayList;
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
          if (oldPosition == null)
               oldPosition = this.getEntity().getPosition();
          Entity entity = this.getEntity();
          Type[][] typesMatrix = entity.getGame().getGameField().getMatrixTypes();
          Pair<Float, Float> newPosition = entity.getPosition();
          if (followingPath.isEmpty()) {
               followingPath = getNextPath(typesMatrix);
               // TODO
               followingPath.stream().skip((int) Math.floor(followingPath.size() / 2));
          }
          move(followingPath.get(followingPath.size() - 1));
          if (Math.round(oldPosition.getX()) != Math.round(newPosition.getX()) ||
                    Math.round(oldPosition.getY()) != Math.round(newPosition.getY()) ||
                    followingPath.get(0) == Direction.CENTER) {
               followingPath.remove(followingPath.size() - 1);
          }
          oldPosition = entity.getPosition();
     }

     private List<Direction> getNextPath(Type[][] typesMatrix) {
          if (isSafe(typesMatrix)) {
               if (powerupLeftExist()) {
                    return getDirectionsTowards(typesMatrix, List.of(Type.POWERUP), true);
               }
          } else {
               return getDirectionsTowards(typesMatrix, List.of(Type.EXPLOSION), false);
          }
          return List.of(Direction.CENTER).stream().collect(Collectors.toList());
     }

     private boolean powerupLeftExist() {
          return (this.getEntity().getGame().getEntities().stream().filter(e -> e.getType().equals(Type.POWERUP))
                    .count() > 0);
     }

     private void move(Direction moveTo) {
          MovementComponent movementComponent = this.getEntity().getComponent(MovementComponent.class).get();
          movementComponent.moveBy(new Pair<Float, Float>(
                    moveTo.getX() * Constants.Input.POSITIVE_MOVE,
                    moveTo.getY() * Constants.Input.POSITIVE_MOVE));
     }

     private List<Direction> getDirectionsTowards(Type[][] matrix, List<Type> types, boolean goTowards) {
          List<Type> toAvoid = List.of(Type.RISING_WALL, Type.BOMB, Type.DESTRUCTIBLE_WALL, Type.INDESTRUCTIBLE_WALL)
                    .stream().collect(Collectors.toList());
          int[][] checkedPositions = new int[matrix.length][matrix[0].length];
          Deque<Pair<Integer, Integer>> unsafePositions = new LinkedList<>();
          Pair<Float, Float> startingPosition = this.getEntity().getPosition();
          if (goTowards)
               toAvoid.add(Type.EXPLOSION);
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
                    checkSides(unsafePositions, checkedPositions, matrix, current);
               } else {
                    return extractPath(current, checkedPositions);
               }
          }
          // if this code is reached no safe position can be found, rip
          return List.of(Direction.CENTER).stream().collect(Collectors.toList());
     }

     private void checkSides(Queue<Pair<Integer, Integer>> checkPositions, int[][] checkedPositions,
               Type[][] typeMatrix, Pair<Integer, Integer> current) {
          for (Direction d : Direction.values()) {
               if (d != Direction.CENTER) {
                    int lastValue = checkedPositions[current.getX()][current.getY()];
                    Pair<Integer, Integer> nextCell = new Pair<Integer, Integer>(current.getX() + d.getX(),
                              current.getY() + d.getY());
                    if (Utilities.isBetween(nextCell.getX(), 0, Constants.UI.Game.TILES_WIDTH) &&
                              Utilities.isBetween(nextCell.getY(), 0, Constants.UI.Game.TILES_HEIGHT)) {
                         if (checkedPositions[nextCell.getX()][nextCell.getY()] == 0 &&
                                   (typeMatrix[nextCell.getX()][nextCell.getY()] != Type.INDESTRUCTIBLE_WALL
                                             && typeMatrix[nextCell.getX()][nextCell
                                                       .getY()] != Type.DESTRUCTIBLE_WALL)) {
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

}
