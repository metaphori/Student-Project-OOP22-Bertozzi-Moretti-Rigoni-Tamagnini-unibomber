package it.unibo.unibomber.game.model.api;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Pair;

/**
 * This interface manages the creation of different entities.
 */
public interface EntityFactory {

    /**
     * @param position    the position where the PowerUp will be placed
     * @param powerUpType the Type of the PowerUp
     * @return the PowerUp described
     */
    Entity makePowerUp(Pair<Float, Float> position, PowerUpType powerUpType);

    /**
     * @param position the initial position
     * @param type     whether it is a playable character or a bot
     * @return an instance of a Bomber
     */
    Entity makeBomber(Pair<Float, Float> position, Type type);

    /**
     * @param position the initial position
     * @return an instance of a Playable Bomber
     */
    Entity makePlayable(Pair<Float, Float> position);

    /**
     * @param position     the initial position
     * @param difficultyAI the difficulty of AI
     * @return an instance of a non playable Bomber
     */
    Entity makeBot(Pair<Float, Float> position, int difficultyAI);

    /**
     * @param placer the reference to the entity which placed the bomb
     *               in normal condition being the bomber
     * @param position     the initial position
     * @return an instance of a non an instance of a non playable Bomb
     */
    Entity makeBomb(Entity placer, Pair<Float, Float> position);

    /**
     * @param position the initial position
     * @return an instance of a non playable a Wall which can be destroyed
     */
    Entity makeDestructibleWall(Pair<Float, Float> position);

    /**
     * @param position the initial position
     * @return an instance of a wall which cannot be destroyed
     */
    Entity makeIndestructibleWall(Pair<Float, Float> position);
}
