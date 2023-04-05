package it.unibo.unibomber.game.ecs.impl;

import java.util.List;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.utilities.Constants;

/**
 * This component manage bombers powerUp.
 */
public class PowerUpHandlerComponent extends PowerUpListComponent {
    private int bombNumber;
    private int bombPlaced;

    /**
     * This method inherit powerUp from the superclass.
     * 
     * @param bombNumber  starting bomb number
     * @param bombFire    bomb power fire
     * @param powerUpList list of all powerUps
     */
    public PowerUpHandlerComponent(final int bombNumber, final int bombFire, final List<PowerUpType> powerUpList) {
        super(bombFire, powerUpList);
        this.bombNumber = bombNumber;
        this.bombPlaced = 0;
    }

    /**
     * @return actual bomb number of player
     */
    public int getBombNumber() {
        return this.bombNumber;
    }

    /**
     * Set bombNumber.
     * 
     * @param bombNumber bomb number to set
     */
    public void setBombNumer(final int bombNumber) {
        this.bombNumber = bombNumber;
    }

    /**
     * @return actual bomb placed of player
     */
    public int getBombPlaced() {
        return this.bombPlaced;
    }

    /**
     * Add bombPlaced of player.
     * 
     * @param bombPlaced bomb placed to add
     */
    public void addBombPlaced(final int bombPlaced) {
        this.bombPlaced += bombPlaced;
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
