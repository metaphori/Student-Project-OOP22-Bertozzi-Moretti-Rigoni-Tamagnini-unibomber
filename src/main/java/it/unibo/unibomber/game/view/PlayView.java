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
import static it.unibo.unibomber.utilities.Constants.UI.Game;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap.COL_PLAYER_SPRITES;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap.ROW_PLAYER_SPRITES;
import static it.unibo.unibomber.utilities.Constants.Bomb.EXPLOSION;
/**
 * Draw playing view statement.
 */
public final class PlayView implements GameLoop {

    private final Play controller;
    private Integer animationIndex = 0;
    private BufferedImage[][] animations;
    private BufferedImage[] bombAnimation;
    private final Map<Type, BufferedImage> sprites;
    private final Map<Type, Float> scale;
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
        this.scale = Constants.UI.Scale.ENTITY_SCALE;
        this.controller = controller;
        indexDir = 0;
        loadSprites();
        for (Integer j = 0; j < animations.length; j++) {
            for (Integer i = 0; i < animations[j].length; i++) {
                animations[j][i] = sprites.get(Type.PLAYABLE).getSubimage(i * Game.PLAYER_DEFAULT, j * Game.PLAYER_DEFAULT,
                Game.PLAYER_DEFAULT, Game.PLAYER_DEFAULT);
            }
        }
        for (Integer i = 0; i < bombAnimation.length; i++) {
            bombAnimation[i] = sprites.get(Type.BOMB).getSubimage(i * Game.PLAYER_DEFAULT,0,
            Game.PLAYER_DEFAULT, Game.PLAYER_DEFAULT);
        }
    }

    private void loadSprites() {
        animations = new BufferedImage[ROW_PLAYER_SPRITES][COL_PLAYER_SPRITES];
        bombAnimation = new BufferedImage[EXPLOSION];
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
            // TODO TOGLIERE IL PRINT DELLE HITBOX
            //controller.getEntities().get(i).getComponent(CollisionComponent.class).get().drawHitbox(g);
            drawImage(g, controller.getEntities().get(i));
        }
    }

    private void drawImage(Graphics g, Entity entity) {
        BufferedImage image = getCorrectImage(entity);
        g.drawImage(image,
                Math.round(entity.getPosition()
                        .getX() * Game.TILES_SIZE),
                Math.round(entity.getPosition()
                        .getY() * Game.TILES_SIZE),
                (int) (Game.TILES_DEFAULT * (Game.SCALE + scale.get(entity.getType()))),
                (int) (Game.TILES_DEFAULT * (Game.SCALE + scale.get(entity.getType()))),
                null);
    }

    private BufferedImage getCorrectImage(Entity entity) {
        if (entity.getType() == Type.PLAYABLE) {
            final var movementComponent = entity.getComponent(MovementComponent.class).get();
            if (!movementComponent.hasMoved()) {
                changePlayerAction(STANDING);
            } else {
                changePlayerAction(WALKING);
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
            }
            return animations[playerAction][animationIndex % Constants.Player.getSpriteAmount(playerAction) + indexDir];
        } else if (entity.getType() == Type.POWERUP) {
            return powerUpSprites.get(entity.getComponent(PowerUpComponent.class).get().getPowerUpType());
        } else if (entity.getType() == Type.BOMB) {
            return bombAnimation[animationIndex % EXPLOSION];
        }
        else
            return sprites.get(entity.getType());

    }
}
