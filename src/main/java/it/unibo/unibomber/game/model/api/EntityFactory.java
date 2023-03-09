package it.unibo.unibomber.game.model.api;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Pair;

public interface EntityFactory {

    /**
     * @param pos         the position where the PowerUp will be placed
     * @param powerUpType the Type of the PowerUp
     * @return the PowerUp described
     */
    Entity makePowerUp(Pair<Float, Float> pos, PowerUpType powerUpType);

    /**
     * @param coordinates the initial position
     * @param type        whether it is a playable character or a bot
     * @return an instance of a Bomber
     */
    Entity makeBomber(Pair<Float, Float> position, Type type);

    /**
     * @param coordinates the initial position
     * @return an instance of a Playable Bomber
     */
    Entity makePlayable(Pair<Float, Float> position);

    /**
     * @param coordinates the initial position
     * @return an instance of a non playable Bomber
     */
    Entity makeBot(Pair<Float, Float> position, int AI_difficulty);

    /**
     * @param placer the reference to the entity which placed the bomb
     *               in normal condition being the bomber
     * @return an instance of a non an instance of a non playable Bomb
     */
    Entity makeBomb(Entity placer);

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