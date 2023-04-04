package it.unibo.unibomber.game.model.impl;

import it.unibo.unibomber.game.ecs.api.Component;
import it.unibo.unibomber.game.ecs.api.Entity;

/**
 * This class serves as an intermediate class in order to describe the behaviour
 * common to all components, in particular the relationship between itself and
 * the
 * Entity it is attatched too.
 */
public abstract class AbstractComponent implements Component {

    private Entity entity;

    /**
     * @return entity
     */
    protected Entity getEntity() {
        return this.entity;
    }

    /**
     * @param entity entity to set.
     */
    protected void setEntity(final Entity entity) {
        this.entity = entity;
    }
}
