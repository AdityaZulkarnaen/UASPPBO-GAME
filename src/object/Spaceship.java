package object;

import core.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import render.Renderable;
import render.Renderer;
import update.Updatable;
import update.Updater;

public class Spaceship implements Renderable, Updatable {
    public static double width = 75;
    private static double height = 75;
    private double x;
    private double y;

    private int layer = 2;

    private static BufferedImage spaceShip;

    private static double speed = 400;

    private static int shootTimerMillis = 500;
    static Timer timer = new Timer(shootTimerMillis);

    public Spaceship(double x, double y) throws Exception {
        this.x = x;
        this.y = y;

        spaceShip = ImageIO.read(new File("res/Spaceships.png"));
        Renderer.addRenderableObject(this);
        Updater.addUpdatableObjects(this);
    }

    public static void minSpeed() {
        if (speed<800) {
            speed += 50; // Increase speed to make the game more challenging
        }
        if (shootTimerMillis > 200) {
            shootTimerMillis -= 50; // Decrease the timer to increase firing rate
            // Reset the timer with the new value
            timer.setAgain(shootTimerMillis);
        }
    }

    public double getHeight() {
        return height;
    }

    @Override
    public boolean drawCollisionBox() { 
        return false; // Changed from true to false
    }

    @Override
    public BufferedImage getBufferedImage() {
        return spaceShip;
    }

    public double getWidth() {
        return width;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if(Input.keys[Input.RIGHT] && x <= Window.getWinWidth() - width)
            x += speed * FPS.getDeltaTime();
        if (Input.keys[Input.LEFT] && x >= 0)
            x -= speed * FPS.getDeltaTime();
        if (Input.keys[Input.UP] && y >= 0)
            y -= speed * FPS.getDeltaTime();
        if (Input.keys[Input.DOWN] && y <= Window.getWinHeight() - height)
            y += speed * FPS.getDeltaTime();
        if (Input.keys[Input.SPACE] && timer.isRinging()) {
            new Bullet(x + (getWidth() / 2), y);
            timer.resetTimer();

            Sound.playSound("res/laser.wav");
        }

        Updatable collidingObject = isColliding(this, "asteroid");
        if (collidingObject != null) {
            Sound.playSound("res/dead.wav");
            handleGameOver();
        }

        // Check collision with enemy bullets
        Updatable collidingEnemyBullet = isColliding(this, "enemyBullet");
        if (collidingEnemyBullet != null) {
            Sound.playSound("res/dead.wav");
            handleGameOver();
        }

        // Check collision with enemy ships
        Updatable collidingEnemy = isColliding(this, "enemy");
        if (collidingEnemy != null) {
            Sound.playSound("res/dead.wav");
            handleGameOver();
        }
    }

    private void handleGameOver() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Updater.removeUpdatable(this);
        Renderer.removeRenderableObject(this);
        
        GameManager.handleGameOver();
    }

    @Override
    public String getID() {
        return "spaceship";
    }

    @Override
    public Renderable getRenderable() {
        return this;
    }
}