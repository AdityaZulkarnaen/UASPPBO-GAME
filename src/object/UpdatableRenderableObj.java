package object;

import java.awt.image.BufferedImage;

import core.FPS;
import core.Window;
import render.Renderable;
import render.Renderer;
import update.Updatable;
import update.Updater;   

public class UpdatableRenderableObj implements Updatable, Renderable {
    private String id;
    private double x, y, width, height, speed;
    private int layer;
    private BufferedImage image;

    public void addData(String id, double x, double y, double width, double height, int layer, double speed, BufferedImage image) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.layer = layer;
        this.speed = speed;
        this.image = image;
    }

    @Override
    public void update() { // Default update method
        y += speed* FPS.getDeltaTime();

        if(y >= Window.getWinHeight()){
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);
        }
    }   

    @Override
    public String getID() {
        return id;
    }

    @Override
    public Renderable getRenderable() {
        return this;
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
    protected double getSpeed() {
        return speed;
    }

    @Override
    public BufferedImage getBufferedImage() {
        return image;
    }

    protected void ChangeX(double x) {
        this.x = x;
    }
    
    protected void ChangeY(double y) {
        this.y = y;
    }

}
