package it.unibo.unibomber.game.model.api;

import java.awt.Rectangle;

/**
 * Menu Button interface.
 */
public interface MenuButton {
    /**
     * @return if mouse is over.
     */
    boolean isMouseOver();

    /**
     * @param mouseOver set mouse over.
     */
    void setMouseOver(boolean mouseOver);

    /**
     * @return if mouse is pressed.
     */
    boolean isMousePressed();

    /**
     * @param mousePressed set mouse pressed.
     */
    void setMousePressed(boolean mousePressed);

    /**
     * @return Rectangle of bounds.
     */
    Rectangle getBounds();

    /**
     * reset.
     */
    void reset();
}
