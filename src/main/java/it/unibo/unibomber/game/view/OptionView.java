package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Option;
import it.unibo.unibomber.game.model.impl.OptionButtonImpl;
import it.unibo.unibomber.utilities.Constants;
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
                // background
                g2.setColor(OPTION_BACKGROUND);
                g2.fillRect(0, 0, Constants.UI.Screen.getgWidth(), Constants.UI.Screen.getgHeight());
                for (final OptionButtonImpl mb : controller.getOptionButtons()) {
                        mb.draw(g);
                }
        }
}
