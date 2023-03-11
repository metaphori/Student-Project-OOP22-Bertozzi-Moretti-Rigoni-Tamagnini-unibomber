package it.unibo.unibomber.game.ecs.impl;

import java.util.List;
import java.util.Random;

import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.utilities.Pair;

import static it.unibo.unibomber.utilities.Constants.Destroy.DESTROYDURATION;
import static it.unibo.unibomber.utilities.Constants.Destroy.DROPPED_POWERUP_PERCENT;

/**
 * This component manage the destroy of the entity.
 */
public final class DestroyComponent extends AbstractComponent {

    private boolean isDestroyed;
    private int destroyFrames;

    /**
     * The constructor set 0 the field destroyFrames
     * and false the field isDestroyed.
     */
    public DestroyComponent() {
        this.isDestroyed = false;
        this.destroyFrames = 0;
    }

    @Override
    public void update() {
        if (this.isDestroyed) {
            this.destroyFrames++;
            if (this.destroyFrames == DESTROYDURATION) {
                dropPowerUps();
                this.getEntity().getGame().removeEntity(this.getEntity());
                this.isDestroyed = false;
                this.destroyFrames = 0;
            }
        }
    }

    /**
     * A method to set if the entity is destroyed.
     */
    public void destroy() {
        this.isDestroyed = true;
    }

    /**
     * A method to know if the entity is destroyed.
     * @return true if the entity is destroyed, false otherwise.
     */
    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    /**
     * A method to drop the powerups.
     * It will drop a single powerup if the destroyed entity was a wall,
     * otherwise it will drop all powerups of the entity player/bot
     */
    private void dropPowerUps() {
        final var entity = this.getEntity();
        final var powerUpComponent = entity.getComponent(PowerUpListComponent.class);
        final List<PowerUpType> powerUps;
        int droppedPowerUps;
        if (powerUpComponent.isPresent()) {
            powerUps = powerUpComponent.get().getPowerUpList();
            droppedPowerUps = (int) Math.ceil(powerUps.size() * DROPPED_POWERUP_PERCENT);
            entity.getGame()
                    .addEntity(entity.getGame().getFactory().makePowerUp(entity.getPosition(), powerUps.get(0)));
            dropRemaining(powerUps, droppedPowerUps);
        }
    }

    /**
     * A method to drop the remaining powerups.
     * 
     * @param powerUps        the list of powerups
     * @param droppedPowerUps the number of powerups to drop
     */
    private void dropRemaining(final List<PowerUpType> powerUps, final int droppedPowerUps) {
        final var game = this.getEntity().getGame();
        final Pair<Integer, Integer> gameDimensions = game.getDimensions();
        Pair<Float, Float> newRandomPos;
        while (powerUps.size() > droppedPowerUps) {
            powerUps.remove((int) (Math.random() * powerUps.size()));
        }
        for (final var powerUp : powerUps) {
            newRandomPos = getRandomPos(gameDimensions);
            game.addEntity(game.getFactory().makePowerUp(newRandomPos, powerUp));
        }
    }

    /**
     * A method to create a random position in the field.
     * 
     * @param gameDimensions the dimensions of the field
     * @return a random position
     */
    private Pair<Float, Float> getRandomPos(final Pair<Integer, Integer> gameDimensions) {
        final Random rnd = new Random();
        Pair<Float, Float> rndPos;
        do {
            rndPos = new Pair<>((float) rnd.nextInt(gameDimensions.getX()),
                    (float) rnd.nextInt(gameDimensions.getY()));
        } while (this.getEntity().getGame().getGameField().getField().containsKey(rndPos));
        return rndPos;
    }
}
