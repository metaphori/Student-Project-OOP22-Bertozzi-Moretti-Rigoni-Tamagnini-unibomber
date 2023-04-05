package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
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
    private ExplosionView view;
    private final ExplosionImpl model;

    /**
     * @return Explode List.
     */
    public List<Entity> getExplode() {
        return model.getExplode();
    }

    /**
     * Explosion constructor.
     * @param controller controller.
     */
    public Explosion(final Explosion controller) {
        model = new ExplosionImpl();
        setExplodeList(controller);
    }

    /**
     * Start Explosion View.
     */
    public Explosion() {
        model = new ExplosionImpl();
        view = new ExplosionView(this);
    }

    /**
     * @param ex controller.
     */
    public void setExplodeList(final Explosion ex) {
        this.model.setExplode(ex.getExplode());
    }

    /**
     * Set entity that is exploding.
     * 
     * @param entity bomb exploding.
     */
    public void setEntityExploding(final Entity entity) {
        model.setEntityExploding(entity);
        view.updateList(this);
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
        return model.gEntity(id);
    }

    /**
     * reset explosion list.
     */
    public void resetEntity() {
        model.resetEntity();
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
