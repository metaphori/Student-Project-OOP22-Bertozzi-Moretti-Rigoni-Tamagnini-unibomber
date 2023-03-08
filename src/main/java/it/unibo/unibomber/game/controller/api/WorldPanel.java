package it.unibo.unibomber.game.controller.api;

import it.unibo.unibomber.game.controller.impl.WorldImpl;

public interface WorldPanel{
    public void updateWorld();
    public WorldImpl getWorld ();
}
