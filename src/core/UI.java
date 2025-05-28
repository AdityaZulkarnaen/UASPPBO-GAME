package core;

import render.Renderable;
import render.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI implements Renderable {
    private final int layer = 100; // High layer to render on top

    public UI() {
        Renderer.addRenderableObject(this);
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
        // Set font and color
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