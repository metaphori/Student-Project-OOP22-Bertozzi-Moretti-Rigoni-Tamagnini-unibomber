package it.unibo.unibomber.game.controller.impl;

import java.awt.event.MouseEvent;

import it.unibo.unibomber.game.controller.api.State;
import it.unibo.unibomber.game.model.impl.MenuButtonImpl;

public class StateImpl implements State {

	protected WorldImpl world;

	public StateImpl(final WorldImpl world) {
		this.world = world;
	}

	@Override
	public final boolean isMouseIn(final MouseEvent e, final MenuButtonImpl mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}
}
