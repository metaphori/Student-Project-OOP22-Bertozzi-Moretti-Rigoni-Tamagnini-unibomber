package it.unibo.unibomber.game.controller.impl;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.impl.StateGameButtonImpl;
import it.unibo.unibomber.game.view.StateGameView;
import it.unibo.unibomber.utilities.Constants.UI.OptionButton;
import it.unibo.unibomber.utilities.Constants.UI.Screen;

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
        buttons[1] = new StateGameButtonImpl(Gamestate.MENU,
                ((Screen.getgWidth() - OptionButton.getGameStateDimension().getX()) / 2
                        - OptionButton.getContinueDimension().getX()) / 2,
                Screen.getgHeight() - Screen.getgHeight() / 4,
                OptionButton.getContinueDimension().getX(), OptionButton.getContinueDimension().getY(), 1);
        buttons[2] = new StateGameButtonImpl(Gamestate.QUIT,
                Screen.getgWidth() - Screen.getgWidth() / 4, Screen.getgHeight() - Screen.getgHeight() / 4,
                OptionButton.getQuitDimension().getX(), OptionButton.getQuitDimension().getY(),
                2);
    }

    /**
     * @return button menu pressed
     */
    public final StateGameButtonImpl[] getButtons() {
        return Arrays.copyOf(buttons, buttons.length);
    }

    @Override
    public final void update() {
        final int index = Gamestate.getGamestate() == Gamestate.WIN ? 3 : 4;
        buttons[0] = new StateGameButtonImpl(null,
                (Screen.getgWidth() - OptionButton.getGameStateDimension().getX()) / 2, Screen.getgHeight() / 4,
                OptionButton.getGameStateDimension().getX(), OptionButton.getGameStateDimension().getY(), index);
        view.update();
    }

    @Override
    public final void draw(final Graphics g) {
        final int index = Gamestate.getGamestate() == Gamestate.WIN ? 3 : 4;
        buttons[0] = new StateGameButtonImpl(null,
                (Screen.getgWidth() - OptionButton.getGameStateDimension().getX()) / 2, Screen.getgHeight() / 4,
                OptionButton.getGameStateDimension().getX(), OptionButton.getGameStateDimension().getY(), index);
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
