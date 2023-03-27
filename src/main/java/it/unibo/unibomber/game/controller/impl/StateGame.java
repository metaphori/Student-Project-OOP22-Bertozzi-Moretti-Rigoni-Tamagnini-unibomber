package it.unibo.unibomber.game.controller.impl;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.impl.StateGameButtonImpl;
import it.unibo.unibomber.game.view.StateGameView;
import it.unibo.unibomber.utilities.Constants;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

/**
 * This class manage the game menu.
 */
public class StateGame extends StateImpl implements MouseListener, GameLoop {

    private StateGameButtonImpl[] buttons = new StateGameButtonImpl[3];
    private final StateGameView view;

    /**
     * This method manage the view of game menu.
     */
    public StateGame() {
        super();
        view = new StateGameView(this);
        loadButtons();
    }

    private void loadButtons() {
        buttons[0] = new StateGameButtonImpl(null, (Constants.UI.Screen.getgWidth() / 2) - 50,
                Constants.UI.Screen.getgHeight() / 4, 100, 50, 0);
        buttons[1] = new StateGameButtonImpl(Gamestate.MENU, (Constants.UI.Screen.getgWidth() / 4 - 50),
                (Constants.UI.Screen.getgHeight() - Constants.UI.Screen.getgHeight() / 4), 50, 25, 1);
        buttons[2] = new StateGameButtonImpl(Gamestate.QUIT,
                Constants.UI.Screen.getgWidth() - Constants.UI.Screen.getgWidth() / 4,
                (Constants.UI.Screen.getgHeight() - Constants.UI.Screen.getgHeight() / 4), 50, 25, 2);

    }

    /**
     * @return button menu pressed
     */
    public final StateGameButtonImpl[] getButtons() {
        return Arrays.copyOf(buttons, buttons.length);
    }

    @Override
    public final void update() {
        view.update();
    }

    @Override
    public final void draw(final Graphics g) {
        view.draw(g);
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    @Override
    public final void mousePressed(final MouseEvent e) {
        for (final StateGameButtonImpl mb : buttons) {
            if (isMouseIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

    @Override
    public final void mouseReleased(final MouseEvent e) {
        for (final StateGameButtonImpl mb : buttons) {
            if (isMouseIn(e, mb)) {
                if (mb.isMousePressed()) {
                    mb.applyGamestate();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (final StateGameButtonImpl mb : buttons) {
            mb.reset();
        }

    }
}
