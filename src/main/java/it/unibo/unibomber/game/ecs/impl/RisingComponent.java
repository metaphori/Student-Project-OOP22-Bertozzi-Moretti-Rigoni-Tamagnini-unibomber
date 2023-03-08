package it.unibo.unibomber.game.ecs.impl;

/**
 * This component manage the rise of the wall
 */
public class RisingComponent extends AbstractComponent{

    private boolean isRising = false;

    @Override
    public void update() {
        if (this.isRising) {

        }
    }
    
    public void riseWall() {
        this.isRising = true;
    }

}
