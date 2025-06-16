import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import core.FPS;
import core.Input;
import core.UI;
import core.Window;
import core.GameManager;
import object.AsteroidSpawner;
import object.Background;
import object.EnemySpawner;
import object.Spaceship;
import render.Renderer;
import update.Updater;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception, UnsupportedAudioFileException, LineUnavailableException, IOException {
        Window window = new Window("Space War", Window.getWinWidth(), Window.getWinHeight());
        Renderer renderer = new Renderer();
        Updater updater = new Updater();

        window.addKeyListener(new Input());
        window.add(renderer);
        window.packWindow();
        window.setVisible(true);

        new Spaceship(Window.getWinWidth() / 2 - (Spaceship.width / 2), Window.getWinHeight() - 150);
        new Background(0);
        new Background(-Window.getWinHeight());
        new AsteroidSpawner();
        new EnemySpawner();
        new UI();

        // Mulai loop game
        FPS.calcBeginTime();
        while (true) {
            GameManager.checkRestart();
            if (GameManager.isGameRunning()) {
                updater.update();
            }
            renderer.repaint();

            FPS.calcDeltaTime();
        }
    }
}