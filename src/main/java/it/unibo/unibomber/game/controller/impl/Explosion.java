package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.impl.ExplodeComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpListComponent;
import it.unibo.unibomber.game.view.ExplosionView;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.game.model.api.Game;

/**
 * Explosion controller.
 */
public final class Explosion implements GameLoop {
    private ExplosionView view;
    private final Game game;
    private Optional<Entity> explode;
    private int power;

    /**
     * Constructor.
     * 
     * @param game
     */
    public Explosion(final Game game) {
        this.power = 1;
        this.game = game;
        view = new ExplosionView(this);
        explode = Optional.empty();
    }

    /**
     * Set entity that is exploding.
     * 
     * @param entity
     */
    public void setEntityExploding(final Entity entity) {
        this.explode = Optional.of(entity);
        power = explode.get().getComponent(PowerUpListComponent.class).get().getBombFire();
    }

    @Override
    public void update() {
    }

    /**
     * @return list of coordinate of explosion.
     */
    public List<Pair<Integer, Integer>> getExplosionList() {
        if (explode.isPresent()) {
            return new ArrayList<>(explode.get().getComponent(ExplodeComponent.class).get().getExplosions());
        }
        return List.of();
    }

    /**
     * @return power of the bomb
     */
    public int getBombPower() {
        return power;
    }
    @Override
    public void draw(final Graphics g) {
        view.draw(g);
    }
}
