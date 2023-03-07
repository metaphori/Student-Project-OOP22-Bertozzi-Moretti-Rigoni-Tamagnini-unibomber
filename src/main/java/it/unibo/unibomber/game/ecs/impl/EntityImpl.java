package it.unibo.unibomber.game.ecs.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import it.unibo.unibomber.game.ecs.api.Component;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.utilities.Pair;

public class EntityImpl implements Entity{

    private final Type type;
    private final Set<Component> components = new HashSet<>();
    private Game Game;
    private Pair<Float,Float> position;
    private float speed = 1;

    public EntityImpl(final Game game,final Pair<Float,Float> position, final Type type) {
        this.Game=game;
        this.position = position;
        this.type = type;
    }

    @Override
    public Set<Component> getComponents() {
        return new HashSet<>(this.components);
    }

    @Override
    public <C extends Component> Optional<C> getComponent(Class<C> componentClass) {
        return this.components.stream()
                            .filter(componentClass::isInstance)
                            .map(componentClass::cast)
                            .findAny();
    }

    @Override
    public Pair<Float, Float> getPosition() {
        return this.position;    
    }

    @Override
    public void setPosition(Pair<Float, Float> position) {
        this.position=position;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public Game getGame() {
        return this.Game;
    }

    @Override
    public Entity addComponent(AbstractComponent component) {
        component.setEntity(this);
        this.components.add(component);
        return this;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public void addSpeed(PowerUpType powerUpType) {
        switch(powerUpType){
            case SPEEDUP: if(this.speed < 2) this.speed+=0.20; break;
            case SPEEDDOWN: if(this.speed > 1) this.speed-=0.20; break;
            default : break;
        }
    }

    @Override
    public void addPosition(Pair<Float, Float> position) {
            this.position = new Pair<>(this.position.getX()+position.getX()
                                      ,this.position.getY()+position.getY());
        }   

}
