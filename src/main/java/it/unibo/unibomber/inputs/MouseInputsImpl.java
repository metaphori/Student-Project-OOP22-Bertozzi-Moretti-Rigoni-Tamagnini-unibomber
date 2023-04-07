package it.unibo.unibomber.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.view.WorldPanelImpl;

/**
 * MouseInputsImpl class.
 */
public final class MouseInputsImpl implements MouseListener {

  private final WorldPanelImpl worldPanel;

  /**
   * MouseInputsImpl constructor.
   * 
   * @param worldPanel Panel of world.
   */
  public MouseInputsImpl(final WorldPanelImpl worldPanel) {
    this.worldPanel = new WorldPanelImpl(worldPanel);
  }

  @Override
  public void mouseClicked(final MouseEvent e) {

  }

  @Override
  public void mousePressed(final MouseEvent e) {
    switch (Gamestate.getGamestate()) {
      case MENU:
        worldPanel.getMenu().mousePressed(e);
        break;
      case OPTION:
        worldPanel.getOption().mousePressed(e);
        break;
      case PLAY:
        break;
      case PAUSE:
      case WIN:
      case LOSE:
        worldPanel.getEndGame().mousePressed(e);
        break;
      default:
        break;

    }
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    switch (Gamestate.getGamestate()) {
      case MENU:
        worldPanel.getMenu().mouseReleased(e);
        break;
      case OPTION:
        worldPanel.getOption().mouseReleased(e);
        break;
      case PLAY:
        break;
      case PAUSE:
      case WIN:
      case LOSE:
        worldPanel.getEndGame().mouseReleased(e);
        break;
      default:
        break;

    }
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
  }

  @Override
  public void mouseExited(final MouseEvent e) {
  }
}
