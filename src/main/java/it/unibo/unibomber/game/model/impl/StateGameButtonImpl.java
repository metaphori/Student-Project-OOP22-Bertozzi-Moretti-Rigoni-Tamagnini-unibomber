package it.unibo.unibomber.game.model.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.utilities.UploadRes;

import it.unibo.unibomber.utilities.Constants.UI.Buttons;

/**
 * Menu Button settings implementation class.
 */
public class StateGameButtonImpl extends AbstractMenuButton implements GameLoop {
  private final Map<Integer, BufferedImage> bufferImages = new HashMap<>();
  private final Optional<Gamestate> gameState;

  /**
   * Return gamestate of button.
   * 
   * @return gamestate.
   */
  public Optional<Gamestate> getGameState() {
    return gameState;
  }

  /**
   * @param gameState state of game to set.
   * @param x x.
   * @param y y.
   * @param w width.
   * @param h height.
   * @param rowIndex index of button.
   */
  public StateGameButtonImpl(final Optional<Gamestate> gameState, final int x, final int y, final int w, final int h,
      final int rowIndex) {
    super(x, y, w, h, Buttons.getOptionButtonSize() / 2, rowIndex);
    this.gameState = gameState;
    loadbufferImages();
  }

  private void loadbufferImages() {
    bufferImages.put(0, UploadRes.getSpriteAtlas("menu/stateGame/pause.png"));
    bufferImages.put(1, UploadRes.getSpriteAtlas("menu/stateGame/continue.png"));
    bufferImages.put(2, UploadRes.getSpriteAtlas("menu/stateGame/quit.png"));
    bufferImages.put(3, UploadRes.getSpriteAtlas("menu/stateGame/win.png"));
    bufferImages.put(4, UploadRes.getSpriteAtlas("menu/stateGame/lose.png"));
  }

  @Override
  public final void draw(final Graphics g) {
    g.drawImage(bufferImages.get(this.getRowIndex()), this.getX(), this.getY(), this.getW(), this.getH(), null);
  }

  @Override
  public void update() {
  }

  /**
   * apply gamestate based on button press.
   */
  public final void applyGamestate() {
    Gamestate.setGameState(gameState.get());
  }

}
