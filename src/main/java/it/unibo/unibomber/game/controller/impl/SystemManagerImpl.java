package it.unibo.unibomber.game.controller.impl;

import java.util.List;
import java.util.Optional;

import it.unibo.unibomber.game.controller.api.SystemManager;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.impl.AIComponent;
import it.unibo.unibomber.game.ecs.impl.BombPlaceComponent;
import it.unibo.unibomber.game.ecs.impl.CollisionComponent;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;
import it.unibo.unibomber.game.ecs.impl.ExplodeComponent;
import it.unibo.unibomber.game.ecs.impl.InputComponent;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.ecs.impl.RaisingComponent;
import it.unibo.unibomber.game.ecs.impl.SlidingComponent;
import it.unibo.unibomber.game.ecs.impl.ThrowComponent;
import it.unibo.unibomber.game.model.impl.AbstractComponent;

/**
 * This implementation manages blocks of Components.
 */
public class SystemManagerImpl implements SystemManager {

     @Override
     public final void update(final Entity entity) {
          updateInputAndAI(entity);

          updatePhysics(entity);
          updateActions(entity);
          updateExplodeAndDestroy(entity);

     }

     /**
      * Updates all component passed as parameters, if present, in order.
      * 
      * @param componentList the list of passed components.
      */
     private void updateAllComponentsInOrder(final List<Optional<? extends AbstractComponent>> componentList) {
          for (final var component : componentList) {
               if (component.isPresent()) {
                    component.get().update();
               }
          }
     }

     /**
      * Updates the explosion and destruction of player/powerup/walls.
      * 
      * @param entity the entity
      */
     private void updateExplodeAndDestroy(final Entity entity) {
          final Optional<ExplodeComponent> explode = entity.getComponent(ExplodeComponent.class);
          final Optional<DestroyComponent> destroy = entity.getComponent(DestroyComponent.class);

          updateAllComponentsInOrder(List.of(explode, destroy));
     }

     /**
      * Updates the place and throw component.
      * 
      * @param entity the entity
      */
     private void updateActions(final Entity entity) {
          final Optional<BombPlaceComponent> placeBomb = entity.getComponent(BombPlaceComponent.class);
          final Optional<ThrowComponent> throwBomb = entity.getComponent(ThrowComponent.class);

          updateAllComponentsInOrder(List.of(placeBomb, throwBomb));
     }

     /**
      * Updates the phisics of all entities.
      * 
      * @param entity the entity
      */
     private void updatePhysics(final Entity entity) {
          final Optional<SlidingComponent> slidingComponent = entity.getComponent(SlidingComponent.class);
          final Optional<MovementComponent> movement = entity.getComponent(MovementComponent.class);
          final Optional<CollisionComponent> collision = entity.getComponent(CollisionComponent.class);
          final Optional<RaisingComponent> raising = entity.getComponent(RaisingComponent.class);

          updateAllComponentsInOrder(List.of(slidingComponent, movement, collision, raising));
     }

     /**
      * Updates the user input and AI input.
      * 
      * @param entity the entity
      */
     private void updateInputAndAI(final Entity entity) {

          final Optional<AIComponent> bot = entity.getComponent(AIComponent.class);
          final Optional<InputComponent> input = entity.getComponent(InputComponent.class);

          updateAllComponentsInOrder(List.of(bot, input));
     }
}
