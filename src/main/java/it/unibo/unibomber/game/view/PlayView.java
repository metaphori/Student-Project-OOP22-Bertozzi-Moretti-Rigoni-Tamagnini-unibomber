package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Play;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.AIComponent;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;
import it.unibo.unibomber.game.ecs.impl.ExplodeComponent;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpComponent;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.UploadRes;
import it.unibo.unibomber.utilities.Constants.UI.GameLoopConstants;

import static it.unibo.unibomber.utilities.Constants.Player;
import static it.unibo.unibomber.utilities.Constants.UI.Screen;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap;
import static it.unibo.unibomber.utilities.Constants.Movement.FRAME_DELAY;

/**
 * Draw playing view statement.
 */
public final class PlayView implements GameLoop {

    private final Play controller;
    private BufferedImage[][] animations;
    private final Map<Type, BufferedImage> sprites;
    private final Map<Type, Float> scale;
    private final Map<PowerUpType, BufferedImage> powerUpSprites;
    private Integer playerAction = Player.STANDING;
    private Integer indexDir;
    private final BufferedImage[] tile;

    /**
     * 
     * @param controller
     */
    public PlayView(final Play controller) {
        this.sprites = SpritesMap.SPRITESPATH;
        this.powerUpSprites = SpritesMap.SPRITESPOWERUPPATH;
        this.scale = Constants.UI.Scale.ENTITY_SCALE;
        this.controller = controller;
        indexDir = 0;
        tile = new BufferedImage[2];
        loadSprites();
    }

    private void loadSprites() {
        animations = new BufferedImage[SpritesMap.ROW_PLAYER_SPRITES * 2
                + SpritesMap.ROW_BOMB_SPRITES + SpritesMap.ROW_WALL_SPRITES][SpritesMap.COL_PLAYER_SPRITES];
        for (Integer j = 0; j < Player.PLAYER_COUNTER; j++) {
            for (Integer i = 0; i < animations[j].length; i++) {
                animations[j][i] = sprites.get(Type.PLAYABLE).getSubimage(i * Screen.PLAYER_DEFAULT,
                        j * Screen.PLAYER_DEFAULT, Screen.PLAYER_DEFAULT,
                        Screen.PLAYER_DEFAULT);
                animations[j + SpritesMap.ROW_PLAYER_SPRITES][i] = sprites.get(Type.BOT).getSubimage(
                        i * Screen.PLAYER_DEFAULT,
                        j * Screen.PLAYER_DEFAULT, Screen.PLAYER_DEFAULT,
                        Screen.PLAYER_DEFAULT);
            }
        }
        for (Integer i = 0; i < SpritesMap.COL_BOMB_SPRITES; i++) {
            animations[SpritesMap.ANIMATION_ROW.get(Type.BOMB)][i] = sprites.get(Type.BOMB)
                    .getSubimage(i * Screen.BOMB_DEFAULT, 0, Screen.BOMB_DEFAULT, Screen.BOMB_DEFAULT);
        }
        for (Integer i = 0; i < SpritesMap.COL_WALL_SPRITES; i++) {
            animations[SpritesMap.ANIMATION_ROW.get(Type.DESTRUCTIBLE_WALL)][i] = sprites.get(Type.DESTRUCTIBLE_WALL)
                    .getSubimage(i * Screen.WALL_DEFAULT, 0, Screen.WALL_DEFAULT, Screen.WALL_DEFAULT);
        }
        for (int i = 0; i < 2; i++) {
            tile[i] = UploadRes.getSpriteAtlas("maps/map" + GameLoopConstants.getLEVEL() + "/grass.png")
                    .getSubimage(i * Screen.WALL_DEFAULT, 0, Screen.WALL_DEFAULT, Screen.WALL_DEFAULT);
        }
    }

    private Integer getAnimationIndex(final Entity entity) {
        return entity.getComponent(MovementComponent.class)
                .get()
                .getPassedFrames();
    }

    @Override
    public void update() {
    }

    /**
     * change the player action for sprites.
     * 
     * @param action
     * @param e
     */
    public void changePlayerAction(final Integer action, final Entity e) {
        if (action == Player.STANDING && playerAction != Player.STANDING) {
            final Integer animation = getAnimationIndex(e) % Constants.Player.getSpriteAmount(playerAction) + indexDir;
            final Integer basicDir = (int) (animation / Constants.Player.getSpriteAmount(playerAction));
            indexDir = basicDir * Constants.Player.getSpriteAmount(action);
        }
        playerAction = action;
    }

