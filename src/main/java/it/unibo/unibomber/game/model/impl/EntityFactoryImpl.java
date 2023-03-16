package it.unibo.unibomber.game.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.AIComponent;
import it.unibo.unibomber.game.ecs.impl.CollisionComponent;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;
import it.unibo.unibomber.game.ecs.impl.EntityImpl;
import it.unibo.unibomber.game.ecs.impl.ExplodeComponent;
import it.unibo.unibomber.game.ecs.impl.InputComponent;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.ecs.impl.BombPlaceComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpHandlerComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpListComponent;
import it.unibo.unibomber.game.ecs.impl.SlidingComponent;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.utilities.Pair;

/**
 * This class implements the interface that manages the creation of different
 * entities.
 */
public class EntityFactoryImpl implements EntityFactory {

    private final Game game;

    /**
     * This method takes the game for create entity.
     * 
     * @param game
     */
    public EntityFactoryImpl(final Game game) {
        this.game = game;
    }

    @Override
    public final Entity makePowerUp(final Pair<Float, Float> position, final PowerUpType powerUpType) {
        return new EntityImpl(game, position, Type.POWERUP)
                .addComponent(new PowerUpComponent(powerUpType))
                .addComponent(new CollisionComponent(true, true, Math.round(position.getX()), Math.round(position.getY())))
                .addComponent(new DestroyComponent());
    }

    @Override
    public final Entity makeBomber(final Pair<Float, Float> position, final Type type) {
        return new EntityImpl(game, position, type)
                .addComponent(new MovementComponent())
                .addComponent(new CollisionComponent(false, true, Math.round(position.getX()), Math.round(position.getY())))
                .addComponent(new BombPlaceComponent())
                .addComponent(new PowerUpHandlerComponent(1, 1, new ArrayList<>()))
                .addComponent(new DestroyComponent());
    }

    @Override
    public final Entity makePlayable(final Pair<Float, Float> position) {
        return makeBomber(position, Type.PLAYABLE)
                .addComponent(new InputComponent());
    }

    @Override
    public final Entity makeBot(final Pair<Float, Float> position, final int difficultyAI) {
        return makeBomber(position, Type.BOT);
                //.addComponent(new AIComponent());
    }

    @Override
    public final Entity makeBomb(final Entity placer, final Pair<Float, Float> position) {
        return new EntityImpl(game, position, Type.BOMB)
                .addComponent(new MovementComponent())
                .addComponent(new SlidingComponent())
                .addComponent(new CollisionComponent(true, true, Math.round(position.getX()), Math.round(position.getY())))
                .addComponent(new ExplodeComponent(placer))
                .addComponent(new PowerUpListComponent(placer))
                .addComponent(new DestroyComponent());
    }

    @Override
    public final Entity makeDestructibleWall(final Pair<Float, Float> position) {
        final Random rnd = new Random();
        if (rnd.nextInt(4) % 2 == 0) {
            return new EntityImpl(game, position, Type.DESTRUCTIBLE_WALL)
                .addComponent(new CollisionComponent(true, false, Math.round(position.getX()), Math.round(position.getY())))
                .addComponent(new PowerUpListComponent(0, 0, List.of(PowerUpType.getRandomPowerUp())))
                .addComponent(new DestroyComponent());
        }
        return new EntityImpl(game, position, Type.DESTRUCTIBLE_WALL)
                .addComponent(new CollisionComponent(true, false, Math.round(position.getX()), Math.round(position.getY())))
                .addComponent(new PowerUpListComponent(0, 0, List.of()))
                .addComponent(new DestroyComponent());
    }

    @Override
    public final Entity makeIndestructibleWall(final Pair<Float, Float> position) {
        return new EntityImpl(game, position, Type.INDESTRUCTIBLE_WALL)
                .addComponent(new CollisionComponent(true, false, Math.round(position.getX()), Math.round(position.getY())));
    }

}
