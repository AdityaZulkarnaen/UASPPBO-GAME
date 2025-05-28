package object;

import core.FPS;
import core.Sound;
import core.Window;
import render.Renderable;
import render.Renderer;
import update.Updatable;
import update.Updater;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EnemyBullet implements Updatable, Renderable {
    private static double width = 20;
    private static double height = 20;
    private double x;
    private double y;

    private final int layer = 1;

    private static BufferedImage enemyBullet;

    private static double speed = 300;

    public EnemyBullet(double x, double y) throws IOException {
        this.x = x - (getWidth() / 2);
        this.y = y;

        enemyBullet = ImageIO.read(new File("res/EnemyBullet.png"));

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
        return false; // Changed from true to false
    }

    @Override
    public BufferedImage getBufferedImage() {
        return enemyBullet;
    }

    @Override
    public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        y += speed * FPS.getDeltaTime(); // Move downward
        
        // Remove if off screen
        if (y > Window.getWinHeight() + getHeight()) {
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);
        }

        // Check collision with player spaceship
        Updatable collidingObject = isColliding(this, "spaceship");
        if (collidingObject != null) {
            // Game Over - remove both objects
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);

            Updater.removeUpdatable(collidingObject);
            Renderer.removeRenderableObject(collidingObject.getRenderable());

            Sound.playSound("res/crushed.wav");
            
            // You can add game over logic here
            System.out.println("Game Over! Enemy bullet hit the player!");
        }
    }

    @Override
    public String getID() {
        return "enemyBullet";
    }

    @Override
    public Renderable getRenderable() {
        return this;
    }
}