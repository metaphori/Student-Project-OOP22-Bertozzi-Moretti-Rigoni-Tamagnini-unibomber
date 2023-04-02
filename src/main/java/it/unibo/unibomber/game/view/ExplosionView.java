package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Explosion;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.UploadRes;
import it.unibo.unibomber.utilities.Constants.UI.Screen;

import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap;
import static it.unibo.unibomber.utilities.Constants.Explode;

/**
 * Explosion View class.
 */
public final class ExplosionView implements GameLoop {
    private final Explosion controller;
    private BufferedImage[][] animations;
    private int frame;
    private int indexDirection;

    /**
     * Explosion view constructor.
     * 
     * @param controller
     */
    public ExplosionView(final Explosion controller) {
        loadSprites();
        indexDirection = 8;
        this.controller = controller;
    }

    private void loadSprites() {
        animations = new BufferedImage[SpritesMap.ROW_EXPLOSION_SPRITES][SpritesMap.COL_EXPLOSION_SPRITES];
        for (Integer j = 0; j < SpritesMap.ROW_EXPLOSION_SPRITES; j++) {
            for (Integer i = 0; i < animations[j].length; i++) {
                animations[j][i] = UploadRes.getSpriteAtlas("bomb/explosion.png").getSubimage(
                        i * Screen.EXPLOSION_DEFAULT, j * Screen.EXPLOSION_DEFAULT,
                        Screen.EXPLOSION_DEFAULT, Screen.EXPLOSION_DEFAULT);
            }
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(final Graphics g) {
        if (Gamestate.getGamestate() == Gamestate.PLAY) {
            final List<List<Pair<Integer, Integer>>> explosions = controller.getExplosionList();
            frame++;
            for (int i = 0; i < explosions.size(); i++) {
                if (!explosions.get(i).isEmpty()) {
                    final Pair<Integer, Integer> center = explosions.get(i).get(0);
                    for (final Pair<Integer, Integer> p1 : explosions.get(i)) {
                        g.drawImage(
                                getCorrectImage(Direction.getDistance(p1, center),
                                        Direction.extractDirecionBetweenTwo(center, p1).get(), i),
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

    private BufferedImage getCorrectImage(final int distance, final Direction dir, final int id) {
        final int d = distance != controller.getBombPower(id) ? 1 : 0;
        setDirectionIndex(dir);
        if (dir == Direction.CENTER) {
            return animations[frame % SpritesMap.ROW_EXPLOSION_SPRITES][indexDirection];
        } else {
            return animations[frame % SpritesMap.ROW_EXPLOSION_SPRITES][indexDirection + d];
        }
    }

    private void setDirectionIndex(final Direction dir) {
        switch (dir) {
            case DOWN:
                indexDirection = Explode.UP_EXPLOSION_ANIMATION_INDEX;
                break;
            case UP:
                indexDirection = Explode.DOWN_EXPLOSION_ANIMATION_INDEX;
                break;
            case RIGHT:
                indexDirection = Explode.RIGHT_EXPLOSION_ANIMATION_INDEX;
                break;
            case LEFT:
                indexDirection = Explode.LEFT_EXPLOSION_ANIMATION_INDEX;
                break;
            default:
                indexDirection = Explode.CENTER_EXPLOSION_ANIMATION_INDEX;
                break;
        }
    }
}
