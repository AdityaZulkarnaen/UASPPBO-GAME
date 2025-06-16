package core;

import object.*;
import render.Renderer;
import update.Updater;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import javax.swing.JOptionPane;

public class GameManager {
    private static boolean gameRunning = true;
    private static boolean gamePaused = false;
    private static boolean enterPressed = false;
    private static boolean escPressed = false;
    private static boolean gameNotStarted = true;

    public static void setGameRunning(boolean running) {
        gameRunning = running;
    }

    public static boolean isGameRunning() {
        return gameRunning && !gamePaused;
    }

    public static boolean isGamePaused() {
        return gamePaused;
    }
    public static boolean isGameNotStarted() {
        return gameNotStarted;
    }

    public static void setGamePaused(boolean paused) {
        gamePaused = paused;
        UI.setGamePaused(paused);
    }

    public static void handleGameOver() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        gameRunning = false;
        gamePaused = false;
        UI.setGameOver(true);

        String playerName = getPlayerNameInput();
        Score.gameOver(playerName);

        Sound.playSound("res/crushed.wav");

        System.out.println("Game Over! Final Score: " + Score.getCurrentScore());
        System.out.println("High Scores:");
        for (int i = 0; i < Math.min(5, Score.getHighScores().size()); i++) {
            HighScoreEntry entry = Score.getHighScores().get(i);
            System.out.println((i + 1) + ". " + entry.getPlayerName() + ": " + entry.getScore());
        }
    }


    private static String getPlayerNameInput() {
        String name = JOptionPane.showInputDialog(null, "Enter your name for the high score:", "Game Over", JOptionPane.PLAIN_MESSAGE);
        if (name == null || name.trim().isEmpty()) {
            return "Player";
        }
        return name;
    }

    public static void checkRestart() throws Exception {
        if (!gameRunning && Input.keys[Input.ENTER] && !enterPressed && !gameNotStarted) {
            enterPressed = true;
            restartGame();
        }
        if (gameNotStarted && Input.keys[Input.ENTER] && !enterPressed) {
            enterPressed = true;
            gameRunning = true;
            gameNotStarted = false;
            UI.setGameNotStarted(false);
        }

        if (!Input.keys[Input.ENTER]) {
            enterPressed = false;
        }


        if (gameRunning && Input.keys[Input.ESC] && !escPressed) {
            escPressed = true;
            togglePause();
        }

        if (!Input.keys[Input.ESC]) {
            escPressed = false;
        }
    }

    private static void togglePause() {
        gamePaused = !gamePaused;
        UI.setGamePaused(gamePaused);
        System.out.println(gamePaused ? "Game Paused" : "Game Resumed");
    }

    private static void restartGame() throws Exception {
        System.out.println("Restarting game...");
        UI.setGameOver(false);
        initializeGame();
        gameRunning = true;
        gamePaused = false;
        enterPressed = false;
        escPressed = false;
        gameNotStarted = false;
        UI.setGameNotStarted(false);
        Score.resetGame();
        UI.setGamePaused(false);
    }
    public static void initializeGame() throws Exception {
        gameRunning = false;
        gamePaused = false;
        enterPressed = false;
        escPressed = false;
        gameNotStarted = true;

        Updater.clearAll();
        Renderer.clearAll();

        new Spaceship(Window.getWinWidth() / 2 - (Spaceship.width / 2), Window.getWinHeight() - 150);
        new Background(0);
        new Background(-Window.getWinHeight());
        new AsteroidSpawner();
        new EnemySpawner();
        new UI();
        Score.resetGame();
        System.out.println("Game Initialized!");
    }
}