package core;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import object.AsteroidSpawner;
import object.Spaceship;

public class Score {
    private static int currentScore = 0;
    private static int currentLevel = 1;
    private static int scoreToNextLevel = 1000; // Score needed to advance to next level
    private static int baseScorePerLevel = 1000;
    private static ArrayList<Integer> highScores = new ArrayList<>();
    private static final String HIGHSCORE_FILE = "highscores.txt";
    private static final int MAX_HIGHSCORES = 10;

    static {
        loadHighScores();
    }

    public static void addScore(int points) {
        currentScore += points;
        checkLevelUp();
    }

    public static void checkLevelUp() {
        if (currentLevel < 5 && currentScore >= scoreToNextLevel) {
            currentLevel++;
            scoreToNextLevel = baseScorePerLevel * currentLevel;
            AsteroidSpawner.minSpeed();
            Spaceship.minSpeed();
            System.out.println("Level Up! Now at Level " + currentLevel);
        }
    }

    public static void resetGame() {
        currentScore = 0;
        currentLevel = 1;
        scoreToNextLevel = baseScorePerLevel;
    }

    public static void gameOver() {
        if (currentScore > 0) {
            addHighScore(currentScore);
            saveHighScores();
        }
    }

    private static void addHighScore(int score) {
        highScores.add(score);
        Collections.sort(highScores, Collections.reverseOrder());
        if (highScores.size() > MAX_HIGHSCORES) {
            highScores.remove(highScores.size() - 1);
        }
    }

    private static void loadHighScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGHSCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    highScores.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {
                    // Skip invalid lines
                }
            }
            Collections.sort(highScores, Collections.reverseOrder());
        } catch (IOException e) {
            // File doesn't exist or can't be read, start with empty list
        }
    }

    private static void saveHighScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HIGHSCORE_FILE))) {
            for (int score : highScores) {
                writer.println(score);
            }
        } catch (IOException e) {
            System.err.println("Could not save high scores: " + e.getMessage());
        }
    }

    // Getters
    public static int getCurrentScore() {
        return currentScore;
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static int getScoreToNextLevel() {
        return scoreToNextLevel;
    }

    public static ArrayList<Integer> getHighScores() {
        return new ArrayList<>(highScores);
    }

    public static boolean isEndlessMode() {
        return currentLevel >= 5;
    }

    public static int getHighScore() {
        return highScores.isEmpty() ? 0 : highScores.get(0);
    }
}