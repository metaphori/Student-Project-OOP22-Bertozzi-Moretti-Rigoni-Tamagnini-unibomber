package it.unibo.unibomber.game.ecs.impl;

import java.util.List;
import it.unibo.unibomber.game.ecs.api.PowerUpType;

public class PowerUpHandlerComponent extends PowerUpListComponent {
    
    public PowerUpHandlerComponent(int bombNumber, int bombPower, List<PowerUpType> powerUpList) {
        super(bombNumber, bombPower, powerUpList);
    }

    /**
     * @param powerUp that modify powerup parameter of player
     */
    public void addPowerUp(PowerUpType powerUpType) {
        //TODO update value with controll

    }
}
