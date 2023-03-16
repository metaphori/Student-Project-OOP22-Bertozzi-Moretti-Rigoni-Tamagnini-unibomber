package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.view.ExplosionView;
import it.unibo.unibomber.game.model.api.Game;

public class Explosion implements GameLoop {
    ExplosionView view;
    private final Game game;

    public Explosion(final Game game) {
        this.game = game;
        view = new ExplosionView(this);
    }
    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
        view.draw(g);
    }

}
