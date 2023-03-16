package it.unibo.unibomber.game.ecs.impl;

import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

/**
 * This component slides entities.
 */
public class SlidingComponent extends AbstractComponent {

    private boolean isSliding;
    private boolean isMoving;
    private Direction dir;

    @Override
    public final void update() {
        if (isSliding) {
            if (!isMoving) {
                // TODO change handling placer in bomb
                dir = this.getEntity().getComponent(ExplodeComponent.class).get().getPlacer()
                        .getComponent(MovementComponent.class).get().getDirection();
                isMoving = true;
            }
            MovementComponent bombMove = this.getEntity().getComponent(MovementComponent.class).get();
            bombMove.moveBy(new Pair<Float, Float>(dir.getX() * Constants.Input.POSITIVE_MOVE,
                    dir.getY() * -Constants.Input.POSITIVE_MOVE));
            isSliding = false;
        }
    }

    /**
     * Set sliding status.
     * 
     * @param isSliding
     */
    public void setSliding(final boolean isSliding) {
        this.isSliding = isSliding;
    }

    /**
     * @return if entity is sliding
     */
    public boolean getSliding() {
        return this.isSliding;
    }

    /**
     * Set moving status.
     * 
     * @param isMoving
     */
    public void setMoving(final boolean isMoving) {
        this.isMoving = isMoving;
    }

    /**
     * @return if entity is moving
     */
    public boolean getMoving() {
        return this.isMoving;
    }

}
