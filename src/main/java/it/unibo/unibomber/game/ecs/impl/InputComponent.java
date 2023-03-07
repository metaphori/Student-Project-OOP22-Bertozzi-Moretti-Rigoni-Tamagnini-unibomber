package it.unibo.unibomber.game.ecs.impl;

import java.util.Optional;

import com.jogamp.newt.event.KeyEvent;

import it.unibo.unibomber.utilities.Pair;

public class InputComponent extends AbstractComponent{


     @Override
     public void update() {
          var a=this.getEntity().getGame().getWorld().getPlay().getKeys();
          Optional<Integer> moveKey = this.getEntity().getGame().getWorld().getPlay().getKeys().stream()
                         .filter(e->e ==(int) KeyEvent.VK_W || e == (int)KeyEvent.VK_A || e == (int)KeyEvent.VK_S || e == (int)KeyEvent.VK_D)
                         .findFirst();
          Optional<Integer> spazio = this.getEntity().getGame().getWorld().getPlay().getKeys().stream()
          .filter(e->e == (int)KeyEvent.VK_SPACE)
          .findFirst();
          if(moveKey.isPresent()){
               Pair<Float,Float> moveBy= new Pair<Float,Float>(0f, 0f);
               switch (moveKey.get()){
                    case (int)KeyEvent.VK_W:
                         moveBy = new Pair<Float,Float>(0f,-0.2f);
                         break;
                    case (int)KeyEvent.VK_A:
                         moveBy = new Pair<Float,Float>(-0.2f,0f);
                         break;
                    case (int)KeyEvent.VK_S:
                         moveBy = new Pair<Float,Float>(0f,0.2f);
                         break;
                    case (int) KeyEvent.VK_D:
                         moveBy = new Pair<Float,Float>(0.2f,0f);
                    break;

               }
               if(spazio.isPresent()){
                    this.getEntity().getComponent(BombPlaceComponent.class).get().placeBomb();
               }
               var movementComponent = this.getEntity().getComponent(MovementComponent.class);
               if(movementComponent.isPresent()){
                    MovementComponent move = movementComponent.get();
                    move.moveBy(moveBy);
               }
               
          }
     }
     /*   
        game.addkeyPressed(87);w
        game.addkeyPressed(65);a
     game.addkeyPressed(83);s
        game.addkeyPressed(68);d
        game.addkeyPressed(27);esc
        game.addkeyPressed(32);space
        game.addkeyPressed(37);left
        game.addkeyPressed(39);right
      */
     
}
