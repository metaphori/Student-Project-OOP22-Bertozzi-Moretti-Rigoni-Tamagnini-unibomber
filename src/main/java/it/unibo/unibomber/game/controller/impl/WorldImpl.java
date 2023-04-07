package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.api.World;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.model.api.Field;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.game.view.WorldPanelImpl;
import it.unibo.unibomber.game.view.WorldWindow;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Constants.UI.GameLoopConstants;
import it.unibo.unibomber.utilities.Constants.UI.Screen;

/**
 * WordImpl constructor.
 */
public final class WorldImpl implements World, Runnable, GameLoop {

  private final WorldPanelImpl unibomberPanel;
  private Menu menu;
  private Option option;
  private Play play;
  private StateGame endGame;
  private Game game;
  private Timer timer;
  private int second;

  /**
   * WorldImpl constructor.
   */
  public WorldImpl() {
    loadSprites();
    unibomberPanel = new WorldPanelImpl(this);
    initClasses();
    unibomberPanel.setClass(this);
    option.setClass(this);
    new WorldWindow(unibomberPanel);
    unibomberPanel.requestFocus();
    startGameLoop();
  }

  /**
   * @param world the world to copy
   */
  public WorldImpl(final World world) {
    this.menu = world.getMenu();
    this.option = world.getOption();
    this.endGame = world.getEndGame();
    this.unibomberPanel = world.getUnibomberPanel();
    this.play = world.getPlay();
  }

  /**
   * Create game.
   */
  public void createGame() {
    game = new GameImpl(this, Screen.getTilesWidth(), Screen.getTilesHeight());
    Screen.setDimensionOnMap();
    game.getGameField().updateField();
  }

  private void initClasses() {
    menu = new Menu();
    option = new Option(this);
    endGame = new StateGame(this);
  }

  private void loadSprites() {
    Constants.UI.SpritesMap.setSpritesMap();
    Constants.Destroy.setDestroyFramesPerType();
    Constants.UI.Scale.setEntityScale();
  }

  private void startGameLoop() {
    final Thread gThread = new Thread(this);
    gThread.start();
  }

  @Override
  public void update() {
    if (second > GameLoopConstants.TIMES_UP_TIMER) {
      game.updateTimesUp();
    }
    switch (Gamestate.getGamestate()) {
      case MENU:
        menu.update();
        initClasses();
        break;
      case OPTION:
        option.update();
        break;
      case PLAY:
        play.update();
        break;
      case PAUSE:
      case WIN:
      case LOSE:
        endGame.update();
        break;
      case QUIT:
      default:
        Runtime.getRuntime().exit(0);
        break;
    }
  }

  @Override
  public void draw(final Graphics g) {
    switch (Gamestate.getGamestate()) {
      case MENU:
        menu.draw(g);
        break;
      case OPTION:
        option.draw(g);
        break;
      case PLAY:
        play.draw(g);
        break;
      case PAUSE:
      case WIN:
      case LOSE:
        play.draw(g);
        endGame.draw(g);
        break;
      default:
        break;
    }
  }

  @Override
  public void run() {
    final double timePerUpdate = GameLoopConstants.NANO_S / GameLoopConstants.UPS_SET;

    long previousTime = System.nanoTime();

    long lastCheck = System.currentTimeMillis();

    double deltaU = 0;
    int delta;
    while (true) {
      final long currentTime = System.nanoTime();

      deltaU += (currentTime - previousTime) / timePerUpdate;
      delta = (int) deltaU;
      previousTime = currentTime;

      while (delta >= 1) {
        delta--;
        update();
        deltaU -= 1;
      }
      unibomberPanel.repaint();
      if (System.currentTimeMillis() - lastCheck >= 1000) {
        lastCheck = System.currentTimeMillis();
      }
    }
  }

  @Override
  public Menu getMenu() {
    return menu;
  }

  @Override
  public Play getPlay() {
    return play;
  }

  @Override
  public Option getOption() {
      return new Option(option);
  }

  @Override
  public Game getGame() {
    return new GameImpl(game);
  }

  @Override
  public void setPlay() {
    this.play = new Play(this);
    play.setClass(this);
    unibomberPanel.setClass(this);
    simpleTimer();
    timer.start();
  }

  @Override
  public StateGame getEndGame() {
    return endGame;
  }

  private void simpleTimer() {
    timer = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        second++;
      }
    });
  }

  @Override
  public void stopTimer() {
    timer.stop();
    second = 0;
  }

  @Override
  public void pauseTimer() {
    timer.stop();
  }

  @Override
  public void startTimer() {
    timer.start();
  }

  @Override
  public List<Entity> getEntities() {
    return this.game.getEntities();
  }

  @Override
  public <C extends Entity> void addEntity(final C entity) {
    this.game.addEntity(entity);
  }

  @Override
  public Field getGameField() {
    return this.game.getGameField();
  }

  @Override
  public WorldPanelImpl getUnibomberPanel() {
    return new WorldPanelImpl(unibomberPanel);
  }

  @Override
  public int getSecond() {
    return this.second;
  }

  @Override
  public Timer getTimer() {
    final Timer newTimer = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        second++;
      }
    });
    newTimer.setDelay(this.timer.getDelay());
    newTimer.setInitialDelay(this.timer.getInitialDelay());
    return newTimer;

  }
}
