package it.unibo.unibomber.controller.api;

import java.awt.event.MouseEvent;

import it.unibo.unibomber.game.model.impl.MenuButtonImpl;
import it.unibo.unibomber.game.model.impl.OptionButtonImpl;
import it.unibo.unibomber.game.model.impl.StateGameButtonImpl;
/**
 * State class.
 */
public interface State {
    /**
     * @param e event.
     * @param mb button.
     * @return true if mouse is in the button.
     */
    boolean isMouseIn(MouseEvent e, MenuButtonImpl mb);
    /**
     * @param e event.
     * @param mb button.
     * @return true if mouse is in the button.
     */
    boolean isMouseIn(MouseEvent e, OptionButtonImpl mb);
    /**
     * @param e event.
     * @param mb button.
     * @return true if mouse is in the button.
     */
    boolean isMouseIn(MouseEvent e, StateGameButtonImpl mb);
}
