package it.unibo.unibomber.game.model.impl;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.api.MenuButton;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.UploadRes;

/**
 * Menu Button settings implementation class.
 */
public class OptionButtonImpl implements MenuButton, GameLoop {
  private final int x, y, width, height, rowIndex;
  private final int xButtonPosition = Constants.UI.Buttons.getBWidht() / 2;
  private final String type;
  private BufferedImage[] bufferImages;
  private boolean mouseOver, mousePressed;
  private final Rectangle bounds;

  /**
   * @param x
   * @param y
   * @param rowIndex
   * @param w
   * @param h
   * @param type
   */
  public OptionButtonImpl(final int x, final int y, final int rowIndex, final int w, final int h, final String type) {
    this.x = x;
    this.y = y;
    this.width = w;
    this.height = h;
    this.rowIndex = rowIndex;
    this.type = type;
    loadbufferImages();
    bounds = new Rectangle(x - xButtonPosition, y, Constants.UI.Buttons.getBWidht(), Constants.UI.Buttons.getBHeight());
  }

  private void loadbufferImages() {
    bufferImages = new BufferedImage[4];
    bufferImages[0] = UploadRes.getSpriteAtlas("menu/left.png");
    bufferImages[1] = UploadRes.getSpriteAtlas("menu/right.png");
    bufferImages[2] = UploadRes.getSpriteAtlas("menu/map.png");
    bufferImages[3] = UploadRes.getSpriteAtlas("menu/ok.png");

  }

  @Override
  public final void draw(final Graphics g) {
    g.drawImage(bufferImages[rowIndex], x, y, width, height, null);
  }

  @Override
  public void update() {
  }

  @Override
  public final boolean isMouseOver() {
    return mouseOver;
  }

  @Override
  public final void setMouseOver(final boolean mouseOver) {
    this.mouseOver = mouseOver;
  }

  @Override
  public final boolean isMousePressed() {
    return mousePressed;
  }

  @Override
  public final void setMousePressed(final boolean mousePressed) {
    this.mousePressed = mousePressed;
  }

  @Override
  public final Rectangle getBounds() {
    return bounds;
  }

  /**
   * setup game based on settings.
   */
  public void setupGame() {
    if (type == "ok") {
      Gamestate.setGameState(Gamestate.PLAY);
    }
  }

  @Override
  public final void reset() {
    mouseOver = false;
    mousePressed = false;
  }
}
