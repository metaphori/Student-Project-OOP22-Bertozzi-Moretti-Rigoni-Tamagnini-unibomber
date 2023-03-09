package it.unibo.unibomber.game.controller.api;

import java.awt.Graphics;

public interface GameLoop {
    public abstract void update();

    public abstract void draw(Graphics g);
}
