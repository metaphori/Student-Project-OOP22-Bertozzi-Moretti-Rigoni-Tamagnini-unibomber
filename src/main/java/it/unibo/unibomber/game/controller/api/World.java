package it.unibo.unibomber.game.controller.api;

import it.unibo.unibomber.game.controller.impl.Menu;
import it.unibo.unibomber.game.controller.impl.Option;
import it.unibo.unibomber.game.controller.impl.Pause;
import it.unibo.unibomber.game.controller.impl.Play;
import it.unibo.unibomber.game.controller.impl.StateGame;
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
     * set Play.
     */
    void setPlay();

    /**
     * @return pause.
     */
    Pause getPause();

    /**
     * @return option.
     */
    Option getOption();

    /**
     * @return stateGame.
     */
    StateGame getEndGame();

    /**
     * @return game.
     */
    Game getGame();
}
