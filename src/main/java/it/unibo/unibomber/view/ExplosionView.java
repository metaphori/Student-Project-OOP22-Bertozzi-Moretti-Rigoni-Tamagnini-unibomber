package it.unibo.unibomber.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import it.unibo.unibomber.controller.api.GameLoop;
import it.unibo.unibomber.controller.impl.Explosion;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.impl.ExplodeComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpListComponent;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.Constants.UI.Screen;

import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap;

/**
 * Explosion View class.
 */
public final class ExplosionView implements GameLoop {
    private final Explosion controller;

    /**
     * Explosion view constructor.
     * 
     * @param controller Explosion controller.
     */
    public ExplosionView(final Explosion controller) {
        this.controller = new Explosion(controller);
    }

    /**
     * @param controller explosion controller.
     */
    public void updateList(final Explosion controller) {
        this.controller.setExplodeList(controller);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(final Graphics g) {
        if (Gamestate.getGamestate() == Gamestate.PLAY && !controller.getExplode().isEmpty()) {
            for (final Entity entity : controller.getExplode()) {
                final List<Pair<Integer, Integer>> explosions = entity.getComponent(ExplodeComponent.class).get()
                        .getExplosions();
                if (!explosions.isEmpty()) {
                    final Pair<Integer, Integer> center = explosions.get(0);
                    for (final Pair<Integer, Integer> p1 : explosions) {
                        g.drawImage(
                                getCorrectImage(
                                        entity.getComponent(ExplodeComponent.class).get().getExpiringFrames(),
                                        Direction.getDistance(p1, center),
                                        Direction.extractDirecionBetweenTwo(center, p1).get(), entity),
                                p1.getY() * Screen.getTilesSize(),
                                p1.getX() * Screen.getTilesSize(),
                                (int) (Screen.getTilesDefault() * Screen.SCALE),
                                (int) (Screen.getTilesDefault() * Screen.SCALE),
                                null);
                    }
                }
            }
        }
    }

    private BufferedImage getCorrectImage(final int frame, final int distance, final Direction dir,
            final Entity entity) {
        final int d = distance != entity.getComponent(PowerUpListComponent.class).get().getBombFire() ? 1 : 0;
        controller.setDirectionIndex(dir);
        if (dir == Direction.CENTER) {
            return controller.getAnimations(frame % SpritesMap.ROW_EXPLOSION_SPRITES, controller.getIndexDirection());
        } else {
            return controller.getAnimations(frame % SpritesMap.ROW_EXPLOSION_SPRITES,
                    controller.getIndexDirection() + d);
        }
    }

}
