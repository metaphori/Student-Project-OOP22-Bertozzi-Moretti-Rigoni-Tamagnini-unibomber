package it.unibo.unibomber.game.ecs.impl;

import java.awt.geom.Rectangle2D;
import java.util.function.BiConsumer;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
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
          final Entity player = this.getEntity();
          if (player.getType() == Type.BOMBER) {
               this.getEntity().getGame().getEntities().stream()
                         .filter(entity -> entity.getType() == Type.BOMB)
                         .filter(entity -> entity.getComponent(CollisionComponent.class).isPresent()
                                   && entity.getComponent(CollisionComponent.class).get().isOver())
                         .filter(entity -> !player.getComponent(CollisionComponent.class).get().getHitbox()
                                   .intersects(entity.getComponent(CollisionComponent.class).get().getHitbox()))
                         .forEach(entity -> entity.getComponent(CollisionComponent.class).get().setOver(false));
          }
          checkCollisions();
     }

     /**
      * For debugging the hitbox.
      * 
      * @param g
      */
     public void drawHitbox(final Graphics g) {
          g.setColor(Color.PINK);
          g.drawRect((int) hitbox.x, (int) hitbox.y,
                    (int) hitbox.width, (int) hitbox.height);
     }

     /**
      * This method manage the collision state of entity.
      * 
      * @param isSolid
      * @param isOver
      * @param x
      * @param y
      * @param biConsumer
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
      * @param isOver
      */
     public void setOver(final boolean isOver) {
          this.isOver = isOver;
     }

     /**
      * This method check if entity collide with other one.
      */
     public void checkCollisions() {
          final Entity entity = this.getEntity();
          if (entity.getType() == Type.BOMBER || entity.getType() == Type.BOMB) {
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
          if (this.getEntity().getType() != Type.BOMB
                    || !this.getEntity().getComponent(ThrowComponent.class).get().isThrowing()) {
               if (hitbox.x > (Screen.getgWidth() - Screen.getTilesSize())) {
                    this.getEntity().setPosition(
                              new Pair<Float, Float>((float) Screen.getTilesWidth() - 1,
                                        this.getEntity().getPosition().getY()));
               } else if (hitbox.x < 0) {
                    this.getEntity().setPosition(new Pair<Float, Float>(0f,
                              this.getEntity().getPosition().getY()));
               } else if (hitbox.y > (Screen.getgHeight() - Screen.getTilesSize())) {
                    this.getEntity().setPosition(
                              new Pair<Float, Float>(this.getEntity().getPosition().getX(),
                                        (float) Screen.getTilesHeight() - 1));
               } else if (hitbox.y < 0) {
                    this.getEntity().setPosition(new Pair<Float, Float>(this.getEntity().getPosition().getX(), 0f));
               }
          }
     }

     private void initHitbox() {
          this.width = (int) Screen.getTilesSize();
          this.height = this.width;
          hitbox = new Rectangle2D.Float(x, y, this.width, this.height);
     }
}
