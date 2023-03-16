package it.unibo.unibomber.game.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import it.unibo.unibomber.game.controller.api.GameLoop;
import static it.unibo.unibomber.utilities.Constants.UI.Game.G_HEIGHT;
import static it.unibo.unibomber.utilities.Constants.UI.Game.G_WIDTH;

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
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, G_WIDTH, G_HEIGHT);
    }

}
