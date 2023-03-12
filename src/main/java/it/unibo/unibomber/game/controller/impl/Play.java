package it.unibo.unibomber.game.controller.impl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.ecs.api.Component;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.model.api.Field;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;
import it.unibo.unibomber.game.model.impl.FieldImpl;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.game.view.PlayView;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.Constants.UI.SpritesMap;

/**
 * This class manage playable game.
 */
public class Play extends StateImpl implements KeyListener, GameLoop {
    // TODO
    // private BufferedImage sprite;
    private Deque<Integer> keyQueue;
    private final Game game;
    private final PlayView view;
    private final Field field;
    private int rows, collums;

    /**
     * This method create the instance of all game parameters.
     * 
     * @param world
     */
    public Play(final WorldImpl world) {
        super();
        new SpritesMap();
        loadMap();
        game = new GameImpl(world, rows, collums);
        view = new PlayView(this);
        field = new FieldImpl(game);
        initClasses();
        // TODO load map at settings not in constructor
    }

    private void initClasses() {
        game.addEntity(new EntityFactoryImpl(game).makePlayable(new Pair<Float, Float>(0f, 1f)));
        // TODO TEST ENTITA MURO PER COLLISIONE DA ELIMINARE
        game.addEntity(new EntityFactoryImpl(game).makeIndestructibleWall(new Pair<Float, Float>(3f, 6f)));

        keyQueue = new LinkedList<>();
    }

    private void loadMap() {
        final List<String> mapData = extractData();
        loadEntities(mapData);
        field.updateField();
    }

    private void loadEntities(final List<String> map) {
        String myTextFile = "./src/main/res/area1.map";
        Path myPath = Paths.get(myTextFile);
        try {
            String[] strArray = Files.lines(myPath)
                    .map(s -> s.split(","))
                    .findFirst()
                    .get();
            rows = Integer.parseInt(strArray[0]);
            collums = Integer.parseInt(strArray[1]);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private List<String> extractData() {
        final List<String> map = new ArrayList<>();
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
        return map;
    }

    @Override
    public final void update() {
        for (int i = 0; i < game.getEntities().size(); i++) {
            for (final Component c : game.getEntities().get(i).getComponents()) {
                c.update();
            }
        }
        field.updateField();
        view.update();
    }

    @Override
    public final void draw(final Graphics g) {
        view.draw(g);
    }

    @Override
    public final void keyReleased(final KeyEvent e) {
        if (keyQueue.contains(e.getKeyCode())) {
            keyQueue.remove(e.getKeyCode());
        }
    }

    @Override
    public final void keyTyped(final KeyEvent e) {
        if (!keyQueue.contains(e.getKeyCode())) {
            keyQueue.addLast(e.getKeyCode());
        }
    }

    @Override
    public final void keyPressed(final KeyEvent e) {
        if (!keyQueue.contains(e.getKeyCode())) {
            keyQueue.addFirst(e.getKeyCode());
        }
    }

    /**
     * @return list of pressed key.
     */
    public final Deque<Integer> getKeys() {
        return keyQueue;
    }

    /**
     * @return all entity in game.
     */
    public final List<Entity> getEntities() {
        return game.getEntities();
    }
}
