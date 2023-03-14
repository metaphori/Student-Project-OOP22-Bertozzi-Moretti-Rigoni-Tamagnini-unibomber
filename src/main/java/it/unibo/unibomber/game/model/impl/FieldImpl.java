package it.unibo.unibomber.game.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpListComponent;
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
     * 
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
                row = (int) Math.round(entity.getPosition().getX());
                col = (int) Math.round(entity.getPosition().getY());
            } else {
                row = Math.round(entity.getPosition().getX());
                col = Math.round(entity.getPosition().getY());
            }
            this.field.put(new Pair<Integer, Integer>(row, col),
                    new Pair<Type, Entity>(entity.getType(), entity));
        }
    }

    @Override
    public Type[][] getMatrixTypes() {
        final Pair<Integer, Integer> gameDimensions = this.game.getDimensions();
        final Type[][] typesMatrix = new Type[gameDimensions.getX()][gameDimensions.getY()];
        addEntitiesToMatrix(typesMatrix);
        addBombExplosionToMatrix(typesMatrix);

        return typesMatrix;
    }

    private void addBombExplosionToMatrix(Type[][] typesMatrix) {
        // TODO only works with basic bombs
        field.keySet().stream()
                .filter(e -> field.get(e).getX().equals(Type.BOMB))
                .forEach(e -> {
                    PowerUpListComponent powerupList = field.get(e).getY().getComponent(PowerUpListComponent.class)
                            .get();

                    for (int i = 0; i < powerupList.getBombFire(); i++) {
                        for( Direction d : Direction.values()){
                            addExplosionToMatrix(typesMatrix, d, new Pair<Integer,Integer>(e.getX()+d.getX(), e.getY()+d.getY()));
                        }
                    }
                });

    }

    private void addExplosionToMatrix(Type[][] typesMatrix,Direction dir, Pair<Integer, Integer> where){
        if (typesMatrix[where.getX()][where.getY()] != Type.DESTRUCTIBLE_WALL
        && typesMatrix[where.getX()][where.getY()] != Type.INDESTRUCTIBLE_WALL)
    typesMatrix[where.getX()][where.getY()] = Type.EXPLOSION;
    }

    private void addEntitiesToMatrix(Type[][] typesMatrix) {
        for (var pos : this.field.keySet()) {
            typesMatrix[pos.getX()][pos.getY()] = this.field.get(pos).getX();
        }
    }
}
