package it.unibo.unibomber.game;

import it.unibo.unibomber.game.controller.impl.WorldImpl;

/**
 * Unibobmer class.
 */
public final class Unibomber {
    /**
     * Unibobmer constructor.
     */
    private Unibomber() {

    }

    /**
     * Main.
     * 
     * @param passedArgs args
     */
    public static void main(final String[] passedArgs) {
        new WorldImpl();
    }
}
