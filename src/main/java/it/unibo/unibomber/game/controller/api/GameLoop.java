package it.unibo.unibomber.game.controller.api;

import java.awt.Graphics;
/**
 * GameLoop class.
 */
public interface GameLoop {
    /**
     * Update.
     */
    void update();

    /**
     * Draw.
     * @param g graphics.
     */
    void draw(Graphics g);
}
