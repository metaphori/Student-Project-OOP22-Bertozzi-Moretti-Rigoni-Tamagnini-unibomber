package it.unibo.unibomber.game.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.unibomber.game.controller.api.World;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.model.api.Field;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.utilities.Pair;
/**
 * GameImpl class.
 */
public class GameImpl implements Game {

    private List<Entity> entities = new ArrayList<>();
    private List<Integer> keysPressedQueue = new ArrayList<>();
    private Field gameField = new FieldImpl(this);
    private int columns;
    private int rows;
    private World world;
    private final EntityFactoryImpl entityFactory = new EntityFactoryImpl(this);

    /**
     * GameImpl constructor.
     * @param world
     */
    public GameImpl(final World world) {
        this.world = world;
    }

    @Override
    public final List<Entity> getEntities() {
        return this.entities;
    }

    @Override
    public final <C extends Entity> void addEntity(final C entity) {
        entities.add(entity);
    }

    @Override
    public final boolean isContained(final int keyCode) {
        return keysPressedQueue.contains(keyCode);
    }

    @Override
    public final void addkeyPressed(final int keyCode) {
        keysPressedQueue.add(keyCode);
    }

    @Override
    public final void removeEntity(final Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void clearKeysPressed() {
        // keysPressedQueue.clear();
    }

    @Override
    public final Pair<Integer, Integer> getDimensions() {
        return new Pair<Integer, Integer>(rows, columns);
    }

    @Override
    public final Field getGameField() {
        return gameField;
    }

    @Override
    public final World getWorld() {
        return this.world;
    }

    @Override
    public final EntityFactoryImpl getFactory() {
        return entityFactory;
    }

}
