package it.unibo.unibomber.game.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.ExplodeComponent;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpListComponent;
import it.unibo.unibomber.game.model.api.Field;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.Utilities;

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
    public final Type[][] getMatrixTypes() {
        final Pair<Integer, Integer> gameDimensions = this.game.getDimensions();
        final Type[][] typesMatrix = new Type[gameDimensions.getX()][gameDimensions.getY()];
        initializeTypeMatrix(typesMatrix);
        addEntitiesToMatrix(typesMatrix);
        handleBombExplosion(typesMatrix);

        return typesMatrix;
    }

    private void initializeTypeMatrix(final Type[][] typesMatrix) {
        for (int x = 0; x < typesMatrix.length; x++) {
            for (int y = 0; y < typesMatrix[0].length; y++) {
                typesMatrix[x][y] = Type.AIR;
            }
        }
    }

    private void handleBombExplosion(final Type[][] typesMatrix) {
        // TODO only works with basic bombs
        field.keySet().stream()
                .filter(e -> field.get(e).getX().equals(Type.BOMB))
                .filter(e -> field.get(e).getY().getComponent(ExplodeComponent.class).get().isExploding())
                .forEach(e -> {
                    PowerUpListComponent powerupList = field.get(e).getY().getComponent(PowerUpListComponent.class)
                            .get();

                    for (Direction d : Direction.values()) {
                        if (d != Direction.CENTER) {
                            addExplosionToMatrix(typesMatrix, e, powerupList.getBombFire(), d, 1);
                        }
                    }
                });

    }

    private void addExplosionToMatrix(final Type[][] typesMatrix, final Pair<Integer, Integer> where,
            final int strength, final Direction d, final int step) {
        if (step <= strength) {
            Pair<Integer, Integer> newDirection = new Pair<Integer, Integer>(where.getX() + d.getX() * step,
                    where.getY() + d.getY() * step);
            if (Utilities.isBetween(newDirection.getX(), 0, Constants.UI.Game.TILES_WIDTH)
                    && Utilities.isBetween(newDirection.getY(), 0, Constants.UI.Game.TILES_HEIGHT)) {
                if (typesMatrix[newDirection.getX()][newDirection.getY()] == Type.AIR) {
                    if (typesMatrix[newDirection.getX()][newDirection.getY()] != Type.DESTRUCTIBLE_WALL
                            && typesMatrix[newDirection.getX()][newDirection.getY()] != Type.INDESTRUCTIBLE_WALL) {
                        typesMatrix[newDirection.getX()][newDirection.getY()] = Type.EXPLOSION;
                    }

                    addExplosionToMatrix(typesMatrix, where, strength, d, step + 1);
                }
            }
        }
    }

    private void addEntitiesToMatrix(final Type[][] typesMatrix) {
        for (var pos : this.field.keySet()) {
            typesMatrix[pos.getX()][pos.getY()] = this.field.get(pos).getX();
        }
    }
}
