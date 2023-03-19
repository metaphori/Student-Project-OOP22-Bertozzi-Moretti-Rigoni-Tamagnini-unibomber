package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Explosion;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.UploadRes;
import it.unibo.unibomber.utilities.Constants.UI.Game;

import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap;

/**
 * Explosion View class.
 */
public final class ExplosionView implements GameLoop {
    private Explosion controller;
    private BufferedImage[][] animations;
    private int frame;
    /**
     * Explosion view constructor.
     * 
     * @param controller
     */
    public ExplosionView(final Explosion controller) {
        loadSprites();
        this.controller = controller;
    }

    private void loadSprites() {
        animations = new BufferedImage[SpritesMap.ROW_EXPLOSION_SPRITES][SpritesMap.COL_EXPLOSION_SPRITES];
        for (Integer j = 0; j < SpritesMap.ROW_EXPLOSION_SPRITES; j++) {
            for (Integer i = 0; i < animations[j].length; i++) {
                animations[j][i] = UploadRes.getSpriteAtlas("explosion.png").getSubimage(
                        i * Game.EXPLOSION_DEFAULT, j * Game.EXPLOSION_DEFAULT,
                        Game.EXPLOSION_DEFAULT, Game.EXPLOSION_DEFAULT);
            }
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(final Graphics g) {
        frame++;
        if (!controller.getExplosionList().isEmpty()) {
            Pair<Integer, Integer> center = controller.getExplosionList().get(0);
            for (Pair<Integer, Integer> p1 : controller.getExplosionList()) {
                g.drawImage(
                        getCorrectImage(Direction.getDistance(p1, center),
                                Direction.extractDirecionBetweenTwo(center, p1).get()),
                        Math.round(p1.getY() * Game.TILES_SIZE),
                        Math.round(p1.getX() * Game.TILES_SIZE),
                        (int) (Game.TILES_DEFAULT * Game.SCALE),
                        (int) (Game.TILES_DEFAULT * Game.SCALE),
                        null);
            }
        }
    }

    private BufferedImage getCorrectImage(int distance, Direction dir) {
        distance = distance != controller.getBombPower() ? 1 : 0;
        switch (dir) {
            case CENTER:
                return animations[frame % SpritesMap.ROW_EXPLOSION_SPRITES][8];
            case DOWN:
                return animations[frame % SpritesMap.ROW_EXPLOSION_SPRITES][6 + distance];
            case UP:
                return animations[frame % SpritesMap.ROW_EXPLOSION_SPRITES][2 + distance];
            case RIGHT:
                return animations[frame % SpritesMap.ROW_EXPLOSION_SPRITES][4 + distance];
            case LEFT:
                return animations[frame % SpritesMap.ROW_EXPLOSION_SPRITES][0 + distance];
            default:
                return animations[frame % SpritesMap.ROW_EXPLOSION_SPRITES][8];
        }
    }

}
