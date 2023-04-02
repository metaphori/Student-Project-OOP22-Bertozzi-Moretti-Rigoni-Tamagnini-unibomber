package it.unibo.unibomber.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.view.WorldPanelImpl;

/**
 * KeyboardInputsImpl class.
 */
public final class KeyboardInputsImpl implements KeyListener {
  private final WorldPanelImpl worldPanel;

  /**
   * KeyboardInputsImpl constructor.
   * 
   * @param worldPanel
   */
  public KeyboardInputsImpl(final WorldPanelImpl worldPanel) {
    this.worldPanel = worldPanel;
  }

  @Override
  public void keyPressed(final KeyEvent e) {
    switch (Gamestate.getGamestate()) {
      case MENU:
        break;
      case PLAY:
        worldPanel.getWorld().getPlay().keyPressed(e);
        break;
      case PAUSE:
        worldPanel.getWorld().getEndGame().keyPressed(e);
        break;
      default:
        break;
    }
  }

  @Override
  public void keyReleased(final KeyEvent e) {
    switch (Gamestate.getGamestate()) {
      case MENU:
        break;
      case PLAY:
        worldPanel.getWorld().getPlay().keyReleased(e);
        break;
      case PAUSE:
        worldPanel.getWorld().getEndGame().keyReleased(e);
        break;
      default:
        break;
    }
  }

  @Override
  public void keyTyped(final KeyEvent e) {
  }
}
