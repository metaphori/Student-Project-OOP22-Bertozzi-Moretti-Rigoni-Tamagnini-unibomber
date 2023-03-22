package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.impl.OptionButtonImpl;
import it.unibo.unibomber.game.view.OptionView;
import it.unibo.unibomber.utilities.Constants;

/**
 * Option class.
 */
public class Option extends StateImpl implements MouseListener, GameLoop {

    private final OptionView view;
    private OptionButtonImpl[] buttons = new OptionButtonImpl[4];

    /**
     * This method manage the view of game option.
     */
    public Option() {
        super();
        view = new OptionView(this);
        loadButtons();
    }

    private void loadButtons() {
       buttons[0] = new OptionButtonImpl(50, 60, 0, 40, 40, "left");
        buttons[1] = new OptionButtonImpl(50 + 200 + 50, 60, 1, 40, 40, "right");
        buttons[2] = new OptionButtonImpl(50 + 50, 40, 2,
                200, 200, "map");
        buttons[3] = new OptionButtonImpl(Constants.UI.Game.getgWidth() - 50, Constants.UI.Game.getgHeight() - 40, 3,
                40, 30, "ok");
    }

    /**
     * @return button menu pressed
     */
    public final OptionButtonImpl[] getButtons() {
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
        for (final OptionButtonImpl mb : buttons) {
            if (isMouseIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

    @Override
    public final void mouseReleased(final MouseEvent e) {
        for (final OptionButtonImpl mb : buttons) {
            if (isMouseIn(e, mb)) {
                if (mb.isMousePressed()) {
                    mb.setupGame();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (final OptionButtonImpl mb : buttons) {
            mb.reset();
        }

    }

}
