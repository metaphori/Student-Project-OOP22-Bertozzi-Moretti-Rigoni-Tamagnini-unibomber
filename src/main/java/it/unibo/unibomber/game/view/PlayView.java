package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.util.Map;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Play;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.CollisionComponent;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;
import it.unibo.unibomber.game.ecs.impl.ExplodeComponent;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpComponent;
import it.unibo.unibomber.utilities.Constants;
import static it.unibo.unibomber.utilities.Constants.Player;
import static it.unibo.unibomber.utilities.Constants.UI.Game;
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
        loadSprites();
    }

    private void loadSprites() {
        animations = new BufferedImage[(SpritesMap.ROW_PLAYER_SPRITES * 2)
                + SpritesMap.ROW_BOMB_SPRITES + SpritesMap.ROW_WALL_SPRITES][SpritesMap.COL_PLAYER_SPRITES];
        for (Integer j = 0; j < Player.PLAYER_COUNTER; j++) {
            for (Integer i = 0; i < animations[j].length; i++) {
                animations[j][i] = sprites.get(Type.PLAYABLE).getSubimage(i * Game.PLAYER_DEFAULT,
                        j * Game.PLAYER_DEFAULT, Game.PLAYER_DEFAULT,
                        Game.PLAYER_DEFAULT);
                animations[j + SpritesMap.ROW_PLAYER_SPRITES][i] = sprites.get(Type.BOT).getSubimage(
                        i * Game.PLAYER_DEFAULT,
                        j * Game.PLAYER_DEFAULT, Game.PLAYER_DEFAULT,
                        Game.PLAYER_DEFAULT);
            }
        }
        for (Integer i = 0; i < SpritesMap.COL_BOMB_SPRITES; i++) {
            animations[SpritesMap.ANIMATION_ROW.get(Type.BOMB)][i] = sprites.get(Type.BOMB)
                    .getSubimage(i * Game.BOMB_DEFAULT, 0, Game.BOMB_DEFAULT, Game.BOMB_DEFAULT);
        }
        for (Integer i = 0; i < SpritesMap.COL_WALL_SPRITES; i++) {
            animations[SpritesMap.ANIMATION_ROW.get(Type.DESTRUCTIBLE_WALL)][i] = sprites.get(Type.DESTRUCTIBLE_WALL)
                    .getSubimage(i * Game.WALL_DEFAULT, 0, Game.WALL_DEFAULT, Game.WALL_DEFAULT);
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

        for (Integer i = 0; i < controller.getEntities().size(); i++) {
            // TODO TOGLIERE IL PRINT DELLE HITBOX
            controller.getEntities().get(i).getComponent(CollisionComponent.class).get().drawHitbox(g);
            if (controller.getEntities().get(i).getType() == Type.BOMB
                    && controller.getEntities().get(i).getComponent(ExplodeComponent.class).get().isExploding()) {
                controller.getExplosionController().draw(g);
            } else {
                drawImage(g, controller.getEntities().get(i));
            }
        }
    }

    private void drawImage(final Graphics g, final Entity entity) {
        BufferedImage image = getCorrectImage(entity);
        g.drawImage(image,
                Math.round(entity.getPosition()
                        .getX() * Game.getTilesSize()),
                Math.round(entity.getPosition()
                        .getY() * Game.getTilesSize()),
                (int) (Game.getTilesDefault() * (Game.SCALE + scale.get(entity.getType()))),
                (int) (Game.getTilesDefault() * (Game.SCALE + scale.get(entity.getType()))),
                null);
    }

    private BufferedImage getCorrectImage(final Entity entity) {
        if (entity.getType() == Type.PLAYABLE || entity.getType() == Type.BOT) {
            final var movementComponent = entity.getComponent(MovementComponent.class).get();
            if (entity.getComponent(DestroyComponent.class).get().isDestroyed()) {
                changePlayerAction(Player.DEFEAT, entity);
            } else if (!movementComponent.hasMoved()) {
                changePlayerAction(Player.STANDING, entity);
            } else {
                changePlayerAction(Player.WALKING, entity);
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
                    default:
                        break;
                }
            }
            return animations[playerAction + SpritesMap.ANIMATION_ROW.get(entity.getType())][getAnimationIndex(entity)
                    % Constants.Player.getSpriteAmount(playerAction)
                    + indexDir];
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
