package object;

import core.FPS;
import core.Window;
import render.Renderer;
import update.Updater;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background extends UpdatableRenderableObj{
    public Background(double y) throws IOException{

        BufferedImage background = ImageIO.read(new File("res/Space.png"));
        addData("background", 0, y, Window.getWinWidth(), Window.getWinHeight(), 0, 200, background);
        Renderer.addRenderableObject(this);
        Updater.addUpdatableObjects(this);
    }
    @Override
    public void update() throws IOException {
        ChangeY(getY() + getSpeed() * FPS.getDeltaTime());

        if(getY() >= Window.getWinHeight()){
            ChangeY(-Window.getWinHeight());
        }
    }
}