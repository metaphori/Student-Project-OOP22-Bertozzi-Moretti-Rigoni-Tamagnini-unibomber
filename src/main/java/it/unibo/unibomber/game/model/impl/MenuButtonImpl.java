package it.unibo.unibomber.game.model.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.UploadRes;

/**
 * Menu Button settings implementation class.
 */
public class MenuButtonImpl extends AbstractMenuButton implements GameLoop {
  private final Gamestate gameState;
  private BufferedImage[] bufferImages;

  /**
   * @param x x.
   * @param y y.
   * @param w width.
   * @param h height.
   * @param rowIndex button index.
   * @param gameState state game of button.
   */
  public MenuButtonImpl(final int x, final int y, final int w, final int h, final int rowIndex, final Gamestate gameState) {
    super(x, y, w, h, Constants.UI.Buttons.getBWidht() / 2, rowIndex);
    this.gameState = gameState;
    loadbufferImages();
  }

  private void loadbufferImages() {
    bufferImages = new BufferedImage[3];
    final BufferedImage temp = UploadRes.getSpriteAtlas(Constants.UI.SpritesMap.MENU_BUTTONS);
    for (int i = 0; i < bufferImages.length; i++) {
      bufferImages[i] = temp.getSubimage(i * Constants.UI.Buttons.WIDTH_DEFAULT,
          this.getRowIndex() * Constants.UI.Buttons.HEIGHT_DEFAULT, Constants.UI.Buttons.WIDTH_DEFAULT,
          Constants.UI.Buttons.HEIGHT_DEFAULT);
    }
  }

  @Override
  public final void draw(final Graphics g) {
    g.drawImage(bufferImages[this.getRowIndex()], this.getX() - getxButtonPosition(), this.getY(),
        this.getW(), this.getH(), null);
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
