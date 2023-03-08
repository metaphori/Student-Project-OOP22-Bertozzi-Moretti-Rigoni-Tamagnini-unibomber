package it.unibo.unibomber.game.controller.impl;

import javax.swing.JFrame;

public class WorldWindow {
    private JFrame jframe;

    /**
     * Set default window settings
     * @param unibomberPanel
     */
    public WorldWindow(WorldPanelImpl unibomberPanel){
      jframe = new JFrame();
      jframe.setSize(400, 400);
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.add(unibomberPanel);
      jframe.setLocationRelativeTo(null);
      jframe.setResizable(false);
      jframe.pack();
      jframe.setVisible(true);
      jframe.setLocationRelativeTo(null);
		}
}
