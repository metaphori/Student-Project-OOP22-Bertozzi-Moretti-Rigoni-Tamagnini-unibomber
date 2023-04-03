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
import it.unibo.unibomber.game.model.impl.AbstractComponent;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.Utilities;

/**
 * This component manage the AI of Bots, meaning movement,
 * placing bombs and interacting with the world.
 */
public final class AIComponent extends AbstractComponent {

     private List<Direction> followingPath;
     private Pair<Float, Float> oldPosition;
     private boolean isGettingCloser;

     /**
      * isGettingCloser is used to get the bot to more closely get to the next
      * cell.
      * 
      * @param position
      */
     public AIComponent(final Pair<Float, Float> position) {
          isGettingCloser = false;
          oldPosition = position;
          followingPath = new ArrayList<>(List.of(Direction.CENTER));
     }

     @Override
     public void update() {
          final Entity entity = this.getEntity();
          final Type[][] typesMatrix = getMatrixTypes();

          setValidPath(typesMatrix);
          move(this.followingPath.get(0));
          if (isPlacingBombAdvantageous(typesMatrix)) {
               placeBomb();
               followingPath = new ArrayList<>(List.of(Direction.CENTER));
          }
          updatePath(oldPosition, entity.getPosition());
          oldPosition = entity.getPosition();
     }

     /**
      * @param typesMatrix
      * @return true if the bot should place a bomb, false otherwise
      */
     private boolean isPlacingBombAdvantageous(final Type[][] typesMatrix) {
          final Type[][] typesWithBomber = getMatrixWithBombers(typesMatrix);
          final List<Type> typesToDestroy = List.of(Type.DESTRUCTIBLE_WALL, Type.BOMBER);
          return wouldBeSafe(typesMatrix, this.getEntity().getPosition())
                    && wouldExplodeNextTo(typesToDestroy, typesWithBomber, this.getEntity().getPosition());
     }

