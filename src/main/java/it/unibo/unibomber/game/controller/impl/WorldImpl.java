package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.api.World;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.view.WorldPanelImpl;
import it.unibo.unibomber.game.view.WorldWindow;
import it.unibo.unibomber.utilities.Constants;

import static it.unibo.unibomber.utilities.Constants.UI.GameLoop.NANO_S;
import static it.unibo.unibomber.utilities.Constants.UI.GameLoop.FPS_SET;
import static it.unibo.unibomber.utilities.Constants.UI.GameLoop.UPS_SET;

/**
 * WordImpl constructor.
 */
public class WorldImpl implements World, Runnable, GameLoop {

  private final WorldPanelImpl unibomberPanel;
  private Menu menu;
  private Play play;
  private Thread gThread;

  /**
   * WorldImpl constructor.
   */
  public WorldImpl() {
    initClasses();
    unibomberPanel = new WorldPanelImpl(this);
    new WorldWindow(unibomberPanel);
    unibomberPanel.requestFocus();
    startGameLoop();
  }

  private void initClasses() {
    loadSprites();
    menu = new Menu();
    play = new Play(this);
  }
  private void loadSprites() {
    new Constants.UI.SpritesMap();
    new Constants.Destroy();
  }

  private void startGameLoop() {
    gThread = new Thread(this);
    gThread.start();
  }

  @Override
  public final void update() {
    switch (Gamestate.getGamestate()) {
      case MENU:
        menu.update();
        break;
      case PLAY:
        play.update();
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
      case PLAY:
        play.draw(g);
        break;
      default:
        break;
    }
  }

  @SuppressWarnings("PMD")
  @Override
  public final void run() {
    double timePerFrame = NANO_S / FPS_SET;
    double timePerUpdate = NANO_S / UPS_SET;

    long previousTime = System.nanoTime();

    int frames = 0;
    int updates = 0;
    long lastCheck = System.currentTimeMillis();

    double deltaU = 0;
    double deltaF = 0;
    while (true) {
      long currentTime = System.nanoTime();

      deltaU += (currentTime - previousTime) / timePerUpdate;
      deltaF += (currentTime - previousTime) / timePerFrame;
      previousTime = currentTime;

      while (deltaU >= 1) {
        update();
        updates++;
        deltaU--;
      }
      unibomberPanel.repaint();
      frames++;
      if (System.currentTimeMillis() - lastCheck >= 1000) {
        lastCheck = System.currentTimeMillis();
        System.out.println("FPS: " + frames + " | UPS: " + updates);
        frames = 0;
        updates = 0;
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
}
