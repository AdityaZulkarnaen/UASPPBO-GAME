package core;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Score {
    private static int currentScore = 0;
    private static int currentLevel = 1;
    private static int scoreToNextLevel = 1000;
    private static int baseScorePerLevel = 1000;
    private static ArrayList<HighScoreEntry> highScores = new ArrayList<>();
    private static final String HIGHSCORE_FILE = "highscores.txt";
    private static final int MAX_HIGHSCORES = 10;
    private static final String DELIMITER = ",";

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
            System.out.println("Level Up! Now at Level " + currentLevel);
        }
    }

    public static void resetGame() {
        currentScore = 0;
        currentLevel = 1;
        scoreToNextLevel = baseScorePerLevel;
    }


    public static void gameOver(String playerName) {
        if (currentScore > 0) {
            addHighScore(playerName, currentScore);
            saveHighScores();
        }
    }

    private static void addHighScore(String playerName, int score) {
        highScores.add(new HighScoreEntry(playerName, score));
        Collections.sort(highScores);
        if (highScores.size() > MAX_HIGHSCORES) {
            highScores.remove(highScores.size() - 1);
        }
    }

    private static void loadHighScores() {
        highScores.clear();
         try (BufferedReader reader = new BufferedReader(new FileReader(HIGHSCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                if (parts.length == 2) {
                    try {
                        String name = parts[0].trim();
                        int score = Integer.parseInt(parts[1].trim());
                        highScores.add(new HighScoreEntry(name, score));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping malformed high score line (score not a number): " + line);
                    }
                } else {
                    System.err.println("Skipping malformed high score line (wrong format): " + line);
                }
            }
            Collections.sort(highScores);
        } catch (IOException e) {
            System.out.println("High score file not found or could not be read. Starting with empty high scores.");
        }
    }

    private static void saveHighScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HIGHSCORE_FILE))) {
            for (HighScoreEntry entry : highScores) {
                writer.println(entry.getPlayerName() + DELIMITER + entry.getScore());
            }
        } catch (IOException e) {
            System.err.println("Could not save high scores: " + e.getMessage());
        }
    }

    public static int getCurrentScore() {
        return currentScore;
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static int getScoreToNextLevel() {
        return scoreToNextLevel;
    }

    public static ArrayList<HighScoreEntry> getHighScores() {
        return new ArrayList<>(highScores);
    }

    public static boolean isEndlessMode() {
        return currentLevel >= 5;
    }

    public static int getHighScore() {
        return highScores.isEmpty() ? 0 : highScores.get(0).getScore();
    }

    public static HighScoreEntry getTopHighScoreEntry() {
        return highScores.isEmpty() ? null : highScores.get(0);
    }
}