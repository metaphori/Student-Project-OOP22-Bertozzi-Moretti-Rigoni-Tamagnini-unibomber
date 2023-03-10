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
     * @param mouseOver
     */
    void setMouseOver(boolean mouseOver);

    /**
     * @return if mouse is pressed.
     */
    boolean isMousePressed();

    /**
     * @param mousePressed
     */
    void setMousePressed(boolean mousePressed);

    /**
     * @return Rectangle of bounds.
     */
    Rectangle getBounds();

    /**
     * set game state.
     */
    void applyGamestate();

    /**
     * reset.
     */
    void reset();
}
