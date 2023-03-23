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
   * @param x
   * @param y
   * @param rowIndex
   * @param gameState
   */
  public MenuButtonImpl(final int x, final int y, final int rowIndex, final Gamestate gameState) {
    super(x, y, rowIndex);
    this.gameState = gameState;
    loadbufferImages();
  }

  private void loadbufferImages() {
    bufferImages = new BufferedImage[3];
    final BufferedImage temp = UploadRes.getSpriteAtlas(Constants.UI.SpritesMap.MENU_BUTTONS);
    for (int i = 0; i < bufferImages.length; i++) {
      bufferImages[i] = temp.getSubimage(i * Constants.UI.Buttons.WIDTH_DEFAULT,
          rowIndex * Constants.UI.Buttons.HEIGHT_DEFAULT, Constants.UI.Buttons.WIDTH_DEFAULT,
          Constants.UI.Buttons.HEIGHT_DEFAULT);
    }
  }

  @Override
  public final void draw(final Graphics g) {
    g.drawImage(bufferImages[rowIndex], x - xButtonPosition, y, Constants.UI.Buttons.getBWidht(),
        Constants.UI.Buttons.getBHeight(), null);
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
