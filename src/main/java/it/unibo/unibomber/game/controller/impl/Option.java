package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.impl.OptionButtonImpl;
import it.unibo.unibomber.game.view.OptionView;
import it.unibo.unibomber.utilities.Constants.UI.Screen;
import it.unibo.unibomber.utilities.Constants.UI.Buttons;
import static it.unibo.unibomber.utilities.Constants.UI.OptionButton;
import static it.unibo.unibomber.utilities.Constants.UI.MapOption;

/**
 * Option class.
 */
public class Option extends StateImpl implements MouseListener, GameLoop {

    private final OptionView view;
    private OptionButtonImpl[] optionButtons = new OptionButtonImpl[4];
    private final WorldImpl world;

    /**
     * This method manage the view of game option.
     * @param world
     */
    public Option(final WorldImpl world) {
        super();
        this.world = world;
        view = new OptionView(this);
        loadButtons();
    }

    private void loadButtons() {
        optionButtons[0] = new OptionButtonImpl(this, Screen.getgWidth() / 4 - Buttons.getOptionButtonSize(),
                Buttons.getDefaultTopDistance() + MapOption.getMapDimension() / 2, 0,
                Buttons.getOptionButtonSize(), Buttons.getOptionButtonSize(), "left");
        optionButtons[1] = new OptionButtonImpl(this, Screen.getgWidth() / 2 - MapOption.getMapDimension() / 2,
                Buttons.getDefaultTopDistance(), 1,
                MapOption.getMapDimension(), MapOption.getMapDimension(), "map");
        optionButtons[2] = new OptionButtonImpl(this, Screen.getgWidth() - (Screen.getgWidth() / 4),
                Buttons.getDefaultTopDistance() + MapOption.getMapDimension() / 2, 2,
                Buttons.getOptionButtonSize(), Buttons.getOptionButtonSize(), "right");
        optionButtons[3] = new OptionButtonImpl(this,
                Screen.getgWidth() - (Buttons.getOptionButtonSize() + (OptionButton.WIDTH_OK_INCREMENT * 2)),
                Screen.getgHeight() - (Buttons.getOptionButtonSize() + OptionButton.WIDTH_OK_INCREMENT),
                3, Buttons.getOptionButtonSize() + OptionButton.WIDTH_OK_INCREMENT,
                Buttons.getOptionButtonSize(), "ok");
    }

    /**
     * @return button option pressed
     */
    public final OptionButtonImpl[] getOptionButtons() {
        return Arrays.copyOf(optionButtons, optionButtons.length);
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
        for (final OptionButtonImpl mb : optionButtons) {
            if (isMouseIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

    @Override
    public final void mouseReleased(final MouseEvent e) {
        for (final OptionButtonImpl mb : optionButtons) {
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
        for (final OptionButtonImpl mb : optionButtons) {
            mb.reset();
        }

    }

    /**
     * @return world.
     */
    public WorldImpl getWorld() {
        return world;
    }
}
