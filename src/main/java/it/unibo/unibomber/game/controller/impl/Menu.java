package it.unibo.unibomber.game.controller.impl;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.impl.MenuButtonImpl;
import it.unibo.unibomber.game.view.MenuView;
import it.unibo.unibomber.utilities.Constants;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



public class Menu extends StateImpl implements MouseListener, KeyListener, GameLoop{

    private MenuButtonImpl[] buttons = new MenuButtonImpl[2];
    private MenuView view;

	public Menu(WorldImpl world) {
		super(world);
        view= new MenuView(this);
		loadButtons();
	}

	private void loadButtons() {
		buttons[0] = new MenuButtonImpl(Constants.UI.Game.G_WIDTH / 2, (int) (150 * Constants.UI.Game.SCALE), 0, Gamestate.PLAY);
		buttons[1] = new MenuButtonImpl(Constants.UI.Game.G_WIDTH / 2, (int) (190 * Constants.UI.Game.SCALE), 1, Gamestate.QUIT);

	}
    public MenuButtonImpl[] getButtons(){
        return buttons;
    }
	@Override
	public void update() {
		view.update();
	}

	@Override
	public void draw(Graphics g) {
        view.draw(g);
	}

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButtonImpl mb : buttons) {
			if (isMouseIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButtonImpl mb : buttons) {
			if (isMouseIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGamestate();
				break;
			}
		}
		resetButtons();
    }

    private void resetButtons() {
		for (MenuButtonImpl mb : buttons) {
			mb.reset();
        }

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
