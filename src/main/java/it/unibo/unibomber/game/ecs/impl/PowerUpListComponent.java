package it.unibo.unibomber.game.ecs.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Optional;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.model.impl.AbstractComponent;

/**
 * This component manage a list of all bombers and bomb powerUps.
 */
public class PowerUpListComponent extends AbstractComponent {
    private int bombFire;
    private final List<PowerUpType> powerUpList;

    /**
     * This method sets all bomber's powerups.
     * 
     * @param bombFire bomb power fire
     * @param powerUpList bomb list of all powerUps
     */
    public PowerUpListComponent(final int bombFire, final List<PowerUpType> powerUpList) {
        this.bombFire = bombFire;
        this.powerUpList = new ArrayList<>(powerUpList);
    }

    /**
     * This method takes all powerups from giver.
     * 
     * @param giver bomber entity
     */
    public PowerUpListComponent(final Entity giver) {
        final Optional<PowerUpListComponent> giversList = giver.getComponent(PowerUpListComponent.class);
        if (giversList.isPresent()) {
            this.bombFire = giversList.get().getBombFire();
            this.powerUpList = giversList.get().getPowerUpList();
        } else {
            throw new MissingFormatArgumentException("Giver does not contain a PowerUpListComponent itSelf");
        }
    }

    @Override
    public void update() {

    }

    /**
     * @return actual bomb fire of player
     */
    public int getBombFire() {
        return this.bombFire;
    }

    /**
     * Set bomb fire.
     * 
     * @param bombFire bomb fire to set
     */
    public void setBombFire(final int bombFire) {
        this.bombFire = bombFire;
    }

    /**
     * @return list of powerup of player
     */
    public List<PowerUpType> getPowerUpList() {
        return new ArrayList<>(this.powerUpList);
    }

    /**
     * @param powerUpType added to powerUpList of player
     */
    public void addPowerUpList(final PowerUpType powerUpType) {
        this.powerUpList.add(powerUpType);
    }

}
