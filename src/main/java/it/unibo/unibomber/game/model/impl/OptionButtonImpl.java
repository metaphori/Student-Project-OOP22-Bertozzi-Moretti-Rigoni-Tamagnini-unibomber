package it.unibo.unibomber.game.model.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.api.Handicap;
import it.unibo.unibomber.game.controller.impl.Option;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.PowerUpHandlerComponent;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.UploadRes;

import it.unibo.unibomber.utilities.Constants.UI.Buttons;
import it.unibo.unibomber.utilities.Constants.UI.Screen;
import it.unibo.unibomber.utilities.Constants.UI.SpritesMap;
import it.unibo.unibomber.utilities.Constants.UI.GameLoopConstants;
import static it.unibo.unibomber.utilities.Constants.UI.MapOption;

/**
 * Menu Button settings implementation class.
 */
public class OptionButtonImpl extends AbstractMenuButton implements GameLoop {
  private final String type;
  private PowerUpType pType;
  private final Map<Integer, BufferedImage> bufferImages = new HashMap<>();
  private final Logger logger = Logger.getLogger(OptionButtonImpl.class.getName());
  private final Option option;
  private final int index;

  /**
   * @param option
   * @param x
   * @param y
   * @param rowIndex
   * @param w
   * @param h
   * @param type
   */
  public OptionButtonImpl(final Option option, final int x, final int y, final int rowIndex, final int w, final int h,
      final String type) {
    super(x, y, w, h, Buttons.getOptionButtonSize() / 2, rowIndex);
    this.option = option;
    this.type = type;
    this.index = -1;
    loadbufferImages();
  }

  /**
   * @param option
   * @param x
   * @param y
   * @param rowIndex
   * @param w
   * @param h
   * @param type
   * @param pType
   */
  public OptionButtonImpl(final Option option, final int x, final int y, final int rowIndex, final int w, final int h,
      final String type, final PowerUpType pType) {
    super(x, y, w, h, Buttons.getOptionButtonSize() / 2, rowIndex);
    this.option = option;
    this.type = type;
    this.pType = pType;
    this.index = -1;
    loadbufferImages();
  }

  /**
   * @param option
   * @param x
   * @param y
   * @param rowIndex
   * @param w
   * @param h
   * @param type
   * @param index
   */
  public OptionButtonImpl(final Option option, final int x, final int y, final int rowIndex, final int w, final int h,
      final String type, final int index) {
    super(x, y, w, h, Buttons.getOptionButtonSize() / 2, rowIndex);
    this.option = option;
    this.type = type;
    this.index = index;
    loadbufferImages();
  }

  private void loadbufferImages() {
    bufferImages.put(Handicap.LEFT.getIndex(), UploadRes.getSpriteAtlas("menu/option/left.png"));
    bufferImages.put(Handicap.RIGHT.getIndex(), UploadRes.getSpriteAtlas("menu/option/right.png"));
    bufferImages.put(Handicap.OK.getIndex(), UploadRes.getSpriteAtlas("menu/option/ok.png"));
    bufferImages.put(Handicap.BOTNUMBER.getIndex(), UploadRes.getSpriteAtlas("menu/option/botNumber.png"));
    bufferImages.put(Handicap.PLUS.getIndex(), UploadRes.getSpriteAtlas("menu/option/+.png"));
    bufferImages.put(Handicap.MINUS.getIndex(), UploadRes.getSpriteAtlas("menu/option/-.png"));
    bufferImages.put(Handicap.PLAYER.getIndex(), UploadRes.getSpriteAtlas("menu/option/player.png"));
    bufferImages.put(Handicap.PLAYER_HOVER.getIndex(), UploadRes.getSpriteAtlas("menu/option/player_hover.png"));
    bufferImages.put(Handicap.BOT.getIndex(), UploadRes.getSpriteAtlas("menu/option/bot.png"));
    bufferImages.put(Handicap.DELETE.getIndex(), UploadRes.getSpriteAtlas("menu/option/delete.png"));
    bufferImages.put(Handicap.DELETE_ALL.getIndex(), UploadRes.getSpriteAtlas("menu/option/delete_all.png"));
    bufferImages.put(Handicap.BOMBUP.getIndex(), UploadRes.getSpriteAtlas("powerUp/bomb_up.png"));
    bufferImages.put(Handicap.FIREUP.getIndex(), UploadRes.getSpriteAtlas("powerUp/fire_up.png"));
    bufferImages.put(Handicap.SPEEDUP.getIndex(), UploadRes.getSpriteAtlas("powerUp/speed_up.png"));
    bufferImages.put(Handicap.BOMBKICK.getIndex(), UploadRes.getSpriteAtlas("powerUp/bomb_kick.png"));
    bufferImages.put(Handicap.POWERGLOVE.getIndex(), UploadRes.getSpriteAtlas("powerUp/power_glove.png"));
  }

