package core;

import object.*;
import render.Renderer;
import update.Updater;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class GameManager {
    private static boolean gameRunning = true;
    private static boolean gamePaused = false; // Add pause state
    private static boolean enterPressed = false;
    private static boolean escPressed = false; // Add ESC key state tracking

    public static void setGameRunning(boolean running) {
        gameRunning = running;
    }

    public static boolean isGameRunning() {
        return gameRunning && !gamePaused; // Game runs only if not paused
    }

    public static boolean isGamePaused() {
        return gamePaused;
    }

    public static void setGamePaused(boolean paused) {
        gamePaused = paused;
        UI.setGamePaused(paused); // Update UI to show pause state
    }

    public static void handleGameOver() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        gameRunning = false;
        gamePaused = false; // Reset pause state when game over
        UI.setGameOver(true);
        
        // Save high score
        Score.gameOver();
        
        Sound.playSound("res/crushed.wav");
        
        System.out.println("Game Over! Final Score: " + Score.getCurrentScore());
        System.out.println("High Scores:");
        for (int i = 0; i < Math.min(5, Score.getHighScores().size()); i++) {
            System.out.println((i + 1) + ". " + Score.getHighScores().get(i));
        }
    }

    public static void checkRestart() throws Exception {
        // Handle restart when game is over
        if (!gameRunning && Input.keys[Input.ENTER] && !enterPressed) {
            enterPressed = true;
            restartGame();
        }
        
        if (!Input.keys[Input.ENTER]) {
            enterPressed = false;
        }

        // Handle pause/unpause when game is running
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
        // Clear all game objects
        Updater.clearAll();
        Renderer.clearAll();
        
        // Reset game state
        gameRunning = true;
        gamePaused = false; // Reset pause state
        UI.setGameOver(false);
        UI.setGamePaused(false); // Reset pause UI
        Score.resetGame();
        
        // Recreate game objects
        new Spaceship(Window.getWinWidth() / 2 - (Spaceship.width / 2), Window.getWinHeight() - 150);
        new Background(0);
        new Background(-Window.getWinHeight());
        new AsteroidSpawner();
        new EnemySpawner();
        new UI();
        
        System.out.println("Game Restarted!");
    }
}