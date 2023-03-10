package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.util.stream.Collectors;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Play;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpComponent;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.UploadRes;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap.SPRITESPATH;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap.SPRITESPOWERUPPATH;
import static it.unibo.unibomber.utilities.Constants.Player.STANDING;
import static it.unibo.unibomber.utilities.Constants.Player.WALKING;
import static it.unibo.unibomber.utilities.Constants.UI.Game.TILES_SIZE;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap.COL_PLAYER_SPRITES;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap.ROW_PLAYER_SPRITES;

/**
 * Draw playing view statement.
 */
public final class PlayView implements GameLoop {

    private Play controller;
    private Integer animationIndex;
    private BufferedImage[][] animations;
    private Integer playerAction = STANDING;
    private static Integer indexDir;

    /**
     * 
     * @param controller
     */
    public PlayView(final Play controller) {
        this.controller = controller;
        indexDir = 0;
        loadSprites();
        /*
         * animations[0][1] = colorImage(animations[0][1]);
         * animations[0][2] = colorImage(animations[0][2]);
         * animations[0][3] = colorImage(animations[0][3]);
         */
    }

    private void loadSprites() {
        animations = new BufferedImage[ROW_PLAYER_SPRITES][COL_PLAYER_SPRITES];
        for (Integer j = 0; j < animations.length; j++) {
            for (Integer i = 0; i < animations[j].length; i++) {
                animations[j][i] = UploadRes.getSpriteAtlas(SPRITESPATH.get(Type.PLAYABLE))
                        .getSubimage(i * TILES_SIZE, j * TILES_SIZE, TILES_SIZE, TILES_SIZE);
            }
        }
    }

    private void updateAnimationFrame() {
        animationIndex = controller.getEntities().stream()
                .filter(x -> x.getType() == Type.PLAYABLE)
                .collect(Collectors.toList())
                .get(0)
                .getComponent(MovementComponent.class)
                .get()
                .getPassedFram();
    }

    @Override
    public void update() {
        updateAnimationFrame();
    }

    /**
     * change the player action for sprites.
     * 
     * @param action
     */
    public void changePlayerAction(final Integer action) {
        if (action == STANDING) {
            Integer animation = (animationIndex % Constants.Player.getSpriteAmount(playerAction)) + indexDir;
            Integer basicDir = (int) (animation / Constants.Player.getSpriteAmount(playerAction));
            indexDir = basicDir * Constants.Player.getSpriteAmount(action);
        }
        playerAction = action;
    }

    /*
     * private static BufferedImage colorImage(BufferedImage image) {
     * int width = image.getWidth();
     * int height = image.getHeight();
     * WritableRaster raster = image.getRaster();
     * for (int xx = 0; xx < width; xx++) {
     * for (int yy = 0; yy < height; yy++) {
     * int[] pixels = raster.getPixel(xx, yy, (int[]) null);
     * if((pixels[0] >= 180 && pixels[0] <= 255)
     * && (pixels[1] >= 150 && pixels[1] <= 255)
     * && (pixels[2] >= 150 && pixels[2] <= 255)){
     * pixels[0] = 0;
     * pixels[1] = 255;
     * pixels[2] = 255;
     * raster.setPixel(xx, yy, pixels);
     * }
     * }
     * }
     * return image;
     * }
     */
    @Override
    public void draw(final Graphics g) {
        for (Integer i = 0; i < controller.getEntities().size(); i++) {
            if (controller.getEntities().get(i).getType() != Type.PLAYABLE
                    && controller.getEntities().get(i).getType() != Type.POWERUP) {
                g.drawImage(UploadRes.getSpriteAtlas(SPRITESPATH.get(controller.getEntities().get(i).getType())),
                        Math.round(controller.getEntities()
                                .get(i)
                                .getPosition()
                                .getX() * Constants.UI.Game.TILES_DEFAULT),
                        Math.round(controller.getEntities()
                                .get(i)
                                .getPosition()
                                .getY() * Constants.UI.Game.TILES_DEFAULT),
                        (int) (Constants.UI.Game.TILES_SIZE),
                        (int) (Constants.UI.Game.TILES_SIZE),
                        null);
            } else if (controller.getEntities().get(i).getType() == Type.POWERUP) {
                g.drawImage(UploadRes.getSpriteAtlas(SPRITESPOWERUPPATH.get(controller
                        .getEntities()
                        .get(i)
                        .getComponent(PowerUpComponent.class)
                        .get().getPowerUpType())),
                        Math.round(controller.getEntities()
                                .get(i)
                                .getPosition()
                                .getX() * Constants.UI.Game.TILES_DEFAULT),
                        Math.round(controller.getEntities()
                                .get(i)
                                .getPosition()
                                .getY() * Constants.UI.Game.TILES_DEFAULT),
                        (int) (Constants.UI.Game.TILES_SIZE),
                        (int) (Constants.UI.Game.TILES_SIZE),
                        null);
            } else if (controller.getEntities().get(i).getType() == Type.PLAYABLE) {
                changePlayerAction(WALKING);
                var movementComponent = controller.getEntities().get(i).getComponent(MovementComponent.class).get();
                switch (movementComponent.getDirection()) {
                    case DOWN:
                        indexDir = 0;
                        break;
                    case LEFT:
                        indexDir = Constants.Player.getSpriteAmount(playerAction) * 1;
                        break;
                    case RIGHT:
                        indexDir = Constants.Player.getSpriteAmount(playerAction) * 2;
                        break;
                    case UP:
                        indexDir = Constants.Player.getSpriteAmount(playerAction) * 3;
                        break;
                    default:
                    case CENTER:
                        indexDir = indexDir % Constants.Player.getSpriteAmount(playerAction);
                        break;
                }
                if(!movementComponent.hasMoved()) {
                    changePlayerAction(STANDING);
                }
                g.drawImage(
                        (animations[playerAction][(animationIndex % Constants.Player.getSpriteAmount(playerAction))
                                + indexDir]),
                        Math.round(controller.getEntities()
                        .get(i)
                        .getPosition()
                        .getX() * Constants.UI.Game.TILES_DEFAULT),
                        Math.round(controller.getEntities()
                        .get(i)
                        .getPosition()
                        .getY() * Constants.UI.Game.TILES_DEFAULT),
                        (int) (Constants.UI.Game.TILES_DEFAULT * (Constants.UI.Game.SCALE + 0.5f)),
                        (int) (Constants.UI.Game.TILES_DEFAULT * (Constants.UI.Game.SCALE + 0.5f)),
                        null);
            }
        }
    }
}
