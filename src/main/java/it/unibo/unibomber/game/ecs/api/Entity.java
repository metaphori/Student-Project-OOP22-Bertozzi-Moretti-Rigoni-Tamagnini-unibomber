package it.unibo.unibomber.game.ecs.api;

import java.util.Optional;
import java.util.Set;

import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.impl.AbstractComponent;
import it.unibo.unibomber.utilities.Pair;

/**
 * This interface represents the skeleton of the entity.
 */
public interface Entity {

    /**
     * @return list of components of the entity
     */
    Set<Component> getComponents();

    /**
     * @param <C>            type of the component requested
     * @param componentClass the class of the requested component
     * @return An optional containing the requested Component, an optional otherwise
     */
    <C extends Component> Optional<C> getComponent(Class<C> componentClass);

    /**
     * @return the position of the entity on the field
     */
    Pair<Float, Float> getPosition();

    /**
     * @param position the new position of the entity
     */
    void setPosition(Pair<Float, Float> position);

    /**
     * @param position the new position to be added to entity
     */
    void addPosition(Pair<Float, Float> position);

    /**
     * @param component the component to be added
     * @return the entity with the component added
     */
    Entity addComponent(AbstractComponent component);

    /**
     * @return the type of the entity
     */
    Type getType();

    /**
     * @return the Game in which the entity is placed
     */
    Game getGame();

    /**
     * @return speed of entity
     */
    float getSpeed();

    /**
     * @param speedValue increment speed of entity.
     */
    void addSpeed(float speedValue);
}
