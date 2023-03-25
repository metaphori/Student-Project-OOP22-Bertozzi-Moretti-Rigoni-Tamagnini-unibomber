package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
import java.awt.Graphics2D;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.api.World;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.game.view.WorldPanelImpl;
import it.unibo.unibomber.game.view.WorldWindow;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Constants.UI.Screen;

import static it.unibo.unibomber.utilities.Constants.UI.GameLoopConstants.NANO_S;
import static it.unibo.unibomber.utilities.Constants.UI.GameLoopConstants.UPS_SET;

/**
 * WordImpl constructor.
 */
public class WorldImpl implements World, Runnable, GameLoop {

  private final WorldPanelImpl unibomberPanel;
  private Menu menu;
  private Option option;
  private Play play;
  private Pause pause;
  private Game game;

  /**
   * WorldImpl constructor.
   */
  public WorldImpl() {
    loadSprites();
    unibomberPanel = new WorldPanelImpl(this);
    initClasses();
    new WorldWindow(unibomberPanel);
    unibomberPanel.requestFocus();
    startGameLoop();
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
    pause = new Pause();
  }
  
  private void loadSprites() {
    new Constants.UI.SpritesMap();
    new Constants.Destroy();
    new Constants.UI.Scale();
    new Constants.UI.MapOption();
  }

  private void startGameLoop() {
    final Thread gThread = new Thread(this);
    gThread.start();
  }

  @Override
  public final void update() {
    switch (Gamestate.getGamestate()) {
      case MENU:
        menu.update();
        break;
      case OPTION:
        option.update();
        break;
      case PLAY:
        play.update();
        break;
      case PAUSE:
        pause.update();
        break;
      case QUIT:
      default:
        System.exit(0);
        break;
    }
  }

  @Override
  public final void draw(final Graphics g) {
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
        play.draw(g);
        pause.draw(g);
        break;
      default:
        break;
    }
  }

  @Override
  public final void run() {
    double timePerUpdate = NANO_S / UPS_SET;

    long previousTime = System.nanoTime();

    long lastCheck = System.currentTimeMillis();

    double deltaU = 0;
    while (true) {
      long currentTime = System.nanoTime();

      deltaU += (currentTime - previousTime) / timePerUpdate;
      previousTime = currentTime;

      while (deltaU >= 1) {
        update();
        deltaU--;
      }
      unibomberPanel.repaint();
      if (System.currentTimeMillis() - lastCheck >= 1000) {
        lastCheck = System.currentTimeMillis();
      }
    }
  }

  @Override
  public final Menu getMenu() {
    return menu;
  }

  @Override
  public final Play getPlay() {
    return play;
  }

  @Override
  public final Pause getPause() {
    return pause;
  }

  @Override
  public final Option getOption() {
    return option;
  }

  @Override
  public final Game getGame() {
    return game;
  }

  @Override
  public void setPlay() {
    play = new Play(this);
  }
}
