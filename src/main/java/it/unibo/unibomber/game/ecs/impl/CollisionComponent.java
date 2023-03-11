package it.unibo.unibomber.game.ecs.impl;

import java.awt.geom.Rectangle2D;

import it.unibo.unibomber.game.ecs.api.Component;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Pair;

import java.awt.Color;
import java.awt.Graphics;

import static it.unibo.unibomber.utilities.Constants.UI.Game.TILES_DEFAULT;
import static it.unibo.unibomber.utilities.Constants.UI.Game.SCALE;
import static it.unibo.unibomber.utilities.Constants.UI.Game.G_WIDTH;
import static it.unibo.unibomber.utilities.Constants.UI.Game.G_HEIGHT;
import static it.unibo.unibomber.utilities.Constants.UI.Game.TILES_WIDTH;
import static it.unibo.unibomber.utilities.Constants.UI.Game.TILES_HEIGHT;
import static it.unibo.unibomber.utilities.Constants.UI.Game.TILES_SIZE;

/**
 * This component manage the collision of entity.
 */
public class CollisionComponent extends AbstractComponent {
     // TODO
     // true if it blocks other entities
     private final boolean isSolid;
     private final boolean isCollided;
     protected Rectangle2D.Float hitbox;
     protected float x, y;
     protected int width, height;

     @Override
     public void update() {
          // update hitbox rectangle coord
          hitbox.x = this.getEntity().getPosition().getX();
          hitbox.y = this.getEntity().getPosition().getY();
          isOutofField();
          checkCollisions();
     }

     public void drawHitbox(Graphics g) {
          // For debugging the hitbox
          g.setColor(Color.PINK);
          g.drawRect((int) (hitbox.x * TILES_DEFAULT * SCALE), (int) (hitbox.y * TILES_DEFAULT * SCALE),
                    (int) hitbox.width, (int) hitbox.height);
     }

     /**
      * This method manage the collision state of entity.
      * 
      * @param isSolid
      * @param isCollided
      */
     public CollisionComponent(final boolean isSolid, final boolean isCollided) {
          this.isSolid = isSolid;
          this.isCollided = isCollided;
          this.x = 0;
          this.y = 0;
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
      * @return true if collids with other entity.
      */
     public boolean isCollided() {
          return isCollided;
     }

     /**
      * This method check if entity collide with other one.
      */
     public void checkCollisions() {
          for (int i = 0; i < this.getEntity().getGame().getEntities().size(); i++) {
               if (this.getEntity().getGame().getEntities().get(i).getType() != Type.PLAYABLE) {
                    final Rectangle2D.Float r = this.getEntity().getGame().getEntities().get(i)
                              .getComponent(CollisionComponent.class).get().getHitbox();
                    if (hitbox.intersects(r)) {
                         System.out.println("TRUE");
                    }
               }
          }
     }

     /**
      * Check if entity is out of field and if it is push back
      */
     private void isOutofField() {
          if (hitbox.x > TILES_WIDTH - 1) {
               this.getEntity().setPosition(
                         new Pair<Float, Float>((float) TILES_WIDTH - 1, this.getEntity().getPosition().getY()));
          } else if (hitbox.x < 0) {
               this.getEntity().setPosition(new Pair<Float, Float>(0f, this.getEntity().getPosition().getY()));
          } else if (hitbox.y > TILES_HEIGHT - 1) {
               this.getEntity().setPosition(
                         new Pair<Float, Float>(this.getEntity().getPosition().getX(), (float) TILES_HEIGHT - 1));
          } else if (hitbox.y < 0) {
               this.getEntity().setPosition(new Pair<Float, Float>(this.getEntity().getPosition().getX(), 0f));
          }
     }

     private void initHitbox() {
          this.width = (int) TILES_SIZE;
          this.height = this.width;
          hitbox = new Rectangle2D.Float(x, y, this.width, this.height);
     }
}
