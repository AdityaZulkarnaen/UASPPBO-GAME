package object;

import core.Timer;
import render.Renderable;
import update.Updatable;
import update.Updater;

import java.io.IOException;
import java.util.Random;

public class EnemySpawner implements Updatable {
    Timer timer = new Timer(3000); // Spawn every 3 seconds
    Random rand = new Random();

    public EnemySpawner() {
        Updater.addUpdatableObjects(this);
    }

    @Override
    public void update() throws IOException {
        if (timer.isRinging()) {
            // 70% chance to spawn an enemy
            if (rand.nextFloat() < 0.7f) {
                new EnemySpaceship();
            }
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