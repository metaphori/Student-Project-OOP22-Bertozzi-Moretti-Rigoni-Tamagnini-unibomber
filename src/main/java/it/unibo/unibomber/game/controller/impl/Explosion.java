package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
import java.util.List;
import java.util.Optional;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.impl.ExplodeComponent;
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

    /**
     * Constructor.
     * @param game
     */
    public Explosion(final Game game) {
        this.game = game;
        view = new ExplosionView(this);
        explode = Optional.empty();
    }

    /**
     * Set entity that is exploding.
     * @param entity
     */
    public void setEntityExploding(final Entity entity) {
        this.explode = Optional.of(entity);
    }

    @Override
    public void update() {
    }

    /**
     * @return list of coordinate of explosion.
     */
    public Optional<List<Pair<Integer, Integer>>> getExplosionList() {
        if (explode.isPresent()) {
            if (!explode.get().getComponent(ExplodeComponent.class).get().getExplosions().isEmpty()) {
                return Optional.of(explode.get().getComponent(ExplodeComponent.class).get().getExplosions());
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public void draw(final Graphics g) {
        view.draw(g);
    }

}
