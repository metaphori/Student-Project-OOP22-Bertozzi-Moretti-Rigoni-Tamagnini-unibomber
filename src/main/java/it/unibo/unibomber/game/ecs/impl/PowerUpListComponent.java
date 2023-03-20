package it.unibo.unibomber.game.ecs.impl;

import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Optional;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;

/**
 * This component manage a list of all bombers powerUp.
 */
public class PowerUpListComponent extends AbstractComponent {

    private Entity placer;
    private int bombNumber;
    private int bombPlaced;
    private int bombFire;
    private final List<PowerUpType> powerUpList;

    /**
     * This method sets all bomber's powerups.
     * 
     * @param bombNumber
     * @param bombFire
     * @param powerUpList
     */
    public PowerUpListComponent(final int bombNumber, final int bombFire, final List<PowerUpType> powerUpList) {
        this.bombNumber = bombNumber;
        this.bombPlaced = 0;
        this.bombFire = bombFire;
        this.powerUpList = powerUpList;
    }

    /**
     * This method takes all powerups from giver.
     * 
     * @param giver
     */
    public PowerUpListComponent(final Entity giver) {
        final Optional<PowerUpListComponent> giversList = giver.getComponent(PowerUpListComponent.class);
        this.placer = giver;
        if (giversList.isPresent()) {
            this.bombNumber = giversList.get().getBombNumber();
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
     * @return bomb placer
     */
    public Entity getPlacer() {
        return placer;
    }

    /**
     * @return actual bomb number of player
     */
    public int getBombNumber() {
        return this.bombNumber;
    }

    /**
     * set bombNumber.
     * 
     * @param bombNumber
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
     * set bombPlaced of player.
     * 
     * @param bombPlaced
     */
    public void addBombPlaced(final int bombPlaced) {
        this.bombPlaced += bombPlaced;
    }

    /**
     * @return actual bomb fire of player
     */
    public int getBombFire() {
        return this.bombFire;
    }

    /**
     * set bomb fire.
     * 
     * @param bombFire
     */
    public void setBombFire(final int bombFire) {
        this.bombFire = bombFire;
    }

    /**
     * @return list of powerup of player
     */
    public List<PowerUpType> getPowerUpList() {
        return this.powerUpList;
    }

    /**
     * @param powerUpType added to powerUpList of player
     */
    public void addPowerUpList(final PowerUpType powerUpType) {
        this.powerUpList.add(powerUpType);
    }

}
