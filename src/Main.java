import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import core.FPS;
import core.Input;
import core.UI;
import core.Window;
import object.AsteroidSpawner;
import object.Background;
import object.EnemySpawner;
import object.Spaceship;
import render.Renderer;
import update.Updater;

public class Main {
    public static void main(String[] args) throws Exception, UnsupportedAudioFileException, LineUnavailableException {
        Window window = new Window("Space War", Window.getWinWidth(), Window.getWinHeight());
        Renderer renderer = new Renderer();

        window.addKeyListener(new Input());
        window.add(renderer);
        window.packWindow();
        window.setVisible(true);

        boolean runGame = true;

        new Spaceship(Window.getWinWidth() / 2 - (Spaceship.width / 2),Window.getWinHeight() - 150);
        new Background(0);
        new Background(-Window.getWinHeight());
        new AsteroidSpawner();
        new EnemySpawner(); // Add enemy spawner
        new UI(); // Add UI for score display

        FPS.calcBeginTime();
        while (runGame) {
            Updater.update();
            renderer.repaint();
            //recalculate delta time
            FPS.calcDeltaTime();
        }

    }
}