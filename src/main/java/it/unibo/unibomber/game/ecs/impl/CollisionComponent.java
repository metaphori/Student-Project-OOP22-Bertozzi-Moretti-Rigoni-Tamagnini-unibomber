package it.unibo.unibomber.game.ecs.impl;

import java.awt.geom.Rectangle2D;
import java.util.function.BiConsumer;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.model.impl.AbstractComponent;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

import java.awt.Color;
import java.awt.Graphics;

import static it.unibo.unibomber.utilities.Constants.UI.Screen;

/**
 * This component manage the collision of entity.
 */
public final class CollisionComponent extends AbstractComponent {
     private final boolean isSolid;
     private boolean isOver;
     private Rectangle2D.Float hitbox;
     private float x, y;
     private int width, height;
     private final BiConsumer<Entity, Entity> biConsumer;

     @Override
     public void update() {
          hitbox.x = (int) (this.getEntity().getPosition().getX() * Screen.getTilesSize());
          hitbox.y = (int) (this.getEntity().getPosition().getY() * Screen.getTilesSize());
          isOutofField();
          changeBombOver();
          checkCollisions();
     }

     /**
      * For debugging the hitbox.
      * 
      * @param g graphics.
      */
     public void drawHitbox(final Graphics g) {
          g.setColor(Color.PINK);
          g.drawRect((int) hitbox.x, (int) hitbox.y,
                    (int) hitbox.width, (int) hitbox.height);
     }

     /**
      * This method manage the collision state of entity.
      * 
      * @param isSolid if is solid.
      * @param isOver if is over.
      * @param x x coord.
      * @param y y coord.
      * @param biConsumer biConsumer type.
      */
     public CollisionComponent(final boolean isSolid, final boolean isOver, final int x, final int y,
               final BiConsumer<Entity, Entity> biConsumer) {
          this.isSolid = isSolid;
          this.isOver = isOver;
          this.x = (int) (x * Screen.getTilesSize());
          this.y = (int) (y * Screen.getTilesSize());
          this.biConsumer = biConsumer;
          initHitbox();
     }

     /**
      * @return hitbox of entity
      */
     public Rectangle2D.Float getHitbox() {
          return new Rectangle2D.Float(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
     }

     /**
      * @return true if is solid.
      */
     public boolean isSolid() {
          return isSolid;
     }

     /**
      * @return true if entity is over with other entity.
      */
     public boolean isOver() {
          return isOver;
     }

     /**
      * @param isOver if is over.
      */
     public void setOver(final boolean isOver) {
          this.isOver = isOver;
     }

     /**
      * This method check if entity collide with other one.
      */
     public void checkCollisions() {
          final Entity entity = this.getEntity();
          if (entity.getType().equals(Type.BOMBER) || entity.getType().equals(Type.BOMB)) {
               entity.getGame().getEntities().stream()
                         .filter(e -> !e.equals(entity))
                         .filter(e -> hitbox.intersects(e.getComponent(CollisionComponent.class).get().getHitbox()))
                         .forEach(e -> {
                              biConsumer.accept(entity, e);
                         });
          }
     }

     /**
      * Check if entity is out of field and if it is push back.
      */
     private void isOutofField() {
          final Entity entity = this.getEntity();
          Pair<Float, Float> newPosition = null;
          if (entity.getType() != Type.BOMB
                    || !entity.getComponent(ThrowComponent.class).get().isThrowing()
                    || entity.getComponent(SlidingComponent.class).get().isSliding()) {
               if (hitbox.x > (Screen.getgWidth() - Screen.getTilesSize())) {
                    newPosition = new Pair<>((float) Screen.getTilesWidth() - 1, entity.getPosition().getY());
               } else if (hitbox.x < 0) {
                    newPosition = new Pair<>(0f, entity.getPosition().getY());
               } else if (hitbox.y > (Screen.getgHeight() - Screen.getTilesSize())) {
                    newPosition = new Pair<>(entity.getPosition().getX(), (float) Screen.getTilesHeight() - 1);
               } else if (hitbox.y < 0) {
                    newPosition = new Pair<>(entity.getPosition().getX(), 0f);
               }
               if (newPosition != null) {
                    entity.setPosition(newPosition);
                    if (entity.getType() == Type.BOMB
                              && entity.getComponent(SlidingComponent.class).get().isSliding()) {
                         entity.getComponent(SlidingComponent.class).get().setSliding(false, Direction.CENTER);
                    }
               }
          }
     }

     /**
      * This method change bomb over status if necessary.
      */
     private void changeBombOver() {
          final Entity player = this.getEntity();
          if (player.getType().equals(Type.BOMBER)) {
               final CollisionComponent playerCollision = player.getComponent(CollisionComponent.class).get();
               this.getEntity().getGame().getEntities().stream()
                         .filter(entity -> entity.getType() == Type.BOMB)
                         .filter(entity -> entity.getComponent(ExplodeComponent.class).get().getPlacer().equals(player))
                         .map(entity -> entity.getComponent(CollisionComponent.class))
                         .filter(entity -> entity.isPresent() && entity.get().isOver())
                         .filter(entity -> !playerCollision.getHitbox().intersects(entity.get().getHitbox()))
                         .forEach(entity -> entity.get().setOver(false));
          }
     }

     private void initHitbox() {
          this.width = (int) Screen.getTilesSize();
          this.height = this.width;
          hitbox = new Rectangle2D.Float(x, y, this.width, this.height);
     }
}
