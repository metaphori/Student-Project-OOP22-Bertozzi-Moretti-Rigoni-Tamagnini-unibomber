package it.unibo.unibomber.game.ecs.impl;

/**
 * This component manage the destroy of the entity
 */
public class DestroyComponent extends AbstractComponent{

    final float droppedPowerUpsPercent = 0.25f;
    private boolean isDestroyed = false;
    private int destroyFrames;


    @Override
    public void update() {
        if (this.isDestroyed) {
            if (this.destroyFrames == 0) {
                this.getEntity().getGame().removeEntity(this.getEntity());
                //TODO: add powerup drop manage
                this.isDestroyed = false;
            } else {
                this.destroyFrames--;
            }
        }
    }

    public void destroy() {
        this.isDestroyed = true;
    }

}
