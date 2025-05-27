package object;

import render.Renderable;
import render.Renderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Spaceship implements Renderable {
    private static double width = 75;
    private static double height = 75;
    private double x;
    private double y;

    private int layer = 1;

    private static BufferedImage spaceShip;

    public Spaceship(double x, double y) throws Exception {
        this.x = x;
        this.y = y;

        spaceShip = ImageIO.read(new File("res/Spaceships.png"));
        Renderer.addRenderableObject(this);
    }

    public static double getHeight() {
        return height;
    }

    public static double getWidth() {
        return width;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(spaceShip, (int) x, (int) y, (int)width, (int)height, null);
    }

    @Override
    public int getLayer() {
        return layer;
    }
}
