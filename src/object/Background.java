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

public class Background implements Renderable, Updatable {
    private static double width = Window.getWinWidth();
    private static double height = Window.getWinHeight();
    private static double x;
    private double y;

    private final int layer = 0;

    private static BufferedImage background;

    private double speed = 400;

    public Background(double y) throws IOException{
        this.y = y;

        background = ImageIO.read(new File("res/Space.png"));
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
        return background;
    }

    @Override
    public void update() {
        y += speed * FPS.getDeltaTime();

        if(y>=Window.getWinHeight()){
            y= -Window.getWinHeight();
        }
    }
}
