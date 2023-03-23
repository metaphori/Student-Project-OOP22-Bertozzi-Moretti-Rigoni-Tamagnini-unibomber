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

     private List<Direction> followingPath = new ArrayList<>();
     private Pair<Float, Float> oldPosition;

     @Override
     public void update() {
          final Entity entity = this.getEntity();
          Type[][] typesMatrix = entity.getGame().getGameField().getMatrixTypes();
          // TODO
          if (typesMatrix[Math.round(entity.getPosition().getX())][Math
                    .round(entity.getPosition().getY())] == Type.BOMB) {
               typesMatrix[Math.round(entity.getPosition().getX())][Math
                         .round(entity.getPosition().getY())] = Type.EXPLOSION;
          }
          // TODO
          if (oldPosition == null) {
               oldPosition = this.getEntity().getPosition();

          }
          checkPath(typesMatrix);
          move(this.followingPath.get(0));
          updatePath(oldPosition, entity.getPosition());
          oldPosition = entity.getPosition();
     }

     /**
      * @param typesMatrix matrix of game types
      *                    makes sure the path the AI is following is not empty
      */
     private void checkPath(final Type[][] typesMatrix) {
          if (this.followingPath.isEmpty()) {
               this.followingPath = getNextPath(typesMatrix);
               Collections.reverse(followingPath);
               this.followingPath = this.followingPath.stream()
                         .limit(3)
                         .collect(Collectors.toList());
          }
          if (this.followingPath.isEmpty()) {
               this.followingPath = new ArrayList<>(List.of(Direction.CENTER));

          }
     }

     /**
      * @param typesMatrix matrix of game types
      * @return a List of directions toward the most advantageous position
      */
     private List<Direction> getNextPath(final Type[][] typesMatrix) {
          if (isSafe(typesMatrix)) {
               final var towardsPowerup = getDirectionsTowards(List.of(Type.POWERUP), true, typesMatrix);
               if (!towardsPowerup.contains(Direction.CENTER)) {
                    return towardsPowerup;
               } else {
                    if (typeLeftExist(Type.DESTRUCTIBLE_WALL)) {
                         if (nextTo(Type.DESTRUCTIBLE_WALL, typesMatrix, this.getEntity().getPosition())) {
                              final PowerUpListComponent powerups = this.getEntity()
                                        .getComponent(PowerUpListComponent.class)
                                        .get();
                              if (powerups.getBombNumber() - powerups.getBombPlaced() > 0
                                        && nextTo(Type.AIR, typesMatrix, this.getEntity().getPosition())) {
                                   final BombPlaceComponent placeBomb = this.getEntity()
                                             .getComponent(BombPlaceComponent.class).get();
                                   placeBomb.placeBomb();
                              }
                              return new ArrayList<>(List.of(Direction.CENTER));
                         } else {
                              // TODO
                              final var r = getDirectionsTowards(List.of(Type.DESTRUCTIBLE_WALL), true, typesMatrix);
                              if (!r.isEmpty()) {
                                   r.remove(0);
                              }
                              return r;
                         }
                    } else {
                         if (((int) (Math.random() * 100)) % 100 == 0) {
                              final PowerUpListComponent powerups = this.getEntity()
                                        .getComponent(PowerUpListComponent.class)
                                        .get();
                              if (powerups.getBombNumber() - powerups.getBombPlaced() > 0
                                        && nextTo(Type.AIR, typesMatrix, this.getEntity().getPosition())) {
                                   final BombPlaceComponent placeBomb = this.getEntity()
                                             .getComponent(BombPlaceComponent.class).get();
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

     /**
      * @param searchedType the type I want to know info about
      * @param typesMatrix  matrix of game types
      * @param position     the position of the AI entity
      * @return whether the entity is next to the searched type
      */
     private boolean nextTo(final Type searchedType, final Type[][] typesMatrix, final Pair<Float, Float> position) {
          for (final Direction d : Direction.valuesNoCenter()) {
               final int nextX = Math.round(position.getX() + d.getX());
               final int nextY = Math.round(position.getY() + d.getY());
               if (Utilities.isBetween(nextX, 0, Constants.UI.Game.TILES_WIDTH)
                         && Utilities.isBetween(nextY, 0, Constants.UI.Game.TILES_HEIGHT)
                         && typesMatrix[nextX][nextY] == searchedType) {
                    return true;
               }
          }
          return false;
     }

     /**
      * @param types       the list of types to go towards/away from
      * @param goTowards   whether to go towards or away from types
      * @param typesMatrix matrix of game types
      * @return the closest (safe) path towards types
      */
     private List<Direction> getDirectionsTowards(final List<Type> types, final boolean goTowards,
               final Type[][] typesMatrix) {
          final List<Type> toAvoid = new ArrayList<>(List.of(Type.RISING_WALL, Type.BOMB, Type.DESTRUCTIBLE_WALL,
                    Type.INDESTRUCTIBLE_WALL, Type.EXPLOSION));
          toAvoid.removeAll(types);
          int[][] checkedPositions = new int[typesMatrix.length][typesMatrix[0].length];
          final Deque<Pair<Integer, Integer>> unsafePositions = new LinkedList<>();
          final Pair<Float, Float> startingPosition = this.getEntity().getPosition();
          checkedPositions[Math.round(startingPosition.getX())][Math.round(startingPosition.getY())] = 1;
          unsafePositions.add(new Pair<Integer, Integer>(
                    Math.round(startingPosition.getX()),
                    Math.round(startingPosition.getY())));

          while (unsafePositions.size() > 0) {
               final Pair<Integer, Integer> current = unsafePositions.poll();
               final Type cellType = typesMatrix[current.getX()][current.getY()];
               if (toAvoid.contains(cellType)) {
                    continue;
               }
               if (types.contains(cellType) ^ goTowards) {
                    checkSides(unsafePositions, checkedPositions, typesMatrix, current, toAvoid);
               } else {
                    return extractPath(current, checkedPositions);
               }
          }
          // if this code is reached no safe position can be found, rip
          return new ArrayList<>(List.of(Direction.CENTER));
     }

     /**
      * @param checkPositions   the queue to add positions to check
      * @param checkedPositions the matrix which tracks already checked positions
      * @param typesMatrix      matrix of game types
      * @param current          the current position being checked
      * @param toAvoid          the types where an entity cannot go towards
      */
     private void checkSides(final Queue<Pair<Integer, Integer>> checkPositions, final int[][] checkedPositions,
               final Type[][] typesMatrix, final Pair<Integer, Integer> current, final List<Type> toAvoid) {
          for (final Direction d : Direction.values()) {
               if (d != Direction.CENTER) {
                    final int lastValue = checkedPositions[current.getX()][current.getY()];
                    final Pair<Integer, Integer> nextCell = new Pair<>(current.getX() + d.getX(),
                              current.getY() + d.getY());
                    if (Utilities.isBetween(nextCell.getX(), 0, Constants.UI.Game.TILES_WIDTH)
                              && Utilities.isBetween(nextCell.getY(), 0, Constants.UI.Game.TILES_HEIGHT)
                              && checkedPositions[nextCell.getX()][nextCell.getY()] == 0
                              && !toAvoid.contains(typesMatrix[nextCell.getX()][nextCell.getY()])) {
                         checkPositions.add(nextCell);
                         checkedPositions[nextCell.getX()][nextCell.getY()] = lastValue + 1;
                    }
               }
          }
     }

     /**
      * @param finalPosition    the final position reached
      * @param checkedPositions the matrix of ordered checked positions
      * @return a list of directions toward the reached type
      */
     private List<Direction> extractPath(final Pair<Integer, Integer> finalPosition, final int[][] checkedPositions) {
          Pair<Integer, Integer> current = finalPosition;
          int currentValue = checkedPositions[current.getX()][current.getY()];
          final List<Direction> path = new ArrayList<>();
          while (currentValue != 1) {
               for (final Direction d : Direction.valuesNoCenter()) {
                    final Pair<Integer, Integer> nextCell = new Pair<>(current.getX() + d.getX(),
                              current.getY() + d.getY());
                    if (Utilities.isBetween(nextCell.getX(), 0, Constants.UI.Game.TILES_WIDTH)
                              && Utilities.isBetween(nextCell.getY(), 0, Constants.UI.Game.TILES_HEIGHT)
                              && checkedPositions[nextCell.getX()][nextCell.getY()] == currentValue - 1) {
                         path.add(d);
                         currentValue--;
                         current = nextCell;
                         break;
                    }
               }
          }
          for (int i = 0; i < path.size(); i++) {
               path.set(i, path.get(i).reverse());
          }
          return path;
     }

     /**
      * @param typesMatrix matrix of game types
      * @return whether a cell is safe
      */
     private boolean isSafe(final Type[][] typesMatrix) {
          final Pair<Float, Float> position = this.getEntity().getPosition();
          final Type type = typesMatrix[Math.round(position.getX())][Math.round(position.getY())];
          return type != Type.EXPLOSION && type != Type.BOMB;
     }

     /**
      * @param type the type searched for
      * @return whether or not a type exists in the current game
      */
     private boolean typeLeftExist(final Type type) {
          return this.getEntity().getGame().getEntities().stream()
                    .filter(e -> e.getType().equals(type))
                    .count() > 0;
     }

     /**
      * @param moveTo the direction to move towards
      */
     private void move(final Direction moveTo) {
          final MovementComponent movementComponent = this.getEntity().getComponent(MovementComponent.class).get();
          movementComponent.moveBy(new Pair<Float, Float>(
                    moveTo.getX() * Constants.Input.POSITIVE_MOVE,
                    moveTo.getY() * Constants.Input.POSITIVE_MOVE));
     }

     /**
      * @param oldPosition the position the entity had last frame
      * @param newPosition the position the entity has now
      *                    this method checks whether or not the next integer has
      *                    been reached, if so it follows the next
      *                    direction
      */
     private void updatePath(final Pair<Float, Float> oldPosition, final Pair<Float, Float> newPosition) {
          if (Math.round(oldPosition.getX()) != Math.round(newPosition.getX())
                    || Math.round(oldPosition.getY()) != Math.round(newPosition.getY())
                    || this.followingPath.get(0) == Direction.CENTER) {
               this.followingPath.remove(0);
          }
     }
}
