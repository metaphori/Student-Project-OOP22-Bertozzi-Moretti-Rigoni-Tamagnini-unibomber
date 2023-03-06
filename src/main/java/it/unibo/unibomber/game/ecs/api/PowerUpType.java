package it.unibo.unibomber.game.ecs.api;

import java.util.Random;

public enum PowerUpType {

    BOMBUP,
    BOMBDOWN,
    FIREUP,
    FIREDOWN,
    FIREFULL,
    SPEEDUP,
    SPEEDDOWN;
    
    /**
     * A method that returns a random powerup.
     * @return random powerUp 
     */
    public static PowerUpType getRandomPowerUp() {
        int index = new Random().nextInt(PowerUpType.values().length);
        return PowerUpType.values()[index];
    }
}
