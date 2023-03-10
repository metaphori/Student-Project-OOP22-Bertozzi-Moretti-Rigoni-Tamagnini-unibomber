package it.unibo.unibomber.game.ecs.impl;

import java.util.Optional;

import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

public class MovementComponent extends AbstractComponent {

    private final int FRAME_DELAY=10;

    private static float globalSpeedMultiplier = 1;
    private boolean hasMoved=false;
    private Pair<Float, Float> moveBy;
    private Direction direction = Direction.DOWN;
    private int framesInDirection = 0;
    private int passedFrame = 0;

    public MovementComponent() {
        moveBy = new Pair<Float, Float>(0f, 0f);
    }

    @Override
    public final void update() {
        this.getEntity().addPosition(moveBy);
        handleDirection();
        // checkCollisions();
    }

    /**
     * Given the direction it updates the number of frames spent
     * in that direction for the animation's sake
     */
    private void handleDirection() {
        Direction newDirection = Direction.extractDirecion(moveBy).orElse(direction);
        if (this.direction == newDirection) {
            this.framesInDirection++;
        } else {
            this.framesInDirection = 0;
            this.direction = newDirection;
        }
        if (framesInDirection == FRAME_DELAY) {
            passedFrame++;
            this.framesInDirection = 0;
        }
    }

    /**
     * Handles the collisions by calling the relative component
     */
    private void checkCollisions() {
        Optional<CollisionComponent> collisionComp = this.getEntity().getComponent(CollisionComponent.class);
        if (collisionComp.isPresent()) {
            collisionComp.get().checkCollisions();
        }
    }

    /**
     * @param moveBy the coordinates to move by
     */
    public final void moveBy(final Pair<Float, Float> moveBy) {
        this.moveBy = new Pair<>(moveBy.getX() * this.getEntity().getSpeed() * globalSpeedMultiplier,
                moveBy.getY() * this.getEntity().getSpeed() * globalSpeedMultiplier);
                if(moveBy.equals(new Pair<Float,Float>(0f,0f)))hasMoved=false;
                else hasMoved=true;
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
    /*
    * @return the number of frames spent in one direction
    * keeping in mind the FRAME_DELAY
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
    public boolean hasMoved(){
        return this.hasMoved;
    }
}
