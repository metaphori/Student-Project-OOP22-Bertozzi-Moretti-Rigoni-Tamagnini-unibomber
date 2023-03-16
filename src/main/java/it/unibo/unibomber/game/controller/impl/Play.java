package it.unibo.unibomber.game.controller.impl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.ecs.api.Component;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.game.view.PlayView;
import it.unibo.unibomber.utilities.Pair;

/**
 * This class manage playable game.
 */
public class Play extends StateImpl implements KeyListener, GameLoop {
    private Deque<Integer> keyQueue;
    private Map<Integer, Boolean> firstFrameKey;
    private final Game game;
    private Explosion explosion;
    private final PlayView view;
    private int rows, collums;

    /**
     * This method create the instance of all game parameters.
     * 
     * @param world
     */
    public Play(final WorldImpl world) {
        super();
        loadDimension();
        game = new GameImpl(world, rows, collums);
        extractData();
        view = new PlayView(this);
        game.getGameField().updateField();
        keyQueue = new LinkedList<>();
        firstFrameKey = new HashMap<>();
        explosion = new Explosion(game);
        // TODO load map at settings not in constructor
    }

    private void loadDimension() {
        String myTextFile = "./src/main/res/area1.map";
        Path myPath = Paths.get(myTextFile);
        try {
            String[] strArray = Files.lines(myPath)
                    .map(s -> s.split(" "))
                    .findFirst()
                    .get();
            rows = Integer.parseInt(strArray[0]);
            collums = Integer.parseInt(strArray[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void extractData() {
        try {
            FileInputStream fstream = new FileInputStream("./src/main/res/area1.map");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            Integer row = 0;
            br.readLine();
            while ((strLine = br.readLine()) != null) {
                String[] tokens = strLine.split(" ");
                for (int i = 0; i < tokens.length; i++) {
                    switch (tokens[i]) {
                        case "0":
                            game.addEntity(new EntityFactoryImpl(game)
                                    .makePlayable(new Pair<Float, Float>((float) i, (float) row)));
                            break;
                        case "1":
                            game.addEntity(new EntityFactoryImpl(game)
                                    .makeBot(new Pair<Float, Float>((float) i, (float) row), 1));
                            break;
                        case "2":
                            game.addEntity(new EntityFactoryImpl(game)
                                    .makePowerUp(new Pair<Float, Float>((float) i, (float) row),
                                            PowerUpType.getRandomPowerUp()));
                            break;
                        case "5":
                            game.addEntity(new EntityFactoryImpl(game)
                                    .makeDestructibleWall(new Pair<Float, Float>((float) i, (float) row)));
                            break;
                        case "6":
                            game.addEntity(new EntityFactoryImpl(game)
                                    .makeIndestructibleWall(new Pair<Float, Float>((float) i, (float) row)));
                            break;
                        default:
                            break;
                    }
                }
                row++;
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public final void update() {
        for (int i = 0; i < game.getEntities().size(); i++) {
            for (final Component c : game.getEntities().get(i).getComponents()) {
                c.update();
            }
        }
        game.getGameField().updateField();
        view.update();
        explosion.update();
        updateKeys();
    }

    /**
     * If those keys are still here they were not presed on the first frame.
     */
    private void updateKeys() {
        for (Integer keyCode : firstFrameKey.keySet()) {
            firstFrameKey.put(keyCode, false);
        }

    }

    @Override
    public final void draw(final Graphics g) {
        view.draw(g);
        explosion.draw(g);
    }

    @Override
    public final void keyReleased(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Gamestate.setGameState(Gamestate.PAUSE);
        } else {
            if (keyQueue.contains(e.getKeyCode())) {
                keyQueue.remove(e.getKeyCode());
            }
            if (firstFrameKey.containsKey(e.getKeyCode())) {
                firstFrameKey.remove(e.getKeyCode());
            }
        }
    }

    @Override
    public final void keyTyped(final KeyEvent e) {
    }

    @Override
    public final void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_ESCAPE) {
            if (!keyQueue.contains(e.getKeyCode())) {
                keyQueue.addFirst(e.getKeyCode());
            }
            if (!firstFrameKey.containsKey(e.getKeyCode())) {
                firstFrameKey.put(e.getKeyCode(), true);
            }
        }
    }

    /**
     * @return list of pressed key.
     */
    public final Deque<Integer> getKeys() {
        return keyQueue;
    }

    /**
     * @return first key with status.
     */
    public final Map<Integer, Boolean> getFirstFrameKeys() {
        return firstFrameKey;
    }

    /**
     * @return all entity in game.
     */
    public final List<Entity> getEntities() {
        return game.getEntities();
    }
}
