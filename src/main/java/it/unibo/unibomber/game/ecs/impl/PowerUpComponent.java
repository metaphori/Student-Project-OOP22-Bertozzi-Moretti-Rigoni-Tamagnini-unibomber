package it.unibo.unibomber.game.ecs.impl;

import it.unibo.unibomber.game.ecs.api.PowerUpType;

public class PowerUpComponent extends AbstractComponent {

    private PowerUpType powerUpType;

    public PowerUpComponent(PowerUpType powerUpType) {
        this.powerUpType = powerUpType;
    }

    @Override
    public void update() {

    }

    /**
     * @return powerup type
     */
    public PowerUpType getPowerUpType(){
        return this.powerUpType;
    }
    
}
