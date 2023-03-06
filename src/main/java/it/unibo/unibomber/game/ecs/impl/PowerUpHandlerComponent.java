package it.unibo.unibomber.game.ecs.impl;

public class PowerUpHandlerComponent extends AbstractComponent {

    private int bombNumber;
    private int bombPower;
    private int speed;

    public PowerUpHandlerComponent(int bombNumber, int bombPower, int speed) {
        this.bombNumber = bombNumber;
        this.bombPower = bombPower;
        this.speed = speed;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    /**
     * @return actual bomb number of player
     */
    public int getBombNumber() {
        return this.bombNumber;
    }

    /**
     * @return actual bomb power of player
     */
    public int getBombPower() {
        return this.bombPower;
    }

    /**
     * @return actual speed of player
     */
    public int getSpeed() {
        return this.speed;
    }

}
