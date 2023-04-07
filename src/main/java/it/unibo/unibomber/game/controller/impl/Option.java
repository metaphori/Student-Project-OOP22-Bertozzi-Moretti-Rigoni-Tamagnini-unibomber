package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.api.Handicap;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.model.impl.OptionButtonImpl;
import it.unibo.unibomber.game.view.HandicapView;
import it.unibo.unibomber.game.view.OptionView;
import it.unibo.unibomber.utilities.Constants.UI.Screen;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.Constants.UI.Buttons;
import static it.unibo.unibomber.utilities.Constants.UI.OptionButton;
import static it.unibo.unibomber.utilities.Constants.UI.MapOption;

/**
 * Option class.
 */
public final class Option extends StateImpl implements MouseListener, GameLoop {

    private OptionView view;
    private HandicapView hView;
    private final Map<Integer, OptionButtonImpl> optionButtons;
    private final List<WorldImpl> world;
    private int focusIndex;
    private final Map<Integer, List<PowerUpType>> powerUpListOfEntity;
    private int basedWidth;

    /**
     * Option costructor.
     * 
     * @param world world.
     */
    public Option(final WorldImpl world) {
        super();
        this.world = new ArrayList<>();
        this.world.add(world);
        this.focusIndex = -1;
        this.basedWidth = 0;
        powerUpListOfEntity = new HashMap<>();
        optionButtons = new HashMap<>();
        hView = new HandicapView(this);
        loadButtons();
        loadPowerUpList();
        view = new OptionView(this);
    }

    private void loadPowerUpList() {
        powerUpListOfEntity.put(0, new ArrayList<>());
    }

