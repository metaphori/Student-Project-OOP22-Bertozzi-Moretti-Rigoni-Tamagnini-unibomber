package it.unibo.unibomber.game.ecs.impl;

import java.util.Optional;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Pair;

/**
 * This component manage bomb placement.
 */
public class BombPlaceComponent extends AbstractComponent {

    private boolean bombPlaced = false;

    @Override
    public final void update() {
        Entity thisEntity = this.getEntity();
        if (this.bombPlaced) {
            Pair<Float, Float> normalizedPosition = new Pair<Float, Float>(
                    (float) Math.round(thisEntity.getPosition().getX()),
                    (float) Math.round(thisEntity.getPosition().getY()));

            Optional<Entity> bombSamePlace = thisEntity.getGame().getEntities().stream()
                    .filter(e -> e.getType().equals(Type.BOMB))
                    .filter(e -> e.getPosition().equals(normalizedPosition))
                    .findFirst();
            if (bombSamePlace.isEmpty()) {
                thisEntity.getGame()
                        .addEntity(thisEntity.getGame().getFactory().makeBomb(thisEntity, normalizedPosition));
                thisEntity.getComponent(PowerUpHandlerComponent.class)
                        .get().setBombPlaced(1);
            }
            this.bombPlaced = false;
        }
    }

    /**
     * This method set the state of the bomb.
     */
    public void placeBomb() {
        if (this.getEntity().getComponent(PowerUpHandlerComponent.class).get().getRemainingBomb() > 0) {
            this.bombPlaced = true;
        }
    }

}
