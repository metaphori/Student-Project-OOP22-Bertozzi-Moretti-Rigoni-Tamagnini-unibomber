package it.unibo.unibomber.game.ecs.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

import static it.unibo.unibomber.utilities.Constants.Explode.EXPLODEDURATION;

/**
 * This component manage the explosion of the bomb.
 */
public class ExplodeComponent extends AbstractComponent {

    private int explodeFrames;
    private boolean isExploding;

    /**
     * In the constuctor, set 0 the field explodeFrames
     * and set false the field isExploding.
     */
    public ExplodeComponent() {
        this.explodeFrames = 0;
        this.isExploding = false;
    }

    @Override
    public final void update() {
        if (this.isExploding) {
            this.explodeFrames++;
            if (this.explodeFrames < EXPLODEDURATION) {
                explodeEntities(this.getEntity().getGame().getEntities().stream()
                        .filter(e -> e.getType() == Type.BOT || e.getType() == Type.PLAYABLE)
                        .collect(Collectors.toList()));
            } else {
                this.getEntity().getComponent(DestroyComponent.class).get().destroy();
                this.explodeFrames = 0;
                this.isExploding = false;
            }
        }
    }

    /**
     * A method to know if the bomb is exploding.
     * 
     * @return true if is exploding, false otherwise
     */
    public boolean isExploding() {
        return this.isExploding;
    }

    /**
    * A method to destroy wall or powerups in the bomb range.
    */
    public void explode() {
        explodeEntities(this.getEntity().getGame().getEntities().stream()
                        .filter(e -> e.getType() != Type.BOT 
                                && e.getType() != Type.PLAYABLE 
                                && e.getType() != Type.INDESTRUCTIBLE_WALL)
                        .collect(Collectors.toList()));
    }

    /**
    * General method to control if there are some entities on the bomb range.
    * @param entitiesList the entities to control
    */
    private void explodeEntities(final List<Entity> entitiesList) {
        final int bombRange = this.getEntity().getComponent(PowerUpListComponent.class).get().getBombFire();
        final var field = this.getEntity().getGame().getGameField().getField();
        entitiesList.stream()
            .forEach(entity -> {
                Arrays.stream(Direction.values()).forEach(dir -> {
                    IntStream.rangeClosed(1, bombRange).mapToObj(countPositions -> {
                        final Pair<Float, Float> checkPos = new Pair<>(
                            entity.getPosition().getX() + dir.getX() * countPositions,
                            entity.getPosition().getY() + dir.getY() * countPositions
                        );
                        return field.get(checkPos);
                    }).filter(Objects::nonNull).findFirst().ifPresent(e -> {
                        if (e.getX() == Type.BOMB) {
                            explodeEntities(List.of(e.getY()));
                        } else {
                            this.getEntity().getComponent(DestroyComponent.class).get().destroy();
                        }
                    });
                });
            });
        this.isExploding = true;
    }
}
