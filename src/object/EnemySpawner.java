package object;

import core.Score;
import core.Timer;
import render.Renderable;
import update.Updatable;
import update.Updater;

import java.io.IOException;
import java.util.Random;

public class EnemySpawner implements Updatable {
    Timer timer = new Timer(3000); // Base spawn time
    Random rand = new Random();

    public EnemySpawner() {
        Updater.addUpdatableObjects(this);
    }

    @Override
    public void update() throws IOException {
        // Only spawn enemies if level 2 or higher
        if (Score.getCurrentLevel() >= 2 && timer.isRinging()) {
            float spawnChance = getSpawnChance();
            
            if (rand.nextFloat() < spawnChance) {
                new EnemySpaceship();
            }
            
            // Adjust timer based on level (faster spawning at higher levels)
            int newTimerMillis = Math.max(1000, 4000 - (Score.getCurrentLevel() * 400));
            timer = new Timer(newTimerMillis);
            timer.resetTimer();
        }
    }

    private float getSpawnChance() {
        int level = Score.getCurrentLevel();
        switch (level) {
            case 1: return 0.0f;  // No enemies
            case 2: return 0.3f;  // 30% chance
            case 3: return 0.5f;  // 50% chance
            case 4: return 0.7f;  // 70% chance
            default: return 0.9f; // 90% chance (endless mode)
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