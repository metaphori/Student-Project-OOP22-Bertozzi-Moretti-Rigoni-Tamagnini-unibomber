package it.unibo.unibomber.game.controller.api;

public enum OptionType {
    LEFT(1),
    RIGHT(2),
    OK(3),
    PLAYER(4),
    PLAYER_HOVER(5),
    BOT(6),
    BOTNUMBER(7),
    PLUS(8),
    MINUS(9),
    DELETE(15),
    DELETE_ALL(16);
    private int index;

    /**
     * @param index index of array in option buffer.
     */
    OptionType(final int index) {
        this.index = index;
    }

    /**
     * @return index of type.
     */
    public int getIndex() {
        return this.index;
    }
}
