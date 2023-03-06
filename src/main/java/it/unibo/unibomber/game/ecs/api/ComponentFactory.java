package it.unibo.unibomber.game.ecs.api;

import java.util.List;

public interface ComponentFactory {
    
    Component makePowerUpHandler(int bombNumber, int bombPower, List<PowerUpType> powerUpList);
}