    @Override
    public void draw(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g.create();
        for (int y = 0; y < Screen.getgHeight(); y += Screen.getTilesSize()) {
            for (int x = 0; x < Screen.getgWidth(); x += Screen.getTilesSize()) {
                g2d.drawImage(tile[(x + y) % 2], x, y,
                        (int) (Screen.getTilesSize()),
                        (int) (Screen.getTilesSize()),
                        null);
            }
        }
        g2d.dispose();
        for (Integer i = 0; i < controller.getEntities().size(); i++) {
            // TODO TOGLIERE IL PRINT DELLE HITBOX
            // controller.getEntities().get(i).getComponent(CollisionComponent.class).get().drawHitbox(g);
            if (controller.getEntities().get(i).getType() == Type.BOMB
                    && controller.getEntities().get(i).getComponent(ExplodeComponent.class).get().isExploding()) {
                controller.getExplosionController().draw(g);
            } else {
                drawImage(g, controller.getEntities().get(i));
            }
        }
    }

    private void drawImage(final Graphics g, final Entity entity) {
        final BufferedImage image = getCorrectImage(entity);
        final Type type;
        if (entity.getType() == Type.BOMBER) {
            type = entity.getComponent(AIComponent.class).isPresent() ? Type.BOT : Type.PLAYABLE;
        } else {
            type = entity.getType();
        }
        g.drawImage(image,
                Math.round(entity.getPosition()
                        .getX() * Screen.getTilesSize()),
                Math.round(entity.getPosition()
                        .getY() * Screen.getTilesSize()),
                (int) (Screen.getTilesDefault() * (Screen.SCALE + scale.get(type))),
                (int) (Screen.getTilesDefault() * (Screen.SCALE + scale.get(type))),
                null);
    }

    private BufferedImage getCorrectImage(final Entity entity) {
        if (entity.getType() == Type.BOMBER) {
            final Type type = entity.getComponent(AIComponent.class).isPresent() ? Type.BOT : Type.PLAYABLE;
            final var movementComponent = entity.getComponent(MovementComponent.class).get();
            if (entity.getComponent(DestroyComponent.class).get().isDestroyed()) {
                changePlayerAction(Player.DEFEAT, entity);
                return animations[playerAction + SpritesMap.ANIMATION_ROW
                        .get(type)][(entity.getComponent(DestroyComponent.class).get().getDestroyFrames()
                                / (FRAME_DELAY / 2)) % Constants.Player.getSpriteAmount(Player.DEFEAT)];
            } else if (!movementComponent.hasMoved()) {
                changePlayerAction(Player.STANDING, entity);
            } else {
                changePlayerAction(Player.WALKING, entity);
                switch (movementComponent.getDirection()) {
                    case UP:
                        indexDir = Constants.Player.getSpriteAmount(playerAction) * 3;
                        break;
                    case LEFT:
                        indexDir = Constants.Player.getSpriteAmount(playerAction) * 1;
                        break;
                    case RIGHT:
                        indexDir = Constants.Player.getSpriteAmount(playerAction) * 2;
                        break;
                    case DOWN:
                        indexDir = 0;
                        break;
                    case CENTER:
                        indexDir = indexDir % Constants.Player.getSpriteAmount(playerAction);
                        break;
                    default:
                        break;
                }
            }
            return animations[playerAction + SpritesMap.ANIMATION_ROW.get(type)][getAnimationIndex(entity)
                    % Constants.Player.getSpriteAmount(playerAction) + indexDir];
        } else if (entity.getType() == Type.POWERUP) {
            return powerUpSprites.get(entity.getComponent(PowerUpComponent.class).get().getPowerUpType());
        } else if (entity.getType() == Type.BOMB) {
            return animations[Player.PLAYER_COUNTER * 2][getAnimationIndex(entity)
                    % Constants.Player.getSpriteAmount(Player.EXPLOSION)];
        } else if (entity.getType() == Type.DESTRUCTIBLE_WALL) {
            if (entity.getComponent(DestroyComponent.class).get().isDestroyed()) {
                return animations[SpritesMap.ANIMATION_ROW.get(
                        Type.DESTRUCTIBLE_WALL)][(entity.getComponent(DestroyComponent.class).get().getDestroyFrames()
                                / (FRAME_DELAY / 2)) % Constants.Player.getSpriteAmount(Player.WALL)];
            } else {
                return animations[SpritesMap.ANIMATION_ROW.get(Type.DESTRUCTIBLE_WALL)][0];
            }
        } else {
            return sprites.get(entity.getType());
        }

    }
}
