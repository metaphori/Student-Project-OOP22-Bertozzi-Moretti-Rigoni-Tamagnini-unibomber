package it.unibo.unibomber.game.controller.api;

import it.unibo.unibomber.game.controller.impl.Menu;
import it.unibo.unibomber.game.controller.impl.Play;

public interface World {

    Menu getMenu();

    Play getPlay();
}
