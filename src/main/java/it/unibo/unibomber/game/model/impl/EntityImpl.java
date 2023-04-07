package it.unibo.unibomber.game.model.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.unibo.unibomber.game.ecs.api.Component;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Pair;

/**
 * This method creates and manages the entity.
 */
public class EntityImpl implements Entity {

    private final Type type;
    private final Set<Component> components;
    private final List<Game> game;
    private Pair<Float, Float> position;
    private float speed;

    /**
     * This method create a new Entity.
     * 
     * @param game     the game playing
     * @param position the position of the entity
     * @param type     the type of the entity
     */
    protected EntityImpl(final Game game, final Pair<Float, Float> position, final Type type) {
        this.game = new ArrayList<>();
        this.game.add(game);
        this.type = type;
        this.position = new Pair<>(position.getX(), position.getY());
        this.components = new HashSet<>();
        speed = Constants.Entity.BASE_SPEED;
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
        return new Pair<>(this.position.getX(), this.position.getY());
    }

    @Override
    public final void setPosition(final Pair<Float, Float> position) {
        this.position = new Pair<>(position.getX(), position.getY());
    }

    @Override
    public final Type getType() {
        return this.type;
    }

    @Override
    public final Game getGame() {
        return this.game.get(0);
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
