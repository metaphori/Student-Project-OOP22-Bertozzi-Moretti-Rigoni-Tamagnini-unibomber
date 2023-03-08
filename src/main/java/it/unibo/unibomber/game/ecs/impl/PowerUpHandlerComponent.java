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
        if (!powerUpType.isComplex()) {
            switch (powerUpType) {
                case FIREUP:
                    if (this.bombPower < 8) {
                        this.bombPower += 1;
                    }
                    break;
                case FIREDOWN:
                    if (this.bombPower > 1) {
                        this.bombPower -= 1;
                    }
                    break;
                case FIREFULL:
                    this.bombPower = 8;
                    break;
                case BOMBUP:
                    if (this.bombNumber < 8) {
                        this.bombNumber += 1;
                    }
                    break;
                case BOMBDOWN:
                    if (this.bombNumber > 1) {
                        this.bombNumber -= 1;
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
