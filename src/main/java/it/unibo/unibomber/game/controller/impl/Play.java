package it.unibo.unibomber.game.controller.impl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.ecs.api.Component;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.model.api.Field;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;
import it.unibo.unibomber.game.model.impl.FieldImpl;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.game.view.PlayView;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.Constants.UI.SpritesMap;


public class Play extends StateImpl implements KeyListener,GameLoop{
    BufferedImage sprite;
    private Deque<Integer> key_queue;
	private Game game;
	private List<String> map = new ArrayList<String>();
	private PlayView view;
	Field field;

    public Play(WorldImpl world) {
		super(world);
		new SpritesMap();
		game = new GameImpl(world);
		view= new PlayView(this);
		field= new FieldImpl(game);
		initClasses();
		//TODO load map at settings not in constructor
		loadMap();
	}
	private void initClasses() {		
		game.addEntity(new EntityFactoryImpl(game).makePlayable(new Pair<Float,Float>(0f, 0f)));
        key_queue = new LinkedList<>();
	}

	private void loadMap() {
        BufferedReader bf;
		try {
			bf = new BufferedReader(new FileReader("./src/main/res/area1.map"));
			String line;
			try {
				line = bf.readLine();
				while (line != null) {
					map.add(line);
					line = bf.readLine();
				}
				bf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for (int index = 0; index < 19; index++) {
			List<String> singleLine = Arrays.asList(map.get(index).split(" "));
			for (int j = 0; j < singleLine.size(); j++) {
				switch(Integer.parseInt(singleLine.get(j))){
					case 6:
						game.addEntity(new EntityFactoryImpl(game).makeIndestructibleWall(new Pair<Float,Float>((float)j, (float)index)));
					break;
                    case 2:
                        game.addEntity(new EntityFactoryImpl(game).makePowerUp(new Pair<Float,Float>((float)j, (float)index),PowerUpType.FIREUP));
                    break;
				}
			}
        }
		field.updateField();
	}

	@Override
	public void update() {
		for(int i = 0; i<game.getEntities().size();i++){
			for(Component c : game.getEntities().get(i).getComponents()){
				c.update();
			}
		}
		field.updateField();
		view.update();
		key_queue.clear();
	}

	@Override
    public void draw(Graphics g) {
		view.draw(g);		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		key_queue.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

    @Override
    public void keyTyped(KeyEvent arg0) {     

    }

    public Deque<Integer> getKeys(){
	return key_queue;
    }

	public List<Entity> getEntities() {
		return game.getEntities();
	}
	public List<String> getMap() {
		return map;
	}
}
