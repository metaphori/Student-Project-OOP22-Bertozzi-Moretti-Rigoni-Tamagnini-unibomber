package it.unibo.unibomber.game.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.controller.impl.Option;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.model.impl.MenuButtonImpl;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.UploadRes;
import it.unibo.unibomber.utilities.Constants.UI.Game;

/**
 * Option view class.
 */
public class OptionView implements GameLoop {
    private final Option controller;
    private BufferedImage delete, deleteAll, map;
    private int menuWidth, menuHeight;

    /**
     * @param controller
     */
    public OptionView(final Option controller) {
        this.controller = controller;
        loadBackground();
    }

    private void loadBackground() {
        map = UploadRes.getSpriteAtlas("menu/map.png");
        delete = UploadRes.getSpriteAtlas("menu/delete.png");
        deleteAll = UploadRes.getSpriteAtlas("menu/delete_all.png");
        menuWidth = 50;
        menuHeight = 40;
    }

    @Override
    public void update() {
        for (final MenuButtonImpl mb : controller.getButtons()) {
            mb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //background
        g2.setColor(new Color(255, 255, 156));
        g2.fillRect(0, 0, Constants.UI.Game.getgWidth(), Constants.UI.Game.getgHeight());
        //map
        g.drawImage(map, Constants.UI.Game.getgWidth() / 4, 10, 250, 250, null);
        g.drawImage(UploadRes.getSpriteAtlas("menu/left.png"), Constants.UI.Game.getgWidth() / 4 -50, (map.getHeight()/2)-20, 50, 50, null);
        g.drawImage(UploadRes.getSpriteAtlas("menu/right.png"), Constants.UI.Game.getgWidth() / 4 + map.getWidth() - 50,(map.getHeight()/2)-20, 50, 50, null);
        //rect power up
        g2.setColor(new Color(214, 214, 214));
        g2.fillRoundRect(20, Constants.UI.Game.getgHeight() - 80, Constants.UI.Game.getgWidth() - 200, 50, 20, 20);
        g.drawImage(UploadRes.getSpriteAtlas("menu/player.png"), 20, Constants.UI.Game.getgHeight() / 2, null);
        g.drawImage(UploadRes.getSpriteAtlas("menu/bot.png"), 20, Constants.UI.Game.getgHeight() / 2 + 100, null);
        g.drawImage(delete, Constants.UI.Game.getgWidth() - 300, Constants.UI.Game.getgHeight() - 75, menuWidth,
                menuHeight, null);
        g.drawImage(deleteAll, Constants.UI.Game.getgWidth() - 245, Constants.UI.Game.getgHeight() - 75, menuWidth,
                menuHeight, null);
        g.drawImage(UploadRes.getSpriteAtlas("menu/ok.png"), Constants.UI.Game.getgWidth() - 150,
                Constants.UI.Game.getgHeight() - 80, menuWidth + 10,
                menuHeight + 10, null);
        // power up
        g.drawImage(Constants.UI.SpritesMap.SPRITESPOWERUPPATH.get(PowerUpType.FIREUP),
                30, Constants.UI.Game.getgHeight() - 75, menuHeight,
                menuHeight, null);
        g.drawImage(Constants.UI.SpritesMap.SPRITESPOWERUPPATH.get(PowerUpType.BOMBUP),
                30 + (int) (Game.getTilesDefault() * Game.SCALE) * 1, Constants.UI.Game.getgHeight() - 75, menuHeight,
                menuHeight, null);
        g.drawImage(Constants.UI.SpritesMap.SPRITESPOWERUPPATH.get(PowerUpType.SPEEDUP),
                30 + (int) (Game.getTilesDefault() * Game.SCALE) * 2, Constants.UI.Game.getgHeight() - 75, menuHeight,
                menuHeight, null);
        g.drawImage(Constants.UI.SpritesMap.SPRITESPOWERUPPATH.get(PowerUpType.KICKBOMB),
                30 + (int) (Game.getTilesDefault() * Game.SCALE) * 3, Constants.UI.Game.getgHeight() - 75, menuHeight,
                menuHeight, null);
        g.drawImage(Constants.UI.SpritesMap.SPRITESPOWERUPPATH.get(PowerUpType.THROWBOMB),
                30 + (int) (Game.getTilesDefault() * Game.SCALE) * 4, Constants.UI.Game.getgHeight() - 75, menuHeight,
                menuHeight, null);

        for (final MenuButtonImpl mb : controller.getButtons()) {
            mb.draw(g);
        }
    }
}
