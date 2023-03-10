package it.unibo.unibomber.game.ecs.impl;

import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import static it.unibo.unibomber.utilities.Constants.Entity.SPEED_CHANGE;
/**
 * The class is responsible for handling the movement of the enetity it is
 * attatched to
 * it also keeps track of the frames said entity spent in one direction so it
 * can be
 * graphically implemented.
 */
public class MovementComponent extends AbstractComponent {

    private static float globalSpeedMultiplier = SPEED_CHANGE;
    private boolean hasMoved;
    private Pair<Float, Float> moveBy = new Pair<>(0f, 0f);
    private Direction direction = Direction.DOWN;
    private int framesInDirection;
    private int passedFrame;

    @Override
    public final void update() {
        this.getEntity().addPosition(moveBy);
        handleDirection();
        // checkCollisions();
    }

    /**
     * Given the direction it updates the number of frames spent
     * in that direction for the animation's sake.
     */
    private void handleDirection() {
        final Direction newDirection = Direction.extractDirecion(moveBy).orElse(direction);
        if (this.direction == newDirection) {
            this.framesInDirection++;
        } else {
            this.framesInDirection = 0;
            this.direction = newDirection;
        }
        if (framesInDirection == Constants.Movement.FRAME_DELAY) {
            passedFrame++;
            this.framesInDirection = 0;
        }
    }

    /**
     * Handles the collisions by calling the relative component.
     */
    /* TODO
    private void checkCollisions() {
        final Optional<CollisionComponent> collisionComp = this.getEntity().getComponent(CollisionComponent.class);
        if (collisionComp.isPresent()) {
            collisionComp.get().checkCollisions();
        }
    }
    */

    /**
     * @param moveBy the coordinates to move by
     */
    public final void moveBy(final Pair<Float, Float> moveBy) {
        this.moveBy = new Pair<>(moveBy.getX() * this.getEntity().getSpeed() * globalSpeedMultiplier,
                moveBy.getY() * this.getEntity().getSpeed() * globalSpeedMultiplier);
        if (moveBy.equals(new Pair<Float, Float>(0f, 0f))) {
            hasMoved = false;
        } else {
            hasMoved = true;
        }
    }

    /**
     * @return the direction this entity is facing
     */
    public final Direction getDirection() {
        return this.direction;
    }

    /**
     * @return the number of frames spent in one direction
     */
    public final int getFrameInDirection() {
        return this.framesInDirection;
    }

    /**
     * @return the number of frames spent in one direction
     *         keeping in mind the FRAME_DELAY
     */
    public final int getPassedFrames() {
        return this.passedFrame;
    }

    /**
     * @param speed the new value of globalSpeedMultiplier
     */
    public static void setGlobalSpeedMultiplier(final float speed) {
        globalSpeedMultiplier = speed;
    }

    /**
     * @return whether the entity has moved in the last game frame
     */
    public boolean hasMoved() {
        return this.hasMoved;
    }
}
