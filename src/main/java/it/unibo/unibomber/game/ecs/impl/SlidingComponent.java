package it.unibo.unibomber.game.ecs.impl;

import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

/**
 * This component slides entities.
 */
public class SlidingComponent extends AbstractComponent {
    private boolean isSliding;
    private Direction direction;

    @Override
    public final void update() {
        MovementComponent bombMove = this.getEntity().getComponent(MovementComponent.class).get();
        if (isSliding) {
            bombMove.moveBy(new Pair<Float, Float>(direction.getX() * Constants.Input.POSITIVE_MOVE,
                    direction.getY() * -Constants.Input.POSITIVE_MOVE));
        } else {
            bombMove.moveBy(new Pair<Float, Float>(0f, 0f));
        }
    }

    /**
     * Set sliding status.
     * 
     * @param isSliding
     */
    public void setSliding(final boolean isSliding, final Direction direction) {
        this.isSliding = isSliding;
        this.direction = direction;
    }

    /**
     * @return if entity is sliding
     */
    public boolean getSliding() {
        return this.isSliding;
    }

}
