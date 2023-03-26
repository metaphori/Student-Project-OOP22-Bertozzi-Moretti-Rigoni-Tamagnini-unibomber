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

/**
 * Explosion controller.
 */
public final class Explosion implements GameLoop {
    private final ExplosionView view;
    private List<Optional<Entity>> explode;
    private final List<Integer> power;

    /**
     * Constructor.
     */
    public Explosion() {
        this.power = new ArrayList<>();
        view = new ExplosionView(this);
        explode = new ArrayList<>();
    }

    /**
     * Set entity that is exploding.
     * 
     * @param entity
     */
    public void setEntityExploding(final Entity entity) {
        this.explode.add(Optional.of(entity));
        this.power.add(entity.getComponent(PowerUpListComponent.class).get().getBombFire());
    }

    @Override
    public void update() {
    }

    /**
     * @return list of coordinate of explosion.
     */
    public List<List<Pair<Integer, Integer>>> getExplosionList() {
        final List<List<Pair<Integer, Integer>>> l = new ArrayList<>();
        if (!explode.isEmpty()) {
            for (final Optional<Entity> e : explode) {
                if (e.isPresent()) {
                    l.add(e.get().getComponent(ExplodeComponent.class).get().getExplosions());
                }
            }
            return new ArrayList<>(l);
        }
        return List.of();
    }

    /**
     * @return power of the bomb
     * @param id
     */
    public int getBombPower(final int id) {
        return power.get(id);
    }

    @Override
    public void draw(final Graphics g) {
        view.draw(g);
    }

    /**
     * @param id
     * @return entity of that id.
     */
    public Entity gEntity(final int id) {
        return explode.get(id).get();
    }

    /**
     * reset explosion list.
     */
    public void resetEntity() {
        this.explode = new ArrayList<>();
    }
}
