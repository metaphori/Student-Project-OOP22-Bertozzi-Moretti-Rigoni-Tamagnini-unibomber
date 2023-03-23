package it.unibo.unibomber.game.model.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.utilities.UploadRes;

/**
 * Menu Button settings implementation class.
 */
public class OptionButtonImpl extends AbstractMenuButton implements GameLoop {
  private final int width, height;
  private final String type;
  private BufferedImage[] bufferImages;

  /**
   * @param x
   * @param y
   * @param rowIndex
   * @param w
   * @param h
   * @param type
   */
  public OptionButtonImpl(final int x, final int y, final int rowIndex, final int w, final int h, final String type) {
    super(x, y, rowIndex);
    this.width = w;
    this.height = h;
    this.type = type;
    loadbufferImages();
  }

  private void loadbufferImages() {
    bufferImages = new BufferedImage[4];
    bufferImages[0] = UploadRes.getSpriteAtlas("menu/left.png");
    bufferImages[1] = UploadRes.getSpriteAtlas("menu/map.png");
    bufferImages[2] = UploadRes.getSpriteAtlas("menu/right.png");
    bufferImages[3] = UploadRes.getSpriteAtlas("menu/ok.png");

  }

  @Override
  public final void draw(final Graphics g) {
    g.drawImage(bufferImages[rowIndex], x, y, width, height, null);
  }

  @Override
  public void update() {
  }

  /**
   * setup game based on settings.
   */
  public void setupGame() {
    if ("ok".equals(type)) {
      Gamestate.setGameState(Gamestate.PLAY);
    }
  }
}
