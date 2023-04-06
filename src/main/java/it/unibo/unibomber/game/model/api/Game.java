package it.unibo.unibomber.game.model.api;

import java.util.List;

import it.unibo.unibomber.game.controller.api.World;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.utilities.Pair;

/**
 * This interface represent all entity and input in game.
 */
public interface Game {

    /**
     * @return the list of all entities in the current Game
     */
    List<Entity> getEntities();

    /**
     * @param <C>    only extension of entity
     * @param entity the entity to be added to the Game
     */
    <C extends Entity> void addEntity(C entity);

    /**
     * @param keyCode code contained
     * @return if key is contaned into Map
     */
    boolean isContained(int keyCode);

    /**
     * @param keyCode the actual code of the keyboard key
     */
    void addkeyPressed(int keyCode);

    /**
     * after the keys are read the list is cleaned.
     */
    void clearKeysPressed();

    /**
     * @param entity the entity to be removed
     */
    void removeEntity(Entity entity);

    /**
     * @return the dimensions of the game
     */
    Pair<Integer, Integer> getDimensions();

    /**
     * @return the Field construct relative to the current game
     */
    Field getGameField();

    /**
     * @return if key is contaned into Map
     */
    World getWorld();

    /**
     * @return the entity factory associated to the game
     */
    EntityFactory getFactory();

    /**
     * Update Times Up.
     */
    void updateTimesUp();

    /**
     * A method to update the new state of the game.
     */
    void updateGameState();

    /**
     * @return allthe pressed keys
     */
    List<Integer> getPressedKeys();
}
