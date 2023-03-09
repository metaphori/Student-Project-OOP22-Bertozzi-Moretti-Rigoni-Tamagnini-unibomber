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
     * @param bombFire
     * @param powerUpList
     */
    public PowerUpHandlerComponent(final int bombNumber, final int bombFire, final List<PowerUpType> powerUpList) {
        super(bombNumber, bombFire, powerUpList);
    }

    /**
     * @param powerUpType that modify powerup parameter of player
     */
    public void addPowerUp(final PowerUpType powerUpType) {
        // TODO Add powerUpType to powerUpList
        if (!powerUpType.isComplex()) {
            switch (powerUpType) {
                case FIREUP:
                    if (this.bombFire < 8) {
                        this.bombFire += 1;
                    }
                    break;
                case FIREDOWN:
                    if (this.bombFire > 1) {
                        this.bombFire -= 1;
                    }
                    break;
                case FIREFULL:
                    this.bombFire = 8;
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
