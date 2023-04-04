package it.unibo.unibomber.model;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.BombPlaceComponent;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;
import it.unibo.unibomber.game.ecs.impl.ExplodeComponent;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static it.unibo.unibomber.utilities.Constants.Explode.EXPLODE_DURATION;
import static it.unibo.unibomber.utilities.Constants.Explode.EXPIRING_TIME;
import static it.unibo.unibomber.utilities.Constants.Destroy.getDestroyFramesPerType;
import static it.unibo.unibomber.utilities.Constants.Destroy.STANDARD_FRAME_DURATION;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class BombTest {

    private static final float PLAYER_STARTING_X = 5.6f;
    private static final float PLAYER_STARTING_Y = 3.4f;
    private static final float BOMB_EXCEPTED_X = 6.0f;
    private static final float BOMB_EXCEPTED_Y = 3.0f;
    private static final float PLAYER_EXCEPTED_X = 5.0f;
    private static final float PLAYER_EXCEPTED_Y = 3.0f;
    private static final float POWERUP_EXCEPTED_X = 6.0f;
    private static final float POWERUP_EXCEPTED_Y = 2.0f;
    private static final float DESWALL_EXCEPTED_X = 7.0f;
    private static final float DESWALL_EXCEPTED_Y = 3.0f;
    private static final float INDESWALL_EXCEPTED_X = 6.0f;
    private static final float INDESWALL_EXCEPTED_Y = 4.0f;
    private static final int FIELD_ROWS = 15;
    private static final int FIELD_COLS = 19;

    private final Game game = new GameImpl(null, FIELD_ROWS, FIELD_COLS);
    private final EntityFactory entityFactory = new EntityFactoryImpl(game);

    private Entity createPlayerEntity() {
        return this.entityFactory.makePlayable(new Pair<Float, Float>(PLAYER_STARTING_X, PLAYER_STARTING_Y));
    }

    private Entity createPlayerForBomb() {
        return this.entityFactory.makePlayable(new Pair<>(PLAYER_EXCEPTED_X, PLAYER_EXCEPTED_Y));
    }

    private Entity createBombEntitiy(final Entity placer) {
        return this.entityFactory.makeBomb(placer, new Pair<>(BOMB_EXCEPTED_X, BOMB_EXCEPTED_Y));
    }

    private Entity createPowerupEntity() {
        return this.entityFactory.makePowerUp(new Pair<>(POWERUP_EXCEPTED_X, POWERUP_EXCEPTED_Y),
                PowerUpType.SPEEDUP);
    }

    private Entity createDesWallEntity() {
        return this.entityFactory.makeDestructibleWall(new Pair<>(DESWALL_EXCEPTED_X, DESWALL_EXCEPTED_Y));
    }

    private Entity createIndesWallEntity() {
        return this.entityFactory.makeIndestructibleWall(new Pair<>(INDESWALL_EXCEPTED_X, INDESWALL_EXCEPTED_Y));
    }

    private int getDestructionFrames(final Type type) {
        return getDestroyFramesPerType().containsKey(type)
                ? getDestroyFramesPerType().get(type)
                : STANDARD_FRAME_DURATION;
    }

    @Test
    void testBombPlace() {
        final Entity player = this.createPlayerEntity();
        game.addEntity(player);
        player.getComponent(BombPlaceComponent.class).get().placeBomb();
        player.getComponent(BombPlaceComponent.class).get().update();
        final Optional<Entity> bombEntity = game.getEntities().stream()
                .filter(entity -> entity.getType().equals(Type.BOMB))
                .findFirst();
        assertTrue(bombEntity.isPresent());
        assertEquals(new Pair<>(BOMB_EXCEPTED_X, BOMB_EXCEPTED_Y), bombEntity.get().getPosition());
    }

    @Test
    void testBombExplosion() {
        final var player = this.createPlayerForBomb();
        final var bomb = this.createBombEntitiy(player);
        final var powerup = this.createPowerupEntity();
        final var desWall = this.createDesWallEntity();
        final var indesWall = this.createIndesWallEntity();
        final List<Entity> entities = new ArrayList<>(List.of(
                player, bomb, powerup, desWall, indesWall));
        Constants.Destroy.setDestroyFramesPerType();
        this.game.addEntity(player);
        this.game.addEntity(bomb);
        this.game.addEntity(powerup);
        this.game.addEntity(desWall);
        this.game.addEntity(indesWall);
        assertTrue(bomb.getComponent(ExplodeComponent.class).isPresent());
        assertTrue(this.game.getEntities().contains(player));
        assertTrue(this.game.getEntities().contains(bomb));
        assertTrue(this.game.getEntities().contains(powerup));
        assertTrue(this.game.getEntities().contains(desWall));
        assertTrue(this.game.getEntities().contains(indesWall));
        assertFalse(bomb.getComponent(ExplodeComponent.class).get().isExploding());
        for (int i = 0; i < (EXPIRING_TIME + EXPLODE_DURATION); i++) {
            bomb.getComponent(ExplodeComponent.class).get().update();
        }
        for (final var entity : entities) {
            if (entity.getComponent(DestroyComponent.class).isPresent()) {
                for (int j = 0; j <= this.getDestructionFrames(entity.getType()); j++) {
                    entity.getComponent(DestroyComponent.class).get().update();
                }
            }
        }
        assertFalse(this.game.getEntities().contains(player));
        assertFalse(this.game.getEntities().contains(bomb));
        assertFalse(this.game.getEntities().contains(powerup));
        assertFalse(this.game.getEntities().contains(desWall));
        assertTrue(this.game.getEntities().contains(indesWall));
    }
}
