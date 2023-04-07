package it.unibo.unibomber.controller.api;

import it.unibo.unibomber.game.ecs.api.Entity;

/**
 * This interface manages the upsate of all components.
 */
public interface SystemManager {

     /**
      * @param entity updates all components of the entity according to a system
      *               paradigm
      */
     void update(Entity entity);

}
