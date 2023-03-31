package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.api.Handicap;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.model.impl.OptionButtonImpl;
import it.unibo.unibomber.game.view.HandicapView;
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
    private final HandicapView hView;
    private final Map<Integer, OptionButtonImpl> optionButtons;
    private final WorldImpl world;
    private int focusIndex;
    private final Map<Integer, List<PowerUpType>> powerUpListOfEntity;
    private int basedWidth;

    /**
     * This method manage the view of game option.
     * 
     * @param world
     */
    public Option(final WorldImpl world) {
        super();
        this.world = world;
        this.focusIndex = -1;
        this.basedWidth = 0;
        powerUpListOfEntity = new HashMap<>();
        optionButtons = new HashMap<>();
        view = new OptionView(this);
        hView = new HandicapView(this);
        loadButtons();
        loadPowerUpList();
    }

    private void loadPowerUpList() {
        powerUpListOfEntity.put(0, new ArrayList<>());
    }

    private void loadButtons() {
        optionButtons.put(Handicap.LEFT.getIndex(), new OptionButtonImpl(this,
                Screen.getgWidth() / 4 - Buttons.getOptionButtonSize(),
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() / 2, Handicap.LEFT.getIndex(),
                Buttons.getOptionButtonSize(), Buttons.getOptionButtonSize(), Handicap.LEFT.getType()));
        optionButtons.put(0, new OptionButtonImpl(this, Screen.getgWidth() / 2 - MapOption.getMapDimension() / 2,
                OptionButton.WIDTH_INCREMENT, 0,
                MapOption.getMapDimension(), MapOption.getMapDimension(), "map"));
        optionButtons.put(Handicap.RIGHT.getIndex(), new OptionButtonImpl(this,
                Screen.getgWidth() - (Screen.getgWidth() / 4),
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() / 2, Handicap.RIGHT.getIndex(),
                Buttons.getOptionButtonSize(), Buttons.getOptionButtonSize(), Handicap.RIGHT.getType()));
        optionButtons.put(Handicap.OK.getIndex(), new OptionButtonImpl(this,
                Screen.getgWidth() - (Buttons.getOptionButtonSize() + (OptionButton.WIDTH_INCREMENT * 2)),
                Screen.getgHeight() - (Buttons.getOptionButtonSize() + OptionButton.WIDTH_INCREMENT),
                Handicap.OK.getIndex(), Buttons.getOptionButtonSize() + OptionButton.WIDTH_INCREMENT,
                Buttons.getOptionButtonSize() - OptionButton.WIDTH_INCREMENT, Handicap.OK.getType()));
        optionButtons.put(Handicap.BOTNUMBER.getIndex(), new OptionButtonImpl(this, OptionButton.WIDTH_INCREMENT,
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() + OptionButton.HEIGHT_BOTNUMBER_SELECTION,
                Handicap.BOTNUMBER.getIndex(), OptionButton.getBombNumberDimension(),
                OptionButton.getBombNumberDimension(), Handicap.BOTNUMBER.getType()));
        optionButtons.put(Handicap.PLUS.getIndex(), new OptionButtonImpl(this,
                OptionButton.WIDTH_INCREMENT + (int) optionButtons.get(Handicap.BOTNUMBER.getIndex()).getW()
                        + OptionButton.WIDTH_INCREMENT,
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() + OptionButton.HEIGHT_BOTNUMBER_SELECTION,
                Handicap.PLUS.getIndex(), OptionButton.getIncrementBotSize(), OptionButton.getIncrementBotSize(),
                Handicap.PLUS.getType()));
        optionButtons.put(Handicap.MINUS.getIndex(), new OptionButtonImpl(this,
                OptionButton.WIDTH_INCREMENT + (int) optionButtons.get(Handicap.BOTNUMBER.getIndex()).getW()
                        + OptionButton.WIDTH_INCREMENT,
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() + OptionButton.HEIGHT_BOTNUMBER_SELECTION
                        + optionButtons.get(Handicap.PLUS.getIndex()).getH(),
                Handicap.MINUS.getIndex(), OptionButton.getIncrementBotSize(), OptionButton.getIncrementBotSize(),
                Handicap.MINUS.getType()));
        optionButtons.put(Handicap.PLAYER.getIndex(), new OptionButtonImpl(this,
                OptionButton.getPlyerSelectioBorderDistance(),
                optionButtons.get(Handicap.BOTNUMBER.getIndex()).getY()
                        + optionButtons.get(Handicap.BOTNUMBER.getIndex()).getH()
                        + OptionButton.PLAYER_WIDTH_INCREMENT,
                Handicap.PLAYER.getIndex(), OptionButton.getPlyerSelectionWidth(),
                OptionButton.getPlyerSelectionHeight(), Handicap.PLAYER.getType(), 0));
        setBot();
        setPowerUp();
        basedWidth += OptionButton.WIDTH_INCREMENT;
        optionButtons.put(Handicap.DELETE.getIndex(),
                new OptionButtonImpl(this, OptionButton.WIDTH_INCREMENT * 2 + basedWidth,
                        OptionButton.getPowerUpSetTopDistance()
                                + (Buttons.getOptionButtonSize() - Buttons.getOptionButtonSize() / 2) / 2,
                        Handicap.DELETE.getIndex(), Buttons.getOptionButtonSize() / 2 + OptionButton.WIDTH_INCREMENT,
                        Buttons.getOptionButtonSize() / 2,
                        Handicap.DELETE.getType()));
        basedWidth += Buttons.getOptionButtonSize() / 2 + OptionButton.WIDTH_INCREMENT * 2;
        optionButtons.put(Handicap.DELETE_ALL.getIndex(),
                new OptionButtonImpl(this, OptionButton.WIDTH_INCREMENT * 2 + basedWidth,
                        OptionButton.getPowerUpSetTopDistance()
                                + (Buttons.getOptionButtonSize() - Buttons.getOptionButtonSize() / 2) / 2,
                        Handicap.DELETE_ALL.getIndex(),
                        Buttons.getOptionButtonSize() / 2 + OptionButton.WIDTH_INCREMENT,
                        Buttons.getOptionButtonSize() / 2,
                        Handicap.DELETE_ALL.getType()));
    }

    /**
     * @return button option pressed
     */
    public final Map<Integer, OptionButtonImpl> getOptionButtons() {
        return Map.copyOf(optionButtons);
    }

    @Override
    public final void update() {
        view.update();
    }

    @Override
    public final void draw(final Graphics g) {
        view.draw(g);
        hView.draw(g);
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
        for (final OptionButtonImpl mb : optionButtons.values()) {
            if (isMouseIn(e, mb)) {
                mb.setMousePressed(true);
                if ("player".equals(mb.getType())) {
                    optionButtons.put(Handicap.PLAYER.getIndex(), new OptionButtonImpl(this,
                            OptionButton.getPlyerSelectioBorderDistance(),
                            optionButtons.get(Handicap.BOTNUMBER.getIndex()).getY()
                                    + optionButtons.get(Handicap.BOTNUMBER.getIndex()).getH()
                                    + OptionButton.PLAYER_WIDTH_INCREMENT,
                            Handicap.PLAYER_HOVER.getIndex(), OptionButton.getPlyerSelectionWidth(),
                            OptionButton.getPlyerSelectionHeight(), Handicap.PLAYER_HOVER.getType(),
                            0));
                    optionButtons.get(Handicap.PLAYER.getIndex()).setMousePressed(true);
                } else {
                    optionButtons.put(Handicap.PLAYER.getIndex(), new OptionButtonImpl(this,
                            OptionButton.getPlyerSelectioBorderDistance(),
                            optionButtons.get(Handicap.BOTNUMBER.getIndex()).getY()
                                    + optionButtons.get(Handicap.BOTNUMBER.getIndex()).getH()
                                    + OptionButton.PLAYER_WIDTH_INCREMENT,
                            Handicap.PLAYER.getIndex(), OptionButton.getPlyerSelectionWidth(),
                            OptionButton.getPlyerSelectionHeight(), Handicap.PLAYER.getType(),
                            0));
                }
            }
        }
    }

    @Override
    public final void mouseReleased(final MouseEvent e) {
        for (final OptionButtonImpl mb : optionButtons.values()) {
            if (isMouseIn(e, mb)) {
                if (mb.isMousePressed()) {
                    if ("+".equals(mb.getType()) || "-".equals(mb.getType())) {
                        resetBot();
                    }
                    mb.setupGame();
                    if ("+".equals(mb.getType()) || "-".equals(mb.getType())) {
                        setBot();
                    }
                    if ("powerup".equals(mb.getType()) && focusIndex >= 0
                            && powerUpListOfEntity.get(focusIndex).size() < OptionButton.MAX_HANDICAP) {
                        powerUpListOfEntity.get(focusIndex).add(mb.getPType());
                    }
                    if ("delete".equals(mb.getType()) && !powerUpListOfEntity.get(focusIndex).isEmpty()) {
                        powerUpListOfEntity.get(focusIndex).remove(powerUpListOfEntity.get(focusIndex).size() - 1);
                    }
                    if ("deleteAll".equals(mb.getType()) && focusIndex >= 0) {
                        powerUpListOfEntity.put(focusIndex, new ArrayList<>());
                    }
                    if ("player".equals(mb.getType()) || "bot".equals(mb.getType())) {
                        focusIndex = mb.getIndex();
                    }
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (final OptionButtonImpl mb : optionButtons.values()) {
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
            heightScale += (int) optionButtons.get(Handicap.PLAYER.getIndex()).getH() + OptionButton.WIDTH_INCREMENT;
            optionButtons.put((Handicap.BOT.getIndex() - 1) + i, new OptionButtonImpl(this, distanceScale,
                    optionButtons.get(Handicap.PLAYER.getIndex()).getY() + heightScale,
                    Handicap.BOT.getIndex(), OptionButton.getPlyerSelectionWidth(),
                    OptionButton.getPlyerSelectionHeight(), Handicap.BOT.getType(), i + 1));
            if (i == 0 && !passed) {
                distanceScale = OptionButton.getPlyerSelectioBorderDistance()
                        + optionButtons.get(Handicap.PLAYER.getIndex()).getW()
                        + OptionButton.WIDTH_INCREMENT;
                heightScale = 0
                        - ((int) optionButtons.get(Handicap.PLAYER.getIndex()).getH() + OptionButton.WIDTH_INCREMENT);
                passed = true;
            }
            powerUpListOfEntity.put(i + 1, new ArrayList<>());
        }
    }

    /**
     * reset all bot.
     */
    private void resetBot() {
        IntStream.range(Handicap.BOT.getIndex() - 1, Handicap.BOT.getIndex() - 1 + MapOption.getNumberOfBot())
                .forEach(i -> optionButtons.put(i, new OptionButtonImpl(null, 0, 0, 0, 0, 0, "empty")));
        powerUpListOfEntity.keySet().removeIf(k -> k != 0);
    }

    /**
     * Set power up buttons.
     */
    private void setPowerUp() {
        for (int i = 0; i < Handicap.getNumberOfPowerUp(); i++) {
            optionButtons.put(16 + i, new OptionButtonImpl(this, OptionButton.WIDTH_INCREMENT * 2 + basedWidth,
                    OptionButton.getPowerUpSetTopDistance()
                            + (Buttons.getOptionButtonSize() - Buttons.getOptionButtonSize() / 2) / 2,
                    10 + i, Buttons.getOptionButtonSize() / 2, Buttons.getOptionButtonSize() / 2, "powerup",
                    Handicap.getPType(10 + i)));
            basedWidth += Buttons.getOptionButtonSize() / 2 + OptionButton.WIDTH_INCREMENT / 2;
        }
    }

    /**
     * @param index
     * @return list of power up of that index.
     */
    public List<PowerUpType> getIndexListPowerUp(final int index) {
        return powerUpListOfEntity.get(index);
    }

    /**
     * @return list of power up of that index.
     */
    public Map<Integer, List<PowerUpType>> getListPowerUp() {
        return this.powerUpListOfEntity;
    }

    /**
     * @param index
     * @return top distance of index button.
     */
    public Integer getTopDistance(final int index) {
        for (final OptionButtonImpl btn : optionButtons.values()) {
            if (btn.getIndex() == index) {
                return btn.getY();
            }
        }
        return 0;
    }
}
