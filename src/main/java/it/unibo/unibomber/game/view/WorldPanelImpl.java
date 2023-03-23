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
import static it.unibo.unibomber.utilities.Constants.UI.Screen;
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
    setSize();
    this.tile = Constants.UI.SpritesMap.SPRITESPATH.get(Type.EMPTY_AREA);
    addKeyListener(new KeyboardInputsImpl(this));
    addMouseListener(new MouseInputsImpl(this));
  }

  private void setSize() {
    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    final double width = screenSize.getWidth();
    final double height = screenSize.getHeight();
    while (width < Screen.getgWidth() || (height - Constants.UI.Screen.BAR_HEIGHT) < Screen.getgHeight()) {
      Screen.changeDimension();
    }
    Screen.changeDimension();
    while ((Buttons.getTopDistanceQuit() + Buttons.getBHeight()) > (Screen.getgHeight())) {
      Buttons.setScaleButton(1);
    }
    setPreferredSize(new Dimension(Screen.getgWidth(), Screen.getgHeight()));
  }

  @Override
  public void paintComponent(final Graphics g) {
    super.paintComponent(g);
    final Graphics2D g2d = (Graphics2D) g.create();
    for (int y = 0; y < Screen.getgHeight(); y += Screen.getTilesSize()) {
      for (int x = 0; x < Screen.getgWidth(); x += Screen.getTilesSize()) {
        g2d.drawImage(tile, x, y,
            (int) (Screen.getTilesSize()),
            (int) (Screen.getTilesSize()),
            this);
      }
    }
    g2d.dispose();
    setPreferredSize(new Dimension(Screen.getgWidth(), Screen.getgHeight()));
    world.draw(g);
  }

  /**
   * @return world
   */
  public WorldImpl getWorld() {
    return world;
  }
}
