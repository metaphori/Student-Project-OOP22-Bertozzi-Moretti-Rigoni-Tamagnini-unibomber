package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.model.impl.ExplosionImpl;
import it.unibo.unibomber.game.view.ExplosionView;
import it.unibo.unibomber.utilities.Direction;

/**
 * Explosion controller.
 */
public final class Explosion implements GameLoop {
    private final ExplosionView view;
    private final ExplosionImpl model;
    private List<Entity> explode;

    /**
     * @return Explode List.
     */
    public List<Entity> getExplode() {
        return new ArrayList<>(explode);
    }

    /**
     * Constructor.
     */
    public Explosion() {
        view = new ExplosionView(this);
        model = new ExplosionImpl();
        explode = new ArrayList<>();
    }

    /**
     * Set entity that is exploding.
     * 
     * @param entity bomb exploding.
     */
    public void setEntityExploding(final Entity entity) {
        this.explode.add(entity);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(final Graphics g) {
        view.draw(g);
    }

    /**
     * @param id id of bomb.
     * @return entity of that id.
     */
    public Entity gEntity(final int id) {
        return explode.get(id);
    }

    /**
     * reset explosion list.
     */
    public void resetEntity() {
        this.explode = new ArrayList<>();
    }

    /**
     * @param i row.
     * @param j col.
     * @return animation of that position.
     */
    public BufferedImage getAnimations(final int i, final int j) {
        return model.getAnimations(i, j);
    }

    /**
     * @return direction of explosion.
     */
    public int getIndexDirection() {
        return model.getIndexDirection();
    }

    /**
     * change direction explosion.
     * 
     * @param dir direction of explosion.
     */
    public void setDirectionIndex(final Direction dir) {
        model.setDirectionIndex(dir);
    }
}
