package it.unibo.unibomber.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.view.WorldPanelImpl;

/**
 * MouseInputsImpl class.
 */
public final class MouseInputsImpl implements MouseListener {

  private final List<WorldPanelImpl> worldPanel;

  /**
   * MouseInputsImpl constructor.
   * 
   * @param worldPanel Panel of world.
   */
  public MouseInputsImpl(final WorldPanelImpl worldPanel) {
    this.worldPanel = new ArrayList<>();
    this.worldPanel.add(worldPanel);
  }

  @Override
  public void mouseClicked(final MouseEvent e) {

  }

  @Override
  public void mousePressed(final MouseEvent e) {
    switch (Gamestate.getGamestate()) {
      case MENU:
        worldPanel.get(0).getWorld().getMenu().mousePressed(e);
        break;
      case OPTION:
        worldPanel.get(0).getWorld().getOption().mousePressed(e);
        break;
      case PLAY:
        break;
      case PAUSE:
      case WIN:
      case LOSE:
        worldPanel.get(0).getWorld().getEndGame().mousePressed(e);
        break;
      default:
        break;

    }
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    switch (Gamestate.getGamestate()) {
      case MENU:
        worldPanel.get(0).getWorld().getMenu().mouseReleased(e);
        break;
      case OPTION:
        worldPanel.get(0).getWorld().getOption().mouseReleased(e);
        break;
      case PLAY:
        break;
      case PAUSE:
      case WIN:
      case LOSE:
        worldPanel.get(0).getWorld().getEndGame().mouseReleased(e);
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
