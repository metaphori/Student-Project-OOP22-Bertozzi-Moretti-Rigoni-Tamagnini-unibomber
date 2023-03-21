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
}
