package it.unibo.unibomber.game.model.impl;

import java.util.Map;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.UploadRes;
import it.unibo.unibomber.utilities.Constants.Player;
import it.unibo.unibomber.utilities.Constants.UI.GameLoopConstants;
import it.unibo.unibomber.utilities.Constants.UI.Screen;
import it.unibo.unibomber.utilities.Constants.UI.SpritesMap;

/**
 * Play model.
 */
public class PlayImpl {
    private BufferedImage[][] animations;
    private final Map<Type, BufferedImage> sprites;
    private final Map<PowerUpType, BufferedImage> powerUpSprites;
    private final BufferedImage[] tile;

    /**
     * Play model controller.
     */
    public PlayImpl() {
        this.sprites = SpritesMap.getSpritespath();
        this.powerUpSprites = SpritesMap.getSpritesPowerupData();
        tile = new BufferedImage[2];
        loadSprites();
    }

    private void loadSprites() {
        animations = new BufferedImage[SpritesMap.ROW_PLAYER_SPRITES * 2
                + SpritesMap.ROW_BOMB_SPRITES + SpritesMap.ROW_WALL_SPRITES][SpritesMap.COL_PLAYER_SPRITES];
        for (int j = 0; j < Player.PLAYER_COUNTER; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = sprites.get(Type.PLAYABLE).getSubimage(i * Screen.PLAYER_DEFAULT,
                        j * Screen.PLAYER_DEFAULT, Screen.PLAYER_DEFAULT,
                        Screen.PLAYER_DEFAULT);
                animations[j + SpritesMap.ROW_PLAYER_SPRITES][i] = sprites.get(Type.BOT).getSubimage(
                        i * Screen.PLAYER_DEFAULT,
                        j * Screen.PLAYER_DEFAULT, Screen.PLAYER_DEFAULT,
                        Screen.PLAYER_DEFAULT);
            }
        }
        for (int i = 0; i < SpritesMap.COL_BOMB_SPRITES; i++) {
            animations[SpritesMap.getAnimationRow().get(Type.BOMB)][i] = sprites.get(Type.BOMB)
                    .getSubimage(i * Screen.BOMB_DEFAULT, 0, Screen.BOMB_DEFAULT, Screen.BOMB_DEFAULT);
        }
        for (int i = 0; i < SpritesMap.COL_WALL_SPRITES; i++) {
            animations[SpritesMap.getAnimationRow().get(Type.DESTRUCTIBLE_WALL)][i] = sprites
                    .get(Type.DESTRUCTIBLE_WALL)
                    .getSubimage(i * Screen.WALL_DEFAULT, 0, Screen.WALL_DEFAULT, Screen.WALL_DEFAULT);
        }
        for (int i = 0; i < 2; i++) {
            tile[i] = UploadRes.getSpriteAtlas("maps/map" + GameLoopConstants.getLEVEL() + "/grass.png")
                    .getSubimage(i * Screen.WALL_DEFAULT, 0, Screen.WALL_DEFAULT, Screen.WALL_DEFAULT);
        }
    }

    /**
     * @param i
     * @param j
     * @return animation of that position.
     */
    public BufferedImage getAnimation(final int i, final int j) {
        return animations[i][j];
    }

    /**
     * @param type
     * @return sprites of that type.
     */
    public BufferedImage getSprites(final Type type) {
        return sprites.get(type);
    }

    /**
     * @param type
     * @return sprites of that power up type.
     */
    public BufferedImage getPowerUpSprites(final PowerUpType type) {
        return powerUpSprites.get(type);
    }

    /**
     * @param type
     * @return get ties sprites.
     */
    public BufferedImage getTileSpritesType(final int type) {
        return tile[type];
    }
}
