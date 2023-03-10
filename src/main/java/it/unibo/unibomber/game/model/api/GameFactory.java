package it.unibo.unibomber.game.model.api;

/**
 * This interface rapresent the game modality.
 */
public interface GameFactory {
    /**
     * @return an instance of a Game where only one player controlls a character
     */
    Game makeOnePlayerGame();

    /**
     * @return an instance of a Game where two player controlls a character
     */
    Game makeTwoPlayerGame();
}
