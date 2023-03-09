package it.unibo.unibomber.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.view.WorldPanelImpl;

public class MouseInputsImpl implements MouseListener{

	private WorldPanelImpl worldPanel;

    public MouseInputsImpl(WorldPanelImpl worldPanel) {
		this.worldPanel = worldPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (Gamestate.state) {
			case MENU:
				worldPanel.getWorld().getMenu().mousePressed(e);
				break;
			case PLAY:
			break;
			default:
				break;
	
			}	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (Gamestate.state) {
			case MENU:
				worldPanel.getWorld().getMenu().mouseReleased(e);
				break;
			case PLAY:
			break;
			default:
				break;
	
			}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
