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
import static java.util.logging.Level.SEVERE;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Option;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.UploadRes;
import static it.unibo.unibomber.utilities.Constants.UI.MapOption;

import it.unibo.unibomber.utilities.Constants.UI.Screen;

/**
 * Menu Button settings implementation class.
 */
public class OptionButtonImpl extends AbstractMenuButton implements GameLoop {
  private final int width, height;
  private final String type;
  private BufferedImage[] bufferImages;
  private static int mapChose = 0;
  private final Logger logger = Logger.getLogger(OptionButtonImpl.class.getName());
  private final Option option;

  /**
   * @param x
   * @param y
   * @param rowIndex
   * @param w
   * @param h
   * @param type
   */
  public OptionButtonImpl(final Option option, final int x, final int y, final int rowIndex, final int w, final int h,
      final String type) {
    super(x, y, rowIndex);
    this.option = option;
    this.width = w;
    this.height = h;
    this.type = type;
    loadbufferImages();
  }

  private void loadbufferImages() {
    bufferImages = new BufferedImage[4];
    bufferImages[0] = UploadRes.getSpriteAtlas("menu/left.png");
    bufferImages[2] = UploadRes.getSpriteAtlas("menu/right.png");
    bufferImages[3] = UploadRes.getSpriteAtlas("menu/ok.png");

  }

  @Override
  public final void draw(final Graphics g) {
    bufferImages[1] = MapOption.MAP_CHOSE_LIST.get(mapChose);
    g.drawImage(bufferImages[this.getRowIndex()], this.getX(), this.getY(), width, height, null);
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
      Gamestate.setGameState(Gamestate.PLAY);
    }
    if ("left".equals(type)) {
      if (mapChose > 0) {
        mapChose--;
      }
    }
    if ("right".equals(type)) {
      if (mapChose < MapOption.MAP_CHOSE_LIST.size() - 1) {
        mapChose++;
      }
    }
  }

  private void loadDimension() {
    final Path myPath = Paths.get(MapOption.MAP_LIST.get(mapChose));
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
      final FileInputStream fstream = new FileInputStream(MapOption.MAP_LIST.get(mapChose));
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
              this.option.getWorld().getGame().addEntity(new EntityFactoryImpl(this.option.getWorld().getGame())
                  .makePlayable(new Pair<Float, Float>((float) i, (float) row)));
              break;
            case "1":
              this.option.getWorld().getGame().addEntity(new EntityFactoryImpl(this.option.getWorld().getGame())
                  .makeBot(new Pair<Float, Float>((float) i, (float) row), 1));
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

}
