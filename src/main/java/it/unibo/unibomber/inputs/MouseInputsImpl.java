package it.unibo.unibomber.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.view.WorldPanelImpl;

/**
 * MouseInputsImpl class.
 */
public final class MouseInputsImpl implements MouseListener {

 private WorldPanelImpl worldPanel;

 /**
  * MouseInputsImpl constructor.
  * 
  * @param worldPanel
  */
 public MouseInputsImpl(final WorldPanelImpl worldPanel) {
  this.worldPanel = worldPanel;
 }

 @Override
 public void mouseClicked(final MouseEvent e) {

 }

 @Override
 public void mousePressed(final MouseEvent e) {
  switch (Gamestate.getGamestate()) {
   case MENU:
    worldPanel.getWorld().getMenu().mousePressed(e);
    break;
   case PLAY:
    break;
   default:
    break;

  }
 }

 @Override
 public void mouseReleased(final MouseEvent e) {
  switch (Gamestate.getGamestate()) {
   case MENU:
    worldPanel.getWorld().getMenu().mouseReleased(e);
    break;
   case PLAY:
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
