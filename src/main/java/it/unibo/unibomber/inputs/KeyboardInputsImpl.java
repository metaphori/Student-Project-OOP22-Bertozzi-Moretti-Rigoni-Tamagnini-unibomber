package it.unibo.unibomber.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.view.WorldPanelImpl;

/**
 * KeyboardInputsImpl class.
 */
public final class KeyboardInputsImpl implements KeyListener {
  private final List<WorldPanelImpl> worldPanel;

  /**
   * KeyboardInputsImpl constructor.
   * 
   * @param worldPanel Panel of world.
   */
  public KeyboardInputsImpl(final WorldPanelImpl worldPanel) {
    this.worldPanel = new ArrayList<>();
    this.worldPanel.add(worldPanel);
  }

  @Override
  public void keyPressed(final KeyEvent e) {
    switch (Gamestate.getGamestate()) {
      case MENU:
        break;
      case PLAY:
        worldPanel.get(0).getWorld().getPlay().keyPressed(e);
        break;
      case PAUSE:
        worldPanel.get(0).getWorld().getEndGame().keyPressed(e);
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
        worldPanel.get(0).getWorld().getPlay().keyReleased(e);
        break;
      case PAUSE:
        worldPanel.get(0).getWorld().getEndGame().keyReleased(e);
        break;
      default:
        break;
    }
  }

  @Override
  public void keyTyped(final KeyEvent e) {
  }
}
