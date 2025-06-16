import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import core.FPS;
import core.Input;
import core.Window;
import core.GameManager;
import render.Renderer;
import update.Updater;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception, UnsupportedAudioFileException, LineUnavailableException, IOException {
        Window window = new Window("Space War", Window.getWinWidth(), Window.getWinHeight());
        Renderer renderer = new Renderer();

        window.addKeyListener(new Input());
        window.add(renderer);
        window.packWindow();
        window.setVisible(true);


        //inisialisasi game
        GameManager.initializeGame();

        // Mulai loop game
        FPS.calcBeginTime();
        while (true) {
            GameManager.checkRestart();
            if (GameManager.isGameRunning()  && !GameManager.isGamePaused() && !GameManager.isGameNotStarted()) {
                Updater.update();
            }
            renderer.repaint();

            FPS.calcDeltaTime();
        }
    }
}