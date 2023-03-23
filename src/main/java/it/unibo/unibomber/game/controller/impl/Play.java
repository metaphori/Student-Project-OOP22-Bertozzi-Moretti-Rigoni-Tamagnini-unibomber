package it.unibo.unibomber.game.controller.impl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.ecs.api.Component;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.ExplodeComponent;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.view.PlayView;

/**
 * This class manage playable game.
 */
public class Play extends StateImpl implements KeyListener, GameLoop {
    private final Deque<Integer> keyQueue;
    private final Map<Integer, Boolean> firstFrameKey;
    private final Explosion explosion;
    private final PlayView view;
    private final WorldImpl world;
    /**
     * This method create the instance of all game parameters.
     * 
     * @param world
     */
    public Play(final WorldImpl world) {
        super();
        view = new PlayView(this);
        this.world = world;
        keyQueue = new LinkedList<>();
        firstFrameKey = new HashMap<>();
        explosion = new Explosion();
    }

    @Override
    public final void update() {
        // this.game.updateTimesUp();
        for (int i = 0; i < this.world.getGame().getEntities().size(); i++) {
            for (final Component c : this.world.getGame().getEntities().get(i).getComponents()) {
                c.update();
            }
        }
        this.world.getGame().getEntities().stream()
                .filter(e -> e.getType() == Type.BOMB)
                .filter(e -> e.getComponent(ExplodeComponent.class).get().isExploding())
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
        return keyQueue;
    }

    /**
     * @return first key with status.
     */
    public final Map<Integer, Boolean> getFirstFrameKeys() {
        return firstFrameKey;
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
}
