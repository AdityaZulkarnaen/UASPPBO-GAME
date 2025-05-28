package core;

import object.*;
import render.Renderer;
import update.Updater;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class GameManager {
    private static boolean gameRunning = true;
    private static boolean enterPressed = false;

    public static void setGameRunning(boolean running) {
        gameRunning = running;
    }

    public static boolean isGameRunning() {
        return gameRunning;
    }

    public static void handleGameOver() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        gameRunning = false;
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
        if (!gameRunning && Input.keys[Input.ENTER] && !enterPressed) {
            enterPressed = true;
            restartGame();
        }
        
        if (!Input.keys[Input.ENTER]) {
            enterPressed = false;
        }
    }

    private static void restartGame() throws Exception {
        // Clear all game objects
        Updater.clearAll();
        Renderer.clearAll();
        
        // Reset game state
        gameRunning = true;
        UI.setGameOver(false);
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