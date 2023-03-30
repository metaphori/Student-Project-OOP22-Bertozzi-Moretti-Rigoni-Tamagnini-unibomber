package it.unibo.unibomber.game.controller.api;

import it.unibo.unibomber.utilities.Constants.UI.OptionButton;

/**
 * Option type index enum.
 */
public enum Handicap {
    /**
     * left index.
     */
    LEFT(1, "left"),
    /**
     * right index.
     */
    RIGHT(2, "right"),
    /**
     * ok index.
     */
    OK(3, "ok"),
    /**
     * bot number index.
     */
    BOTNUMBER(4, "botNumber"),
    /**
     * plus index.
     */
    PLUS(5, "+"),
    /**
     * minuse index.
     */
    MINUS(6, "-"),
    /**
     * player index.
     */
    PLAYER(7, "player"),
    /**
     * player hover index.
     */
    PLAYER_HOVER(8, "player"),
    /**
     * bot index.
     */
    BOT(9, "bot"),
    /**
     * bot index.
     */
    BOMBUP(10, null),
    /**
     * bot index.
     */
    FIREUP(11, null),
    /**
     * bot index.
     */
    SPEEDUP(12, null),
    /**
     * bot index.
     */
    BOMBKICK(13, null),
    /**
     * bot index.
     */
    POWERGLOVE(14, null),
    /**
     * delete index.
     */
    DELETE(21, "delete"),
    /**
     * delete all index.
     */
    DELETE_ALL(22, "deleteAll");

    private int index;
    private String type;

    /**
     * @param index index of array in option buffer.
     * @param type of handicap
     */
    Handicap(final int index, final String type) {
        this.index = index;
        this.type = type;
    }

    /**
     * @return index of handicap.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * @return type of handicap.
     */
    public String getType() {
        return this.type;
    }

    /**
     * @return number of power up handicap.
     */
    public static int getNumberOfPowerUp() {
        return OptionButton.HANDICAP_NUMBER_POWERUP;
    }
}
