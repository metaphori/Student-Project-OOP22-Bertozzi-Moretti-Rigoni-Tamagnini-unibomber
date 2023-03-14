package it.unibo.unibomber.game.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.model.api.Field;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

/**
 * Field is an object that manages the game field frame by frame.
 */
public class FieldImpl implements Field {

    /**
     * Field will contain all entity info.
     */
    private final Map<Pair<Integer, Integer>, Pair<Type, Entity>> field = new HashMap<>();
    private final Game game;

    /**
     * Initialize the field.
     * @param game the game started
     */
    public FieldImpl(final Game game) {
        this.game = game;
    }

    @Override
    public final Map<Pair<Integer, Integer>, Pair<Type, Entity>> getField() {
        return new HashMap<>(this.field);
    }

    @Override
    public final void updateField() {
        int row;
        int col;
        final var fieldentities = this.game.getEntities().stream()
                .filter(e -> e.getType() != Type.BOT 
                        && e.getType() != Type.PLAYABLE)
                .collect(Collectors.toList());
        this.field.clear();
        for (final var entity : fieldentities) {
            if (entity.getType() == Type.BOMB 
                && (entity.getComponent(MovementComponent.class).get().getDirection() == Direction.LEFT 
                    || entity.getComponent(MovementComponent.class).get().getDirection() == Direction.UP)) {
                row = (int) Math.floor(entity.getPosition().getX());
                col = (int) Math.floor(entity.getPosition().getY());
            } else {
                row = (int) Math.ceil(entity.getPosition().getX());
                col = (int) Math.ceil(entity.getPosition().getY());
            }
            this.field.put(new Pair<Integer, Integer>(row, col),
                    new Pair<Type, Entity>(entity.getType(), entity));
        }
    }

    @Override
    public Type[][] getEntitiesTypes() {
        final Pair<Integer, Integer> gameDimensions = this.game.getDimensions();
        final Type[][] typesMatrix = getInitializedMatrix(gameDimensions.getX(), gameDimensions.getY());
        for (var pos : this.field.keySet()) {
            typesMatrix[pos.getX()][pos.getY()] = this.field.get(pos).getX();
        }
        return typesMatrix;
    }

    private Type[][] getInitializedMatrix(final int rows, final int cols) {
        final Type[][] typesMatrix = new Type[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                typesMatrix[i][j] = null;
            }
        }
        return typesMatrix;
    }
}
