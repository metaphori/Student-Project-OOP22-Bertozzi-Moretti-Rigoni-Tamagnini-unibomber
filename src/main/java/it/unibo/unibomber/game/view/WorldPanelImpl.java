package it.unibo.unibomber.game.view;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.inputs.MouseInputsImpl;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.UploadRes;
import it.unibo.unibomber.game.controller.impl.WorldImpl;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.inputs.KeyboardInputsImpl;
import static it.unibo.unibomber.utilities.Constants.UI.Game.G_WIDTH;
import static it.unibo.unibomber.utilities.Constants.UI.Game.G_HEIGHT;
import static it.unibo.unibomber.utilities.Constants.UI.Game.TILES_SIZE;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap.SPRITESPATH;

/**
 * WordPanel implement class.
 */
public final class WorldPanelImpl extends JPanel {
  private WorldImpl world;

  /**
   * WordPanelImpl constructor.
   * 
   * @param world
   */
  public WorldPanelImpl(final WorldImpl world) {
    this.world = world;
    setSize();
    addKeyListener(new KeyboardInputsImpl(this));
    addMouseListener(new MouseInputsImpl(this));
  }

  private void setSize() {
    setPreferredSize(new Dimension(G_WIDTH, G_HEIGHT));
  }

  private BufferedImage tile;

  @Override
  public void paintComponent(final Graphics g) {
    super.paintComponent(g);
    tile = UploadRes.getSpriteAtlas(SPRITESPATH.get(Type.EMPTY_AREA));
    Graphics2D g2d = (Graphics2D) g.create();
    for (int y = 0; y < G_HEIGHT; y += TILES_SIZE) {
      for (int x = 0; x < G_WIDTH; x += TILES_SIZE) {
        g2d.drawImage(tile, x, y,
            (int) (Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE),
            (int) (Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE),
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
