package object;

import core.Timer;
import java.io.IOException;
import render.Renderable;
import update.Updatable;
import update.Updater;

public class AsteroidSpawner implements Updatable {
    private static int speed=1000;
    static Timer timer = new Timer(speed);

    public AsteroidSpawner() {
        Updater.addUpdatableObjects(this);
    }

    public static void minSpeed() {
        AsteroidSpawner.speed -= 200; // Decrease the timer to increase spawning rate
        if (AsteroidSpawner.speed < 300) {
            AsteroidSpawner.speed = 300; // Set a minimum speed to avoid too fast spawning
        }
        timer.setAgain(speed);
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
