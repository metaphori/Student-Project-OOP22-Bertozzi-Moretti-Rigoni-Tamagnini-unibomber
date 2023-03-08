package it.unibo.unibomber.game.ecs.impl;

import java.util.List;
import java.util.stream.Collectors;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

public class ExplodeComponent extends AbstractComponent {

     private boolean isExploding = false;

     @Override
     public void update() {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'update'");
     }

     /**
      * A method to destroy wall or powerups in the bomb range
      */
     public void explode() {
          Pair<Float, Float> checkPos;
          int bombRange = this.getEntity().getComponent(PowerUpListComponent.class).get().getBombPower();
          List<Entity> entitiesList = this.getEntity().getGame().getEntities().stream()
                              .filter(e -> e.getType() != Type.BOT && e.getType() != Type.PLAYABLE)
                              .collect(Collectors.toList());
          var field = this.getEntity().getGame().getGameField().getField();
          int countPositions;
          for (var entity : entitiesList) {
               if (entity.getType() == Type.BOMB && this.isExploding) {
                    for (var dir : Direction.values()) {
                         countPositions = 1;
                         while(countPositions <= bombRange) {
                              checkPos = new Pair<Float,Float>(entity.getPosition().getX() + dir.getX() * countPositions, 
                              entity.getPosition().getY() + dir.getY() * countPositions);
                              if (field.containsKey(checkPos)) {
                                   if (field.get(checkPos).getX() == Type.BOMB) {
                                        explode();
                                   } else {
                                        this.getEntity().getComponent(DestroyComponent.class).get().destroy();
                                   }
                              }
                              countPositions++;
                         }
                    }
               }
          }
          this.isExploding = true;
     }

}
