package it.unibo.unibomber.game.model.api;

import it.unibo.unibomber.utilities.Pair;

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
     * Option.
     */
    OPTION,
    /**
     * Pause.
     */
    PAUSE,
    /**
     * Quit game.
     */
    QUIT,
    /**
     * Player wins game.
     */
    WIN,
    /**
     * Player wins game.
     */
    LOSE;

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

    /**
     * @return pair of button gamestate if game is in pause or terminated.
     */
    public static Pair<Gamestate, Gamestate> getButtonStateGame() {
        if (state.equals(PAUSE)) {
            return new Pair<>(PLAY, MENU);
        } else if (state.equals(WIN) || state.equals(LOSE)) {
            return new Pair<>(MENU, QUIT);
        }
        return new Pair<>(PLAY, PLAY);
    }
}
