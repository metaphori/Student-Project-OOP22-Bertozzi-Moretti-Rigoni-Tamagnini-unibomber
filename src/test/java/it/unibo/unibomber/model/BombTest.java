package it.unibo.unibomber.model;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.BombPlaceComponent;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.utilities.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class BombTest {

    private static final float PLAYER_STARTING_X = 5.6f;
    private static final float PLAYER_STARTING_Y = 3.4f;
    private static final float BOMB_EXCEPTED_X = 6.0f;
    private static final float BOMB_EXCEPTED_Y = 3.0f;

    private final Game game = new GameImpl(null);
    private final EntityFactory entityFactory = new EntityFactoryImpl(game);

    private Entity createPlayerEntity() {
        return this.entityFactory.makePlayable(new Pair<Float, Float>(PLAYER_STARTING_X, PLAYER_STARTING_Y));
    }

    @Test
    void testBombPlace() {
        Entity player = this.createPlayerEntity();
        game.addEntity(player);
        player.getComponent(BombPlaceComponent.class).get().placeBomb();
        player.getComponent(BombPlaceComponent.class).get().update();
        Optional<Entity> bombEntity = game.getEntities().stream()
                .filter(entity -> entity.getType() == Type.BOMB)
                .findFirst();
        assertTrue(bombEntity.isPresent());
        assertEquals(new Pair<>(BOMB_EXCEPTED_X, BOMB_EXCEPTED_Y), bombEntity.get().getPosition());
    }

}
