package it.unibo.unibomber.game.model.api;

public enum Gamestate {
    PLAY, MENU, OPTIONS, QUIT;

    private static Gamestate state = MENU;

    /**
     * @return actual state
     */
    public static Gamestate getGamestate() {
        return state;
    }
    /**
     * SetGameState.
     * @param st
     */
    public static void setGameState(final Gamestate st) {
        state = st;
    }
}
