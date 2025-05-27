package object;

import core.FPS;
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

public class Asteroid implements Updatable, Renderable {
    private double width;
    private double height;
    private double x;
    private double y;

    private final int layer = 1;

    private static BufferedImage asteroid;

    private double speed = 150;

    Random rand = new Random();

    public Asteroid() throws IOException {
        int dimension = rand.nextInt(75+1);
        if(dimension <35)
            dimension = 35;

        width = dimension;
        height = dimension;

        int posX = rand.nextInt((int)Window.getWinWidth() - (int)getWidth() + 1);
        this.x = posX;
        this.y = -getHeight();

        asteroid = ImageIO.read(new File("res/Asteroid.png"));

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
        return asteroid;
    }

    @Override
    public void update() throws IOException {
        y += speed* FPS.getDeltaTime();

        if(y >= Window.getWinHeight()){
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);
        }
    }
}
