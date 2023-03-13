package it.unibo.unibomber.game.ecs.impl;

import java.awt.geom.Rectangle2D;

import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Pair;

import java.awt.Color;
import java.awt.Graphics;

import static it.unibo.unibomber.utilities.Constants.UI.Game.TILES_DEFAULT;
import static it.unibo.unibomber.utilities.Constants.UI.Game.SCALE;
import static it.unibo.unibomber.utilities.Constants.UI.Game.TILES_SIZE;

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
          hitbox.x = (int) (this.getEntity().getPosition().getX() * TILES_DEFAULT * SCALE);
          hitbox.y = (int) (this.getEntity().getPosition().getY() * TILES_DEFAULT * SCALE);
          // isOutofField();
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
      * @param isCollided
      */
     public CollisionComponent(final boolean isSolid, final boolean isOverstable, final int x, final int y) {
          this.isSolid = isSolid;
          this.isOverstable = isOverstable;
          this.x = (int) (x * TILES_DEFAULT * SCALE);
          this.y = (int) (y * TILES_DEFAULT * SCALE);
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
          if (this.getEntity().getType() == Type.PLAYABLE) {
               for (int i = 0; i < this.getEntity().getGame().getEntities().size(); i++) {
                    if (this.getEntity().getGame().getEntities().get(i).getType() != this.getEntity().getType()) {

                         final Rectangle2D.Float r = this.getEntity().getGame().getEntities().get(i)
                                   .getComponent(CollisionComponent.class).get().getHitbox();
                         if (hitbox.intersects(r)) {
                              if (this.getEntity().getGame().getEntities().get(i)
                                        .getComponent(CollisionComponent.class)
                                        .get().isSolid()
                                        && !this.getEntity().getGame().getEntities().get(i)
                                                  .getComponent(CollisionComponent.class)
                                                  .get().isOverstable()) {
                                   this.getEntity().setPosition(
                                             new Pair<Float, Float>(
                                                       (float) Math.round(
                                                                 this.getEntity()
                                                                           .getPosition().getX()),
                                                       (float) Math.round(
                                                                 this.getEntity()
                                                                           .getPosition().getY())));
                              }
                              else {
                                   System.out.print(this.getEntity().getGame().getEntities().get(i)
                                             .getComponent(PowerUpComponent.class).get().getPowerUpType());
                              }
                         }
                    }
               }
          }
     }

     /**
      * Check if entity is out of field and if it is push back
      */
     /*
      * private void isOutofField() {
      * if (hitbox.x > TILES_WIDTH - 1) {
      * this.getEntity().setPosition(
      * new Pair<Float, Float>((float) TILES_WIDTH - 1,
      * this.getEntity().getPosition().getY()));
      * } else if (hitbox.x < 0) {
      * this.getEntity().setPosition(new Pair<Float, Float>(0f,
      * this.getEntity().getPosition().getY()));
      * } else if (hitbox.y > TILES_HEIGHT - 1) {
      * this.getEntity().setPosition(
      * new Pair<Float, Float>(this.getEntity().getPosition().getX(), (float)
      * TILES_HEIGHT - 1));
      * } else if (hitbox.y < 0) {
      * this.getEntity().setPosition(new Pair<Float,
      * Float>(this.getEntity().getPosition().getX(), 0f));
      * }
      * }
      */
     private void initHitbox() {
          this.width = (int) TILES_SIZE;
          this.height = this.width;
          hitbox = new Rectangle2D.Float(x, y, this.width, this.height);
     }
}
