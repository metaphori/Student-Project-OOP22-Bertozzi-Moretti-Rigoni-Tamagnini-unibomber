package it.unibo.unibomber.game.ecs.impl;

/**
 * This component manage the collision of entity.
 */
public class CollisionComponent extends AbstractComponent {
     // TODO
     // true if it blocks other entities
     private boolean isSolid;

     @Override
     public void update() {

     }

     /**
      * This method manage the solid state of entity.
      * 
      * @param isSolid
      */
     public CollisionComponent(final boolean isSolid) {
          this.isSolid = isSolid;
     }

     /**
      * This method check if entity collide with other one.
      */
     public void checkCollisions() {
     }

}
