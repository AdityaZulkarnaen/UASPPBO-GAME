package core;

import object.Spaceship;
import render.Renderer;

public class Entry {
    public static void main(String[] args) throws Exception {
        Window window = new Window("Space War", Window.getWinWidth(), Window.getWinHeight());
        Renderer renderer = new Renderer();

        window.addKeyListener(new Input());
        window.add(renderer);
        window.packWindow();
        window.setVisible(true);

        boolean runGame = true;

        new Spaceship(100,100);

        FPS.calcBeginTime();
        while (runGame) {
            renderer.repaint();
            //recalculate delta time
            FPS.calcDeltaTime();
        }

    }
}
