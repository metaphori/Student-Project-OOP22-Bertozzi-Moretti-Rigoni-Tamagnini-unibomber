package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Option;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.utilities.Constants.UI.Buttons;
import it.unibo.unibomber.utilities.Constants.UI.OptionButton;
import it.unibo.unibomber.utilities.Constants.UI.SpritesMap;

/**
 * Handicap power up view.
 */
public final class HandicapView implements GameLoop {
    private final List<Option> controller;

    /**
     * @param controller Handicap controller.
     */
    public HandicapView(final Option controller) {
        this.controller = new ArrayList<>();
                this.controller.add(controller);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(final Graphics g) {

        for (final Integer index : this.controller.get(0).getListPowerUp().keySet()) {
            if (!this.controller.get(0).getIndexListPowerUp(index).isEmpty()) {
                int basedWidth = this.controller.get(0).getButtonPosition(index).getX()
                        + OptionButton.getBombNumberDimension();
                for (final PowerUpType i : this.controller.get(0).getIndexListPowerUp(index)) {
                    g.drawImage(SpritesMap.getSpritesPowerupData().get(i), basedWidth,
                            this.controller.get(0).getButtonPosition(index).getY()
                                    + (this.controller.get(0).getHeightIndexButton(index) / 2)
                                    - this.controller.get(0).getHeightIndexButton(index) / OptionButton.HANDICAP_DIVIDER,
                            Buttons.getOptionButtonSize() / 2, Buttons.getOptionButtonSize() / 2, null);
                    basedWidth += Buttons.getOptionButtonSize() / 2;
                }
            }
        }
    }
}
