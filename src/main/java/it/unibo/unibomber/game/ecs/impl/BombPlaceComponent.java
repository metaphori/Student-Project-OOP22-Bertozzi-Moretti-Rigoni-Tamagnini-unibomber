package it.unibo.unibomber.game.ecs.impl;

/**
 * This component manage bomb placement.
 */
public class BombPlaceComponent extends AbstractComponent {

    private boolean bombPlaced = false;

    @Override
    public final void update() {
        if (this.bombPlaced) {
            this.getEntity().getGame().addEntity(this.getEntity().getGame().getFactory().makeBomb(this.getEntity()));
            this.getEntity().getComponent(PowerUpHandlerComponent.class)
                    .get().setBombPlaced(
                            this.getEntity().getComponent(PowerUpHandlerComponent.class).get().getBombPlaced() + 1);
            this.bombPlaced = false;
        }
    }

    /**
     * This method set the state of the bomb.
     */
    public void placeBomb() {
        this.bombPlaced = true;
    }

}
