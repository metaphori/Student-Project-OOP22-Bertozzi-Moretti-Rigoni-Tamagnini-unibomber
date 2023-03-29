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
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Level.SEVERE;

import it.unibo.unibomber.game.controller.api.GameLoop;
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
  private String type;
  private PowerUpType pType;
  private BufferedImage[] bufferImages;
  private final Logger logger = Logger.getLogger(OptionButtonImpl.class.getName());
  private final Option option;
  private int index;

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
    bufferImages = new BufferedImage[17];
    bufferImages[0] = UploadRes.getSpriteAtlas("menu/option/left.png");
    bufferImages[2] = UploadRes.getSpriteAtlas("menu/option/right.png");
    bufferImages[3] = UploadRes.getSpriteAtlas("menu/option/ok.png");
    bufferImages[4] = UploadRes.getSpriteAtlas("menu/option/player.png");
    bufferImages[5] = UploadRes.getSpriteAtlas("menu/option/player_hover.png");
    bufferImages[6] = UploadRes.getSpriteAtlas("menu/option/bot.png");
    bufferImages[7] = UploadRes.getSpriteAtlas("menu/option/botNumber.png");
    bufferImages[8] = UploadRes.getSpriteAtlas("menu/option/+.png");
    bufferImages[9] = UploadRes.getSpriteAtlas("menu/option/-.png");
    bufferImages[10] = UploadRes.getSpriteAtlas("powerUp/bomb_up.png");
    bufferImages[11] = UploadRes.getSpriteAtlas("powerUp/fire_up.png");
    bufferImages[12] = UploadRes.getSpriteAtlas("powerUp/speed_up.png");
    bufferImages[13] = UploadRes.getSpriteAtlas("powerUp/bomb_kick.png");
    bufferImages[14] = UploadRes.getSpriteAtlas("powerUp/power_glove.png");
    bufferImages[15] = UploadRes.getSpriteAtlas("menu/option/delete.png");
    bufferImages[16] = UploadRes.getSpriteAtlas("menu/option/delete_all.png");
  }

  @Override
  public final void draw(final Graphics g) {
    bufferImages[1] = MapOption.MAP_CHOSE_LIST.get(GameLoopConstants.getLEVEL());
    g.drawImage(bufferImages[this.getRowIndex()], this.getX(), this.getY(), this.getW(), this.getH(), null);
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
      option.getWorld().setPlay();
      Gamestate.setGameState(Gamestate.PLAY);
      System.out.println(this.option.getWorld().getGame().getEntities().stream().filter(e -> e.getType()== Type.BOMBER).collect(Collectors.toList()).get(0).getComponent(PowerUpHandlerComponent.class).get().getPowerUpList());
    }
    if ("left".equals(type) && GameLoopConstants.getLEVEL() > 0) {
      GameLoopConstants.setLEVEL(GameLoopConstants.getLEVEL() - 1);
    }
    if ("right".equals(type) && GameLoopConstants.getLEVEL() < MapOption.MAP_CHOSE_LIST.size() - 1) {
      GameLoopConstants.setLEVEL(GameLoopConstants.getLEVEL() + 1);
    }
    if ("+".equals(type) && MapOption.getNumberOfBot() < 8) {
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
              for (PowerUpType pt : option.getListPowerUp(0)) {
                e.getComponent(PowerUpHandlerComponent.class).get().addPowerUp(pt);
              }
              this.option.getWorld().getGame().addEntity(e);
              break;
            case "1":
              if (botPlaced < MapOption.getNumberOfBot()) {
                botPlaced++;
                e = new EntityFactoryImpl(this.option.getWorld().getGame())
                    .makeBot(new Pair<Float, Float>((float) i, (float) row), 1);
                for (PowerUpType pt : option.getListPowerUp(botPlaced)) {
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
