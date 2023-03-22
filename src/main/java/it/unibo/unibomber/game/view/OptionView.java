package it.unibo.unibomber.game.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Option;
import it.unibo.unibomber.game.model.impl.OptionButtonImpl;
import it.unibo.unibomber.utilities.Constants;

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
                loadBackground();
        }

        private void loadBackground() {
        }

        @Override
        public void update() {
                for (final OptionButtonImpl mb : controller.getButtons()) {
                        mb.update();
                }
        }

        @Override
        public void draw(final Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                // background
                g2.setColor(new Color(255, 255, 156));
                g2.fillRect(0, 0, Constants.UI.Game.getgWidth(), Constants.UI.Game.getgHeight());
                // rect power up
                g2.setColor(new Color(214, 214, 214));
                g2.fillRoundRect(20, Constants.UI.Game.getgHeight() - 80, Constants.UI.Game.getgWidth() - 200, 50, 20,
                                20);
                for (final OptionButtonImpl mb : controller.getButtons()) {
                        mb.draw(g);
                }
        }
}