     /**
      * @param typesMatrix matrix of game types.
      *                    fills the path if empty.
      */
     private void setValidPath(final Type[][] typesMatrix) {
          if (this.followingPath.isEmpty()) {
               this.followingPath = getNextPath(typesMatrix);
               Collections.reverse(followingPath);
               this.followingPath = this.followingPath.stream()
                         .limit(1)
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
          if (!isSafe(typesMatrix)) {
               return getDirectionsTowards(Type.EXPLOSION, false, typesMatrix);
          } else {
               List<Direction> goTowards = getDirectionsTowards(Type.POWERUP, true, typesMatrix);
               if (goTowards.contains(Direction.CENTER)) {
                    if (typeLeftExist(Type.DESTRUCTIBLE_WALL)) {
                         final List<Direction> path = getDirectionsTowards(Type.DESTRUCTIBLE_WALL, true, typesMatrix);
                         path.remove(0);
                         return path;
                    } else {
                         final Type[][] typesWithBomber = getMatrixWithBombers(typesMatrix);
                         goTowards = getDirectionsTowards(Type.BOMBER, true, typesWithBomber);
                    }
               }
               return goTowards;
          }
     }

     /**
      * @param typesMatrix the matrix of types
      * @param position    the position where the bomb would be placed
      * @return true if the bot would be safe placing a bomb right now, false
      *         otherwise
      */
     private boolean wouldBeSafe(final Type[][] typesMatrix, final Pair<Float, Float> position) {
          if (typesMatrix[Math.round(position.getX())][Math.round(position.getY())] == Type.EXPLOSION) {
               return false;
          }
          Type[][] newMatrix = new Type[typesMatrix.length][typesMatrix[0].length];
          for (int x = 0; x < typesMatrix.length; x++) {
               for (int y = 0; y < typesMatrix[0].length; y++) {
                    newMatrix[x][y] = typesMatrix[x][y];
               }
          }
          for (final Direction d : Direction.valuesNoCenter()) {
               final int strength = this.getEntity().getComponent(PowerUpListComponent.class).get().getBombFire();
               addExplosionToMatrix(newMatrix, new Pair<>(Math.round(position.getX()), Math.round(position.getY())),
                         strength, d, 0);
          }
          return getDirectionsTowards(Type.EXPLOSION, false, newMatrix).get(0) != Direction.CENTER;
     }

     /**
      * handles the bomb placement by the AI.
      */
     private void placeBomb() {
          final PowerUpListComponent powerups = this.getEntity()
                    .getComponent(PowerUpListComponent.class)
                    .get();
          if (powerups.getBombNumber() - powerups.getBombPlaced() > 0) {
               final BombPlaceComponent placeBomb = this.getEntity()
                         .getComponent(BombPlaceComponent.class).get();
               placeBomb.placeBomb();
          }
     }

     /**
      * @param searchedTypes the list of types checked for potential explosion
      * @param typesMatrix   matrix of game types
      * @param position      the position the potential bomb will be placed in
      * @return whether the bomb will destroy one if the searchedtypes
      */
     private boolean wouldExplodeNextTo(final List<Type> searchedTypes, final Type[][] typesMatrix,
               final Pair<Float, Float> position) {
          final int strength = this.getEntity().getComponent(PowerUpListComponent.class).get().getBombFire();
          final List<Type> solidTypes = List.of(Type.INDESTRUCTIBLE_WALL, Type.BOMB, Type.DESTRUCTIBLE_WALL,
                    Type.POWERUP);
          for (final Direction d : Direction.valuesNoCenter()) {
               for (int i = 1; i <= strength; i++) {
                    final Pair<Integer, Integer> newPosition = new Pair<>(Math.round(position.getX()) + d.getX() * i,
                              Math.round(position.getY()) + d.getY() * i);
                    if (Utilities.isBetween(newPosition.getX(), 0, Constants.UI.Screen.getTilesWidth())
                              && Utilities.isBetween(newPosition.getY(), 0, Constants.UI.Screen.getTilesHeight())) {
                         if (searchedTypes.contains(typesMatrix[newPosition.getX()][newPosition.getY()])) {
                              return true;
                         }
                         if (solidTypes.contains(typesMatrix[newPosition.getX()][newPosition.getY()])) {
                              break;
                         }
                    }
               }
          }
          return false;
     }

     /**
      * @param type        the type to go towards/away from
      * @param goTowards   whether to go towards or away from types
      * @param typesMatrix matrix of game types
      * @return the closest (safe) path towards types
      */
     private List<Direction> getDirectionsTowards(final Type type, final boolean goTowards,
               final Type[][] typesMatrix) {
          final List<Type> toAvoid = new ArrayList<>(List.of(Type.RISING_WALL, Type.BOMB, Type.DESTRUCTIBLE_WALL,
                    Type.INDESTRUCTIBLE_WALL, Type.EXPLOSION));
          toAvoid.remove(type);
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
               if (type.equals(cellType) ^ goTowards) {
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
                    if (Utilities.isBetween(nextCell.getX(), 0, Constants.UI.Screen.getTilesWidth())
                              && Utilities.isBetween(nextCell.getY(), 0, Constants.UI.Screen.getTilesHeight())
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
                    final int nextX = current.getX() + d.getX();
                    final int nextY = current.getY() + d.getY();
                    if (Utilities.isBetween(nextX, 0, Constants.UI.Screen.getTilesWidth())
                              && Utilities.isBetween(nextY, 0, Constants.UI.Screen.getTilesHeight())
                              && checkedPositions[nextX][nextY] == currentValue - 1) {
                         path.add(d);
                         currentValue--;
                         current = new Pair<>(nextX, nextY);
                         break;
                    }
               }
          }
          for (int i = 0; i < path.size(); i++) {
               path.set(i, path.get(i).reverse());
          }
          if (path.isEmpty()) {
               path.add(Direction.CENTER);
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
          if ((Math.round(oldPosition.getX()) == Math.round(newPosition.getX())
                    && Math.round(oldPosition.getY()) == Math.round(newPosition.getY()))
                              && !canMoveFurther(newPosition)) {
               this.followingPath.remove(0);
               isGettingCloser = false;
          }
     }

     /**
      * @param currentPosition the position of the bot
      * @return whether or not the bot can move further to better allign with the
      *         cell
      *         without jepardizing it's safety
      */
     private boolean canMoveFurther(final Pair<Float, Float> currentPosition) {
          isGettingCloser = true;
          final float currentDifferenceX = Math.abs(currentPosition.getX()) - Math.abs(Math.round(currentPosition.getX()));
          final float currentDifferenceY = Math.abs(currentPosition.getY()) - Math.abs(Math.round(currentPosition.getY()));
          final Pair<Float, Float> tryPosition = new Pair<>(currentPosition.getX() + followingPath.get(0).getX()
                    * this.getEntity().getSpeed() * Constants.Movement.MULTIPLIER_GLOBAL_SPEED,
                    currentPosition.getY() + followingPath.get(0).getY() * this.getEntity().getSpeed()
                              * Constants.Movement.MULTIPLIER_GLOBAL_SPEED);
          final float nextDifferenceX = Math.abs(tryPosition.getX()) - Math.abs(Math.round(currentPosition.getX()));
          final float nextDifferenceY = Math.abs(tryPosition.getY()) - Math.abs(Math.round(currentPosition.getY()));

          final boolean isCloser = 1-Math.abs(currentDifferenceX) < 1-Math.abs(nextDifferenceX)
                    || 1-Math.abs(currentDifferenceY) < 1-Math.abs(nextDifferenceY);
          final boolean isOver = Math.round(tryPosition.getX()) != Math.round(currentPosition.getX())
                    || Math.round(tryPosition.getY()) != Math.round(currentPosition.getY());

          return isCloser && !isOver;
     }

     /**
      * @return a matrix with the sizes of the game field with all the types in play.
      */
     private Type[][] getMatrixTypes() {
          final Pair<Integer, Integer> gameDimensions = this.getEntity().getGame().getDimensions();
          final Type[][] typesMatrix = new Type[gameDimensions.getX()][gameDimensions.getY()];
          initializeTypeMatrix(typesMatrix);
          addEntitiesToMatrix(typesMatrix);
          handleBombExplosion(typesMatrix);

          return typesMatrix;
     }

     /**
      * adds to a type matrix the bomber in play.
      * 
      * @param typesMatrix matrix of game types
      * @return the matrix of types in play
      */
     private Type[][] getMatrixWithBombers(final Type[][] typesMatrix) {
          final Type[][] typesWithBomber = new Type[typesMatrix.length][typesMatrix[0].length];
          for (int x = 0; x < typesMatrix.length; x++) {
               for (int y = 0; y < typesMatrix[0].length; y++) {
                    typesWithBomber[x][y] = typesMatrix[x][y];
               }
          }

          final List<Entity> entities = this.getEntity().getGame().getEntities();
          entities.stream()
                    .filter(e -> e.getType().equals(Type.BOMBER))
                    .map(Entity::getPosition)
                    .filter(e -> !e.equals(this.getEntity().getPosition()))
                    .map(e -> Utilities.getRoundedPair(e))
                    .forEach(e -> {
                         typesWithBomber[e.getX()][e.getY()] = Type.BOMBER;
                    });
          return typesWithBomber;
     }

     private void initializeTypeMatrix(final Type[][] typesMatrix) {
          for (int x = 0; x < typesMatrix.length; x++) {
               for (int y = 0; y < typesMatrix[0].length; y++) {
                    typesMatrix[x][y] = Type.AIR;
               }
          }
     }

     /**
      * handles the explosion of future bombs in the typeMatrix.
      * 
      * @param typesMatrix matrix of game types.
      */
     private void handleBombExplosion(final Type[][] typesMatrix) {
          final var field = this.getEntity().getGame().getGameField().getField();
          field.keySet().stream()
                    .filter(e -> field.get(e).getX().equals(Type.BOMB))
                    .forEach(e -> {
                         final PowerUpListComponent powerupList = field.get(e).getY()
                                   .getComponent(PowerUpListComponent.class)
                                   .get();

                         for (final Direction d : Direction.valuesNoCenter()) {
                              addExplosionToMatrix(typesMatrix, e, powerupList.getBombFire(), d, 1);
                         }
                         if (Math.round(this.getEntity().getPosition().getX()) == e.getX()
                                   && Math.round(this.getEntity().getPosition().getY()) == e.getY()) {
                              typesMatrix[e.getX()][e.getY()] = Type.EXPLOSION;
                         }
                    });

     }

     /**
      * a recursive method which adds one row of explosion tiles at a time.
      * 
      * @param typesMatrix the matrix of types.
      * @param where       the initial position.
      * @param strength    the tsrength of the bomb.
      * @param d           the direction the current explosion line is facing.
      * @param step        how many steps of recurison it is in.
      */
     private void addExplosionToMatrix(final Type[][] typesMatrix, final Pair<Integer, Integer> where,
               final int strength, final Direction d, final int step) {
          if (step <= strength) {
               final Pair<Integer, Integer> newDirection = new Pair<>(where.getX() + d.getX() * step,
                         where.getY() + d.getY() * step);
               final List<Type> volatileTypes = List.of(Type.AIR, Type.EXPLOSION, Type.POWERUP);
               if (Utilities.isBetween(newDirection.getX(), 0, Constants.UI.Screen.getTilesWidth())
                         && Utilities.isBetween(newDirection.getY(), 0, Constants.UI.Screen.getTilesHeight())
                         && volatileTypes.contains(typesMatrix[newDirection.getX()][newDirection.getY()])) {
                    typesMatrix[newDirection.getX()][newDirection.getY()] = Type.EXPLOSION;
                    addExplosionToMatrix(typesMatrix, where, strength, d, step + 1);
               }
          }
     }

     /**
      * adds all types exept bombers to the type matrix.
      * 
      * @param typesMatrix matrix of game types
      */
     private void addEntitiesToMatrix(final Type[][] typesMatrix) {
          final var field = this.getEntity().getGame().getGameField().getField();
          for (final Pair<Integer, Integer> pos : field.keySet()) {
               typesMatrix[pos.getX()][pos.getY()] = field.get(pos).getX();
          }
     }
}
