package it.unibo.unibomber.game.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Option;
import it.unibo.unibomber.game.model.impl.OptionButtonImpl;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Constants.UI.Buttons;
import it.unibo.unibomber.utilities.Constants.UI.OptionButton;

import static it.unibo.unibomber.utilities.Constants.UI.OptionButton.OPTION_BACKGROUND;

/**
 * Option view class.
 */
public final class OptionView implements GameLoop {
        private final Option controller;
        /**
         * @param controller
         */
        public OptionView(final Option controller) {
                this.controller = controller;
        }

        @Override
        public void update() {
                for (final OptionButtonImpl mb : controller.getOptionButtons()) {
                        mb.update();
                }
        }

        @Override
        public void draw(final Graphics g) {
                final Graphics2D g2 = (Graphics2D) g;
                g2.setColor(OPTION_BACKGROUND);
                g2.fillRect(0, 0, Constants.UI.Screen.getgWidth(), Constants.UI.Screen.getgHeight());
                Graphics2D g3 = (Graphics2D) g;
                g3.setColor(new Color(214, 214, 214));
                g3.fillRoundRect(OptionButton.WIDTH_INCREMENT, OptionButton.getPowerUpSetTopDistance(), 300, Buttons.getOptionButtonSize() - OptionButton.WIDTH_INCREMENT, 5, 5);
                for (final OptionButtonImpl mb : controller.getOptionButtons()) {
                        if (!"empty".equals(mb.getType())) {
                                mb.draw(g);
                        }
                }

        }
}
