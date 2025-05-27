package object;

import core.Timer;
import update.Updatable;
import update.Updater;

import java.io.IOException;

public class AsteroidSpawner implements Updatable {
    Timer timer = new Timer(500);

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
}
