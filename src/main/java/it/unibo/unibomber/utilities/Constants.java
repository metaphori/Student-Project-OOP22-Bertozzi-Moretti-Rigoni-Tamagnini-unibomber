package it.unibo.unibomber.utilities;

import java.util.HashMap;
import java.util.Map;

import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import java.awt.image.BufferedImage;

/**
 * Constants class.
 */
public class Constants {
    /**
     * UI constants.
     */
    public static class UI {
        /**
         * Button settings constans.
         */
        public static class Buttons {
            /**
             * distance from top of play button.
             */
            public static final int TOP_DISTANCE_PLAY = 120;
            /**
             * distance from top of quit button.
             */
            public static final int TOP_DISTANCE_QUIT = TOP_DISTANCE_PLAY + 50;
            /**
             * single button witdh.
             */
            public static final int WIDTH_DEFAULT = 140;
            /**
             * single button height.
             */
            public static final int HEIGHT_DEFAULT = 40;
            /**
             * button width scale.
             */
            public static final int B_WIDTH = (int) (WIDTH_DEFAULT * (Game.SCALE - 2f));
            /**
             * button height scale.
             */
            public static final int B_HEIGHT = (int) (HEIGHT_DEFAULT * (Game.SCALE - 2f));
        }

        /**
         * Game settings constans.
         */
        public static class Game {
            /**
             * tiles default dimention.
             */
            public static int TILES_DEFAULT = 16;
            /**
             * scale.
             */
            public static final float SCALE = 3f;
            /**
             * arena width in tiles.
             */
            public static final int TILES_WIDTH = 15;
            /**
             * arena height in tiles.
             */
            public static final int TILES_HEIGHT = 19;
            /**
             * tiles dimension scaled.
             */
            public static int TILES_SIZE = (int) (TILES_DEFAULT * SCALE);
            /**
             * game width.
             */
            public static int G_WIDTH = TILES_SIZE * TILES_WIDTH;
            /**
             * game height.
             */
            public static int G_HEIGHT = TILES_SIZE * TILES_HEIGHT;
            /**
             * player default dimension.
             */
            public static final int PLAYER_DEFAULT = 48;
            /**
             * bomb default dimension.
             */
            public static final int BOMB_DEFAULT = 48;
            /**
             * explosion default dimension.
             */
            public static final int EXPLOSION_DEFAULT = 48;
            public static void changeDimension() {
                TILES_DEFAULT--;
                TILES_SIZE = (int) (TILES_DEFAULT * SCALE);
                G_WIDTH = TILES_SIZE * TILES_WIDTH;
                G_HEIGHT = TILES_SIZE * TILES_HEIGHT;
            }
        }

        /**
         * GameLoop settings constans.
         */
        public static class GameLoop {
            /**
             * one nano second.
             */
            public static final double NANO_S = 1_000_000_000.0;
            /**
             * fps setting.
             */
            public static final int FPS_SET = 60;
            /**
             * ups setting.
             */
            public static final int UPS_SET = 60;
        }

        /**
         * Sprites settings constans.
         */
        public static class SpritesMap {
            /**
             * MENU_BUTTONS sprites path.
             */
            public static final String MENU_BUTTONS = "button_atlas.png";
            /**
             * MENU_BACKGROUND sprites path.
             */
            public static final String MENU_BACKGROUND = "menu_background.png";
            /**
             * SHADOW sprites path.
             */
            public static final String SHADOW = "shadow.png";
            /**
             * max col of player sprites animation.
             */
            public static final int COL_PLAYER_SPRITES = 24;
            /**
             * max row of player sprites animation.
             */
            public static final int ROW_PLAYER_SPRITES = 2;
            /**
             * max row of bomb sprites animation.
             */
            public static final int ROW_BOMB_SPRITES = 1;
            /**
             * max row of bomb sprites animation.
             */
            public static final int COL_BOMB_SPRITES = 3;
             /**
             * max row of explosion sprites animation.
             */
            public static final int ROW_EXPLOSION_SPRITES = 1;
            /**
             * max row of explosion sprites animation.
             */
            public static final int COL_EXPLOSION_SPRITES = 9;
            /**
             * Map of row of entity animation.
             */
            public static final Map<Type, Integer> ANIMATION_ROW = new HashMap<>();
            /**
             * Map of type and path of sprites.
             */
            public static final Map<Type, BufferedImage> SPRITESPATH = new HashMap<>();
            /**
             * Map of powerup type and path of powerup sprites.
             */
            public static final Map<PowerUpType, BufferedImage> SPRITESPOWERUPPATH = new HashMap<>();

