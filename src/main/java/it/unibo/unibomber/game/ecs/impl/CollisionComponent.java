package it.unibo.unibomber.game.ecs.impl;

public class CollisionComponent extends AbstractComponent {
     //TODO
     // true if it blocks other entities
     private boolean isSolid;

     @Override
     public void update() {

     }

     public CollisionComponent(final boolean isSolid) {
          this.isSolid = isSolid;
     }

     public void checkCollisions() {
     }

}
