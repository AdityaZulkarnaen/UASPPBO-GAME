package object;

import core.Timer;
import java.io.IOException;
import render.Renderable;
import update.Updatable;
import update.Updater;

public class AsteroidSpawner implements Updatable {
    private static int speed=1000;
    Timer timer = new Timer(speed);

    public AsteroidSpawner() {
        Updater.addUpdatableObjects(this);
    }


    @Override
    public void update() throws IOException {
        if (timer.isRinging()){
            new Asteroid();
            timer.resetTimer();
        }
    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public Renderable getRenderable() {
        return null;
    }
}
