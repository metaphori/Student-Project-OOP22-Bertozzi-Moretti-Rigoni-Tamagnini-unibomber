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
     private boolean isOverstable;
     private boolean isCollided;
     private Rectangle2D.Float hitbox;
     private float x, y;
     private int width, height;

     @Override
     public void update() {
          // update hitbox rectangle coord
          hitbox.x = (int) (this.getEntity().getPosition().getX() * Game.TILES_SIZE);
          hitbox.y = (int) (this.getEntity().getPosition().getY() * Game.TILES_SIZE);
          isOutofField();
          Entity player = this.getEntity();
          if (player.getType() == Type.PLAYABLE) {
               for (Entity entity : this.getEntity().getGame().getEntities()) {
                    if (entity.getType() == Type.BOMB) {
                         // TODO refactor
                         int Playerx = Math.round(player.getPosition().getX());
                         int Playery = Math.round(player.getPosition().getY());
                         int bombx = Math.round(entity.getPosition().getX());
                         int bomby = Math.round(entity.getPosition().getY());
                         int playerFloorx = (int) Math.floor(player.getPosition().getX());
                         int playerFloory = (int) Math.floor(player.getPosition().getY());
                         int playerCeilx = (int) Math.ceil(player.getPosition().getX());
                         int playerCeily = (int) Math.ceil(player.getPosition().getY());
                         if (bombx != Playerx || bomby != Playery) {
                              if (bombx != playerFloorx || bomby != playerFloory) {
                                   if (bombx != playerCeilx || bomby != playerCeily) {
                                        entity.getComponent(CollisionComponent.class).get().setOverstable(false);
                                   }
                              }
                         }
                    }
               }
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
          this.isCollided = false;
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
      * @return true if is collied.
      */
     public boolean isCollided() {
          return isCollided;
     }

     /**
      * @param isCollided
      */
     public void setCollided(boolean isCollided) {
          this.isCollided = isCollided;
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
     public void setOverstable(boolean isOverstable) {
          this.isOverstable = isOverstable;
     }

     /**
      * This method check if entity collide with other one.
      */
     public void checkCollisions() {
          Entity entity = this.getEntity();
          // TODO refactor
          this.getEntity().getGame().getEntities().stream()
                    .forEach(e -> {
                         if (hitbox.intersects(e.getComponent(CollisionComponent.class).get().getHitbox())) {
                              e.getComponent(CollisionComponent.class).get().setCollided(true);
                         } else {
                              e.getComponent(CollisionComponent.class).get().setCollided(false);
                         }
                    });

          if (entity.getType() == Type.PLAYABLE || entity.getType() == Type.BOMB) {
               entity.getGame().getEntities().stream()
                         .filter(e -> !e.equals(entity))
                         .filter(e -> hitbox.intersects(e.getComponent(CollisionComponent.class).get().getHitbox()))
                         .forEach(e -> {

                              if (e.getType() == Type.POWERUP) {
                                   if (entity.getType() != Type.BOMB) {
                                        PowerUpType powerUpType = e.getComponent(PowerUpComponent.class).get()
                                                  .getPowerUpType();
                                        PowerUpHandlerComponent powerUpHandlerComponent = entity
                                                  .getComponent(PowerUpHandlerComponent.class).get();
                                        powerUpHandlerComponent.addPowerUp(powerUpType);
                                        e.getComponent(DestroyComponent.class).get().destroy();
                                   } else {
                                        e.getComponent(DestroyComponent.class).get().destroy();
                                   }
                              }
                              if (e.getType() == Type.BOMB && entity.getType() != Type.BOMB
                                        && !e.getComponent(CollisionComponent.class).get().isOverstable()
                                        && entity.getComponent(PowerUpHandlerComponent.class).get().getPowerUpList()
                                                  .contains(PowerUpType.KICKBOMB)) {
                                   e.getComponent(SlidingComponent.class).get().setSliding(true);
                              }
                              CollisionComponent collision = e.getComponent(CollisionComponent.class).get();
                              if (collision.isSolid() && !collision.isOverstable()) {
                                   float thisX = Math.round(entity.getPosition().getX());
                                   float thisY = Math.round(entity.getPosition().getY());
                                   float eX = Math.round(e.getPosition().getX());
                                   float eY = Math.round(e.getPosition().getY());
                                   if (thisX == eX && thisY != eY) {
                                        entity.setPosition(new Pair<Float, Float>(thisX, thisY));
                                   } else if (thisX != eX && thisY == eY) {
                                        entity.setPosition(new Pair<Float, Float>(thisX, thisY));
                                   } else if (thisX != eX && thisY != eY) {
                                        entity.setPosition(new Pair<Float, Float>(thisX, thisY));
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
