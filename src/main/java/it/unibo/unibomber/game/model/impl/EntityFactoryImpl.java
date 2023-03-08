package it.unibo.unibomber.game.model.impl;

import java.util.List;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.BombPlaceComponent;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;
import it.unibo.unibomber.game.ecs.impl.EntityImpl;
import it.unibo.unibomber.game.ecs.impl.PowerUpComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpListComponent;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.utilities.Pair;

public class EntityFactoryImpl implements EntityFactory{

    private final  Game game;

    public EntityFactoryImpl(Game game){
        this.game = game;
    }

    @Override
    public Entity makePowerUp(Pair<Float, Float> pos, PowerUpType powerUpType) {
        return new EntityImpl(game, pos, Type.POWERUP)
            .addComponent(new PowerUpComponent(powerUpType))
            .addComponent(new DestroyComponent());
    }

    @Override
    public Entity makeBomber(Pair<Float, Float> position, Type type) {
        return new EntityImpl(game,position,type)
            //TODO add other component
            .addComponent(new PowerUpListComponent(1, 1, List.of()))
            .addComponent(new BombPlaceComponent())
            .addComponent(new DestroyComponent());  
    }

    @Override
    public Entity makePlayable(Pair<Float, Float> position) {
        throw new UnsupportedOperationException("Unimplemented method 'makePlayable'");
    }

    @Override
    public Entity makeBot(Pair<Float, Float> position, int AI_difficulty) {
        throw new UnsupportedOperationException("Unimplemented method 'makeBot'");
    }

    @Override
    public Entity makeBomb(Entity placer) {
        return new EntityImpl(game, placer.getPosition(), Type.BOMB)
                    //TODO add other component
                   .addComponent(new PowerUpListComponent(placer))
                   .addComponent(new DestroyComponent());
    }

    @Override
    public Entity makeDestructibleWall(Pair<Float, Float> position) {
        return new EntityImpl(game, position, Type.DESTRUCTIBLE_WALL)
                    .addComponent(new DestroyComponent());
    }

    @Override
    public Entity makeIndestructibleWall(Pair<Float, Float> position) {
        return new EntityImpl(game, position, Type.INDESTRUCTIBLE_WALL);
    }
    
}
