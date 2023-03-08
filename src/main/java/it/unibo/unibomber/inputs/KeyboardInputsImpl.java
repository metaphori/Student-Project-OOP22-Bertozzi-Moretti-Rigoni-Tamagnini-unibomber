package it.unibo.unibomber.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unibo.unibomber.game.controller.impl.WorldPanelImpl;
import it.unibo.unibomber.game.model.api.Gamestate;

public class KeyboardInputsImpl implements KeyListener{
	
    private WorldPanelImpl worldPanel;

	public KeyboardInputsImpl(WorldPanelImpl worldPanel) {
		this.worldPanel = worldPanel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (Gamestate.state) {
			case PLAY:
			worldPanel.getWorld().getPlay().keyPressed(e);
				break;
			default:
				break;
		}
	}

    @Override
    public void keyReleased(KeyEvent e) {
		switch (Gamestate.state) {
			case PLAY:
			worldPanel.getWorld().getPlay().keyPressed(e);
				break;
			default:
				break;
		}
     }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
