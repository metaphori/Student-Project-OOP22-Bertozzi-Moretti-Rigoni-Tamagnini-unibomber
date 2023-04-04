package it.unibo.unibomber.game.controller.impl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.api.SystemManager;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;
import it.unibo.unibomber.game.ecs.impl.ExplodeComponent;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.impl.PlayImpl;
import it.unibo.unibomber.game.view.PlayView;

/**
 * This class manage playable game.
 */
public class Play extends StateImpl implements KeyListener, GameLoop {
    private final Deque<Integer> keyQueue;
    private final Map<Integer, Boolean> firstFrameKey;
    private final Explosion explosion;
    private final PlayView view;
    private final PlayImpl model;
    private final WorldImpl world;
    private final SystemManager systems;

    /**
     * This method create the instance of all game parameters.
     * 
     * @param world
     */
    public Play(final WorldImpl world) {
        super();
        view = new PlayView(this);
        model = new PlayImpl();
        this.world = world;
        keyQueue = new LinkedList<>();
        firstFrameKey = new HashMap<>();
        explosion = new Explosion();
        systems = new SystemManagerImpl();
    }

    @Override
    public final void update() {
        explosion.resetEntity();
        for (int i = 0; i < this.world.getGame().getEntities().size(); i++) {
            systems.update(this.world.getGame().getEntities().get(i));
        }
        this.world.getGame().getEntities().stream()
                .filter(e -> e.getType().equals(Type.BOMB))
                .filter(e -> e.getComponent(ExplodeComponent.class).get().isExploding())
                .filter(e -> !e.getComponent(DestroyComponent.class).get().isDestroyed())
                .forEach((e) -> {
                    explosion.setEntityExploding(e);
                });
        explosion.update();
        this.world.getGame().getGameField().updateField();
        view.update();
        updateKeys();
    }

    /**
     * If those keys are still here they were not presed on the first frame.
     */
    private void updateKeys() {
        for (final Integer keyCode : firstFrameKey.keySet()) {
            firstFrameKey.put(keyCode, false);
        }

    }

    @Override
    public final void draw(final Graphics g) {
        view.draw(g);
    }

    @Override
    public final void keyReleased(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Gamestate.setGameState(Gamestate.PAUSE);
            this.world.pauseTimer();
            this.world.getEndGame().loadButtons();
        } else {
            if (keyQueue.contains(e.getKeyCode())) {
                keyQueue.remove(e.getKeyCode());
            }
            if (firstFrameKey.containsKey(e.getKeyCode())) {
                firstFrameKey.remove(e.getKeyCode());
            }
        }
    }

    @Override
    public final void keyTyped(final KeyEvent e) {
    }

    @Override
    public final void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_ESCAPE) {
            if (!keyQueue.contains(e.getKeyCode())) {
                keyQueue.addFirst(e.getKeyCode());
            }
            if (!firstFrameKey.containsKey(e.getKeyCode())) {
                firstFrameKey.put(e.getKeyCode(), true);
            }
        }
    }

    /**
     * @return list of pressed key.
     */
    public final Deque<Integer> getKeys() {
        return new LinkedList<>(keyQueue);
    }

    /**
     * @return first key with status.
     */
    public final Map<Integer, Boolean> getFirstFrameKeys() {
        return new HashMap<>(firstFrameKey);
    }

    /**
     * @return all entity in game.
     */
    public final List<Entity> getEntities() {
        return this.world.getGame().getEntities();
    }

    /**
     * @return explosion controller.
     */
    public Explosion getExplosionController() {
        return explosion;
    }

    /**
     * @param i
     * @param j
     * @return animation of that position.
     */
    public BufferedImage getAnimation(final int i, final int j) {
        return model.getAnimation(i, j);
    }

    /**
     * @param type
     * @return sprites of that type.
     */
    public BufferedImage getSprites(final Type type) {
        return model.getSprites(type);
    }

    /**
     * @param type
     * @return sprites of that power up type.
     */
    public BufferedImage getPowerUpSprites(final PowerUpType type) {
        return model.getPowerUpSprites(type);
    }

    /**
     * @param type
     * @return get ties sprites.
     */
    public BufferedImage getTileSpritesType(final int type) {
        return model.getTileSpritesType(type);
    }
}
