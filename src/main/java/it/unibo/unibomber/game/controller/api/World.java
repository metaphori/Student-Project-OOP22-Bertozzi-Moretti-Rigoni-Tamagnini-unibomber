package it.unibo.unibomber.game.controller.api;

import it.unibo.unibomber.game.controller.impl.Menu;
import it.unibo.unibomber.game.controller.impl.Play;

/**
 * World class.
 */
public interface World {

    /**
     * @return menu.
     */
    Menu getMenu();

    /**
     * @return play.
     */
    Play getPlay();
}
