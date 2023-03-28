package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.stream.IntStream;

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
    private OptionButtonImpl[] optionButtons = new OptionButtonImpl[8 + MapOption.getNumberOfBot()];
    private final WorldImpl world;

    /**
     * This method manage the view of game option.
     * 
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
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() / 2, 0,
                Buttons.getOptionButtonSize(), Buttons.getOptionButtonSize(), "left");
        optionButtons[1] = new OptionButtonImpl(this, Screen.getgWidth() / 2 - MapOption.getMapDimension() / 2,
                OptionButton.WIDTH_INCREMENT, 1,
                MapOption.getMapDimension(), MapOption.getMapDimension(), "map");
        optionButtons[2] = new OptionButtonImpl(this, Screen.getgWidth() - (Screen.getgWidth() / 4),
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() / 2, 2,
                Buttons.getOptionButtonSize(), Buttons.getOptionButtonSize(), "right");
        optionButtons[3] = new OptionButtonImpl(this,
                Screen.getgWidth() - (Buttons.getOptionButtonSize() + (OptionButton.WIDTH_INCREMENT * 2)),
                Screen.getgHeight() - (Buttons.getOptionButtonSize() + OptionButton.WIDTH_INCREMENT),
                3, Buttons.getOptionButtonSize() + OptionButton.WIDTH_INCREMENT,
                Buttons.getOptionButtonSize(), "ok");
        optionButtons[4] = new OptionButtonImpl(this, OptionButton.WIDTH_INCREMENT,
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() + OptionButton.HEIGHT_BOTNUMBER_SELECTION,
                7, OptionButton.getBombNumberDimension(), OptionButton.getBombNumberDimension(), "botNumber");
        optionButtons[5] = new OptionButtonImpl(this,
                OptionButton.WIDTH_INCREMENT + (int) optionButtons[4].getW() + OptionButton.WIDTH_INCREMENT,
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() + OptionButton.HEIGHT_BOTNUMBER_SELECTION,
                8, OptionButton.getIncrementBotSize(), OptionButton.getIncrementBotSize(), "+");
        optionButtons[6] = new OptionButtonImpl(this,
                OptionButton.WIDTH_INCREMENT + (int) optionButtons[4].getW() + OptionButton.WIDTH_INCREMENT,
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() + OptionButton.HEIGHT_BOTNUMBER_SELECTION
                        + optionButtons[5].getH(),
                9, OptionButton.getIncrementBotSize(), OptionButton.getIncrementBotSize(), "-");
        optionButtons[7] = new OptionButtonImpl(this, OptionButton.getPlyerSelectioBorderDistance(),
                optionButtons[4].getY() + optionButtons[4].getH() + OptionButton.PLAYER_WIDTH_INCREMENT,
                4, OptionButton.getPlyerSelectionWidth(), OptionButton.getPlyerSelectionHeight(), "player");
        // 1 scalare quando > 4
        setBot();
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
                if ("player".equals(mb.getType())) {
                    optionButtons[7] = new OptionButtonImpl(this, OptionButton.getPlyerSelectioBorderDistance(),
                            OptionButton.PLAYER_WIDTH_INCREMENT + MapOption.getMapDimension()
                                    + (OptionButton.WIDTH_INCREMENT * 10),
                            5, OptionButton.getPlyerSelectionWidth(), OptionButton.getPlyerSelectionHeight(), "player");
                } else {
                    optionButtons[7] = new OptionButtonImpl(this, OptionButton.getPlyerSelectioBorderDistance(),
                            OptionButton.PLAYER_WIDTH_INCREMENT + MapOption.getMapDimension()
                                    + (OptionButton.WIDTH_INCREMENT * 10),
                            4, OptionButton.getPlyerSelectionWidth(), OptionButton.getPlyerSelectionHeight(), "player");
                }
            }
        }
    }

    @Override
    public final void mouseReleased(final MouseEvent e) {
        for (final OptionButtonImpl mb : optionButtons) {
            if (isMouseIn(e, mb)) {
                if (mb.isMousePressed()) {
                    if ("+".equals(mb.getType()) || "-".equals(mb.getType())) {
                        resetBot();
                    }
                    mb.setupGame();
                    if ("+".equals(mb.getType()) || "-".equals(mb.getType())) {
                        setBot();
                    }
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

    /**
     * Set dynamic Bot based on constant number.
     */
    private void setBot() {
        int distanceScale = OptionButton.getPlyerSelectioBorderDistance();
        int heightScale = 0;
        boolean passed = false;
        for (int i = 0; i < MapOption.getNumberOfBot(); i++) {
            heightScale += (int) optionButtons[7].getH() + OptionButton.WIDTH_INCREMENT;
            optionButtons[8 + i] = new OptionButtonImpl(this, distanceScale,
                    optionButtons[7].getY() + heightScale,
                    6, OptionButton.getPlyerSelectionWidth(), OptionButton.getPlyerSelectionHeight(), "bot");
            if (i > 2 && !passed) {
                distanceScale = OptionButton.getPlyerSelectioBorderDistance() + optionButtons[7].getW()
                        + OptionButton.WIDTH_INCREMENT;
                heightScale = 0 - ((int) optionButtons[7].getH() + OptionButton.WIDTH_INCREMENT);
                passed = true;
            }
        }
    }

    /**
     * reset all bot.
     */
    private void resetBot() {
        IntStream.range(8, 8 + MapOption.getNumberOfBot())
                .forEach(i -> optionButtons[i] = new OptionButtonImpl(null, 0, 0, 0, 0, 0, "empty"));
    }
}
