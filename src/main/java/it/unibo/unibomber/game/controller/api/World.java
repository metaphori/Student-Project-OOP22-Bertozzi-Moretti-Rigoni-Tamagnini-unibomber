package it.unibo.unibomber.game.controller.api;

import it.unibo.unibomber.game.controller.impl.Menu;
import it.unibo.unibomber.game.controller.impl.Option;
import it.unibo.unibomber.game.controller.impl.Pause;
import it.unibo.unibomber.game.controller.impl.Play;
import it.unibo.unibomber.game.model.api.Game;

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
    /**
     * @return pause.
     */
    Pause getPause();
    /**
     * @return option.
     */
    Option getOption();
    /**
     * @return game.
     */
    Game getGame();
}
