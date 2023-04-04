package it.unibo.unibomber.game.model.impl;

import java.awt.Rectangle;

import it.unibo.unibomber.game.model.api.MenuButton;

/**
 * an intermediate class for menu buttons.
 */
public abstract class AbstractMenuButton implements MenuButton {

    /**
     * if mouseover o pressed true.
     */
    private boolean mouseOver, mousePressed;
    /**
     * bounds of button.
     */
    private final Rectangle bounds;
    /**
     * Position of button in panel and ind vector.
     */
    private final int x, y, w, h;
    private int rowIndex;
    /**
     * position of left top angle of bounds.
     */
    private final int xButtonPosition;

    /**
     * @param x               x coordinate of the button.
     * @param y               y coordinate of the button.
     * @param w               widht of the button.
     * @param h               hieght of the button.
     * @param xButtonPosition x coordinate of left top bound.
     * @param rowIndex        index which represents the button row.
     */
    public AbstractMenuButton(final int x, final int y, final int w, final int h, final int xButtonPosition,
            final int rowIndex) {
        this.x = x;
        this.y = y;
        this.rowIndex = rowIndex;
        this.w = w;
        this.h = h;
        this.xButtonPosition = xButtonPosition;
        bounds = new Rectangle(x, y, w,
                h);
    }

    /**
     * @return x
     */
    public final int getX() {
        return x;
    }

    /**
     * @return y
     */
    public final int getY() {
        return y;
    }

    /**
     * @return height.
     */
    public int getH() {
        return h;
    }

    /**
     * @return width.
     */
    public int getW() {
        return w;
    }

    /**
     * @return rowindex
     */
    protected final int getRowIndex() {
        return rowIndex;
    }
    /**
     * @return rowindex
     */
    protected final void setRowIndex(final int rowIndex) {
        this.rowIndex = rowIndex;
    }

    /**
     * @return xButtonPosition.
     */
    public final int getxButtonPosition() {
        return xButtonPosition;
    }

    @Override
    public final boolean isMouseOver() {
        return mouseOver;
    }

    @Override
    public final void setMouseOver(final boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    @Override
    public final boolean isMousePressed() {
        return mousePressed;
    }

    @Override
    public final void setMousePressed(final boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    @Override
    public final Rectangle getBounds() {
        return new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public final void reset() {
        mouseOver = false;
        mousePressed = false;
    }
}