            /**
             * SpriteMap constructor.
             */
            public SpritesMap() {
                // TYPE
                SPRITESPATH.put(Type.PLAYABLE, UploadRes.getSpriteAtlas("player_sprites.png"));
                SPRITESPATH.put(Type.BOT, UploadRes.getSpriteAtlas("bot_sprites.png"));
                SPRITESPATH.put(Type.POWERUP, null);
                SPRITESPATH.put(Type.EMPTY_AREA, UploadRes.getSpriteAtlas("grass.png"));
                SPRITESPATH.put(Type.RISING_WALL, null);
                SPRITESPATH.put(Type.DESTRUCTIBLE_WALL, UploadRes.getSpriteAtlas("destructible_wall.png"));
                SPRITESPATH.put(Type.INDESTRUCTIBLE_WALL, UploadRes.getSpriteAtlas("indestructible_wall.png"));
                SPRITESPATH.put(Type.BOMB, UploadRes.getSpriteAtlas("bomb.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.FIREUP, UploadRes.getSpriteAtlas("fire_up.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.FIREDOWN, UploadRes.getSpriteAtlas("fire_down.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.FIREFULL, UploadRes.getSpriteAtlas("max_fire.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.BOMBUP, UploadRes.getSpriteAtlas("bomb_up.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.BOMBDOWN, UploadRes.getSpriteAtlas("bomb_down.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.SPEEDUP, UploadRes.getSpriteAtlas("speed_up.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.SPEEDDOWN, UploadRes.getSpriteAtlas("speed_down.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.KICKBOMB, UploadRes.getSpriteAtlas("bomb_kick.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.THROWBOMB, UploadRes.getSpriteAtlas("power_glove.png"));
                ANIMATION_ROW.put(Type.PLAYABLE, 0);
                ANIMATION_ROW.put(Type.BOT, 2);
                ANIMATION_ROW.put(Type.BOMB, 4);

            }

        }

        /**
         * Entity scale settings constans.
         */
        public static final class Scale {
            /**
             * Map of type and scale of this entity .
             */
            public static final Map<Type, Float> ENTITY_SCALE = new HashMap<>();

            /**
             * Scale constructor.
             */
            public Scale() {
                ENTITY_SCALE.put(Type.PLAYABLE, 0.2f);
                ENTITY_SCALE.put(Type.BOT, 0.5f);
                ENTITY_SCALE.put(Type.BOMB, -0.5f);
                ENTITY_SCALE.put(Type.POWERUP, 0f);
                ENTITY_SCALE.put(Type.DESTRUCTIBLE_WALL, 0f);
                ENTITY_SCALE.put(Type.INDESTRUCTIBLE_WALL, 0f);
            }
        }
    }

    /**
     * Player animation constans.
     */
    public static final class Player {
        /**
         * STANDING animation.
         */
        public static final int STANDING = 0;
        /**
         * WALKING animation.
         */
        public static final int WALKING = 1;
        /**
         * POWER_GLOVE animation.
         */
        public static final int POWER_GLOVE = 2;
        /**
         * THROWING animation.
         */
        public static final int THROWING = 3;
        /**
         * PUNCH animation.
         */
        public static final int PUNCH = 4;
        /**
         * SHIELD animation.
         */
        public static final int SHIELD = 5;
        /**
         * STUN animation.
         */
        public static final int STUN = 6;
        /**
         * VICTORY animation.
         */
        public static final int VICTORY = 7;
        /**
         * DEFEAT animation.
         */
        public static final int DEFEAT = 8;
        /**
         * IDLE animation.
         */
        public static final int IDLE = 9;
        /**
         * JUMPUP_DOWN animation.
         */
        public static final int JUMPUP_DOWN = 10;
        /**
         * CRYING animation.
         */
        public static final int CRYING = 11;
        /**
         * DANCING animation.
         */
        public static final int DANCING = 12;
        /**
         * EXPLOSION animation.
         */
        public static final int EXPLOSION = 13;
        /**
         * STANDING number of animations.
         */
        public static final int STANDING_ANIMATION = 3;
        /**
         * WALKING number of animations.
         */
        public static final int WALKING_ANIMATION = 6;
        /**
         * POWER_GLOVE number of animations.
         */
        public static final int POWER_GLOVE_ANIMATION = 6;
        /**
         * THROWING number of animations.
         */
        public static final int THROWING_ANIMATION = 5;
        /**
         * PUNCH number of animations.
         */
        public static final int PUNCH_ANIMATION = 3;
        /**
         * SHIELD number of animations.
         */
        public static final int SHIELD_ANIMATION = 2;
        /**
         * STUN number of animations.
         */
        public static final int STUN_ANIMATION = 8;
        /**
         * VICTORY number of animations.
         */
        public static final int VICTORY_ANIMATION = 6;
        /**
         * DEFEAT number of animations.
         */
        public static final int DEFEAT_ANIMATION = 14;
        /**
         * IDLE number of animations.
         */
        public static final int IDLE_ANIMATION = 9;
        /**
         * JUMPUP_DOWN number of animations.
         */
        public static final int JUMPUP_DOWN_ANIMATION = 9;
        /**
         * CRYING number of animations.
         */
        public static final int CRYING_ANIMATION = 4;
        /**
         * DANCING number of animations.
         */
        public static final int DANCING_ANIMATION = 8;
        /**
         * EXPLOSION number of animations.
         */
        public static final int EXPLOSION_ANIMATION = 3;
        /**
         * EXPLOSION_COUNTER number of animations.
         */
        public static final int EXPLOSION_COUNTER = 1;
        /**
         * PLAYER_COUNTER number of animations.
         */
        public static final int PLAYER_COUNTER = 2;

        /**
         * @param playerAction
         * @return the number of animation of that action
         */
        public static Integer getSpriteAmount(final int playerAction) {
            switch (playerAction) {
                case STANDING:
                    return STANDING_ANIMATION;
                case PUNCH:
                    return PUNCH_ANIMATION;
                case WALKING:
                    return WALKING_ANIMATION;
                case POWER_GLOVE:
                    return POWER_GLOVE_ANIMATION;
                case VICTORY:
                    return VICTORY_ANIMATION;
                case THROWING:
                    return THROWING_ANIMATION;
                case CRYING:
                    return CRYING_ANIMATION;
                case SHIELD:
                    return SHIELD_ANIMATION;
                case STUN:
                    return STUN_ANIMATION;
                case DEFEAT:
                    return DEFEAT_ANIMATION;
                case JUMPUP_DOWN:
                    return JUMPUP_DOWN_ANIMATION;
                case DANCING:
                    return DANCING_ANIMATION;
                case EXPLOSION:
                    return EXPLOSION_ANIMATION;
                default:
                    return 1;
            }
        }
    }

    /**
     * Input settings constans.
     */
    public static class Input {
        /**
         * no keys value.
         */
        public static final int NO_KEYS_VALUE = -1;
        /**
         * positive movement.
         */
        public static final Float POSITIVE_MOVE = 0.2f;
        /**
         * negative movement.
         */
        public static final Float NEGATIVE_MOVE = -0.2f;
    }

    /**
     * powerUp settings constans.
     */
    public static class PowerUp {
        /**
         * Percentage of have complex power up.
         */
        public static final int COMPLEX_PERCENTAGE = 25;
        /**
         * powerupSpeed increase.
         */
        public static final float SPEED_POWERUP_CHANGE = 0.07f;
    }

    /**
     * Enity speed settings constans.
     */
    public static class Entity {
        /**
         * Entity speed.
         */
        public static final float BASE_SPEED = 0.3f;
    }

    /**
     * Explode duration settings constans.
     */
    public static class Explode {
        /**
         * Explode duration.
         */
        public static final int EXPLODE_DURATION = 9;

        /**
         * Expiring time before bomb explodes.
         */
        public static final int EXPIRING_TIME = 90;
    }

    /**
     * Destroy duration settings constans.
     */
    public static class Destroy {

        /**
         * the number of frames the destruction of effective frames the destruction of a
         * type takes.
         */
        private static final Map<Type, Integer> DESTROY_FRAMES_PER_TYPE = new HashMap<>();

        /**
         * constructor.
         */
        public Destroy() {
            DESTROY_FRAMES_PER_TYPE.put(Type.POWERUP, 0);
            DESTROY_FRAMES_PER_TYPE.put(Type.BOMB, 0);
        }

        /**
         * Percentage of powerup drop.
         */
        public static final float DROPPED_POWERUP_PERCENT = 0.25f;

        /**
         * standard duration of the destrution in frames.
         */
        public static final int STANDARD_FRAME_DURATION = 4;

        /**
         * @param type the type of the entity asked
         * @return the number of actual frames
         */
        public static Integer getDestructionFrames(final Type type) {
            return DESTROY_FRAMES_PER_TYPE.containsKey(type) ? DESTROY_FRAMES_PER_TYPE.get(type) : STANDARD_FRAME_DURATION;
        }
    }

    /**
     * Movements constans.
     */
    public static class Movement {
        /**
         * Number of frames between one animation and the other.
         */
        public static final int FRAME_DELAY = 10;

        /**
         * Global speed multiplier.
         */
        public static final float MULTIPLIER_GLOBAL_SPEED = 1f;
    }
}
