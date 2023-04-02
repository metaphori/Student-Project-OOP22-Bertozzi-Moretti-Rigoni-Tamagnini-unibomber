package it.unibo.unibomber.game.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Word Window class.
 */
public class WorldWindow {
  /**
   * Set default window settings.
   * 
   * @param unibomberPanel
   */
  public WorldWindow(final WorldPanelImpl unibomberPanel) {
    final JFrame jframe = new JFrame();
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jframe.add(unibomberPanel);
    //TODO sistemare licona
    final ImageIcon icon = new ImageIcon("/it/unibo/sprites/menu/icon.png");
    jframe.setTitle("Unibomber");
    jframe.setIconImage(icon.getImage());
    jframe.setLocationRelativeTo(null);
    jframe.setResizable(false);
    jframe.pack();
    jframe.setVisible(true);
    jframe.setFocusable(true);
    jframe.requestFocus();
    jframe.setLocationRelativeTo(null);
  }
}
