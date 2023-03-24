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
        final MovementComponent bombMovement = this.getEntity().getComponent(MovementComponent.class).get();
        final Pair<Float, Float> bombPosition = this.getEntity().getPosition();
        if (isThrowing) {
            if (Math.round(bombPosition.getX()) != (float) (finalPos.getX())
                    || Math.round(bombPosition.getY()) != (float) (finalPos.getY())) {
                bombMovement.moveBy(new Pair<Float, Float>(playerDir.getX() * Constants.Input.POSITIVE_MOVE,
                        playerDir.getY() * Constants.Input.NEGATIVE_MOVE));
            } else {
                if (checkFinalPosition()) {
                    bombMovement.moveBy(new Pair<Float, Float>(0f, 0f));
                    this.getEntity().setPosition(Utilities.getFloatPair(finalPos));
                    this.isThrowing = false;
                } else {
                    finalPos = new Pair<>(finalPos.getX() + (playerDir.getX() * 1),
                            finalPos.getY() + (-playerDir.getY() * 1));
                }
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
        this.finalPos = calculateStandardPosition();
    }

    /**
     * @return bomb throwing status
     */
    public final boolean isThrowing() {
        return this.isThrowing;
    }

    /**
     * This method calculate where bomb will be throwed.
     * 
     * @return standard position
     */
    private Pair<Integer, Integer> calculateStandardPosition() {
        return new Pair<>(startingPos.getX() + (playerDir.getX() * 3), startingPos.getY() + (-playerDir.getY() * 3));
    }

    /**
     * This method check if bomb can be stopped.
     * 
     * @return final position status
     */
    private boolean checkFinalPosition() {
        final Map<Pair<Integer, Integer>, Pair<Type, Entity>> fieldMap = this.getEntity().getGame().getGameField().getField();
        return fieldMap.entrySet().stream()
                .filter(e -> e.getKey().equals(finalPos))
                .map(Map.Entry::getValue)
                .noneMatch(value -> value.getX() == Type.INDESTRUCTIBLE_WALL
                        || value.getX() == Type.DESTRUCTIBLE_WALL
                        || value.getX() == Type.BOMB);

    }

}
