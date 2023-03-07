package it.unibo.unibomber.game.model.api;

public interface GameFactory {
    /**
     * @param keyCodes the key code which corresponds to the player inputs,
     *                 as per the specifics in the controlls file they are:
     *                 [0]left [1]up [2]right [3]down [4]place/pickup/throw Bomb
     *                 [5]activate Remote Controlled Bombs/Line Placement
     *                 [6]menu/pause ingame
     * @return an instance of a Game where only one player controlls a character
     */
    Game makeOnePlayerGame();

    /**
     * @param keyCodesPlayerOne the key code which corresponds to the first player inputs, see makeOnePlayerGame
     * @param keyCodesPlayerTwo the key code which corresponds to the second player inputs, see makeOnePlayerGame
     * @return an instance of a Game where only one player controlls a character
     */
    Game makeTwoPlayerGame();
}
