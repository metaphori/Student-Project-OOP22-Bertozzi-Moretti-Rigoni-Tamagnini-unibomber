package it.unibo.unibomber.utilities;

import java.util.HashMap;
import java.util.Map;

import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;

public class Constants {
    public static class UI {
		public static class Buttons {
			public static final int WIDTH_DEFAULT = 140; //single button witdh
			public static final int HEIGHT_DEFAULT = 40; //single button height
			public static final int B_WIDTH = (int) (WIDTH_DEFAULT * (Game.SCALE-0.5f)); //button width scale
			public static final int B_HEIGHT = (int) (HEIGHT_DEFAULT * (Game.SCALE-0.5f)); //button height scale
		}
		public static class Game {
			public final static int TILES_DEFAULT = 16;
			public final static float SCALE = 2f;
			public final static int TILES_WIDTH = 15;
			public final static int TILES_HEIGHT = 18;
			public final static int TILES_SIZE = (int) (TILES_DEFAULT * SCALE);
			public final static int G_WIDTH = TILES_SIZE * TILES_WIDTH;
			public final static int G_HEIGHT = TILES_SIZE * TILES_HEIGHT;
		
		}
		public static class GameLoop {	
			public final static int FPS_SET = 60;
		}
		public static class SpritesMap{
			public static final String MENU_BUTTONS = "button_atlas.png";
			public static final String MENU_BACKGROUND = "menu_background.png";
			public static final String SHADOW = "shadow.png";
			
			public static final Map<Type,String> spritesPath= new HashMap<>();
			public static final Map<PowerUpType,String> spritesPoweUpPath= new HashMap<>();
			public SpritesMap(){
				//TYPE
				spritesPath.put(Type.PLAYABLE, "stand.png");
				spritesPath.put(Type.BOT, null);
				spritesPath.put(Type.POWERUP, null);
				spritesPath.put(Type.EMPTY_AREA, "grass.png");
				spritesPath.put(Type.RISING_WALL, null);
				spritesPath.put(Type.DESTRUCTIBLE_WALL, null);
				spritesPath.put(Type.INDESTRUCTIBLE_WALL, "indestructible_wall.png");
				spritesPath.put(Type.BOMB, "bomba.png");
				spritesPoweUpPath.put(PowerUpType.FIREUP, "fire_up.png");

			}
		}
	}

	public static class Player {
		public static final int STANDING = 0;
		public static final int WALKING = 1;
		public static final int POWER_GLOVE = 2;
		public static final int THROWING = 3;
		public static final int PUNCH = 4;
		public static final int SHIELD = 5;
		public static final int STUN = 6;
		public static final int VICTORY = 7;
		public static final int DEFEAT = 8;
		public static final int IDLE = 9;
		public static final int JUMPUP_DOWN = 10;
		public static final int CRYING = 11;
		public static final int DANCING = 12;

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case STANDING:
			case PUNCH:
				return 3;
			case WALKING:
			case POWER_GLOVE:
			case VICTORY:
				return 6;
			case THROWING:
				return 5;
			case CRYING:
				return 4;
			case SHIELD:
				return 2;
			case STUN:
				return 8;
			case DEFEAT:
				return 14;
			case JUMPUP_DOWN:
				return 9;
			case DANCING:
				return 8;
			default:
				return 1;
			}
		}
	}
	public static class PowerUp {
		public static final int complexPercentage = 25;
	}
	
}
