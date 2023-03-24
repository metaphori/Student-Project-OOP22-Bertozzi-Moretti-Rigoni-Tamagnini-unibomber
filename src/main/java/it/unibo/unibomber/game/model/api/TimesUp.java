package it.unibo.unibomber.game.model.api;
/**
 * TimesUp interface.
 */
public interface TimesUp {
     /**
      * start raising wall.
      */
     void start();

     /**
      * raise wall if start was called previously.
      */
     void update();
}