    private void loadButtons() {
        optionButtons.put(Handicap.LEFT.getIndex(), new OptionButtonImpl(
                Screen.getgWidth() / 4 - Buttons.getOptionButtonSize(),
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() / 2, Handicap.LEFT.getIndex(),
                Buttons.getOptionButtonSize(), Buttons.getOptionButtonSize(), Handicap.LEFT.getType()));
        optionButtons.put(0, new OptionButtonImpl(Screen.getgWidth() / 2 - MapOption.getMapDimension() / 2,
                OptionButton.WIDTH_INCREMENT, 0,
                MapOption.getMapDimension(), MapOption.getMapDimension(), "map"));
        optionButtons.put(Handicap.RIGHT.getIndex(), new OptionButtonImpl(
                Screen.getgWidth() - (Screen.getgWidth() / 4),
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() / 2, Handicap.RIGHT.getIndex(),
                Buttons.getOptionButtonSize(), Buttons.getOptionButtonSize(), Handicap.RIGHT.getType()));
        optionButtons.put(Handicap.OK.getIndex(), new OptionButtonImpl(
                Screen.getgWidth() - (Buttons.getOptionButtonSize() + (OptionButton.WIDTH_INCREMENT * 2)),
                Screen.getgHeight() - (Buttons.getOptionButtonSize() + OptionButton.WIDTH_INCREMENT),
                Handicap.OK.getIndex(), Buttons.getOptionButtonSize() + OptionButton.WIDTH_INCREMENT,
                Buttons.getOptionButtonSize() - OptionButton.WIDTH_INCREMENT, Handicap.OK.getType()));
        optionButtons.put(Handicap.BOTNUMBER.getIndex(), new OptionButtonImpl(OptionButton.WIDTH_INCREMENT,
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() + OptionButton.HEIGHT_BOTNUMBER_SELECTION,
                Handicap.BOTNUMBER.getIndex(), OptionButton.getBombNumberDimension(),
                OptionButton.getBombNumberDimension(), Handicap.BOTNUMBER.getType()));
        optionButtons.put(Handicap.PLUS.getIndex(), new OptionButtonImpl(
                OptionButton.WIDTH_INCREMENT + (int) optionButtons.get(Handicap.BOTNUMBER.getIndex()).getW()
                        + OptionButton.WIDTH_INCREMENT,
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() + OptionButton.HEIGHT_BOTNUMBER_SELECTION,
                Handicap.PLUS.getIndex(), OptionButton.getIncrementBotSize(), OptionButton.getIncrementBotSize(),
                Handicap.PLUS.getType()));
        optionButtons.put(Handicap.MINUS.getIndex(), new OptionButtonImpl(
                OptionButton.WIDTH_INCREMENT + (int) optionButtons.get(Handicap.BOTNUMBER.getIndex()).getW()
                        + OptionButton.WIDTH_INCREMENT,
                OptionButton.WIDTH_INCREMENT + MapOption.getMapDimension() + OptionButton.HEIGHT_BOTNUMBER_SELECTION
                        + optionButtons.get(Handicap.PLUS.getIndex()).getH(),
                Handicap.MINUS.getIndex(), OptionButton.getIncrementBotSize(), OptionButton.getIncrementBotSize(),
                Handicap.MINUS.getType()));
        optionButtons.put(Handicap.PLAYER.getIndex(), new OptionButtonImpl(
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
                new OptionButtonImpl(OptionButton.WIDTH_INCREMENT * 2 + basedWidth,
                        OptionButton.getPowerUpSetTopDistance()
                                + (Buttons.getOptionButtonSize() - Buttons.getOptionButtonSize() / 2) / 2,
                        Handicap.DELETE.getIndex(), Buttons.getOptionButtonSize() / 2 + OptionButton.WIDTH_INCREMENT,
                        Buttons.getOptionButtonSize() / 2,
                        Handicap.DELETE.getType()));
        basedWidth += Buttons.getOptionButtonSize() / 2 + OptionButton.WIDTH_INCREMENT * 2;
        optionButtons.put(Handicap.DELETE_ALL.getIndex(),
                new OptionButtonImpl(OptionButton.WIDTH_INCREMENT * 2 + basedWidth,
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
    public Map<Integer, OptionButtonImpl> getOptionButtons() {
        return new HashMap<>(optionButtons);
    }

    @Override
    public void update() {
        view.update();
    }

    @Override
    public void draw(final Graphics g) {
        view = new OptionView(this);
        hView = new HandicapView(this);
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
    public void mousePressed(final MouseEvent e) {
        for (final OptionButtonImpl mb : optionButtons.values()) {
            if (isMouseIn(e, mb)) {
                final Handicap hH = Handicap.PLAYER.getType().equals(mb.getType()) ? Handicap.PLAYER_HOVER
                        : Handicap.BOT_HOVER;
                mb.setMousePressed(true);
                if (Handicap.PLAYER.getType().equals(mb.getType()) || Handicap.BOT.getType().equals(mb.getType())) {
                    resetHover();
                    for (final Entry<Integer, OptionButtonImpl> btn : optionButtons.entrySet()) {
                        if (optionButtons.get(btn.getKey()).equals(mb)) {
                            optionButtons.get(btn.getKey()).changeRowIndex(hH.getIndex());
                        }
                    }
                }
                if (Handicap.OK.getType().equals(mb.getType())) {
                    for (final Entry<Integer, OptionButtonImpl> btn : optionButtons.entrySet()) {
                        optionButtons.get(btn.getKey()).setOption(this);
                    }
                }
            }
        }
    }

    private void resetHover() {
        Handicap h;
        for (final Entry<Integer, OptionButtonImpl> btn : optionButtons.entrySet()) {
            h = Handicap.PLAYER.getType().equals(optionButtons.get(btn.getKey()).getType()) ? Handicap.PLAYER
                    : Handicap.BOT;
            if (Handicap.PLAYER.getType().equals(optionButtons.get(btn.getKey()).getType())
                    || Handicap.BOT.getType().equals(optionButtons.get(btn.getKey()).getType())) {
                optionButtons.get(btn.getKey()).changeRowIndex(h.getIndex());
            }
        }
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        for (final OptionButtonImpl mb : optionButtons.values()) {
            if (isMouseIn(e, mb)) {
                if (mb.isMousePressed()) {
                    if ("+".equals(mb.getType()) || "-".equals(mb.getType())) {
                        focusIndex = -1;
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
     * @return world copy.
     */
    public WorldImpl getWorld() {
        return world.get(0);
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
            optionButtons.put((Handicap.BOT.getIndex() - 1) + i, new OptionButtonImpl(distanceScale,
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
                .forEach(i -> optionButtons.put(i, new OptionButtonImpl(0, 0, 0, 0, 0, "empty")));
        powerUpListOfEntity.keySet().removeIf(k -> k != 0);
    }

    /**
     * Set power up buttons.
     */
    private void setPowerUp() {
        for (int i = 0; i < Handicap.getNumberOfPowerUp(); i++) {
            optionButtons.put(16 + i, new OptionButtonImpl(OptionButton.WIDTH_INCREMENT * 2 + basedWidth,
                    OptionButton.getPowerUpSetTopDistance()
                            + (Buttons.getOptionButtonSize() - Buttons.getOptionButtonSize() / 2) / 2,
                    10 + i, Buttons.getOptionButtonSize() / 2, Buttons.getOptionButtonSize() / 2, "powerup",
                    Handicap.getPType(10 + i)));
            basedWidth += Buttons.getOptionButtonSize() / 2 + OptionButton.WIDTH_INCREMENT / 2;
        }
    }

    /**
     * @param index index of powerup to get.
     * @return list of power up of that index.
     */
    public List<PowerUpType> getIndexListPowerUp(final int index) {
        return new ArrayList<>(powerUpListOfEntity.get(index));
    }

    /**
     * @return list of power up of that index.
     */
    public Map<Integer, List<PowerUpType>> getListPowerUp() {
        return new HashMap<>(this.powerUpListOfEntity);
    }

    /**
     * @param index index of button to get.
     * @return cordiante of index button.
     */
    public Pair<Integer, Integer> getButtonPosition(final int index) {
        for (final OptionButtonImpl btn : optionButtons.values()) {
            if (btn.getIndex() == index) {
                return new Pair<>(btn.getX(), btn.getY());
            }
        }
        return new Pair<>(0, 0);
    }

    /**
     * @param index index of button to get.
     * @return height of index button.
     */
    public Integer getHeightIndexButton(final int index) {
        for (final OptionButtonImpl btn : optionButtons.values()) {
            if (btn.getIndex() == index) {
                return btn.getH();
            }
        }
        return 0;
    }
}
