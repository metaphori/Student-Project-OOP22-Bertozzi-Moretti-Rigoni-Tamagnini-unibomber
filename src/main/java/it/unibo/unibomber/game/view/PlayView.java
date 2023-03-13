package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.util.Map;
import java.util.stream.Collectors;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Play;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.CollisionComponent;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpComponent;
import it.unibo.unibomber.utilities.Constants;
import static it.unibo.unibomber.utilities.Constants.Player.STANDING;
import static it.unibo.unibomber.utilities.Constants.Player.WALKING;
import static it.unibo.unibomber.utilities.Constants.UI.Game.TILES_SIZE;
import static it.unibo.unibomber.utilities.Constants.UI.Game.TILES_DEFAULT;
import static it.unibo.unibomber.utilities.Constants.UI.Game.SCALE;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap.COL_PLAYER_SPRITES;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap.ROW_PLAYER_SPRITES;

/**
 * Draw playing view statement.
 */
public final class PlayView implements GameLoop {

    private final Play controller;
    private Integer animationIndex;
    private BufferedImage[][] animations;
    private final Map<Type, BufferedImage> sprites;
    private final Map<PowerUpType, BufferedImage> powerUpSprites;
    private Integer playerAction = STANDING;
    private Integer indexDir;

    /**
     * 
     * @param controller
     */
    public PlayView(final Play controller) {
        this.sprites = Constants.UI.SpritesMap.SPRITESPATH;
        this.powerUpSprites = Constants.UI.SpritesMap.SPRITESPOWERUPPATH;
        this.controller = controller;
        indexDir = 0;
        loadSprites();
        for (Integer j = 0; j < animations.length; j++) {
            for (Integer i = 0; i < animations[j].length; i++) {
                animations[j][i] = sprites.get(Type.PLAYABLE).getSubimage(i * TILES_SIZE, j * TILES_SIZE,
                        TILES_SIZE, TILES_SIZE);
            }
        }
    }

    private void loadSprites() {
        animations = new BufferedImage[ROW_PLAYER_SPRITES][COL_PLAYER_SPRITES];
    }

    private void updateAnimationFrame() {
        animationIndex = controller.getEntities().stream()
                .filter(x -> x.getType() == Type.PLAYABLE)
                .collect(Collectors.toList())
                .get(0)
                .getComponent(MovementComponent.class)
                .get()
                .getPassedFrames();
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
        if (action == STANDING && playerAction != STANDING) {
            final Integer animation = animationIndex % Constants.Player.getSpriteAmount(playerAction) + indexDir;
            final Integer basicDir = (int) (animation / Constants.Player.getSpriteAmount(playerAction));
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
            controller.getEntities().get(i).getComponent(CollisionComponent.class).get().drawHitbox(g);
            drawImage(g, controller.getEntities().get(i));
        }
    }

    private void drawImage(Graphics g, Entity entity) {
        BufferedImage image = getCorrectImage(entity);
        g.drawImage(image,
                Math.round(entity.getPosition()
                        .getX() * TILES_DEFAULT * SCALE),
                Math.round(entity.getPosition()
                        .getY() * TILES_DEFAULT * SCALE),
                (int) (TILES_DEFAULT * SCALE),
                (int) (TILES_DEFAULT * SCALE),
                null);
    }

    private BufferedImage getCorrectImage(Entity entity) {
        if (entity.getType() == Type.PLAYABLE) {
            changePlayerAction(WALKING);
            final var movementComponent = entity.getComponent(MovementComponent.class).get();
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
                case CENTER:
                    indexDir = indexDir % Constants.Player.getSpriteAmount(playerAction);
                    break;
            }
            if (!movementComponent.hasMoved()) {
                changePlayerAction(STANDING);
            }
            return animations[playerAction][animationIndex % Constants.Player.getSpriteAmount(playerAction) + indexDir];
        } else if (entity.getType() == Type.POWERUP) {
            return powerUpSprites.get(entity.getComponent(PowerUpComponent.class).get().getPowerUpType());
        } else
            return sprites.get(entity.getType());

    }
}
