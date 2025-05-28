package object;

import core.FPS;
import core.Timer;
import core.Window;
import render.Renderable;
import render.Renderer;
import update.Updatable;
import update.Updater;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class EnemySpaceship implements Renderable, Updatable {
    private static double width = 60;
    private static double height = 60;
    private double x;
    private double y;

    private final int layer = 2;

    private static BufferedImage enemyShip;

    private double speed = 100;
    private double direction = 1; // 1 for right, -1 for left
    private int movementPattern;
    private double originalX;
    private double oscillationTime = 0;

    private static int shootTimerMillis = 1000;
    Timer shootTimer = new Timer(shootTimerMillis);

    Random rand = new Random();

    public EnemySpaceship() throws IOException {
        // Random spawn position at top of screen
        this.x = rand.nextInt((int)(Window.getWinWidth() - width));
        this.y = -height;
        this.originalX = x;
        
        // Random movement pattern (0: straight down, 1: zigzag, 2: sine wave)
        this.movementPattern = rand.nextInt(3);

        enemyShip = ImageIO.read(new File("res/Enemy.png"));
        Renderer.addRenderableObject(this);
        Updater.addUpdatableObjects(this);
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean drawCollisionBox() {
        return true;
    }

    @Override
    public BufferedImage getBufferedImage() {
        return enemyShip;
    }

    @Override
    public void update() throws IOException {
        // Movement patterns
        switch (movementPattern) {
            case 0: // Straight down
                y += speed * FPS.getDeltaTime();
                break;
                
            case 1: // Zigzag pattern
                y += speed * FPS.getDeltaTime();
                x += direction * speed * 0.5 * FPS.getDeltaTime();
                
                // Change direction when hitting screen edges
                if (x <= 0 || x >= Window.getWinWidth() - width) {
                    direction *= -1;
                }
                break;
                
            case 2: // Sine wave pattern
                y += speed * FPS.getDeltaTime();
                oscillationTime += FPS.getDeltaTime();
                x = originalX + Math.sin(oscillationTime * 3) * 100;
                
                // Keep within screen bounds
                if (x < 0) x = 0;
                if (x > Window.getWinWidth() - width) x = Window.getWinWidth() - width;
                break;
        }

        // Shooting logic
        if (shootTimer.isRinging() && y > 0 && y < Window.getWinHeight() - 100) {
            new EnemyBullet(x + (getWidth() / 2), y + getHeight());
            shootTimer.resetTimer();
        }

        // Remove if off screen
        if (y >= Window.getWinHeight() + height) {
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);
        }

        // Check collision with player bullets
        Updatable collidingBullet = isColliding(this, "bullet");
        if (collidingBullet != null) {
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);
            
            Updater.removeUpdatable(collidingBullet);
            Renderer.removeRenderableObject(collidingBullet.getRenderable());
        }
    }

    @Override
    public String getID() {
        return "enemy";
    }

    @Override
    public Renderable getRenderable() {
        return this;
    }
}