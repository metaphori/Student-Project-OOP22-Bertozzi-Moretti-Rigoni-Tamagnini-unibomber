package it.unibo.unibomber.game.ecs.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import it.unibo.unibomber.game.ecs.api.Component;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.utilities.Pair;

/**
 * This method creates and manages the entity.
 */
public class EntityImpl implements Entity {

    private final Type type;
    private final Set<Component> components = new HashSet<>();
    private final Game game;
    private Pair<Float, Float> position;
    private float speed = 1;

    /**
     * This method create a new Entity.
     * 
     * @param game
     * @param position
     * @param type
     */
    public EntityImpl(final Game game, final Pair<Float, Float> position, final Type type) {
        this.game = game;
        this.position = position;
        this.type = type;
    }

    @Override
    public final Set<Component> getComponents() {
        return new HashSet<>(this.components);
    }

    @Override
    public final <C extends Component> Optional<C> getComponent(final Class<C> componentClass) {
        return this.components.stream()
                .filter(componentClass::isInstance)
                .map(componentClass::cast)
                .findAny();
    }

    @Override
    public final Pair<Float, Float> getPosition() {
        return this.position;
    }

    @Override
    public final void setPosition(final Pair<Float, Float> position) {
        this.position = position;
    }

    @Override
    public final Type getType() {
        return this.type;
    }

    @Override
    public final Game getGame() {
        return this.game;
    }

    @Override
    public final Entity addComponent(final AbstractComponent component) {
        component.setEntity(this);
        this.components.add(component);
        return this;
    }

    @Override
    public final float getSpeed() {
        return this.speed;
    }

    @Override
    public final void addSpeed(final float speedValue) {
        this.speed += speedValue;
    }

    @Override
    public final void addPosition(final Pair<Float, Float> position) {
        this.position = new Pair<>(this.position.getX() + position.getX(), this.position.getY() + position.getY());
    }

}
