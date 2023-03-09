package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.util.stream.Collectors;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Play;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.game.ecs.impl.PowerUpComponent;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.UploadRes;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap.*;
import static it.unibo.unibomber.utilities.Constants.Player.*;

public class PlayView  implements GameLoop{

    Play controller; 
	private Integer animationIndex = 0;
	private BufferedImage[][] animations;
	private Integer playerAction = STANDING;
    static Integer indexDir = 0;
    public PlayView(Play controller){
        this.controller=controller;
        loadSprites();
        /*animations[0][1] = colorImage(animations[0][1]);
        animations[0][2] = colorImage(animations[0][2]);
        animations[0][3] = colorImage(animations[0][3]);*/
    }

    private void loadSprites() {
		animations = new BufferedImage[2][24];
		for (Integer j = 0; j < animations.length; j++) {
			for (Integer i = 0; i < animations[j].length; i++) {
				animations[j][i] = UploadRes.GetSpriteAtlas(spritesPath.get(Type.PLAYABLE)).getSubimage(i * 48, j * 48, 48, 48);
			}
		}
	}

    private void updateAnimationFrame() {
		animationIndex = controller.getEntities().stream()
		.filter(x -> x.getType() == Type.PLAYABLE)
		.collect(Collectors.toList())
		.get(0)
		.getComponent(MovementComponent.class)
		.get()
		.getPassedFram();
	}

    @Override
    public void update() {
        updateAnimationFrame();
    }

    public void changePlayerAction(Integer action){
        if(action==STANDING){
            Integer animation =(animationIndex % Constants.Player.GetSpriteAmount(playerAction))+indexDir;
            Integer basicDir=(int)(animation/Constants.Player.GetSpriteAmount(playerAction));
            indexDir=basicDir*Constants.Player.GetSpriteAmount(action);
        }
        playerAction=action;
    }

    private static BufferedImage colorImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();
        for (int xx = 0; xx < width; xx++) {
          for (int yy = 0; yy < height; yy++) {
            int[] pixels = raster.getPixel(xx, yy, (int[]) null);
            if((pixels[0]>=180 && pixels[0]<=255)
            && (pixels[1]>=150 && pixels[1]<=255)
            && (pixels[2]>=150 && pixels[2]<=255)){
                pixels[0]=0;
                pixels[1]=255;
                pixels[2]=255;
                raster.setPixel(xx, yy, pixels);
            }
          }
        }
        return image;
    }

    @Override
    public void draw(Graphics g) {
        for(Integer i = 0; i<controller.getEntities().size();i++) {
            if(controller.getEntities().get(i).getType() != Type.PLAYABLE && controller.getEntities().get(i).getType() != Type.POWERUP) {
                g.drawImage(UploadRes.GetSpriteAtlas(spritesPath.get(controller.getEntities().get(i).getType())),
                Math.round(controller.getEntities().get(i).getPosition().getX() * Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE),
                Math.round(controller.getEntities().get(i).getPosition().getY() * Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE),
                (int)(Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE), 
                (int)(Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE), 
                null);
            }
            else if(controller.getEntities().get(i).getType() == Type.POWERUP){
                g.drawImage(UploadRes.GetSpriteAtlas(spritesPoweUpPath.get(controller.getEntities().get(i).getComponent(PowerUpComponent.class).get().getPowerUpType())),
                Math.round(controller.getEntities().get(i).getPosition().getX() * Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE),
                Math.round(controller.getEntities().get(i).getPosition().getY() * Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE),
                (int)(Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE), 
                (int)(Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE), 
                null);
            }
            else if(controller.getEntities().get(i).getType() == Type.PLAYABLE){
                switch(controller.getEntities().get(i).getComponent(MovementComponent.class).get().getDirection()){
                    case DOWN:
                        indexDir=0;
                        break;
                    case LEFT:
                        indexDir=Constants.Player.GetSpriteAmount(playerAction)*1;
                    break;
                    case RIGHT:
                        indexDir=Constants.Player.GetSpriteAmount(playerAction)*2;
                        break;
                    case UP:
                        indexDir=Constants.Player.GetSpriteAmount(playerAction)*3;
                        break;
                    case CENTER:
                        break;
                    default:
                        break; 
                }
                g.drawImage((animations[playerAction][(animationIndex % Constants.Player.GetSpriteAmount(playerAction))+indexDir]),
                Math.round(controller.getEntities().get(i).getPosition().getX()* Constants.UI.Game.TILES_DEFAULT),
                Math.round(controller.getEntities().get(i).getPosition().getY()* Constants.UI.Game.TILES_DEFAULT),
                (int)(Constants.UI.Game.TILES_DEFAULT * (Constants.UI.Game.SCALE+0.5f)), 
                (int)(Constants.UI.Game.TILES_DEFAULT * (Constants.UI.Game.SCALE+0.5f)), 
                null);
            }
        }         
    }
}
