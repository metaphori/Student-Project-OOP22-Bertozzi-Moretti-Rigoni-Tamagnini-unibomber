package it.unibo.unibomber.game.ecs.impl;

import java.util.Optional;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.model.impl.AbstractComponent;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Pair;

/**
 * This component manage bomb placement.
 */
public class BombPlaceComponent extends AbstractComponent {

    private boolean bombPlaced;

    @Override
    public final void update() {
        final Entity thisEntity = this.getEntity();
        if (this.bombPlaced) {
            final Pair<Float, Float> normalizedPosition = new Pair<>(
                    (float) Math.round(thisEntity.getPosition().getX()),
                    (float) Math.round(thisEntity.getPosition().getY()));

            final Optional<Entity> bombSamePlace = thisEntity.getGame().getEntities().stream()
                    .filter(e -> e.getType().equals(Type.BOMB))
                    .filter(e -> e.getPosition().equals(normalizedPosition))
                    .findFirst();
            if (bombSamePlace.isEmpty()) {
                final var bombCreate = thisEntity.getGame().getFactory().makeBomb(thisEntity, normalizedPosition);
                bombCreate.addSpeed(Constants.Bomb.BASE_SPEED);
                thisEntity.getGame().addEntity(bombCreate);
                thisEntity.getComponent(PowerUpHandlerComponent.class)
                        .get().addBombPlaced(1);
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
