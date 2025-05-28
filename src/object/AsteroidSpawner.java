package object;

import core.Timer;
import java.io.IOException;
import render.Renderable;
import update.Updatable;
import update.Updater;

public class AsteroidSpawner implements Updatable {
    Timer timer = new Timer(1000);

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
