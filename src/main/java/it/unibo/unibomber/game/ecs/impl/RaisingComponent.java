package it.unibo.unibomber.game.ecs.impl;

import it.unibo.unibomber.game.ecs.api.Entity;

public class RaisingComponent extends AbstractComponent{

     private int normalizedFrames;

     public RaisingComponent() {
          this.normalizedFrames = 0;
     }
     @Override
     public void update() {
          Entity entity = this.getEntity();
          this.normalizedFrames++;
          if(this.normalizedFrames >= 5){
               entity.getGame().addEntity(entity.getGame().getFactory().makeIndestructibleWall(entity.getPosition()));
               entity.getGame().getEntities().remove(entity);
          }
     }
     
}
