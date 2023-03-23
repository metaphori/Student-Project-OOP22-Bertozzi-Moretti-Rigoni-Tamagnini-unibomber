package it.unibo.unibomber.game.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Menu;
import it.unibo.unibomber.game.model.impl.MenuButtonImpl;
import it.unibo.unibomber.utilities.UploadRes;
import it.unibo.unibomber.utilities.Constants;

/**
 * Menu view class.
 */
public final class MenuView implements GameLoop {

    private final Menu controller;
    private BufferedImage backgroundImage;
    private int menuWidth, menuHeight;

    /**
     * @param controller
     */
    public MenuView(final Menu controller) {
        this.controller = controller;
        loadBackground();
    }

    @Override
    public void update() {
        for (final MenuButtonImpl mb : controller.getButtons()) {
            mb.update();
        }
    }

    private void loadBackground() {
        backgroundImage = UploadRes.getSpriteAtlas(Constants.UI.SpritesMap.MENU_BACKGROUND);
        menuWidth = (int) (Constants.UI.Screen.getgWidth());
        menuHeight = (int) (Constants.UI.Screen.getgHeight());
    }

    @Override
    public void draw(final Graphics g) {
        g.drawImage(backgroundImage, 0, 0, menuWidth, menuHeight, null);

        for (final MenuButtonImpl mb : controller.getButtons()) {
            mb.draw(g);
        }
    }
}
