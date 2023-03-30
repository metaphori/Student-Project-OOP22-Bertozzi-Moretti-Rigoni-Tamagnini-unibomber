package it.unibo.unibomber.game.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.unibomber.game.controller.api.World;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.AIComponent;
import it.unibo.unibomber.game.model.api.Field;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.utilities.Pair;

/**
 * GameImpl class.
 */
public class GameImpl implements Game {

    private final List<Entity> entities = new ArrayList<>();
    private final List<Integer> keysPressedQueue = new ArrayList<>();
    private final Field gameField = new FieldImpl(this);
    // private final TimesUpImpl timesUp = new TimesUpImpl(this);
    private final int columns;
    private final int rows;
    private final World world;
    private final EntityFactoryImpl entityFactory = new EntityFactoryImpl(this);

    /**
     * GameImpl constructor.
     * 
     * @param world
     * @param rows
     * @param columns
     */
    public GameImpl(final World world, final int rows, final int columns) {
        this.world = world;
        this.rows = rows;
        this.columns = columns;
        // this.timesUp.start();
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

    @Override
    public final void updateTimesUp() {
        // this.timesUp.update();
    }

    @Override
    public final void updateGameState() {
        final int playersLive = (int) this.entities.stream()
                .filter(e -> e.getType() == Type.BOMBER)
                .filter(e -> e.getComponent(AIComponent.class).isEmpty())
                .count();
        final int botLive = (int) this.entities.stream()
                .filter(e -> e.getType() == Type.BOMBER)
                .filter(e -> e.getComponent(AIComponent.class).isPresent())
                .count();
        if (botLive == 0) {
            Gamestate.setGameState(Gamestate.WIN);
            this.world.getEndGame().loadButtons();
        } else if (playersLive == 0) {
            Gamestate.setGameState(Gamestate.LOSE);
            this.world.getEndGame().loadButtons();
        }
    }

}
