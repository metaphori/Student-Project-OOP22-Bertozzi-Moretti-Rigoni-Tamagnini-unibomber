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
            MovementComponent playerMove = this.getEntity().getComponent(MovementComponent.class).get();
            Pair<Float, Float> movement;
            switch (dir) {
                case UP:
                    movement = new Pair<>(0f, Constants.Input.NEGATIVE_MOVE);
                    break;
                case LEFT:
                    movement = new Pair<>(Constants.Input.NEGATIVE_MOVE, 0f);
                    break;
                case DOWN:
                    movement = new Pair<>(0f, Constants.Input.POSITIVE_MOVE);
                    break;
                case RIGHT:
                    movement = new Pair<>(Constants.Input.POSITIVE_MOVE, 0f);
                    break;
                default:
                    movement = new Pair<>(0f, 0f);
                    break;
            }
            playerMove.moveBy(movement);
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
