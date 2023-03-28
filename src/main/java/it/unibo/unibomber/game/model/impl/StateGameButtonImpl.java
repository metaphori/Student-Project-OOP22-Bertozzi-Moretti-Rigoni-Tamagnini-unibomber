package it.unibo.unibomber.game.model.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.utilities.UploadRes;

import it.unibo.unibomber.utilities.Constants.UI.Buttons;

/**
 * Menu Button settings implementation class.
 */
public class StateGameButtonImpl extends AbstractMenuButton implements GameLoop {
  private BufferedImage[] bufferImages;
  private final Gamestate gameState;

  /**
   * @param gameState
   * @param x
   * @param y
   * @param w
   * @param h
   * @param rowIndex
   */
  public StateGameButtonImpl(final Gamestate gameState, final int x, final int y, final int w, final int h, final int rowIndex) {
    super(x, y, w, h, Buttons.getOptionButtonSize() / 2, rowIndex);
    this.gameState = gameState;
    loadbufferImages();
  }

  private void loadbufferImages() {
    bufferImages = new BufferedImage[3];
    bufferImages[0] = UploadRes.getSpriteAtlas("menu/ok.png");
    bufferImages[1] = UploadRes.getSpriteAtlas("menu/left.png");
    bufferImages[2] = UploadRes.getSpriteAtlas("menu/right.png");

  }

  @Override
  public final void draw(final Graphics g) {
    g.drawImage(bufferImages[this.getRowIndex()], this.getX(), this.getY(), this.getW(), this.getH(), null);
  }

  @Override
  public void update() {
  }

  /**
   * apply gamestate based on button press.
   */
  public final void applyGamestate() {
    Gamestate.setGameState(gameState);
  }

}
