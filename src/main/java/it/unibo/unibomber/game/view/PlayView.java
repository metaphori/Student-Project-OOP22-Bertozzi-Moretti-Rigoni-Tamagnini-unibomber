package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.util.stream.Collectors;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Play;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.ecs.impl.MovementComponent;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.UploadRes;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap.*;
import static it.unibo.unibomber.utilities.Constants.Player.*;

public class PlayView  implements GameLoop{

    Play controller; 
	private int animationIndex = 0;
	private BufferedImage[][] animations;
	private int playerAction = STANDING;

    public PlayView(Play controller){
        this.controller=controller;
        loadAnimations();
    }

    private void loadAnimations() {
		animations = new BufferedImage[1][3];
		for (int j = 0; j < animations.length; j++) {
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = UploadRes.GetSpriteAtlas(spritesPath.get(Type.PLAYABLE)).getSubimage(i * 16, j * 16, 16, 16);
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
		.getFrameInDirection();
	}

    @Override
    public void update() {
        updateAnimationFrame();
    }

    @Override
    public void draw(Graphics g) {
        if(animationIndex % 20 == 0){
        for(int i = 0; i<controller.getEntities().size();i++) {
            if(controller.getEntities().get(i).getType()!=Type.PLAYABLE) {
                g.drawImage(UploadRes.GetSpriteAtlas(spritesPath.get(controller.getEntities().get(i).getType())),
                Math.round(controller.getEntities().get(i).getPosition().getX() * Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE),
                Math.round(controller.getEntities().get(i).getPosition().getY() * Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE),
                (int)(Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE), 
                (int)(Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE), 
                null);
            }
        }
        Entity player = controller.getEntities().stream()
            .filter(x -> x.getType()==Type.PLAYABLE)
            .collect(Collectors.toList()).get(0);
            g.drawImage((animations[playerAction][animationIndex % 3]),
            Math.round(player.getPosition().getX()* Constants.UI.Game.TILES_DEFAULT),
            Math.round(player.getPosition().getY()* Constants.UI.Game.TILES_DEFAULT),
            (int)(Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE), 
            (int)(Constants.UI.Game.TILES_DEFAULT * Constants.UI.Game.SCALE), 
            null);
        }
    }
}
