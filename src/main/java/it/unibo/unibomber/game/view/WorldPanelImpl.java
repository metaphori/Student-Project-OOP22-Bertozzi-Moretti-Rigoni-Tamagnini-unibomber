package it.unibo.unibomber.game.view;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import it.unibo.unibomber.inputs.MouseInputsImpl;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.game.controller.api.World;
import it.unibo.unibomber.game.controller.impl.Menu;
import it.unibo.unibomber.game.controller.impl.Option;
import it.unibo.unibomber.game.controller.impl.Play;
import it.unibo.unibomber.game.controller.impl.StateGame;
import it.unibo.unibomber.game.controller.impl.WorldImpl;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.model.api.Field;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.inputs.KeyboardInputsImpl;
import static it.unibo.unibomber.utilities.Constants.UI.Screen;
import static it.unibo.unibomber.utilities.Constants.UI.Buttons;

/**
 * WordPanel implement class.
 */
public final class WorldPanelImpl extends JPanel implements World {
  private static final long serialVersionUID = -8854543282432946255L;
  private transient WorldImpl world;

  /**
   * WordPanelImpl constructor.
   * 
   * @param world world
   */
  public WorldPanelImpl(final WorldImpl world) {
    this.world = new WorldImpl(world);
    setSize();
  }

  /**
   * WordPanelImpl copy constructor.
   * 
   * @param worldpanel world
   */
  public WorldPanelImpl(final WorldPanelImpl worldpanel) {
    this.world = worldpanel.getWorld();
  }

  private void setSize() {
    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    final double width = screenSize.getWidth();
    final double height = screenSize.getHeight();
    while (width < Screen.getgWidth() || (height - Constants.UI.Screen.BAR_HEIGHT) < Screen.getgHeight()) {
      Screen.changeDimension();
    }
    Screen.changeDimension();
    while ((Buttons.getTopDistanceQuit() + Buttons.getBHeight()) > Screen.getgHeight()) {
      Buttons.setScaleButton(1);
    }
    setPreferredSize(new Dimension(Screen.getgWidth(), Screen.getgHeight()));
  }

  private WorldImpl getWorld() {
    return world;
  }

  @Override
  public void paintComponent(final Graphics g) {
    super.paintComponent(g);
    world.draw(g);
  }

  @Override
  public Play getPlay() {
    return world.getPlay();
  }

  @Override
  public Menu getMenu() {
    return world.getMenu();
  }

  @Override
  public void setPlay() {
    world.setPlay();
  }

  @Override
  public Option getOption() {
    return world.getOption();
  }

  @Override
  public StateGame getEndGame() {
    return world.getEndGame();
  }

  @Override
  public Game getGame() {
    return world.getGame();
  }

  @Override
  public void stopTimer() {
    world.stopTimer();
  }

  @Override
  public void pauseTimer() {
    world.pauseTimer();
  }

  @Override
  public void startTimer() {
    world.startTimer();
  }

  @Override
  public List<Entity> getEntities() {
    return world.getEntities();
  }

  @Override
  public <C extends Entity> void addEntity(final C entity) {
    world.addEntity(entity);
  }

  @Override
  public Field getGameField() {
    return world.getGameField();
  }

  @Override
  public WorldPanelImpl getUnibomberPanel() {
    return world.getUnibomberPanel();
  }

  @Override
  public Timer getTimer() {
    return world.getTimer();
  }

  @Override
  public int getSecond() {
    return world.getSecond();
  }

  /**
   * Set class of world.
   * @param world
   */
  public void setClass(final WorldImpl world) {
    this.world = new WorldImpl(world);
    addKeyListener(new KeyboardInputsImpl(this));
    addMouseListener(new MouseInputsImpl(this));
  }
}
