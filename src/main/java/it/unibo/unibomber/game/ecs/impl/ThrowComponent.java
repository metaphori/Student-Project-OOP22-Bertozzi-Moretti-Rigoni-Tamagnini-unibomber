package it.unibo.unibomber.game.ecs.impl;

import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

/**
 * This component throw entities.
 */
public class ThrowComponent extends AbstractComponent {
    private boolean isThrowing;
    private Pair<Integer, Integer> startingPos;
    private Pair<Float, Float> finalPos;
    private Direction playerDir;

    @Override
    public final void update() {
        // TODO make controll if is wall or other bomb
        MovementComponent bombMovement = this.getEntity().getComponent(MovementComponent.class).get();
        Pair<Float, Float> bombPosition = this.getEntity().getPosition();
        if (isThrowing) {
            if (Math.round(bombPosition.getX()) != (float) (finalPos.getX())
                    || Math.round(bombPosition.getY()) != (float) (finalPos.getY())) {
                bombMovement.moveBy(new Pair<Float, Float>(playerDir.getX() * Constants.Input.POSITIVE_MOVE,
                        playerDir.getY() * -Constants.Input.POSITIVE_MOVE));
                // TODO movement bug
            } else {
                this.getEntity().setPosition(finalPos);
                this.isThrowing = false;
            }
        } else {
            bombMovement.moveBy(new Pair<Float, Float>(0f, 0f));
        }
    }

    /**
     * Set throwing status.
     * 
     * @param isThrowing
     * @param startingPos
     * @param playerDir
     */
    public final void throwBomb(final boolean isThrowing, final Pair<Integer, Integer> startingPos,
            final Direction playerDir) {
        this.isThrowing = isThrowing;
        this.startingPos = startingPos;
        this.playerDir = playerDir;
        this.finalPos = calculateFinalPosition();
    }

    /**
     * This method calculate where bomb will be throwed.
     * 
     * @return final position
     */
    private Pair<Float, Float> calculateFinalPosition() {
        return new Pair<Float, Float>((float) startingPos.getX() + (playerDir.getX() * 4),
                (float) startingPos.getY() + (-playerDir.getY() * 4));
    }

}
