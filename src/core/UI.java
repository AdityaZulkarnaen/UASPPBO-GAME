package core;

import render.Renderable;
import render.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI implements Renderable {
    private final int layer = 100; // High layer to render on top
    private static boolean gameOver = false;

    public UI() {
        Renderer.addRenderableObject(this);
    }

    public static void setGameOver(boolean gameOver) {
        UI.gameOver = gameOver;
    }

    public static boolean isGameOver() {
        return gameOver;
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
        }
    }
}