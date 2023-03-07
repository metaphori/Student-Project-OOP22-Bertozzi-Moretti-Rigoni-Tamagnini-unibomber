package it.unibo.unibomber.game.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.unibomber.game.controller.api.World;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.model.api.Field;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.utilities.Pair;

public class GameImpl implements Game {

    private List<Entity> entities = new ArrayList<>();
    private List<Integer> keysPressedQueue = new ArrayList<>();  
    private Field gameField = new FieldImpl();
    private int columns;
    private int rows;
    private World world;
    private final EntityFactoryImpl entityFactory=new EntityFactoryImpl(this);


    public GameImpl(World world){
        this.world=world;
    }
    @Override
    public List<Entity> getEntities() {
        return this.entities;
    }

    @Override
    public <C extends Entity> void addEntity(C entity) {
        entities.add(entity);
    }

    @Override
    public boolean isContained(int keyCode) {
        return keysPressedQueue.contains(keyCode);
    }

    @Override
    public void addkeyPressed(int keyCode) {
        keysPressedQueue.add(keyCode);
    }

    @Override
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void clearKeysPressed() {
        //keysPressedQueue.clear();
    }  
    @Override
    public Pair<Integer,Integer> getDimensions(){
        return new Pair<Integer,Integer>(rows, columns);
    }

    @Override
    public Field getGameField() {
        return gameField;
    }
    
    @Override
    public World getWorld() {
        return this.world   ;
    }
    
    @Override
    public EntityFactoryImpl getFactory(){
        return entityFactory;
    }

}
