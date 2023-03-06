package it.unibo.unibomber.game.model.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.model.api.Field;
import it.unibo.unibomber.utilities.Pair;

public class FieldImpl implements Field{

    private final Map<Pair<Integer, Integer>, Pair<Type, Entity>> field = new HashMap<>();

    @Override
    public Map<Pair<Integer, Integer>, Pair<Type, Entity>> getField() {
        return new HashMap<>(this.field);
    }

    @Override
    public void updateField() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateField'");
    }
    
}
