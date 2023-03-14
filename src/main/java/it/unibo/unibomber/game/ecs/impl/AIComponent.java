package it.unibo.unibomber.game.ecs.impl;

import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

/**
 * This component manage the AI of Bots.
 */
public final class AIComponent extends AbstractComponent {

     @Override
     public void update() {
          Type[][] matrix = this.getEntity().getGame().getGameField().getMatrixTypes();
          String s = "";
          for(int x=0;x<5;x++){
               s="";
               for(int y=0;y<6;y++)
               s+=matrix[x][y];
               System.out.println(s);
          }
          if (isSafe(matrix)) {

          } else {
               moveToSafety(matrix);
          }
     }

     private void moveToSafety(Type[][] matrix) {
          Direction moveTo = getDirectionToSafety();
          this.getEntity().getComponent(MovementComponent.class).get().moveBy(null);
     }

     private Direction getDirectionToSafety() {
          return null;
     }

     private boolean isSafe(Type[][] matrix) {
          Pair<Float, Float> position = this.getEntity().getPosition();
          Type type = matrix[(int)Math.floor(position.getX())][(int)Math.floor(position.getY())];
          return type == Type.EXPLOSION || type == Type.BOMB? false : true;
     }

}
