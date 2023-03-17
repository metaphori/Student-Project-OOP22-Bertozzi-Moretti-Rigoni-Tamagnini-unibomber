package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Explosion;
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

    /**
     * Explosion view constructor.
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
        if (!controller.getExplosionList().isEmpty()) {
            for (Pair<Integer, Integer> p1 : controller.getExplosionList().get()) {
                g.drawImage(getCorrectImage(), 
                Math.round(p1.getY() * Game.TILES_SIZE),
                Math.round(p1.getX() * Game.TILES_SIZE),
                (int) (Game.TILES_DEFAULT * Game.SCALE),
                (int) (Game.TILES_DEFAULT * Game.SCALE),
                null);
            }
        }
    }
    private BufferedImage getCorrectImage() {
        return animations[0][0];
    }

}
