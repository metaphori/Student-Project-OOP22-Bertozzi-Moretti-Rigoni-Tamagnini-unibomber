package it.unibo.unibomber.game.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.utilities.Constants;

/**
 * Draw game pause view statement.
 */
public class PauseView implements GameLoop {

    @Override
    public void update() {
    }

    @Override
    public final void draw(final Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0, 0, Constants.UI.Screen.getOpacity()));
        g2.fillRect(0, 0, Constants.UI.Screen.getgWidth(), Constants.UI.Screen.getgHeight());

        final Graphics2D g3 = (Graphics2D) g;
        g3.setColor(new Color(255, 0, 0));
        g2.fillRect((Constants.UI.Screen.getgWidth() / 2)- 50, (Constants.UI.Screen.getgHeight() / 4), 100, 50);
    }

}
