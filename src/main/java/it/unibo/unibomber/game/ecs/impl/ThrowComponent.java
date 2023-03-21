package it.unibo.unibomber.game.ecs.impl;

import java.util.Map;

import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.Utilities;

/**
 * This component throw entities.
 */
public class ThrowComponent extends AbstractComponent {
    private boolean isThrowing;
    private Direction playerDir;
    private Pair<Integer, Integer> startingPos;
    private Pair<Integer, Integer> finalPos;

    @Override
    public final void update() {
        MovementComponent bombMovement = this.getEntity().getComponent(MovementComponent.class).get();
        Pair<Float, Float> bombPosition = this.getEntity().getPosition();
        if (isThrowing) {
            if (Math.round(bombPosition.getX()) != (float) (finalPos.getX())
                    || Math.round(bombPosition.getY()) != (float) (finalPos.getY())) {
                bombMovement.moveBy(new Pair<Float, Float>(playerDir.getX() * Constants.Input.POSITIVE_MOVE,
                        playerDir.getY() * Constants.Input.NEGATIVE_MOVE));
            } else {
                bombMovement.moveBy(new Pair<Float, Float>(0f, 0f));
                this.getEntity().setPosition(Utilities.getFloatPair(finalPos));
                this.isThrowing = false;
            }
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
     * @return bomb throwing status
     */
    public final boolean getThrowing() {
        return this.isThrowing;
    }

    /**
     * This method calculate where bomb will be throwed.
     * 
     * @return final position
     */
    private Pair<Integer, Integer> calculateFinalPosition() {
        Map<Pair<Integer, Integer>, Pair<Type, Entity>> fieldMap = this.getEntity().getGame().getGameField().getField();
        Pair<Integer, Integer> finalPosition = new Pair<>(startingPos.getX() + (playerDir.getX() * 4),
                startingPos.getY() + (-playerDir.getY() * 4));
        while (fieldMap.containsKey(finalPosition) && (fieldMap.get(finalPosition).getX() == Type.INDESTRUCTIBLE_WALL
                || fieldMap.get(finalPosition).getX() == Type.DESTRUCTIBLE_WALL)) {
            finalPosition = new Pair<>(finalPosition.getX() + (playerDir.getX() * 2),
                    finalPosition.getY() + (-playerDir.getY() * 2));
        }
        return finalPosition;
    }

}
