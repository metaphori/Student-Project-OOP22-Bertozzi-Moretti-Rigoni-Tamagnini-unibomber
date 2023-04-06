package it.unibo.unibomber.game.controller.api;

import java.util.List;
import javax.swing.Timer;

import it.unibo.unibomber.game.controller.impl.Menu;
import it.unibo.unibomber.game.controller.impl.Option;
import it.unibo.unibomber.game.controller.impl.Play;
import it.unibo.unibomber.game.controller.impl.StateGame;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.model.api.Field;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.view.WorldPanelImpl;

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

    /**
     * Stop timer of game.
     */
    void stopTimer();

    /**
     * Set in pause timer.
     */
    void pauseTimer();

    /**
     * Start Timer.
     */
    void startTimer();

    /**
     * @return the list of entities
     */
    List<Entity> getEntities();

    /**
     * @param <C>    extensions of entity
     * @param entity entity to add
     */
    <C extends Entity> void addEntity(C entity);

    /**
     * @return the Field construct relative to the current game
     */
    Field getGameField();

    /**
     * @return a copy of UniBomberPanel
     */
    WorldPanelImpl getUnibomberPanel();

    /**
     * @return a copy of timer
     */
    Timer getTimer();

    /**
     * @return number of seconds
     */
    int getSecond();
}
