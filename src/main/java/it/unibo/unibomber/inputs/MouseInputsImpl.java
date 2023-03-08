package it.unibo.unibomber.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import it.unibo.unibomber.game.controller.api.WorldPanel;

public class MouseInputsImpl implements MouseListener{

	private WorldPanel worldPanel;

    public MouseInputsImpl(WorldPanel worldPanel) {
		this.worldPanel = worldPanel;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
