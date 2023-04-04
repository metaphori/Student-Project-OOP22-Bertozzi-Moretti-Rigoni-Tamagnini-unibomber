package it.unibo.unibomber.game.controller.impl;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.impl.StateGameButtonImpl;
import it.unibo.unibomber.game.view.StateGameView;
import it.unibo.unibomber.utilities.Constants.UI.OptionButton;
import it.unibo.unibomber.utilities.Constants.UI.Screen;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Optional;

/**
 * This class manage the game menu.
 */
public final class StateGame extends StateImpl implements MouseListener, KeyListener, GameLoop {

    private StateGameButtonImpl[] buttons = new StateGameButtonImpl[3];
    private final StateGameView view;
    private final WorldImpl world;

    /**
     * This method manage the view of game menu.
     * 
     * @param world
     */
    protected StateGame(final WorldImpl world) {
        super();
        this.world = world;
        view = new StateGameView(this);
        loadButtons();
    }

    /**
     * Load button based on gamestate.
     */
    public void loadButtons() {
        int index;
        if (Gamestate.getGamestate() == Gamestate.PAUSE) {
            index = 0;
        } else {
            index = Gamestate.getGamestate().equals(Gamestate.WIN) ? 3 : 4;
        }
        buttons[0] = new StateGameButtonImpl(null,
                (Screen.getgWidth() - OptionButton.getGameStateDimension().getX()) / 2, Screen.getgHeight() / 4,
                OptionButton.getGameStateDimension().getX(), OptionButton.getGameStateDimension().getY(), index);
        buttons[1] = new StateGameButtonImpl(Optional.of(Gamestate.getButtonStateGame().getX()),
                ((Screen.getgWidth() - OptionButton.getGameStateDimension().getX()) / 2
                        - OptionButton.getContinueDimension().getX()) / 2,
                Screen.getgHeight() - Screen.getgHeight() / 4,
                OptionButton.getContinueDimension().getX(), OptionButton.getContinueDimension().getY(), 1);
        buttons[2] = new StateGameButtonImpl(Optional.of(Gamestate.getButtonStateGame().getY()),
                Screen.getgWidth() - Screen.getgWidth() / 4, Screen.getgHeight() - Screen.getgHeight() / 4,
                OptionButton.getQuitDimension().getX(), OptionButton.getQuitDimension().getY(),
                2);
    }

    /**
     * @return button menu pressed
     */
    public StateGameButtonImpl[] getButtons() {
        return Arrays.copyOf(buttons, buttons.length);
    }

    @Override
    public void update() {
        view.update();
    }

    @Override
    public void draw(final Graphics g) {
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
    public void mousePressed(final MouseEvent e) {
        for (final StateGameButtonImpl mb : buttons) {
            if (isMouseIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        for (final StateGameButtonImpl mb : buttons) {
            if (isMouseIn(e, mb)) {
                if (mb.isMousePressed() && mb.getGameState().isPresent()) {
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
    public void keyPressed(final KeyEvent e) {
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        if (Gamestate.getGamestate().equals(Gamestate.PAUSE) && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.world.startTimer();
            Gamestate.setGameState(Gamestate.PLAY);
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }
}
