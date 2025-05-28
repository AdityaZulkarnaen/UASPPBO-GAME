package object;

import core.FPS;
import core.Score;
import core.Sound;
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

public class Bullet implements Updatable, Renderable {
    private static double width = 30;
    private static double height = 40;
    private double x;
    private double y;

    private final int layer = 1;

    private static BufferedImage bullet;

    private static double speed = 800;

    public Bullet(double x, double y) throws IOException {
        this.x = x - (getWidth() / 2);
        this.y = y;

        bullet = ImageIO.read(new File("res/Bullet.png"));

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
        return bullet;
    }

    @Override
    public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        y -= speed * FPS.getDeltaTime();
        if(y< -getHeight()){
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);
        }

        Updatable collidingObject = isColliding(this, "asteroid");
        if (collidingObject != null) {
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);

            Updater.removeUpdatable(collidingObject);
            Renderer.removeRenderableObject(collidingObject.getRenderable());

            // Add score for destroying asteroid
            Score.addScore(100);

            Sound.playSound("res/crushed.wav");
        }

        // Check collision with enemy spaceships
        Updatable collidingEnemy = isColliding(this, "enemy");
        if (collidingEnemy != null) {
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);

            Updater.removeUpdatable(collidingEnemy);
            Renderer.removeRenderableObject(collidingEnemy.getRenderable());

            // Add score for destroying enemy
            Score.addScore(200);

            Sound.playSound("res/crushed.wav");
        }
    }

    @Override
    public String getID() {
        return "bullet";
    }

    @Override
    public Renderable getRenderable() {
        return this;
    }
}
