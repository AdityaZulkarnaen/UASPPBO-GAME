package object;

import core.FPS;
import core.Sound;
import core.Timer;
import core.Window;
import render.Renderer;
import update.Updatable;
import update.Updater;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class EnemySpaceship extends UpdatableRenderableObj {
    private double direction = 1; // 1 for right, -1 for left
    private int movementPattern;
    private double originalX;
    private double oscillationTime = 0;

    private static int shootTimerMillis = 1000;
    Timer shootTimer = new Timer(shootTimerMillis);

    Random rand = new Random();

    public EnemySpaceship() throws IOException {
        // Random spawn position at top of screen
        double x = rand.nextInt((int)(Window.getWinWidth() - 60));
        originalX = x;

        // Random movement pattern (0: straight down, 1: zigzag, 2: sine wave)
        movementPattern = rand.nextInt(3);

        BufferedImage enemyShip = ImageIO.read(new File("res/Enemy.png"));
        addData("enemy", x, -60, 60, 60, 2, 100, enemyShip);

        Renderer.addRenderableObject(this);
        Updater.addUpdatableObjects(this);
    }
    @Override
    public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        // Movement patterns
        switch (movementPattern) {
            case 0: // Straight down
                ChangeY(getY() + getSpeed() * FPS.getDeltaTime());
                break;
                
            case 1: // Zigzag pattern
                ChangeY(getY() + getSpeed() * FPS.getDeltaTime());
                ChangeX(getX() + direction * getSpeed() * 0.5 * FPS.getDeltaTime());

                // Change direction when hitting screen edges
                if (getX() <= 0 || getX() >= Window.getWinWidth() - getWidth()) {
                    direction *= -1;
                }
                break;
                
            case 2: // Sine wave pattern
                ChangeY(getY() + getSpeed() * FPS.getDeltaTime());
                oscillationTime += FPS.getDeltaTime();
                ChangeX(originalX + Math.sin(oscillationTime * 3) * 100);
                
                // Keep within screen bounds
                if (getX() < 0) ChangeX(0);
                if (getX() > Window.getWinWidth() - getWidth()) ChangeX(Window.getWinWidth() - getWidth());
                break;
        }

        // Shooting logic
        if (shootTimer.isRinging() && getY() > 0 && getY() < Window.getWinHeight() - 100) {
            new EnemyBullet(getX() + (getWidth() / 2), getY() + getHeight());
            Sound.playSound("res/laser2.wav");
            shootTimer.resetTimer();
        }

        // Remove if off screen
        if (getY() >= Window.getWinHeight() + getHeight()) {
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);
        }

        // Check collision with player bullets
        Updatable collidingBullet = isColliding(this, "bullet");
        if (collidingBullet != null) {
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);
            Sound.playSound("res/EnemyDeath.wav");
            
            Updater.removeUpdatable(collidingBullet);
            Renderer.removeRenderableObject(collidingBullet.getRenderable());
        }
    }
}