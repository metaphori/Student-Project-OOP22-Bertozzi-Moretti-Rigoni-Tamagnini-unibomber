package it.unibo.unibomber.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unibo.unibomber.game.controller.impl.WorldPanelImpl;

public class KeyboardInputsImpl implements KeyListener{
	
    private WorldPanelImpl worldPanel;

	public KeyboardInputsImpl(WorldPanelImpl worldPanel) {
		this.worldPanel = worldPanel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

    @Override
    public void keyReleased(KeyEvent e) {
     }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
