package object;

import core.Window;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import render.Renderer;
import update.Updater;

public class Asteroid extends UpdatableRenderableObj {
    public Asteroid() throws IOException {
        Random rand = new Random();
        int dimension = rand.nextInt(75+1);
        if(dimension <35)
            dimension = 35;

        int posX = rand.nextInt((int)Window.getWinWidth() - (int)getWidth() + 1);
        addData("asteroid", posX, -dimension, dimension, dimension, 1, 150, ImageIO.read(new File("res/Asteroid.png")));

        Renderer.addRenderableObject(this);
        Updater.addUpdatableObjects(this);
    }
}
