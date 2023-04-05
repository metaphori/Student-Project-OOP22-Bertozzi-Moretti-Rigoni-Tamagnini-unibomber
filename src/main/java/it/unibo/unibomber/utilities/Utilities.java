package it.unibo.unibomber.utilities;

import java.util.Optional;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;

/**
 * Some utilities method.
 */
public final class Utilities {
    private Utilities() {
    }

    /**
     * Establishes if a value is beetween 2 value.
     * 
     * @param value value that must be compared
     * @param min   min value
     * @param max   max value
     * @return beetween status
     */
    public static boolean isBetween(final int value, final int min, final int max) {
        return value >= min && value < max;
    }

    /**
     * Establishes if a float value is beetween included 2 value.
     * 
     * @param value value that must be compared
     * @param min   min value
     * @param max   max value
     * @return beetween included status
     */
    public static boolean isBetweenIncluded(final float value, final int min, final int max) {
        return value >= min && value <= max;
    }

    /**
     * This method convert Integer pair to Float pair.
     * 
     * @param integerPair pair that must be converted
     * @return Float pair
     */
    public static Pair<Float, Float> getFloatPair(final Pair<Integer, Integer> integerPair) {
        return new Pair<Float, Float>((float) integerPair.getX(), (float) integerPair.getY());
    }

    /**
     * This method convert a float pair to a rounded integer pair.
     * 
     * @param floatPair pair that must be rounded
     * @return rounded pair
     */
    public static Pair<Integer, Integer> getRoundedPair(final Pair<Float, Float> floatPair) {
        return new Pair<Integer, Integer>(Math.round(floatPair.getX()), Math.round(floatPair.getY()));
    }

    /**
     * This method return life status of player.
     * 
     * @param bomber who must be checked
     * @return whether he is alive or not
     */
    public static boolean isAlive(final Entity bomber) {
        final Optional<DestroyComponent> destroyBomber = bomber.getComponent(DestroyComponent.class);
        return !destroyBomber.isPresent() || !destroyBomber.get().isDestroyed();
    }
}
