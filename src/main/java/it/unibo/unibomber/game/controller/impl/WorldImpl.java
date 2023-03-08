package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.api.World;
import it.unibo.unibomber.game.controller.api.WorldPanel;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.utilities.Constants;

public class WorldImpl implements World,Runnable, GameLoop{
    
	private WorldPanel unibomberPanel;
	private Thread g_Thread;

    public WorldImpl(){
		initClasses();
       	unibomberPanel = new WorldPanelimpl(this);
		new WorldWindow(unibomberPanel);
		unibomberPanel.requestFocus();
		startGameLoop();
    }

	private void initClasses() {
	}

	private void startGameLoop() {
		g_Thread = new Thread(this);
		g_Thread.start();
	}
	
	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics g){

	}

	@Override
	public void run() {double timePerFrame = 1000000000.0 / Constants.UI.GameLoop.FPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			if (deltaF >= 1) {
				update();
				unibomberPanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}

}
