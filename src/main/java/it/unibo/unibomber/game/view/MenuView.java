package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Menu;
import it.unibo.unibomber.game.model.impl.MenuButtonImpl;
import it.unibo.unibomber.utilities.UploadRes;
import it.unibo.unibomber.utilities.Constants;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap.*;

public class MenuView implements GameLoop{
    
    Menu controller; 
    private BufferedImage backgroundImage;
	private int menuWidth, menuHeight;
    
    public MenuView(Menu controller){
        this.controller=controller;
        loadBackground();
    }
    @Override
    public void update() {
        for (MenuButtonImpl mb : controller.getButtons())
			mb.update();
    }
    
    private void loadBackground() {
		backgroundImage = UploadRes.GetSpriteAtlas(MENU_BACKGROUND);
		menuWidth = (int) (Constants.UI.Game.G_WIDTH);
		menuHeight = (int) (Constants.UI.Game.G_HEIGHT);
	}
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, menuWidth, menuHeight, null);

		for (MenuButtonImpl mb : controller.getButtons())
			mb.draw(g);
    }
    
}
