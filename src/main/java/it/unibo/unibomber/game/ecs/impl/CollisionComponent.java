package it.unibo.unibomber.game.ecs.impl;

public class CollisionComponent extends AbstractComponent{
     //true if it blocks other entities
     boolean isSolid;
     @Override
     public void update() {

     }

     public CollisionComponent(boolean isSolid){
          this.isSolid=isSolid;
     }

     public void checkCollisions() {
     }
     
}
