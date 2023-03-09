package it.unibo.unibomber.game.model.api;

import java.awt.Rectangle;

public interface MenuButton {
    boolean isMouseOver();

    void setMouseOver(boolean mouseOver);

    boolean isMousePressed();

    void setMousePressed(boolean mousePressed);

    Rectangle getBounds();

    void applyGamestate();

    void reset();
}
