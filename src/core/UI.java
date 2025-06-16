package core;

import render.Renderable;
import render.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI implements Renderable {
    private final int layer = 100; // High layer to render on top
    private static boolean gameOver = false;
    private static boolean gamePaused = false; // Add pause state
    private static boolean gameNotStarted = true; // Add not started state

    public UI() {
        Renderer.addRenderableObject(this);
    }

    public static void setGameOver(boolean gameOver) {
        UI.gameOver = gameOver;
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public static void setGamePaused(boolean paused) {
        UI.gamePaused = paused;
    }

    public static boolean isGamePaused() {
        return gamePaused;
    }
    public static void setGameNotStarted(boolean notStarted) {
        UI.gameNotStarted = notStarted;
    }
    public static boolean isGameNotStarted() {
        return gameNotStarted;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getWidth() {
        return Window.getWinWidth();
    }

    @Override
    public double getHeight() {
        return Window.getWinHeight();
    }

    @Override
    public boolean drawCollisionBox() {
        return false;
    }

    @Override
    public BufferedImage getBufferedImage() {
        return null; // We'll override drawSprite instead
    }

    @Override
    public void drawSprite(Graphics2D g) {
        if (gameOver) {
            // Draw semi-transparent overlay
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, (int)Window.getWinWidth(), (int)Window.getWinHeight());

            // Draw GAME OVER text
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.setColor(Color.RED);
            FontMetrics fm = g.getFontMetrics();
            String gameOverText = "GAME OVER";
            int textWidth = fm.stringWidth(gameOverText);
            g.drawString(gameOverText,
                (int)(Window.getWinWidth() - textWidth) / 2,
                (int)Window.getWinHeight() / 2 - 50);

            // Draw final score
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.setColor(Color.WHITE);
            fm = g.getFontMetrics();
            String scoreText = "Final Score: " + Score.getCurrentScore();
            textWidth = fm.stringWidth(scoreText);
            g.drawString(scoreText,
                (int)(Window.getWinWidth() - textWidth) / 2,
                (int)Window.getWinHeight() / 2);

            // Draw restart instruction
            g.setFont(new Font("Arial", Font.BOLD, 20));
            fm = g.getFontMetrics();
            String restartText = "Press ENTER to restart";
            textWidth = fm.stringWidth(restartText);
            g.drawString(restartText,
                (int)(Window.getWinWidth() - textWidth) / 2,
                (int)Window.getWinHeight() / 2 + 50);
        } else if (gamePaused) {
            // Draw pause screen
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, (int)Window.getWinWidth(), (int)Window.getWinHeight());

            // Draw PAUSED text
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.setColor(Color.YELLOW);
            FontMetrics fm = g.getFontMetrics();
            String pausedText = "PAUSED";
            int textWidth = fm.stringWidth(pausedText);
            g.drawString(pausedText,
                (int)(Window.getWinWidth() - textWidth) / 2,
                (int)Window.getWinHeight() / 2 - 25);

            // Draw resume instruction
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            fm = g.getFontMetrics();
            String resumeText = "Press ESC to resume";
            textWidth = fm.stringWidth(resumeText);
            g.drawString(resumeText,
                (int)(Window.getWinWidth() - textWidth) / 2,
                (int)Window.getWinHeight() / 2 + 25);
        } else if (gameNotStarted) {
            // Draw only a light overlay and instructions, so game background is visible
            g.setColor(new Color(0, 0, 0, 80));
            g.fillRect(0, 0, (int)Window.getWinWidth(), (int)Window.getWinHeight());

            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.setColor(Color.CYAN);
            FontMetrics fm = g.getFontMetrics();
            String titleText = "SPACE WAR";
            int textWidth = fm.stringWidth(titleText);
            g.drawString(titleText,
                (int)(Window.getWinWidth() - textWidth) / 2,
                (int)Window.getWinHeight() / 2 - 50);

            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.setColor(Color.WHITE);
            fm = g.getFontMetrics();
            String startText = "Press ENTER to start";
            textWidth = fm.stringWidth(startText);
            g.drawString(startText,
                (int)(Window.getWinWidth() - textWidth) / 2,
                (int)Window.getWinHeight() / 2);

            String highScoreText = "High Score: " + Score.getHighScore();
            textWidth = fm.stringWidth(highScoreText);
            g.drawString(highScoreText,
                (int)(Window.getWinWidth() - textWidth) / 2,
                (int)Window.getWinHeight() / 2 + 40);
        } else {
            // Normal UI display
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.setColor(Color.WHITE);

            // Draw current score
            g.drawString("Score: " + Score.getCurrentScore(), 10, 25);

            // Draw current level
            g.drawString("Level: " + Score.getCurrentLevel(), 10, 45);

            // Draw progress to next level (if not in endless mode)
            if (!Score.isEndlessMode()) {
                int remaining = Score.getScoreToNextLevel() - Score.getCurrentScore();
                g.drawString("Next Level: " + remaining, 10, 65);
            } else {
                g.drawString("ENDLESS MODE", 10, 65);
            }

            // Draw high score
            g.drawString("High Score: " + Score.getHighScore(), 10, 85);

            // Draw pause instruction
            g.drawString("Press ESC to pause", 10, 105);
        }
    }
}