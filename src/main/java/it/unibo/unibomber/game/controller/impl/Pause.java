package it.unibo.unibomber.game.controller.impl;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.impl.StateGameButtonImpl;
import it.unibo.unibomber.game.view.PauseView;
import it.unibo.unibomber.utilities.Constants.UI.OptionButton;
import it.unibo.unibomber.utilities.Constants.UI.Screen;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

/**
 * This class manage the game menu.
 */
public class Pause extends StateImpl implements MouseListener, KeyListener, GameLoop {

    private StateGameButtonImpl[] buttons = new StateGameButtonImpl[3];
    private final PauseView view;

    /**
     * This method manage the view of game menu.
     */
    public Pause() {
        super();
        view = new PauseView(this);
        loadButtons();
    }

    private void loadButtons() {
        buttons[0] = new StateGameButtonImpl(null,
                (Screen.getgWidth() - OptionButton.getGameStateDimension().getX()) / 2, Screen.getgHeight() / 4,
                OptionButton.getGameStateDimension().getX(), OptionButton.getGameStateDimension().getY(), 0);
        buttons[1] = new StateGameButtonImpl(Gamestate.PLAY,
                (((Screen.getgWidth() - OptionButton.getGameStateDimension().getX()) / 2)
                        - OptionButton.getContinueDimension().getX()) / 2,
                (Screen.getgHeight() - Screen.getgHeight() / 4),
                OptionButton.getContinueDimension().getX(), OptionButton.getContinueDimension().getY(), 1);
        buttons[2] = new StateGameButtonImpl(Gamestate.MENU,
                Screen.getgWidth() - Screen.getgWidth() / 4, (Screen.getgHeight() - Screen.getgHeight() / 4),
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
        view.update();
    }

    @Override
    public final void draw(final Graphics g) {
        view.draw(g);
    }

    @Override
    public final void mouseClicked(final MouseEvent e) {
    }

    @Override
    public final void mouseEntered(final MouseEvent e) {
    }

    @Override
    public final void mouseExited(final MouseEvent e) {
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

    @Override
    public final void keyPressed(final KeyEvent e) {
    }

    @Override
    public final void keyReleased(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Gamestate.setGameState(Gamestate.PLAY);
        }
    }

    @Override
    public final void keyTyped(final KeyEvent e) {
    }
}
