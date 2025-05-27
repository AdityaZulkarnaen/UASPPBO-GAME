package object;

import core.FPS;
import core.Input;
import core.Window;
import render.Renderable;
import render.Renderer;
import update.Updatable;
import update.Updater;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Spaceship implements Renderable, Updatable {
    public static double width = 75;
    private static double height = 75;
    private double x;
    private double y;

    private int layer = 1;

    private static BufferedImage spaceShip;

    private double speed = 200;

    public Spaceship(double x, double y) throws Exception {
        this.x = x;
        this.y = y;

        spaceShip = ImageIO.read(new File("res/Spaceships.png"));
        Renderer.addRenderableObject(this);
        Updater.addUpdatableObjects(this);
    }

    public double getHeight() {
        return height;
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
    public void update() {
        if(Input.keys[Input.RIGHT] && x <= Window.getWinWidth() - width)
            x += speed * FPS.getDeltaTime();
        if (Input.keys[Input.LEFT] && x >= 0)
            x -= speed * FPS.getDeltaTime();
        if (Input.keys[Input.UP] && y >= 0)
            y -= speed * FPS.getDeltaTime();
        if (Input.keys[Input.DOWN] && y <= Window.getWinHeight() - height)
            y += speed * FPS.getDeltaTime();

    }
}
