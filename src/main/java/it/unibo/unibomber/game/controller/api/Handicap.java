package it.unibo.unibomber.game.controller.api;

import it.unibo.unibomber.game.ecs.api.PowerUpType;
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
    BOMBUP(10, PowerUpType.BOMBUP),
    /**
     * bot index.
     */
    FIREUP(11, PowerUpType.FIREUP),
    /**
     * bot index.
     */
    SPEEDUP(12, PowerUpType.SPEEDUP),
    /**
     * bot index.
     */
    BOMBKICK(13, PowerUpType.KICKBOMB),
    /**
     * bot index.
     */
    POWERGLOVE(14, PowerUpType.THROWBOMB),
    /**
     * delete index.
     */
    DELETE(21, "delete"),
    /**
     * delete all index.
     */
    DELETE_ALL(22, "deleteAll"),
    /**
     * player hover index.
     */
    BOT_HOVER(23, "bot");

    private int index;
    private String type;
    private PowerUpType pType;

    /**
     * @param index index of array in option buffer.
     * @param type  of handicap
     */
    Handicap(final int index, final String type) {
        this.index = index;
        this.type = type;
    }

    Handicap(final int index, final PowerUpType pType) {
        this.index = index;
        this.pType = pType;
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
     * @param index of power up
     * @return power up type.
     */
    public static PowerUpType getPType(final int index) {
        for (final Handicap h : Handicap.values()) {
            if (h.getIndex() == index) {
                return h.pType;
            }
        }
        return BOMBUP.pType;
    }

    /**
     * @return number of power up handicap.
     */
    public static int getNumberOfPowerUp() {
        return OptionButton.HANDICAP_NUMBER_POWERUP;
    }
}
