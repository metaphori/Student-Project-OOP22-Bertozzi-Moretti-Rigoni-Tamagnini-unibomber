package it.unibo.unibomber.utilities;

/**
 * Some utilities method.
 */
public final class Utilities {
    private Utilities() {
    }

    /**
     * Establishes if a value is beetween 2 value.
     * 
     * @param value
     * @param min
     * @param max
     * @return beetween status
     */
    public static boolean isBetween(final int value, final int min, final int max) {
        return (value >= min && value < max);
    }

    /**
     * This method convert Integer pair to Float pair.
     * 
     * @param integerPair
     * @return Float pair
     */
    public static Pair<Float, Float> getFloatPair(final Pair<Integer, Integer> integerPair) {
        return new Pair<Float, Float>((float) integerPair.getX(), (float) integerPair.getY());
    }

    /**
     * This method convert a float pair to a rounded integer pair.
     * 
     * @param floatPair
     * @return rounded pair
     */
    public static Pair<Integer, Integer> getRoundedPair(final Pair<Float, Float> floatPair) {
        return new Pair<Integer, Integer>(Math.round(floatPair.getX()), Math.round(floatPair.getY()));
    }
}
