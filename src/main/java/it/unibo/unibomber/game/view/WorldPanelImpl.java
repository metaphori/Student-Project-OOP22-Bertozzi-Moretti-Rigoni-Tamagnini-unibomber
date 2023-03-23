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
import static it.unibo.unibomber.utilities.Constants.UI.Buttons;

/**
 * WordPanel implement class.
 */
public final class WorldPanelImpl extends JPanel {
  private static final long serialVersionUID = -8854543282432946255L;
  private final transient WorldImpl world;
  private final transient BufferedImage tile;

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
    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    final double width = screenSize.getWidth();
    final double height = screenSize.getHeight();
    while (width < Game.getgWidth() || (height - Constants.UI.Game.BAR_HEIGHT) < Game.getgHeight()) {
      Game.changeDimension();
    }
    Game.changeDimension();
    while ((Buttons.getTopDistanceQuit() + Buttons.getBHeight()) > (Game.getgHeight())) {
      Buttons.setScaleButton(1);
    }
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
