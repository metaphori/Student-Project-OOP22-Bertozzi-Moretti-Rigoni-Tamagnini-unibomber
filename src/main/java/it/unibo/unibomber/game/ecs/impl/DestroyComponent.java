package it.unibo.unibomber.game.ecs.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Pair;

import static it.unibo.unibomber.utilities.Constants.Destroy.STANDARD_FRAME_DURATION;
import static it.unibo.unibomber.utilities.Constants.Destroy.DROPPED_POWERUP_PERCENT;

/**
 * This component manage the destroy of the entity.
 */
public final class DestroyComponent extends AbstractComponent {

    private boolean isDestroyed;
    private int destroyFrames;
    private int destroyFramesPerType;

    /**
     * The constructor set 0 the field destroyFrames
     * and false the field isDestroyed.
     */
    public DestroyComponent() {
        this.isDestroyed = false;
        this.destroyFrames = 0;
        this.destroyFramesPerType = -1;
    }

    @Override
    public void update() {
        if (this.destroyFramesPerType == -1) {
            this.destroyFramesPerType = Constants.Destroy.getDestroyFramesPerType().containsKey(this.getEntity().getType())
                    ? Constants.Destroy.getDestroyFramesPerType().get(this.getEntity().getType())
                    : STANDARD_FRAME_DURATION;
        }
        if (this.isDestroyed) {
            this.destroyFrames++;
            if (this.destroyFrames >= this.destroyFramesPerType) {
                if (this.getEntity().getType() != Type.BOMB
                        && this.getEntity().getType() != Type.POWERUP) {
                    dropPowerUps();
                } else {
                    this.getEntity().getGame().updateGameState();
                    this.getEntity().getGame().removeEntity(this.getEntity());
                }
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
     * 
     * @return true if the entity is destroyed, false otherwise.
     */
    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    /**
     * A method to know the desctrution frames.
     * 
     * @return timer of destruction frame.
     */
    public int getDestroyFrames() {
        return this.destroyFrames;
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
        this.getEntity().getGame().removeEntity(this.getEntity());
        entity.getGame().updateGameState();
        if (powerUpComponent.isPresent()) {
            powerUps = new ArrayList<>(powerUpComponent.get().getPowerUpList());
            droppedPowerUps = (int) Math.ceil(powerUps.size() * DROPPED_POWERUP_PERCENT);
            if (!powerUps.isEmpty()) {
                if (entity.getType() != Type.BOMBER) {
                    entity.getGame()
                            .addEntity(
                                    entity.getGame().getFactory().makePowerUp(entity.getPosition(), powerUps.get(0)));
                    powerUps.remove(0);
                }
                dropRemaining(powerUps, droppedPowerUps);
            }
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
        Pair<Integer, Integer> coord;
        do {
            coord = new Pair<>(rnd.nextInt(gameDimensions.getX()),
                    rnd.nextInt(gameDimensions.getY()));
        } while (this.getEntity().getGame().getGameField().getField().containsKey(coord));
        return new Pair<>((float) coord.getX(),
                (float) coord.getY());
    }
}
