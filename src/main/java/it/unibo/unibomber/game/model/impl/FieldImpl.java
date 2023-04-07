package it.unibo.unibomber.game.model.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.ThrowComponent;
import it.unibo.unibomber.game.model.api.Field;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.utilities.Pair;

/**
 * Field is an object that manages the game field frame by frame.
 */
public class FieldImpl implements Field {

    /**
     * Field will contain all entity info.
     */
    private final Map<Pair<Integer, Integer>, Pair<Type, Entity>> field = new HashMap<>();
    private final List<Game> game;

    /**
     * Initialize the field.
     * 
     * @param game the game started
     */
    public FieldImpl(final Game game) {
        this.game = new ArrayList<>();
        this.game.add(game);
    }

    @Override
    public final Map<Pair<Integer, Integer>, Pair<Type, Entity>> getField() {
        return new HashMap<>(this.field);
    }

    @Override
    public final void updateField() {
        int row;
        int col;
        final var fieldentities = this.game.get(0).getEntities().stream()
                .filter(e -> !e.getType().equals(Type.BOMBER))
                .collect(Collectors.toList());
        this.field.clear();
        for (final var entity : fieldentities) {
            if (!entity.getType().equals(Type.BOMB)
                    || !entity.getComponent(ThrowComponent.class).get().isThrowing()) {
                row = Math.round(entity.getPosition().getX());
                col = Math.round(entity.getPosition().getY());
                this.field.put(new Pair<Integer, Integer>(row, col),
                        new Pair<Type, Entity>(entity.getType(), entity));
            }
        }
    }
}
