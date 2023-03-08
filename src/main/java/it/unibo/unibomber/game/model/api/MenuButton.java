package it.unibo.unibomber.game.model.api;
import java.awt.Rectangle;

public interface MenuButton {
    public boolean isMouseOver();
    public void setMouseOver(boolean mouseOver);
    public boolean isMousePressed();
    public void setMousePressed(boolean mousePressed);
    public Rectangle getBounds();
    public void applyGamestate();
    public void reset();
}
