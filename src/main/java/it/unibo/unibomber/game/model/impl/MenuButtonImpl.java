package it.unibo.unibomber.game.model.impl;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.model.api.MenuButton;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.UploadRes;
/**
 * Menu Button settings implementation class.
 */
public class MenuButtonImpl implements MenuButton, GameLoop {
 private int index;
 private final int x, y, rowIndex;
 private final int xButtonPosition = Constants.UI.Buttons.B_WIDTH / 2;
 private final Gamestate gameState;
 private BufferedImage[] bufferImages;
 private boolean mouseOver, mousePressed;
 private final Rectangle bounds;

 /**
  * @param x
  * @param y
  * @param rowIndex
  * @param gameState
  */
 public MenuButtonImpl(final int x, final int y, final int rowIndex, final Gamestate gameState) {
  this.x = x;
  this.y = y;
  this.rowIndex = rowIndex;
  this.gameState = gameState;
  loadbufferImages();
  bounds = new Rectangle(x - xButtonPosition, y, Constants.UI.Buttons.B_WIDTH, Constants.UI.Buttons.B_HEIGHT);
 }

 private void loadbufferImages() {
  bufferImages = new BufferedImage[3];
  final BufferedImage temp = UploadRes.getSpriteAtlas(Constants.UI.SpritesMap.MENU_BUTTONS);
  for (int i = 0; i < bufferImages.length; i++) {
   bufferImages[i] = temp.getSubimage(i * Constants.UI.Buttons.WIDTH_DEFAULT,
     rowIndex * Constants.UI.Buttons.HEIGHT_DEFAULT, Constants.UI.Buttons.WIDTH_DEFAULT,
     Constants.UI.Buttons.HEIGHT_DEFAULT);
  }
 }

 @Override
 public final void draw(final Graphics g) {
  g.drawImage(bufferImages[index], x - xButtonPosition, y, Constants.UI.Buttons.B_WIDTH,
    Constants.UI.Buttons.B_HEIGHT, null);
 }

 @Override
 public void update() {
 }

 @Override
 public final boolean isMouseOver() {
  return mouseOver;
 }

 @Override
 public final void setMouseOver(final boolean mouseOver) {
  this.mouseOver = mouseOver;
 }

 @Override
 public final boolean isMousePressed() {
  return mousePressed;
 }

 @Override
 public final void setMousePressed(final boolean mousePressed) {
  this.mousePressed = mousePressed;
 }

 @Override
 public final Rectangle getBounds() {
  return bounds;
 }

 @Override
 public final void applyGamestate() {
  Gamestate.setGameState(gameState);
 }

 @Override
 public final void reset() {
  mouseOver = false;
  mousePressed = false;
 }
}
