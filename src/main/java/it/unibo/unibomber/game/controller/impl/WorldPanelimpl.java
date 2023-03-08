package it.unibo.unibomber.game.controller.impl;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import it.unibo.unibomber.inputs.MouseInputsImpl;
import it.unibo.unibomber.inputs.KeyboardInputsImpl;
import static it.unibo.unibomber.utilities.Constants.UI.Game.G_WIDTH;
import static it.unibo.unibomber.utilities.Constants.UI.Game.G_HEIGHT;

public class WorldPanelImpl extends JPanel  {

  private WorldImpl world;

    public WorldPanelImpl(WorldImpl world){
      this.world=world;
      setSize();
      addKeyListener(new KeyboardInputsImpl(this));
      addMouseListener(new MouseInputsImpl(this));
    }

    private void setSize(){
      setPreferredSize(new Dimension(G_WIDTH,G_HEIGHT));
    }

    /**
     * update world panel
     */
    public void updateWorld() {

    }

    public void paintComponent(Graphics g){
      super.paintComponent(g);   
      world.draw(g);
    }

    /**
     * @return world
     */
    public WorldImpl getWorld () {
      return world;
    }
}
