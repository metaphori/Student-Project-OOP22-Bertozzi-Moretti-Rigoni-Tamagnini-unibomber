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
     * @param g
     */
    void draw(Graphics g);
}
