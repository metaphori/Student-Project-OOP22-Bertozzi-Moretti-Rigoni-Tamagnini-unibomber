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
     private final boolean isSolid;
     private boolean isOverstable;
     private Rectangle2D.Float hitbox;
     private float x, y;
     private int width, height;

     @Override
     public void update() {
          // update hitbox rectangle coord
          hitbox.x = (int) (this.getEntity().getPosition().getX() * Game.getTilesSize());
          hitbox.y = (int) (this.getEntity().getPosition().getY() * Game.getTilesSize());
          isOutofField();
          Entity player = this.getEntity();
          if (player.getType() == Type.PLAYABLE) {
               this.getEntity().getGame().getEntities().stream()
                         .filter(entity -> entity.getType() == Type.BOMB)
                         .filter(entity -> entity.getComponent(CollisionComponent.class).isPresent()
                                   && entity.getComponent(CollisionComponent.class).get().isOverstable())
                         .filter(entity -> !player.getComponent(CollisionComponent.class).get().getHitbox()
                                   .intersects(entity.getComponent(CollisionComponent.class).get().getHitbox()))
                         .forEach(entity -> entity.getComponent(CollisionComponent.class).get().setOverstable(false));
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
      * @param isOverstable
      * @param x
      * @param y
      */
     public CollisionComponent(final boolean isSolid, final boolean isOverstable, final int x, final int y) {
          this.isSolid = isSolid;
          this.isOverstable = isOverstable;
          this.x = (int) (x * Game.getTilesSize());
          this.y = (int) (y * Game.getTilesSize());
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
      * @param isOverstable
      */
     public void setOverstable(final boolean isOverstable) {
          this.isOverstable = isOverstable;
     }

     /**
      * This method check if entity collide with other one.
      */
     public void checkCollisions() {
          Entity entity = this.getEntity();
          if (entity.getType() == Type.PLAYABLE || entity.getType() == Type.BOMB || entity.getType() == Type.BOT) {
               entity.getGame().getEntities().stream()
                         .filter(e -> !e.equals(entity))
                         .filter(e -> hitbox.intersects(e.getComponent(CollisionComponent.class).get().getHitbox()))
                         .forEach(e -> {
                              if (e.getType() == Type.POWERUP && entity.getType() != Type.BOMB) {
                                   PowerUpType powerUpType = e.getComponent(PowerUpComponent.class).get()
                                             .getPowerUpType();
                                   PowerUpHandlerComponent powerUpHandlerComponent = entity
                                             .getComponent(PowerUpHandlerComponent.class).get();
                                   powerUpHandlerComponent.addPowerUp(powerUpType);
                              }
                              if (e.getType() == Type.POWERUP) {
                                   e.getComponent(DestroyComponent.class).get().destroy();
                              }
                              if (entity.getType() == Type.BOMB
                                        && (e.getType() == Type.PLAYABLE || e.getType() == Type.BOT)
                                        && !entity.getComponent(CollisionComponent.class).get().isOverstable()
                                        && e.getComponent(PowerUpHandlerComponent.class).get().getPowerUpList()
                                                  .contains(PowerUpType.KICKBOMB)) {
                                   entity.getComponent(SlidingComponent.class).get().setSliding(true,
                                             e.getComponent(MovementComponent.class).get().getDirection());
                              }
                              CollisionComponent collision = e.getComponent(CollisionComponent.class).get();
                              if (collision.isSolid() && !collision.isOverstable()) {
                                   if (entity.getType() == Type.BOMB) {
                                        entity.getComponent(SlidingComponent.class).get().setSliding(false, null);
                                   }
                                   float thisX = Math.round(entity.getPosition().getX());
                                   float thisY = Math.round(entity.getPosition().getY());
                                   float eX = Math.round(e.getPosition().getX());
                                   float eY = Math.round(e.getPosition().getY());
                                   boolean isOccupied = entity.getGame().getGameField().getField()
                                             .entrySet().stream()
                                             .anyMatch(entry -> entry.getKey().equals(new Pair<Integer, Integer>(
                                                       (Math.round(thisX) + entity.getComponent(MovementComponent.class)
                                                                 .get().getDirection().getX()),
                                                       (Math.round(thisY)
                                                                 + -entity.getComponent(MovementComponent.class).get()
                                                                           .getDirection().getY()))));

                                   if (!isOccupied) {
                                        if (thisX != eX || thisY != eY) {
                                             entity.setPosition(new Pair<Float, Float>(thisX, thisY));
                                        }
                                   } else {
                                        if (thisX == eX || thisY != eY) {
                                             entity.setPosition(
                                                       new Pair<Float, Float>(entity.getPosition().getX(), thisY));
                                        } else if (thisX != eX && thisY == eY) {
                                             entity.setPosition(
                                                       new Pair<Float, Float>(thisX, entity.getPosition().getY()));
                                        }
                                   }

                              }
                         });
          }
     }

     /**
      * Check if entity is out of field and if it is push back.
      */
     private void isOutofField() {
          if (hitbox.x > (Game.getgWidth() - Game.getTilesSize())) {
               this.getEntity().setPosition(
                         new Pair<Float, Float>((float) Game.TILES_WIDTH - 1,
                                   this.getEntity().getPosition().getY()));
          } else if (hitbox.x < 0) {
               this.getEntity().setPosition(new Pair<Float, Float>(0f,
                         this.getEntity().getPosition().getY()));
          } else if (hitbox.y > (Game.getgHeight() - Game.getTilesSize())) {
               this.getEntity().setPosition(
                         new Pair<Float, Float>(this.getEntity().getPosition().getX(), (float) Game.TILES_HEIGHT - 1));
          } else if (hitbox.y < 0) {
               this.getEntity().setPosition(new Pair<Float, Float>(this.getEntity().getPosition().getX(), 0f));
          }
     }

     private void initHitbox() {
          this.width = (int) Game.getTilesSize();
          this.height = this.width;
          hitbox = new Rectangle2D.Float(x, y, this.width, this.height);
     }
}
