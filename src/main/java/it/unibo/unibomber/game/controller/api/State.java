package it.unibo.unibomber.game.controller.api;

import java.awt.event.MouseEvent;

import it.unibo.unibomber.game.model.impl.MenuButtonImpl;
/**
 * State class.
 */
public interface State {
    /**
     * @param e
     * @param mb
     * @return true if mouse is in the button.
     */
    boolean isMouseIn(MouseEvent e, MenuButtonImpl mb);
}
