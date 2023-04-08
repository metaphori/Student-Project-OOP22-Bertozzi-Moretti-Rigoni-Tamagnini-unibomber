package it.unibo.unibomber.game.ecs.impl;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.model.impl.AbstractComponent;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Utilities;

/**
 * This component slides entities.
 */
public class SlidingComponent extends AbstractComponent {
    private boolean isSliding;
    private Direction direction;

    @Override
    public final void update() {
        final Entity placerEntity = this.getEntity().getComponent(ExplodeComponent.class).get().getPlacer();
        if (this.isSliding && Utilities.isAlive(placerEntity)) {
            final MovementComponent bombMove = this.getEntity().getComponent(MovementComponent.class).get();
            bombMove.moveBy(direction);
        }
    }

    /**
     * Set sliding status.
     * 
     * @param direction player direction
     * @param isSliding set sliding status
     */
    public void setSliding(final boolean isSliding, final Direction direction) {
        if (!isSliding) {
            this.getEntity().getComponent(MovementComponent.class).get()
                    .moveBy(Direction.CENTER);
        }
        this.isSliding = isSliding;
        this.direction = direction;
    }

    /**
     * @return if entity is sliding
     */
    public boolean isSliding() {
        return this.isSliding;
    }

}
