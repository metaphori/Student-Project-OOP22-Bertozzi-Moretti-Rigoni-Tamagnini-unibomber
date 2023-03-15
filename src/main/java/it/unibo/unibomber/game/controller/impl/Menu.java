package it.unibo.unibomber.game.controller.impl;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.impl.MenuButtonImpl;
import it.unibo.unibomber.game.view.MenuView;
import it.unibo.unibomber.utilities.Constants;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import static it.unibo.unibomber.utilities.Constants.UI.Buttons.TOP_DISTANCE_PLAY;
import static it.unibo.unibomber.utilities.Constants.UI.Buttons.TOP_DISTANCE_QUIT;

/**
 * This class manage the game menu.
 */
public class Menu extends StateImpl implements MouseListener, GameLoop {

    private MenuButtonImpl[] buttons = new MenuButtonImpl[2];
    private final MenuView view;

    /**
     * This method manage the view of game menu.
     */
    public Menu() {
        super();
        view = new MenuView(this);
        loadButtons();
    }

    private void loadButtons() {
        buttons[0] = new MenuButtonImpl(Constants.UI.Game.G_WIDTH / 2,
                (int) (TOP_DISTANCE_PLAY * Constants.UI.Game.SCALE), 0,
                Gamestate.PLAY);
        buttons[1] = new MenuButtonImpl(Constants.UI.Game.G_WIDTH / 2,
                (int) (TOP_DISTANCE_QUIT * Constants.UI.Game.SCALE), 1,
                Gamestate.QUIT);

    }

    /**
     * @return button menu pressed
     */
    public final MenuButtonImpl[] getButtons() {
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
        for (final MenuButtonImpl mb : buttons) {
            if (isMouseIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

    @Override
    public final void mouseReleased(final MouseEvent e) {
        for (final MenuButtonImpl mb : buttons) {
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
        for (final MenuButtonImpl mb : buttons) {
            mb.reset();
        }

    }
}
