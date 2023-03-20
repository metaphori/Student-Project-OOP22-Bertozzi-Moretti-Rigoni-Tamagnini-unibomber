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
        // TODO Auto-generated method stub
    }

    @Override
    public final void draw(final Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0, 0, Constants.UI.Game.getOpacity()));
        g2.fillRect(0, 0, Constants.UI.Game.getgWidth(), Constants.UI.Game.getgHeight());
    }

}
