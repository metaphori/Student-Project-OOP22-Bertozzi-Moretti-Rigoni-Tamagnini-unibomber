package it.unibo.unibomber.game.ecs.impl;

import java.util.List;
import java.util.Random;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.model.api.Game;
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
            if (this.destroyFrames-- == 0) {
                dropPowerUps();
                this.getEntity().getGame().removeEntity(this.getEntity());
            }
        }
    }
    
    public void dropPowerUps(){
        Entity entity = this.getEntity();
        var entityPowerupComponent = entity.getComponent(PowerUpListComponent.class);
        if(entityPowerupComponent.isPresent()){
            List<PowerUpType> entityPowerups = entityPowerupComponent.get().getPowerUpList();
            int droppedEntities = (int)Math.ceil(entityPowerups.size()*droppedPowerUpsPercent);
            dropFirstEntity(entityPowerups);
            dropRemainingPowerup(entityPowerups, droppedEntities);
        }
    }

    public void destroy() {
        this.isDestroyed = true;
    }

    private void dropRemainingPowerup(List<PowerUpType> entityPowerups, int droppedEntities) {
        while(entityPowerups.size()>droppedEntities){
            entityPowerups.remove((int)(Math.random()*entityPowerups.size()));
        }
        Game thisGame = this.getEntity().getGame();
        for(PowerUpType powerUp : entityPowerups){
            Pair<Integer,Integer> dimensions = this.getEntity().getGame().getDimensions();
            Pair<Float,Float> newPosition = getLegalPosition(dimensions);
            thisGame.addEntity(thisGame.getFactory().makePowerUp(newPosition, powerUp));
        }
    }

    private Pair<Float, Float> getLegalPosition(Pair<Integer, Integer> dimensions) {
        final Random rnd = new Random();
        Pair<Float,Float> newPosition;
        do{
            newPosition=new Pair<Float,Float>
                        ((float)rnd.nextInt(dimensions.getX()), (float)rnd.nextInt(dimensions.getY()));
        }while(this.getEntity().getGame().getGameField().getField().containsKey(newPosition));

        return newPosition;
    }

    private void dropFirstEntity(List<PowerUpType> entityPowerups) {        
        Entity entity = this.getEntity();
        entity.getGame().addEntity(entity.getGame().getFactory()
              .makePowerUp(entity.getPosition(), entityPowerups.get(0)));
        entityPowerups.remove(0);
    }

}
