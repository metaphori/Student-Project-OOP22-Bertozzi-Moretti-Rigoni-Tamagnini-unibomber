package it.unibo.unibomber.game.model.api;

/**
 * This enum represent the state of game.
 */
public enum Gamestate {
    /**
     * Game started.
     */
    PLAY,
    /**
     * Menu.
     */
    MENU,
    /**
     * Pause.
     */
    PAUSE,
    /**
    * Quit game.
    */
    QUIT;

    private static Gamestate state = MENU;

    /**
     * @return actual state
     */
    public static Gamestate getGamestate() {
        return state;
    }

    /**
     * SetGameState.
     * 
     * @param st
     */
    public static void setGameState(final Gamestate st) {
        state = st;
    }
}
