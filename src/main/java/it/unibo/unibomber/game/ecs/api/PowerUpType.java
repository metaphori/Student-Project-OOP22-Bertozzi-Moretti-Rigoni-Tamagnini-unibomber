package it.unibo.unibomber.game.ecs.api;

import java.util.Random;

import it.unibo.unibomber.utilities.Constants;

import java.util.List;

public enum PowerUpType {

    BOMBUP(false),
    BOMBDOWN(false),
    FIREUP(false),
    FIREDOWN(false),
    FIREFULL(false),
    SPEEDUP(false),
    SPEEDDOWN(false),
    KICKBOMB(true),
    THROWBOMB(true);

    private boolean isComplex;

    /**
     * @param isComplex to establish if powerUp is complex or not
     */
    PowerUpType(final Boolean isComplex) {
        this.isComplex = isComplex;
    }

    /**
     * @return if powerUp is complex or not
     */
    public boolean isComplex() {
        return this.isComplex;
    }

    /**
     * A method that returns a random powerup 25% complex and 75% not complex.
     * 
     * @return random powerUp
     */
    public static PowerUpType getRandomPowerUp() {
        Random rnd = new Random();
        return List.of(PowerUpType.values()).stream()
                .filter(e -> rnd.nextInt(100) <= Constants.PowerUp.complexPercentage ? e.isComplex : !e.isComplex)
                .findAny()
                .orElseThrow();
    }

}
