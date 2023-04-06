package it.unibo.unibomber.game.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.unibomber.game.controller.api.World;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.AIComponent;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.api.Field;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.utilities.Pair;

/**
 * GameImpl class.
 */
public final class GameImpl implements Game {

    private final List<Entity> entities;
    private final List<Integer> keysPressedQueue;
    private final Field gameField;
    private final TimesUpImpl timesUp;
    private final int columns;
    private final int rows;
    private final World world;
    private final EntityFactory entityFactory;

    /**
     * GameImpl constructor.
     * 
     * @param world   world.
     * @param rows    rows number.
     * @param columns cols number.
     */
    public GameImpl(final World world, final int rows, final int columns) {
        this.world = world;
        this.rows = rows;
        this.columns = columns;
        this.timesUp = new TimesUpImpl(this);
        this.timesUp.start();
        this.keysPressedQueue = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.gameField = new FieldImpl(this);
        this.entityFactory = new EntityFactoryImpl(this);
    }

    /**
     * GameImpl copy constructor.
     * 
     * @param game game.
     */
    public GameImpl(final Game game) {
        this.world = game.getWorld();
        this.rows = game.getDimensions().getX();
        this.columns = game.getDimensions().getY();
        this.timesUp = null;
        this.keysPressedQueue = new ArrayList<>(game.getPressedKeys());
        this.entities = game.getEntities();
        this.gameField = game.getGameField();
        this.entityFactory = game.getFactory();
    }

    @Override
    public List<Entity> getEntities() {
        return this.entities;
    }

    @Override
    public <C extends Entity> void addEntity(final C entity) {
        entities.add(entity);
    }

    @Override
    public boolean isContained(final int keyCode) {
        return keysPressedQueue.contains(keyCode);
    }

    @Override
    public void addkeyPressed(final int keyCode) {
        keysPressedQueue.add(keyCode);
    }

    @Override
    public void removeEntity(final Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void clearKeysPressed() {
        // keysPressedQueue.clear();
    }

    @Override
    public Pair<Integer, Integer> getDimensions() {
        return new Pair<Integer, Integer>(rows, columns);
    }

    @Override
    public Field getGameField() {
        return gameField;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public EntityFactory getFactory() {
        return entityFactory;
    }

    @Override
    public void updateTimesUp() {
        this.timesUp.update();
    }

    @Override
    public void updateGameState() {
        final int playersLive = (int) this.entities.stream()
                .filter(e -> e.getType().equals(Type.BOMBER))
                .filter(e -> e.getComponent(AIComponent.class).isEmpty())
                .count();
        final int botLive = (int) this.entities.stream()
                .filter(e -> e.getType().equals(Type.BOMBER))
                .filter(e -> e.getComponent(AIComponent.class).isPresent())
                .count();
        if (botLive == 0) {
            Gamestate.setGameState(Gamestate.WIN);
            if (this.world != null) {
                this.world.getEndGame().loadButtons();
                this.world.stopTimer();
            }
        } else if (playersLive == 0) {
            Gamestate.setGameState(Gamestate.LOSE);
            if (this.world != null) {
                this.world.getEndGame().loadButtons();
                this.world.stopTimer();
            }
        }
    }

    @Override
    public List<Integer> getPressedKeys() {
        return new ArrayList<>(this.keysPressedQueue);
    }

}
