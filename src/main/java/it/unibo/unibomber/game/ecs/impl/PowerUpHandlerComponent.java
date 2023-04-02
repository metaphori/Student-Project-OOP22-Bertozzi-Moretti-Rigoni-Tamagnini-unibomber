package it.unibo.unibomber.game.ecs.impl;

import java.util.List;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.utilities.Constants;

/**
 * This component manage bombers powerUp.
 */
public class PowerUpHandlerComponent extends PowerUpListComponent {

    /**
     * This method inherit powerUp from the superclass.
     * 
     * @param bombNumber
     * @param bombFire
     * @param powerUpList
     */
    public PowerUpHandlerComponent(final int bombNumber, final int bombFire, final List<PowerUpType> powerUpList) {
        super(bombNumber, bombFire, powerUpList);
    }

    /**
     * @return remaining bomb of player
     */
    public final int getRemainingBomb() {
        return this.getBombNumber() - this.getBombPlaced();
    }

    /**
     * @param powerUpType that modify powerup parameter of player
     */
    public void addPowerUp(final PowerUpType powerUpType) {
        this.addPowerUpList(powerUpType);
        switch (powerUpType) {
            case FIREUP:
                if (this.getBombFire() < 8) {
                    this.setBombFire(this.getBombFire() + 1);
                }
                break;
            case FIREDOWN:
                if (this.getBombFire() > 1) {
                    this.setBombFire(this.getBombFire() - 1);
                }
                break;
            case FIREFULL:
                this.setBombFire(8);
                break;
            case BOMBUP:
                if (this.getBombNumber() < 8) {
                    this.setBombNumer(this.getBombNumber() + 1);
                }
                break;
            case BOMBDOWN:
                if (this.getBombNumber() > 1) {
                    this.setBombNumer(this.getBombNumber() - 1);
                }
                break;
            case SPEEDUP:
                if (this.getEntity().getSpeed() < Constants.Entity.MAX_SPEED) {
                    this.getEntity().addSpeed(Constants.PowerUp.SPEED_POWERUP_CHANGE);
                }
                break;
            case SPEEDDOWN:
                if (this.getEntity().getSpeed() > Constants.Entity.MIN_SPEED) {
                    this.getEntity().addSpeed(-Constants.PowerUp.SPEED_POWERUP_CHANGE);
                }
                break;
            default:
                break;
        }
    }
}
