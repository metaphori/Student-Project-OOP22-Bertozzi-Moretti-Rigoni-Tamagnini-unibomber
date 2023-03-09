package it.unibo.unibomber.game.view;

import javax.swing.JFrame;

/**
 * Word Window class.
 */
public class WorldWindow {
  private JFrame jframe;

  /**
   * Set default window settings.
   * 
   * @param unibomberPanel
   */
  public WorldWindow(final WorldPanelImpl unibomberPanel) {
    jframe = new JFrame();
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jframe.add(unibomberPanel);
    jframe.setLocationRelativeTo(null);
    jframe.setResizable(false);
    jframe.pack();
    jframe.setVisible(true);
    jframe.setLocationRelativeTo(null);
  }
}
