package it.unibo.unibomber.game.model.api;

public interface GameFactory {
    /**
     * @return an instance of a Game where only one player controlls a character
     */
    Game makeOnePlayerGame();

    /**
     * @return an instance of a Game where only one player controlls a character
     */
    Game makeTwoPlayerGame();
}
