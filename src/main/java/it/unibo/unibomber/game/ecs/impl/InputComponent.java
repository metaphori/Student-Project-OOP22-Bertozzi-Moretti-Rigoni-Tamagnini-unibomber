package it.unibo.unibomber.game.ecs.impl;

import java.util.Optional;
import java.awt.event.KeyEvent;

import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Pair;

/**
 * This class manage the input key.
 */
public class InputComponent extends AbstractComponent {

     @Override
     public final void update() {

          final Optional<Integer> moveKey = getMoveKey();
          // TODO change to getFunctionalKeys when needed
          final Optional<Integer> spazio = getSpaceKey();
          final Pair<Float, Float> moveBy = calculateMovement(moveKey);

          if (spazio.isPresent()) {
               this.getEntity().getComponent(BombPlaceComponent.class).get().placeBomb();
          }
          updateMovement(moveBy);
     }

     /**
      * @param moveBy how much the player should move
      *               updates the movementComponent relative to this player
      */
     private void updateMovement(final Pair<Float, Float> moveBy) {
          final var movementComponent = this.getEntity().getComponent(MovementComponent.class);

          if (movementComponent.isPresent()) {
               final MovementComponent move = movementComponent.get();
               move.moveBy(moveBy);
          }
     }

     /**
      * @param moveKey the last movement Key pressed
      * @return how much the player should move given moveKey
      */
     private Pair<Float, Float> calculateMovement(final Optional<Integer> moveKey) {
          final Integer keyValue = moveKey.isPresent() ? moveKey.get() : Constants.Input.NO_KEYS_VALUE;
          switch (keyValue) {
               case KeyEvent.VK_W:
                    return new Pair<Float, Float>(0f, Constants.Input.NEGATIVE_MOVE);
               case KeyEvent.VK_A:
                    return new Pair<Float, Float>(Constants.Input.NEGATIVE_MOVE, 0f);

               case KeyEvent.VK_S:
                    return new Pair<Float, Float>(0f, Constants.Input.POSITIVE_MOVE);

               case KeyEvent.VK_D:
                    return new Pair<Float, Float>(Constants.Input.POSITIVE_MOVE, 0f);
               default:
                    return new Pair<Float, Float>(0f, 0f);
          }
     }

     private Optional<Integer> getSpaceKey() {
          final var keyPressed = this.getEntity().getGame().getWorld().getPlay().getFirstFrameKeys();
          return keyPressed.keySet().stream()
                    .filter(e -> e == (int) KeyEvent.VK_SPACE)
                    .filter(e-> keyPressed.get(e).equals(true))
                    .findFirst();
     }

     /**
      * @return the last key (relative to the 4 movement keys) the player pressed
      */
     private Optional<Integer> getMoveKey() {
          final var keyPressed = this.getEntity().getGame().getWorld().getPlay().getKeys();

          return keyPressed.stream()
                    .filter(e -> e == (int) KeyEvent.VK_W
                              || e == (int) KeyEvent.VK_A
                              || e == (int) KeyEvent.VK_S
                              || e == (int) KeyEvent.VK_D)
                    .findFirst();
     }

}
