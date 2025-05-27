package object;

import update.Updatable;
import update.Updater;

import render.Renderer;
import render.Renderable;

import core.Window;
import core.FPS;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
    public BufferedImage getBufferedImage() {
        return bullet;
    }

    @Override
    public void update() throws IOException {
        y -= speed * FPS.getDeltaTime();
        if(y< -getHeight()){
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);
        }
    }
}
