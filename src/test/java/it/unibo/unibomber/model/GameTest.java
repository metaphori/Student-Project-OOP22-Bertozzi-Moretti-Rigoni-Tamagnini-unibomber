package it.unibo.unibomber.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static it.unibo.unibomber.utilities.Constants.Destroy.getDestroyFramesPerType;
import static it.unibo.unibomber.utilities.Constants.Destroy.STANDARD_FRAME_DURATION;

import org.junit.jupiter.api.Test;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.DestroyComponent;
import it.unibo.unibomber.game.model.api.EntityFactory;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.impl.EntityFactoryImpl;
import it.unibo.unibomber.game.model.impl.GameImpl;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Pair;

class GameTest {

    private static final int FIELD_ROWS = 15;
    private static final int FIELD_COLS = 19;
    private static final float PLAYER_X = 5.6f;
    private static final float PLAYER_Y = 3.4f;
    private static final int DIFFICULT_AI = 1;

    private final Game game = new GameImpl(null, FIELD_ROWS, FIELD_COLS);
    private final EntityFactory entityFactory = new EntityFactoryImpl(this.game);
    private Entity bot;
    private Entity player;

    private int getDestroyFrames(final Type type) {
        return getDestroyFramesPerType().containsKey(type) ? getDestroyFramesPerType().get(type)
                : STANDARD_FRAME_DURATION;
    }

    private void setPlayerAndBot() {
        this.bot = this.entityFactory.makeBot(new Pair<>(PLAYER_X, PLAYER_Y), DIFFICULT_AI);
        this.player = this.entityFactory.makePlayable(new Pair<>(PLAYER_X, PLAYER_Y));
        Constants.Destroy.setDestroyFramesPerType();
        this.game.addEntity(bot);
        this.game.addEntity(player);
    }

    @Test
    void testPlay() {
        Gamestate.setGameState(Gamestate.PLAY);
        assertEquals(Gamestate.PLAY, Gamestate.getGamestate());
    }

    @Test
    void testVictory() {
        this.setPlayerAndBot();
        Gamestate.setGameState(Gamestate.PLAY);
        assertTrue(bot.getComponent(DestroyComponent.class).isPresent());
        assertTrue(player.getComponent(DestroyComponent.class).isPresent());
        bot.getComponent(DestroyComponent.class).get().destroy();
        for (int i = 0; i <= this.getDestroyFrames(bot.getType()); i++) {
            bot.getComponent(DestroyComponent.class).get().update();
        }
        assertEquals(Gamestate.WIN, Gamestate.getGamestate());
        assertTrue(this.game.getEntities().contains(player));
        assertFalse(this.game.getEntities().contains(bot));
    }

    @Test
    void testLose() {
        this.setPlayerAndBot();
        Gamestate.setGameState(Gamestate.PLAY);
        assertTrue(bot.getComponent(DestroyComponent.class).isPresent());
        assertTrue(player.getComponent(DestroyComponent.class).isPresent());
        player.getComponent(DestroyComponent.class).get().destroy();
        for (int i = 0; i <= this.getDestroyFrames(player.getType()); i++) {
            player.getComponent(DestroyComponent.class).get().update();
        }
        assertEquals(Gamestate.LOSE, Gamestate.getGamestate());
        assertTrue(this.game.getEntities().contains(bot));
        assertFalse(this.game.getEntities().contains(player));
    }
}
