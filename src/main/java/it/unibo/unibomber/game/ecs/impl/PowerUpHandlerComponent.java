package it.unibo.unibomber.game.ecs.impl;

import java.util.List;
import it.unibo.unibomber.game.ecs.api.PowerUpType;

/**
 * This component manage bombers powerUp.
 */
public class PowerUpHandlerComponent extends PowerUpListComponent {

    /**
     * This method inherit powerUp from the superclass.
     * @param bombNumber
     * @param bombPower
     * @param powerUpList
     */
    public PowerUpHandlerComponent(final int bombNumber, final int bombPower, final List<PowerUpType> powerUpList) {
        super(bombNumber, bombPower, powerUpList);
    }

    /**
     * @param powerUpType that modify powerup parameter of player
     */
    public void addPowerUp(final PowerUpType powerUpType) {
        // TODO Add powerUpType to powerUpList
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
