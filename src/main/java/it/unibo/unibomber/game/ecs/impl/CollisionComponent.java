package it.unibo.unibomber.game.ecs.impl;

import java.awt.geom.Rectangle2D;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Pair;

import java.awt.Color;
import java.awt.Graphics;

import static it.unibo.unibomber.utilities.Constants.UI.Game;

/**
 * This component manage the collision of entity.
 */
public final class CollisionComponent extends AbstractComponent {
     // TODO
     // true if it blocks other entities
     private final boolean isSolid;
     private final boolean isOverstable;
     private Rectangle2D.Float hitbox;
     private float x, y;
     private int width, height;

     @Override
     public void update() {
          // update hitbox rectangle coord
          hitbox.x = (int) (this.getEntity().getPosition().getX() * Game.TILES_SIZE);
          hitbox.y = (int) (this.getEntity().getPosition().getY() * Game.TILES_SIZE);
          isOutofField();
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
      * @param isSolid
      * @param isOverstable
      * @param x
      * @param y
      */
     public CollisionComponent(final boolean isSolid, final boolean isOverstable, final int x, final int y) {
          this.isSolid = isSolid;
          this.isOverstable = isOverstable;
          this.x = (int) (x * Game.TILES_SIZE);
          this.y = (int) (y * Game.TILES_SIZE);
          initHitbox();
     }

     /**
      * @return hitbox of entity
      */
     public Rectangle2D.Float getHitbox() {
          return hitbox;
     }

     /**
      * @return true if is solid.
      */
     public boolean isSolid() {
          return isSolid;
     }

     /**
      * @return true if entity is overstable with other entity.
      */
     public boolean isOverstable() {
          return isOverstable;
     }

     /**
      * This method check if entity collide with other one.
      */
     public void checkCollisions() {
          Entity entity = this.getEntity();
          if (entity.getType() == Type.PLAYABLE) {
          entity.getGame().getEntities().stream()
                    .filter(e -> !e.equals(entity))
                    .filter(e -> hitbox.intersects(e.getComponent(CollisionComponent.class).get().getHitbox()))
                    .forEach(e -> {

                         if (e.getType() == Type.POWERUP) {
                              PowerUpType powerUpType = e.getComponent(PowerUpComponent.class).get().getPowerUpType();
                              PowerUpHandlerComponent powerUpHandlerComponent = entity
                                        .getComponent(PowerUpHandlerComponent.class).get();
                              powerUpHandlerComponent.addPowerUp(powerUpType);
                              e.getComponent(DestroyComponent.class).get().destroy();
                         }
                         CollisionComponent collision = e.getComponent(CollisionComponent.class).get();
                         if (collision.isSolid() && !collision.isOverstable()) {
                              if (Math.round(this.getEntity().getPosition().getX()) != Math
                                        .round(e.getPosition().getX())
                                        || Math.round(this.getEntity().getPosition().getY()) != Math
                                                  .round(e.getPosition().getY())) {
                                   this.getEntity().setPosition(new Pair<Float, Float>(
                                             (float) Math.round(this.getEntity().getPosition().getX()),
                                             (float) Math.round(this.getEntity().getPosition().getY())));
                              }
                         }
                    });
          }
     }

     /**
      * Check if entity is out of field and if it is push back.
      */
     private void isOutofField() {
          if (hitbox.x > (Game.G_WIDTH - Game.TILES_SIZE)) {
               this.getEntity().setPosition(
                         new Pair<Float, Float>((float) Game.TILES_WIDTH - 1,
                                   this.getEntity().getPosition().getY()));
          } else if (hitbox.x < 0) {
               this.getEntity().setPosition(new Pair<Float, Float>(0f,
                         this.getEntity().getPosition().getY()));
          } else if (hitbox.y > (Game.G_HEIGHT - Game.TILES_SIZE)) {
               this.getEntity().setPosition(
                         new Pair<Float, Float>(this.getEntity().getPosition().getX(), (float) Game.TILES_HEIGHT - 1));
          } else if (hitbox.y < 0) {
               this.getEntity().setPosition(new Pair<Float, Float>(this.getEntity().getPosition().getX(), 0f));
          }
     }

     private void initHitbox() {
          this.width = (int) Game.TILES_SIZE;
          this.height = this.width;
          hitbox = new Rectangle2D.Float(x, y, this.width, this.height);
     }
}