  @Override
  public final void draw(final Graphics g) {
    bufferImages.put(0, MapOption.MAP_CHOSE_LIST.get(GameLoopConstants.getLEVEL()));
    g.drawImage(bufferImages.get(this.getRowIndex()), this.getX(), this.getY(), this.getW(), this.getH(), null);
  }

  @Override
  public void update() {
  }

  /**
   * setup game based on settings.
   */
  public void setupGame() {
    if ("ok".equals(type)) {
      loadDimension();
      this.option.getWorld().createGame();
      extractData();
      SpritesMap.addSprites(Type.DESTRUCTIBLE_WALL,
          UploadRes.getSpriteAtlas("wall/map" + GameLoopConstants.getLEVEL() + "/destructible_wall.png"));
      SpritesMap.addSprites(Type.INDESTRUCTIBLE_WALL,
          UploadRes.getSpriteAtlas("wall/map" + GameLoopConstants.getLEVEL() + "/indestructible_wall.png"));
      SpritesMap.setRaisingWallTexture();
      option.getWorld().setPlay();
      Gamestate.setGameState(Gamestate.PLAY);
    }
    if ("left".equals(type) && GameLoopConstants.getLEVEL() > 0) {
      GameLoopConstants.setLEVEL(GameLoopConstants.getLEVEL() - 1);
    }
    if ("right".equals(type) && GameLoopConstants.getLEVEL() < MapOption.MAP_CHOSE_LIST.size() - 1) {
      GameLoopConstants.setLEVEL(GameLoopConstants.getLEVEL() + 1);
    }
    if ("+".equals(type) && MapOption.getNumberOfBot() < 3) {
      MapOption.incrementBot();
    }
    if ("-".equals(type) && MapOption.getNumberOfBot() > 1) {
      MapOption.decrementBot();
    }
  }

  private void loadDimension() {
    final Path myPath = Paths.get(MapOption.MAP_LIST.get(GameLoopConstants.getLEVEL()));
    try {
      final String[] strArray = Files.lines(myPath)
          .map(s -> s.split(" "))
          .findFirst()
          .get();
      Screen.setTilesWidth(Integer.parseInt(strArray[0]));
      Screen.setTilesHeight(Integer.parseInt(strArray[1]));
    } catch (IOException e) {
      logger.log(SEVERE, e.getMessage());
    }
  }

  private void extractData() {
    try {
      int botPlaced = 0;
      Entity e;
      final FileInputStream fstream = new FileInputStream(MapOption.MAP_LIST.get(GameLoopConstants.getLEVEL()));
      final DataInputStream in = new DataInputStream(fstream);
      final BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String strLine;
      Integer row = 0;
      br.readLine();
      strLine = br.readLine();
      while (strLine != null) {
        final String[] tokens = strLine.split(" ");
        for (int i = 0; i < tokens.length; i++) {
          switch (tokens[i]) {
            case "0":
              e = new EntityFactoryImpl(this.option.getWorld().getGame())
                  .makePlayable(new Pair<Float, Float>((float) i, (float) row));
              for (final PowerUpType pt : option.getIndexListPowerUp(0)) {
                e.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(pt);
              }
              this.option.getWorld().getGame().addEntity(e);
              break;
            case "1":
              if (botPlaced < MapOption.getNumberOfBot()) {
                botPlaced++;
                e = new EntityFactoryImpl(this.option.getWorld().getGame())
                    .makeBot(new Pair<Float, Float>((float) i, (float) row), 1);
                for (final PowerUpType pt : option.getIndexListPowerUp(botPlaced)) {
                  e.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(pt);
                }
                this.option.getWorld().getGame().addEntity(e);
              }
              break;
            case "2":
              this.option.getWorld().getGame().addEntity(new EntityFactoryImpl(this.option.getWorld().getGame())
                  .makePowerUp(new Pair<Float, Float>((float) i, (float) row),
                      PowerUpType.getRandomPowerUp()));
              break;
            case "5":
              this.option.getWorld().getGame().addEntity(new EntityFactoryImpl(this.option.getWorld().getGame())
                  .makeDestructibleWall(new Pair<Float, Float>((float) i, (float) row)));
              break;
            case "6":
              this.option.getWorld().getGame().addEntity(new EntityFactoryImpl(this.option.getWorld().getGame())
                  .makeIndestructibleWall(new Pair<Float, Float>((float) i, (float) row)));
              break;
            default:
              break;
          }
        }
        row++;
        strLine = br.readLine();
      }
      in.close();
    } catch (IOException e) {
      logger.log(SEVERE, e.getMessage());
    }
  }

  /**
   * @return type of button.
   */
  public String getType() {
    return type;
  }

  /**
   * @return type of button.
   */
  public PowerUpType getPType() {
    return pType;
  }

  /**
   * @return index in power up list.
   */
  public int getIndex() {
    return index;
  }
}
