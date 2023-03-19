package it.unibo.unibomber.game.controller.impl;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import it.unibo.unibomber.game.controller.api.GameLoop;
import it.unibo.unibomber.game.model.api.Gamestate;
import it.unibo.unibomber.game.view.PauseView;

/**
 * Manages the pause of the game.
 */
public final class Pause implements MouseListener, KeyListener, GameLoop {
    private final PauseView view;

    /**
     * Constructor.
     */
    public Pause() {
        super();
        view = new PauseView();
    }

    @Override
    public void update() {
        view.update();
    }

    @Override
    public void draw(final Graphics g) {
        view.draw(g);
    }

    @Override
    public void keyPressed(final KeyEvent e) {
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Gamestate.setGameState(Gamestate.PLAY);
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        // TODO Auto-generated method stub
    }

}
