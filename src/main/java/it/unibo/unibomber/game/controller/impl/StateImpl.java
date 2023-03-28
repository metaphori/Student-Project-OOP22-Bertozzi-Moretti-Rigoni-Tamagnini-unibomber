package it.unibo.unibomber.game.controller.impl;

import java.awt.event.MouseEvent;

import it.unibo.unibomber.game.model.impl.OptionButtonImpl;
import it.unibo.unibomber.game.model.impl.StateGameButtonImpl;
import it.unibo.unibomber.game.controller.api.State;
import it.unibo.unibomber.game.model.impl.MenuButtonImpl;
/**
 * State impl class.
 */
public class StateImpl implements State {

 @Override
 public final boolean isMouseIn(final MouseEvent e, final MenuButtonImpl mb) {
  return mb.getBounds().contains(e.getX() + mb.getxButtonPosition(), e.getY());
 }

@Override
public final boolean isMouseIn(final MouseEvent e, final OptionButtonImpl mb) {
    return mb.getBounds().contains(e.getX(), e.getY());
}
@Override
public final boolean isMouseIn(final MouseEvent e, final StateGameButtonImpl mb) {
    return mb.getBounds().contains(e.getX(), e.getY());
}

}
