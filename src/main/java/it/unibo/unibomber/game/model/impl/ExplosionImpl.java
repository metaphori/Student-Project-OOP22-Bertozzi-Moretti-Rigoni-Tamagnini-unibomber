package it.unibo.unibomber.game.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.UploadRes;
import it.unibo.unibomber.utilities.Constants.Explode;
import it.unibo.unibomber.utilities.Constants.UI.Screen;
import it.unibo.unibomber.utilities.Constants.UI.SpritesMap;

/**
 * Explosion Model.
 */
public class ExplosionImpl {
    private BufferedImage[][] animations;

    private int indexDirection;
    private List<Entity> explode;

    /**
     * @param explode list of explosion.
     */
    public void setExplode(final List<Entity> explode) {
        this.explode = new ArrayList<>(explode);
    }

    /**
     * ExplosionImpl.
     */
    public ExplosionImpl() {
        loadSprites();
        explode = new ArrayList<>();
        indexDirection = 8;
    }

    private void loadSprites() {
        animations = new BufferedImage[SpritesMap.ROW_EXPLOSION_SPRITES][SpritesMap.COL_EXPLOSION_SPRITES];
        for (Integer j = 0; j < SpritesMap.ROW_EXPLOSION_SPRITES; j++) {
            for (Integer i = 0; i < animations[j].length; i++) {
                animations[j][i] = UploadRes.getSpriteAtlas("bomb/explosion.png").getSubimage(
                        i * Screen.EXPLOSION_DEFAULT, j * Screen.EXPLOSION_DEFAULT,
                        Screen.EXPLOSION_DEFAULT, Screen.EXPLOSION_DEFAULT);
            }
        }
    }

    /**
     * @param i row.
     * @param j col.
     * @return animation of that position.
     */
    public BufferedImage getAnimations(final int i, final int j) {
        return animations[i][j];
    }

    /**
     * @return direction of explosion.
     */
    public int getIndexDirection() {
        return indexDirection;
    }

    /**
     * set direciont of explosion.
     * 
     * @param dir direction explosion.
     */
    public void setDirectionIndex(final Direction dir) {
        switch (dir) {
            case DOWN:
                indexDirection = Explode.UP_EXPLOSION_ANIMATION_INDEX;
                break;
            case UP:
                indexDirection = Explode.DOWN_EXPLOSION_ANIMATION_INDEX;
                break;
            case RIGHT:
                indexDirection = Explode.RIGHT_EXPLOSION_ANIMATION_INDEX;
                break;
            case LEFT:
                indexDirection = Explode.LEFT_EXPLOSION_ANIMATION_INDEX;
                break;
            default:
                indexDirection = Explode.CENTER_EXPLOSION_ANIMATION_INDEX;
                break;
        }
    }

    /**
     * @return Explode List.
     */
    public List<Entity> getExplode() {
        return new ArrayList<>(explode);
    }

    /**
     * Set entity that is exploding.
     * 
     * @param entity bomb exploding.
     */
    public void setEntityExploding(final Entity entity) {
        this.explode.add(entity);
    }

    /**
     * @param id id of bomb.
     * @return entity of that id.
     */
    public Entity gEntity(final int id) {
        return explode.get(id);
    }

    /**
     * reset explosion list.
     */
    public void resetEntity() {
        this.explode = new ArrayList<>();
    }

}
