package it.unibo.unibomber.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;

import java.awt.Color;
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
        public static final class Buttons {
            /**
             * default size of option button.
             */
            public static final int DEFAULT_OPTION_BUTTON_SIZE = 20;
            /**
             * Scale Button size.
             */
            private static float scaleButton = 3f;
            /**
             * default distance from top for button start.
             */
            private static int defaultTopDistance = DEFAULT_OPTION_BUTTON_SIZE * (int) scaleButton;
            /**
             * distance from top of play button.
             */
            private static int topDistancePlay = (defaultTopDistance * (int) (getScaleButton())) * 2;
            /**
             * distance from top of quit button.
             */
            private static int topDistanceQuit = topDistancePlay + 100;
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
            private static int bWidht = (int) (WIDTH_DEFAULT * (getScaleButton() - 1f));
            /**
             * button height scale.
             */
            private static int bHeight = (int) (HEIGHT_DEFAULT * (getScaleButton() - 1f));
            /**
             * size of option button.
             */
            private static int optionButtonSize = DEFAULT_OPTION_BUTTON_SIZE * (int) getScaleButton();

            /**
             * @return default distance from top for button start
             */
            public static int getDefaultTopDistance() {
                return defaultTopDistance;
            }

            /**
             * @return settings option size.
             */
            public static int getOptionButtonSize() {
                return optionButtonSize;
            }

            /**
             * @return top distance play button.
             */
            public static int getTopDistancePlay() {
                return topDistancePlay;
            }

            /**
             * @return scale of button.
             */
            public static float getScaleButton() {
                return scaleButton;
            }

            /**
             * set scale button.
             * 
             * @param dec index of decrement.
             */
            public static void setScaleButton(final float dec) {
                scaleButton -= dec;
                bWidht = (int) (WIDTH_DEFAULT * (getScaleButton() - 1f));
                bHeight = (int) (HEIGHT_DEFAULT * (getScaleButton() - 1f));
                topDistancePlay = (defaultTopDistance * (int) (getScaleButton())) * 2;
                topDistanceQuit = topDistancePlay + 100;
                optionButtonSize = DEFAULT_OPTION_BUTTON_SIZE * (int) getScaleButton();
                defaultTopDistance = DEFAULT_OPTION_BUTTON_SIZE * (int) scaleButton;
                MapOption.mapDimension = MapOption.DEFAULT_MAP_SELECTION_SIZE * (int) scaleButton;
                OptionButton.bombNumberDimension = OptionButton.BOTNUMBER_DEFAULT_SIZE * (int) Buttons.scaleButton;
                OptionButton.incrementBotSize = OptionButton.bombNumberDimension / 2;
                OptionButton.plyerSelectionWidth = OptionButton.PLAYER_SELECTION_WIDTH * (int) Buttons.scaleButton;
                OptionButton.plyerSelectionHeight = OptionButton.PLAYER_SELECTION_HEIGHT * (int) Buttons.scaleButton;
                OptionButton.plyerSelectioBorderDistance = (Screen.getgWidth()
                        - (OptionButton.plyerSelectionWidth * 2 + 10)) / 2;
                OptionButton.powerUpSetTopDistance = Screen.getgHeight()
                        - (Buttons.getOptionButtonSize() + OptionButton.WIDTH_INCREMENT);
                OptionButton.gameStateDimension = new Pair<>(
                        OptionButton.GAMESTATE_W_DEFAULT * (int) Buttons.scaleButton,
                        OptionButton.GAMESTATE_H_DEFAULT * (int) Buttons.scaleButton);
                OptionButton.continueDimension = new Pair<>(
                        OptionButton.CONTINUE_W_DEFAULT * (int) Buttons.scaleButton,
                        OptionButton.CONTINUE_H_DEFAULT * (int) Buttons.scaleButton);
                OptionButton.quitDimension = new Pair<>(
                        OptionButton.QUIT_W_DEFAULT * (int) Buttons.scaleButton,
                        OptionButton.QUIT_H_DEFAULT * (int) Buttons.scaleButton);
            }

            /**
             * @return top distace quit button.
             */
            public static int getTopDistanceQuit() {
                return topDistanceQuit;
            }

            /**
             * @return button width.
             */
            public static int getBWidht() {
                return bWidht;
            }

            /**
             * @return button height.
             */
            public static int getBHeight() {
                return bHeight;
            }

            /**
             * Constructor.
             */
            private Buttons() {

            }
        }

        /**
         * Option button constans.
         */
        public static final class OptionButton {
            /**
             * DIVIDER HANDICAP CENTER ON PLAYER SET.
             */
            public static final int HANDICAP_DIVIDER = 7;
            /**
             * Max number of handicap.
             */
            public static final int MAX_HANDICAP = 6;
            /**
             * Number of handicap power up.
             */
            public static final int HANDICAP_NUMBER_POWERUP = 5;
            /**
             * Default power up handicap conteiner width.
             */
            public static final int CONTAINER_WIDTH = 300;
            /**
             * Default arc for option rectangle.
             */
            public static final int ARC_RECT = 5;
            /**
             * handicap container background color.
             */
            public static final Color CONTAINER_COLOR = new Color(214, 214, 214);
            /**
             * Option background color.
             */
            public static final Color OPTION_BACKGROUND = new Color(255, 255, 156);
            /**
             * Default gamestate witdh.
             */
            private static final int GAMESTATE_W_DEFAULT = 50;
            /**
             * Default gamestate height.
             */
            private static final int GAMESTATE_H_DEFAULT = 15;
            /**
             * Default continue witdh.
             */
            private static final int CONTINUE_W_DEFAULT = 50;
            /**
             * Default continue height.
             */
            private static final int CONTINUE_H_DEFAULT = 15;
            /**
             * Default quit witdh.
             */
            private static final int QUIT_W_DEFAULT = 30;
            /**
             * Default quit height.
             */
            private static final int QUIT_H_DEFAULT = 15;
            /**
             * Increment of with on height of ok button.
             */
            public static final int WIDTH_INCREMENT = 10;
            /**
             * Increment of player section with on height of ok button.
             */
            public static final int PLAYER_WIDTH_INCREMENT = 70;
            /**
             * Increment of height for bot number selction.
             */
            public static final int HEIGHT_BOTNUMBER_SELECTION = 30;
            /**
             * bombNumber default size.
             */
            private static final int BOTNUMBER_DEFAULT_SIZE = 15;
            /**
             * player selection width default size.
             */
            private static final int PLAYER_SELECTION_WIDTH = 80;
            /**
             * player selection height default size.
             */
            private static final int PLAYER_SELECTION_HEIGHT = 20;
            /**
             * gamestate dimention.
             */
            private static Pair<Integer, Integer> gameStateDimension = new Pair<>(
                    GAMESTATE_W_DEFAULT * (int) Buttons.scaleButton, GAMESTATE_H_DEFAULT * (int) Buttons.scaleButton);
            /**
             * Contiunue button dimension.
             */
            private static Pair<Integer, Integer> continueDimension = new Pair<>(
                    CONTINUE_W_DEFAULT * (int) Buttons.scaleButton, CONTINUE_H_DEFAULT * (int) Buttons.scaleButton);
            /**
             * Quit button dimension.
             */
            private static Pair<Integer, Integer> quitDimension = new Pair<>(
                    QUIT_W_DEFAULT * (int) Buttons.scaleButton, QUIT_H_DEFAULT * (int) Buttons.scaleButton);

            /**
             * Selection bombNumber dimension scaled.
             */
            private static int bombNumberDimension = BOTNUMBER_DEFAULT_SIZE * (int) Buttons.scaleButton;
            /**
             * Selection incrementBotSize dimension scaled.
             */
            private static int incrementBotSize = bombNumberDimension / 2;
            /**
             * player selection width dimension scaled.
             */
            private static int plyerSelectionWidth = PLAYER_SELECTION_WIDTH * (int) Buttons.scaleButton;

            /**
             * player selection height dimension scaled.
             */
            private static int plyerSelectionHeight = PLAYER_SELECTION_HEIGHT * (int) Buttons.scaleButton;

            /**
             * player selection border distance scaled.
             */
            private static int plyerSelectioBorderDistance = (Screen.getgWidth() - (plyerSelectionWidth * 2 + 10)) / 2;
            /**
             * Top distance for power up set.
             */
            private static int powerUpSetTopDistance = Screen.getgHeight()
                    - (Buttons.getOptionButtonSize() + OptionButton.WIDTH_INCREMENT);

            /**
             * Option button constuctor.
             */
            private OptionButton() {
            }

            /**
             * @return quit dimension.
             */
            public static Pair<Integer, Integer> getQuitDimension() {
                return quitDimension;
            }

            /**
             * @return gamestate dimension.
             */
            public static Pair<Integer, Integer> getGameStateDimension() {
                return gameStateDimension;
            }

            /**
             * @return conitnue dimension.
             */
            public static Pair<Integer, Integer> getContinueDimension() {
                return continueDimension;
            }

            /**
             * @return Top distance for power up set.
             */
            public static int getPowerUpSetTopDistance() {
                return powerUpSetTopDistance;
            }

            /**
             * @return plyerSelectioBorderDistance.
             */
            public static int getPlyerSelectioBorderDistance() {
                return plyerSelectioBorderDistance;
            }

            /**
             * @return plyerSelectionHeight.
             */
            public static int getPlyerSelectionHeight() {
                return plyerSelectionHeight;
            }

            /**
             * @return plyerSelectionWidth.
             */
            public static int getPlyerSelectionWidth() {
                return plyerSelectionWidth;
            }

            /**
             * @return incrementBotSize button.
             */
            public static int getIncrementBotSize() {
                return incrementBotSize;
            }

            /**
             * @return bombNumber button.
             */
            public static int getBombNumberDimension() {
                return bombNumberDimension;
            }
        }

        /**
         * Constanst for map settings.
         */
        public static final class MapOption {
            /**
             * default size of map selection.
             */
            private static final int DEFAULT_MAP_SELECTION_SIZE = 55;
            /**
             * Selection map dimension.
             */
            private static int mapDimension = DEFAULT_MAP_SELECTION_SIZE * (int) Buttons.scaleButton;

            /**
             * List of chosing map.
             */
            public static final List<BufferedImage> MAP_CHOSE_LIST = List.of(
                    UploadRes.getSpriteAtlas("maps/map0/map.png"),
                    UploadRes.getSpriteAtlas("maps/map1/map.png"));
            /**
             * List of map file.
             */
            public static final List<String> MAP_LIST = List.of(
                "./src/main/resources/it/unibo/sprites/maps/map0/arena.map",
                "./src/main/resources/it/unibo/sprites/maps/map1/arena.map");
            /**
             * Number of bot in game.
             */
            private static int numberOfBot = 3;

            /**
             * @return number of bot.
             */
            public static int getNumberOfBot() {
                return numberOfBot;
            }

            /**
             * increment number of bot.
             */
            public static void incrementBot() {
                numberOfBot++;
            }

            /**
             * decrement number of bot.
             */
            public static void decrementBot() {
                numberOfBot--;
            }

            /**
             * Constructor.
             */
            private MapOption() {

            }

            /**
             * @return map dimension.
             */
            public static int getMapDimension() {
                return mapDimension;
            }
        }

        /**
         * Game settings constans.
         */
        public static final class Screen {
            /**
             * bar height.
             */
            public static final int BAR_HEIGHT = 40;
            /**
             * tiles default dimention.
             */
            private static int tilesDefault = 16;
            /**
             * scale.
             */
            public static final float SCALE = 3f;
            /**
             * default tilest width.
             */
            private static final int DEFAULT_TILES_WIDTH = 15;
            /**
             * default tilest wihrightdth.
             */
            private static final int DEFAULT_TILES_HEIGHT = 19;
            /**
             * arena width in tiles.
             */
            private static int tilesWidth = DEFAULT_TILES_WIDTH;

            /**
             * arena height in tiles.
             */
            private static int tilesHeight = DEFAULT_TILES_HEIGHT;

            /**
             * tiles dimension scaled.
             */
            private static int tilesSize = (int) (tilesDefault * SCALE);
            /**
             * game width.
             */
            private static int gWidth = tilesSize * tilesWidth;
            /**
             * game height.
             */
            private static int gHeight = tilesSize * tilesHeight;
            /**
             * player default dimension.
             */
            public static final int PLAYER_DEFAULT = 48;
            /**
             * bomb default dimension.
             */
            public static final int BOMB_DEFAULT = 48;
            /**
             * wall default dimension.
             */
            public static final int WALL_DEFAULT = 48;
            /**
             * explosion default dimension.
             */
            public static final int EXPLOSION_DEFAULT = 48;
            /**
             * opacity default value.
             */
            private static final int OPACITY = 125;

            /**
             * Change window size.
             */
            public static void changeDimension() {
                tilesDefault--;
                tilesSize = (int) (tilesDefault * SCALE);
                gWidth = tilesSize * tilesWidth;
                gHeight = tilesSize * tilesHeight;
            }

            /**
             * Set Current dimension based on map.
             */
            public static void setDimensionOnMap() {
                gWidth = tilesSize * tilesWidth;
                gHeight = tilesSize * tilesHeight;
            }

            /**
             * @return default dimention.
             */
            public static int getTilesDefault() {
                return tilesDefault;
            }

            /**
             * @param tilesDefault set default dimention.
             */
            public static void setTilesDefault(final int tilesDefault) {
                Screen.tilesDefault = tilesDefault;
            }

            /**
             * @return dimension scaled.
             */
            public static int getTilesSize() {
                return tilesSize;
            }

            /**
             * @param tilesSize set dimension scaled.
             */
            public static void setTilesSize(final int tilesSize) {
                Screen.tilesSize = tilesSize;
            }

            /**
             * @return get Height
             */
            public static int getgHeight() {
                return gHeight;
            }

            /**
             * @param gHeight set Height
             */
            public static void setgHeight(final int gHeight) {
                Screen.gHeight = gHeight;
            }

            /**
             * @return width
             */
            public static int getgWidth() {
                return gWidth;
            }

            /**
             * @param gWidth set Width
             */
            public static void setgWidth(final int gWidth) {
                Screen.gWidth = gWidth;
            }

            /**
             * @return opacity
             */
            public static int getOpacity() {
                return OPACITY;
            }

            /**
             * @return tilesHeight
             */
            public static int getTilesHeight() {
                return tilesHeight;
            }

            /**
             * @param tilesHeight
             */
            public static void setTilesHeight(final int tilesHeight) {
                Screen.tilesHeight = tilesHeight;
            }

            /**
             * @return tilesWidth
             */
            public static int getTilesWidth() {
                return tilesWidth;
            }

            /**
             * @param tilesWidth
             */
            public static void setTilesWidth(final int tilesWidth) {
                Screen.tilesWidth = tilesWidth;
            }

            /**
             * Constructor.
             */
            private Screen() {

            }
        }

        /**
         * GameLoop settings constans.
         */
        public static final class GameLoopConstants {
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
            /**
             * Second for start times up.
             */
            public static final int TIMES_UP_TIMER = 120;
            /**
             * Game level.
             */
            private static int level;

            /**
             * @return game level.
             */
            public static int getLEVEL() {
                return level;
            }

            /**
             * @param lEVEL
             */
            public static void setLEVEL(final int lEVEL) {
                level = lEVEL;
            }

            /**
             * Game loop Constructor.
             */
            private GameLoopConstants() {

            }

        }

        /**
         * Sprites settings constans.
         */
        public static final class SpritesMap {
            /**
             * MENU_BUTTONS sprites path.
             */
            public static final String MENU_BUTTONS = "menu/button_atlas.png";
            /**
             * MENU_BACKGROUND sprites path.
             */
            public static final String MENU_BACKGROUND = "menu/menu_background.png";
            /**
             * SHADOW sprites path.
             */
            public static final String SHADOW = "player/shadow.png";
            /**
             * max col of player sprites animation.
             */
            public static final int COL_PLAYER_SPRITES = 24;
            /**
             * max row of player sprites animation.
             */
            public static final int ROW_PLAYER_SPRITES = 4;
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
            public static final int ROW_EXPLOSION_SPRITES = 4;
            /**
             * max row of explosion sprites animation.
             */
            public static final int COL_EXPLOSION_SPRITES = 9;
            /**
             * max row of wall sprites animation.
             */
            public static final int ROW_WALL_SPRITES = 1;
            /**
             * max row of wall sprites animation.
             */
            public static final int COL_WALL_SPRITES = 4;
            /**
             * Map of row of entity animation.
             */
            private static final Map<Type, Integer> ANIMATION_ROW = new HashMap<>();

            /**
             * Map of type and path of sprites.
             */
            private static final Map<Type, BufferedImage> SPRITESPATH = new HashMap<>();

            /**
             * List of destructible wall based on level chose.
             */
            private static final List<BufferedImage> DESTRUCTIBLE_WALL_LIST = new ArrayList<>();

            /**
             * List of indestucrtible wall based on level chose.
             */
            private static final List<BufferedImage> INDESTRUCTIBLE_WALL_LIST = new ArrayList<>();

            /**
             * Map of powerup type and path of powerup sprites.
             */
            private static final Map<PowerUpType, BufferedImage> SPRITESPOWERUPPATH = new HashMap<>();
            private static final int ANIMATION_ROW_PLAYABLE = 0;
            private static final int ANIMATION_ROW_BOT = 4;
            private static final int ANIMATION_ROW_BOMB = 8;
            private static final int ANIMATION_ROW_DESTRUCTIBLE_WALL = 9;

            /**
             * SpriteMap constructor.
             */
            private SpritesMap() {

            }

            /**
             * Set Sprites Map.
             */
            public static void setSpritesMap() {
                SPRITESPATH.put(Type.PLAYABLE, UploadRes.getSpriteAtlas("player/player_sprites.png"));
                SPRITESPATH.put(Type.BOT, UploadRes.getSpriteAtlas("player/bot_sprites.png"));
                SPRITESPATH.put(Type.POWERUP, null);
                SPRITESPATH.put(Type.BOMB, UploadRes.getSpriteAtlas("bomb/bombs.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.FIREUP, UploadRes.getSpriteAtlas("powerUp/fire_up.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.FIREDOWN, UploadRes.getSpriteAtlas("powerUp/fire_down.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.FIREFULL, UploadRes.getSpriteAtlas("powerUp/max_fire.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.BOMBUP, UploadRes.getSpriteAtlas("powerUp/bomb_up.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.BOMBDOWN, UploadRes.getSpriteAtlas("powerUp/bomb_down.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.SPEEDUP, UploadRes.getSpriteAtlas("powerUp/speed_up.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.SPEEDDOWN, UploadRes.getSpriteAtlas("powerUp/speed_down.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.KICKBOMB, UploadRes.getSpriteAtlas("powerUp/bomb_kick.png"));
                SPRITESPOWERUPPATH.put(PowerUpType.THROWBOMB, UploadRes.getSpriteAtlas("powerUp/power_glove.png"));
                ANIMATION_ROW.put(Type.PLAYABLE, ANIMATION_ROW_PLAYABLE);
                ANIMATION_ROW.put(Type.BOT, ANIMATION_ROW_BOT);
                ANIMATION_ROW.put(Type.BOMB, ANIMATION_ROW_BOMB);
                ANIMATION_ROW.put(Type.DESTRUCTIBLE_WALL, ANIMATION_ROW_DESTRUCTIBLE_WALL);
            }

            /**
             * Set texture raising wall based on map chose.
             */
            public static void setRaisingWallTexture() {
                SPRITESPATH.put(Type.RISING_WALL, UploadRes
                        .getSpriteAtlas("wall/map" + GameLoopConstants.getLEVEL() + "/indestructible_wall.png"));
            }

            /**
             * @param t
             * @param bImage
             */
            public static void addSprites(final Type t, final BufferedImage bImage) {
                SPRITESPATH.put(t, bImage);
            }

            /**
             * @return the map of PowerUpType BufferedImage
             */
            public static Map<PowerUpType, BufferedImage> getSpritesPowerupData() {
                return SPRITESPOWERUPPATH;
            }

            /**
             * @return ANIMATION_ROW
             */
            public static Map<Type, Integer> getAnimationRow() {
                return ANIMATION_ROW;
            }

            /**
             * @return SPRITESPATH
             */
            public static Map<Type, BufferedImage> getSpritespath() {
                return SPRITESPATH;
            }

            /**
             * @return DESTRUCTIBLE_WALL_LIST
             */
            public static List<BufferedImage> getDestructibleWallList() {
                return DESTRUCTIBLE_WALL_LIST;
            }

            /**
             * @return INDESTRUCTIBLE_WALL_LIST
             */
            public static List<BufferedImage> getIndestructibleWallList() {
                return INDESTRUCTIBLE_WALL_LIST;
            }
        }

        /**
         * Entity scale settings constans.
         */
        public static final class Scale {
            /**
             * Map of type and scale of this entity .
             */
            private static final Map<Type, Float> ENTITY_SCALE = new HashMap<>();

            private static final float PLAYABLE_SCALE = 0.2f;
            private static final float BOT_SCALE = 0.5f;
            private static final float BOMB_SCALE = -0.5f;
            private static final float POWERUP_SCALE = 0f;
            private static final float DESTRUCTIBLE_WALL_SCALE = 0f;
            private static final float INDESTRUCTIBLE_WALL_SCALE = 0f;

            /**
             * Scale constructor.
             */
            private Scale() {
            }

            /**
             * @return ENTITY_SCALE.
             */
            public static Map<Type, Float> getEntityScale() {
                return ENTITY_SCALE;
            }

            /**
             * sets entity scale.
             */
            public static void setEntityScale() {
                ENTITY_SCALE.put(Type.PLAYABLE, PLAYABLE_SCALE);
                ENTITY_SCALE.put(Type.BOT, BOT_SCALE);
                ENTITY_SCALE.put(Type.BOMB, BOMB_SCALE);
                ENTITY_SCALE.put(Type.POWERUP, POWERUP_SCALE);
                ENTITY_SCALE.put(Type.DESTRUCTIBLE_WALL, DESTRUCTIBLE_WALL_SCALE);
                ENTITY_SCALE.put(Type.INDESTRUCTIBLE_WALL, INDESTRUCTIBLE_WALL_SCALE);
                ENTITY_SCALE.put(Type.RISING_WALL, INDESTRUCTIBLE_WALL_SCALE);
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
        public static final int POWER_GLOVE = 8;
        /**
         * THROWING animation.
         */
        public static final int THROWING = 7;
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
        public static final int VICTORY = 3;
        /**
         * DEFEAT animation.
         */
        public static final int DEFEAT = 2;
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
         * WALL animation.
         */
        public static final int WALL = 14;
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
        public static final int VICTORY_ANIMATION = 12;
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
         * WALL number of animations.
         */
        public static final int WALL_ANIMATION = 3;
        /**
         * EXPLOSION_COUNTER number of animations.
         */
        public static final int EXPLOSION_COUNTER = 1;
        /**
         * PLAYER_COUNTER number of animations.
         */
        public static final int PLAYER_COUNTER = 4;

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
                case WALL:
                    return WALL_ANIMATION;
                default:
                    return 1;
            }
        }

        /**
         * Constructor.
         */
        private Player() {

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

        /**
         * Entity max speed.
         */
        public static final float MAX_SPEED = 0.57f;

        /**
         * Entity min speed.
         */
        public static final float MIN_SPEED = 0.31f;
    }

    /**
     * Bomb constans.
     */
    public static class Bomb {
        /**
         * Entity bomb speed.
         */
        public static final float BASE_SPEED = 0.58f;

        /**
         * Standard range of throw bomb.
         */
        public static final int STANDARD_THROW = 3;
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
        public static final int EXPIRING_TIME = EXPLODE_DURATION * Movement.FRAME_DELAY;
        /**
         * Center position of explosion animation index.
         */
        public static final int CENTER_EXPLOSION_ANIMATION_INDEX = 8;
        /**
         * Down position of explosion animation index.
         */
        public static final int DOWN_EXPLOSION_ANIMATION_INDEX = 2;
        /**
         * Up position of explosion animation index.
         */
        public static final int UP_EXPLOSION_ANIMATION_INDEX = 6;
        /**
         * Left position of explosion animation index.
         */
        public static final int LEFT_EXPLOSION_ANIMATION_INDEX = 0;
        /**
         * Right position of explosion animation index.
         */
        public static final int RIGHT_EXPLOSION_ANIMATION_INDEX = 4;
    }

    /**
     * Destroy duration settings constans.
     */
    public static final class Destroy {

        /**
         * the number of frames the destruction of effective frames the destruction of a
         * type takes.
         */
        private static final Map<Type, Integer> DESTROY_FRAMES_PER_TYPE = new HashMap<>();

        private static final int DESTROY_FRAMES_POWERUP = 0;
        private static final int DESTROY_FRAMES_BOMB = 0;
        private static final int DESTROY_FRAMES_DESTRUCTIBLE_WALL = 15;
        private static final int DESTROY_FRAMES_RISING_WALL = 5;
        private static final int DESTROY_FRAMES_PLAYER = 70;
        /**
         * Percentage of powerup drop.
         */
        public static final float DROPPED_POWERUP_PERCENT = 0.25f;
        /**
         * standard duration of the destrution in frames.
         */
        public static final int STANDARD_FRAME_DURATION = 0;

        /**
         * constructor.
         */
        private Destroy() {
        }

        /**
         * @return DESTROY_FRAMES_PER_TYPE
         */
        public static Map<Type, Integer> getDestroyFramesPerType() {
            return DESTROY_FRAMES_PER_TYPE;
        }

        /**
         * setsDestroyFrames.
         */
        public static void setDestroyFramesPerType() {
            DESTROY_FRAMES_PER_TYPE.put(Type.POWERUP, DESTROY_FRAMES_POWERUP);
            DESTROY_FRAMES_PER_TYPE.put(Type.BOMB, DESTROY_FRAMES_BOMB);
            DESTROY_FRAMES_PER_TYPE.put(Type.DESTRUCTIBLE_WALL, DESTROY_FRAMES_DESTRUCTIBLE_WALL);
            DESTROY_FRAMES_PER_TYPE.put(Type.BOMBER, DESTROY_FRAMES_PLAYER);
            DESTROY_FRAMES_PER_TYPE.put(Type.RISING_WALL, DESTROY_FRAMES_RISING_WALL);

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
