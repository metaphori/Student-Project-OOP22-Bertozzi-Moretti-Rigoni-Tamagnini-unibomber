package it.unibo.unibomber.game.ecs.impl;

import it.unibo.unibomber.game.ecs.api.Component;
import it.unibo.unibomber.game.ecs.api.Entity;

/*
 * this class serves as an intermediate class in order to describe the behaviour 
 * common to all components, in particular the relationship between itself and the
 * Entity it is attatched to
 */
public abstract class AbstractComponent implements Component {

    /**
     * {@inheritDoc}
     */
    private Entity entity;

    protected Entity getEntity() {
        return this.entity;
    }

    /**
     * {@inheritDoc}
     */
    protected void setEntity(Entity entity) {
        this.entity = entity;
    }
}
