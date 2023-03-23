package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.impl.OptionButtonImpl;
import it.unibo.unibomber.game.view.OptionView;
import it.unibo.unibomber.utilities.Constants.UI.Game;
import it.unibo.unibomber.utilities.Constants.UI.Buttons;

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
        buttons[0] = new OptionButtonImpl(Game.getgWidth() / 4 - Buttons.getDefaultOptionButtonSize(),
                (Buttons.DEFAULT_TOP_DISTANCE + 125), 0,
                Buttons.getDefaultOptionButtonSize(), Buttons.getDefaultOptionButtonSize(), "left");
        buttons[1] = new OptionButtonImpl((Game.getgWidth() / 2) - 125, Buttons.DEFAULT_TOP_DISTANCE, 1,
                250, 250, "map");
        buttons[2] = new OptionButtonImpl(Game.getgWidth() - (Game.getgWidth() / 4),
                (Buttons.DEFAULT_TOP_DISTANCE + 125), 2,
                Buttons.getDefaultOptionButtonSize(), Buttons.getDefaultOptionButtonSize(), "right");
        buttons[3] = new OptionButtonImpl(
                Game.getgWidth() - (Buttons.getDefaultOptionButtonSize() + 20),
                Game.getgHeight() - (Buttons.getDefaultOptionButtonSize() + 10),
                3, Buttons.getDefaultOptionButtonSize() + 10, Buttons.getDefaultOptionButtonSize(), "ok");
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
