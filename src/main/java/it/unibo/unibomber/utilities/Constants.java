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
			public final static float SCALE = 3f;
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
			}
		}
	}	
}
