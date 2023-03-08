package it.unibo.unibomber.game.model.impl;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.api.MenuButton;
import it.unibo.unibomber.utilities.UploadRes;
import static it.unibo.unibomber.utilities.Constants.UI.Buttons.*;
import static it.unibo.unibomber.utilities.Constants.UI.SpritesMap.*;

public class MenuButtonImpl implements MenuButton, GameLoop{
    private int x, Y, rowIndex, index;
	private int x_button_position = B_WIDTH / 2;
	private Gamestate game_state;
	private BufferedImage[] buffer_images;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;

	public MenuButtonImpl(int x, int Y, int rowIndex, Gamestate game_state) {
		this.x = x;
		this.Y = Y;
		this.rowIndex = rowIndex;
		this.game_state = game_state;
		loadbuffer_images();
		bounds = new Rectangle(x - x_button_position, Y, B_WIDTH, B_HEIGHT);
	}

	private void loadbuffer_images() {
		buffer_images = new BufferedImage[3];
		BufferedImage temp = UploadRes.GetSpriteAtlas(MENU_BUTTONS);
		for (int i = 0; i < buffer_images.length; i++)
			buffer_images[i] = temp.getSubimage(i * WIDTH_DEFAULT, rowIndex * HEIGHT_DEFAULT, WIDTH_DEFAULT, HEIGHT_DEFAULT);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(buffer_images[index], x - x_button_position, Y, B_WIDTH, B_HEIGHT, null);
	}

	@Override
	public void update() {
	}

	@Override
	public boolean isMouseOver() {
		return mouseOver;
	}

	@Override
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	@Override
	public boolean isMousePressed() {
		return mousePressed;
	}

	@Override
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void applyGamestate() {
		Gamestate.state = game_state;
	}

	@Override
	public void reset() {
		mouseOver = false;
		mousePressed = false;
	}
}
