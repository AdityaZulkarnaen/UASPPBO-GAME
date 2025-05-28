package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public static final int SPACE = 4;
    public static final int ENTER = 5;
    public static final int ESC = 6; // Add ESC key

    public static boolean[] keys = new boolean[7]; // Increase array size

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[LEFT] = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[RIGHT] = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            keys[UP] = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[DOWN] = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[SPACE] = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            keys[ENTER] = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // Add ESC key handling
            keys[ESC] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[LEFT] = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[RIGHT] = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            keys[UP] = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[DOWN] = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[SPACE] = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            keys[ENTER] = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // Add ESC key handling
            keys[ESC] = false;
        }
    }
}
