package it.unibo.unibomber.game.view;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;

import it.unibo.unibomber.inputs.MouseInputsImpl;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.game.controller.impl.WorldImpl;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.inputs.KeyboardInputsImpl;
import static it.unibo.unibomber.utilities.Constants.UI.Game;

/**
 * WordPanel implement class.
 */
public final class WorldPanelImpl extends JPanel {
  private final WorldImpl world;
  private final BufferedImage tile;
  //TODO Add in constants
  private static final int BAR_HEIFHT = 40;

  /**
   * WordPanelImpl constructor.
   * 
   * @param world
   */
  public WorldPanelImpl(final WorldImpl world) {
    this.world = world;
    this.tile = Constants.UI.SpritesMap.SPRITESPATH.get(Type.EMPTY_AREA);
    setSize();
    addKeyListener(new KeyboardInputsImpl(this));
    addMouseListener(new MouseInputsImpl(this));
  }

  private void setSize() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    while (width < Game.getgWidth() || (height - BAR_HEIFHT) < Game.getgHeight()) {
      Game.changeDimension();
    }
    Game.changeDimension();
    setPreferredSize(new Dimension(Game.getgWidth(), Game.getgHeight()));
  }

  @Override
  public void paintComponent(final Graphics g) {
    super.paintComponent(g);
    final Graphics2D g2d = (Graphics2D) g.create();
    for (int y = 0; y < Game.getgHeight(); y += Game.getTilesSize()) {
      for (int x = 0; x < Game.getgWidth(); x += Game.getTilesSize()) {
        g2d.drawImage(tile, x, y,
            (int) (Game.getTilesSize()),
            (int) (Game.getTilesSize()),
            this);
      }
    }
    g2d.dispose();
    world.draw(g);
  }

  /**
   * @return world
   */
  public WorldImpl getWorld() {
    return world;
  }
}
