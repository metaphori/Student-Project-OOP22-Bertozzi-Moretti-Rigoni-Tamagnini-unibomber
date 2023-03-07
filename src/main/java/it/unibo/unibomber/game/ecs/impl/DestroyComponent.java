package it.unibo.unibomber.game.ecs.impl;

import java.util.List;
import java.util.Random;

import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.utilities.Pair;

/**
 * This component manage the destroy of the entity
 */
public class DestroyComponent extends AbstractComponent{

    final float droppedPowerUpsPercent = 0.25f;
    private boolean isDestroyed = false;
    private int destroyFrames;

    @Override
    public void update() {
        if (this.isDestroyed) {
            if (this.destroyFrames == 0) {
                dropPowerUps();
                this.getEntity().getGame().removeEntity(this.getEntity());
                this.isDestroyed = false;
            } else {
                this.destroyFrames--;
            }
        }
    }

    /**
     * A method to set if the entity is destroyed
     */
    public void destroy() {
        this.isDestroyed = true;
    }

    /**
     * A method to drop the powerups.
     * It will drop a single powerup if the destroyed entity was a wall, otherwise it will drop all powerups of the entity player/bot
     */
    private void dropPowerUps() {
        var entity = this.getEntity();
        var powerUpComponent = entity.getComponent(PowerUpListComponent.class);
        List<PowerUpType> powerUps;
        int droppedPowerUps;
        if (powerUpComponent.isPresent()) {
            powerUps = powerUpComponent.get().getPowerUpList();
            droppedPowerUps = (int) Math.ceil(powerUps.size() * this.droppedPowerUpsPercent);
            entity.getGame().addEntity(entity.getGame().getFactory().makePowerUp(entity.getPosition(), powerUps.get(0)));
            dropRemaining(powerUps, droppedPowerUps);
        }
    }

    /**
     * A method to drop the remaining powerups
     * @param powerUps the list of powerups
     * @param droppedPowerUps the number of powerups to drop
     */
    private void dropRemaining(List<PowerUpType> powerUps, int droppedPowerUps) {
        Pair<Integer, Integer> gameDimensions;
        Pair<Float, Float> newRandomPos;
        var game = this.getEntity().getGame();
        while (powerUps.size() > droppedPowerUps) {
            powerUps.remove((int) (Math.random()*powerUps.size()));
        }
        for (var powerUp : powerUps) {
            gameDimensions = game.getDimensions();
            newRandomPos = getRandomPos(gameDimensions);
            game.addEntity(game.getFactory().makePowerUp(newRandomPos, powerUp));
        }
    }

    /**
     * A method to create a random position in the field
     * @param gameDimensions the dimensions of the field
     * @return a random position
     */
    private Pair<Float, Float> getRandomPos(Pair<Integer, Integer> gameDimensions) {
        final Random rnd = new Random();
        Pair<Float, Float> rndPos;
        do {
            rndPos = new Pair<Float,Float>((float) rnd.nextInt(gameDimensions.getX()), 
                    (float) rnd.nextInt(gameDimensions.getY()));
        } while(this.getEntity().getGame().getGameField().getField().containsKey(rndPos));
        return rndPos;
    } 
}
