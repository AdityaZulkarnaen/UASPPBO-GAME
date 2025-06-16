package object;

import core.FPS;
import core.Score;
import core.Sound;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import render.Renderer;
import update.Updatable;
import update.Updater;

public class Bullet extends UpdatableRenderableObj{
    public Bullet(double x, double y) throws IOException {
        BufferedImage bullet = ImageIO.read(new File("res/Bullet.png"));
        addData("bullet", x - (getWidth() / 2), y, 30, 40, 1, 800, bullet);

        Renderer.addRenderableObject(this);
        Updater.addUpdatableObjects(this);
    }
    
    @Override
    public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        ChangeY(getY() - getSpeed() * FPS.getDeltaTime());
        if(getY() < -getHeight()){
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);
        }
        Updatable collidingObject = isColliding(this, "asteroid");
        if (collidingObject != null) {
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);

            Updater.removeUpdatable(collidingObject);
            Renderer.removeRenderableObject(collidingObject.getRenderable());

            // Add score for destroying asteroid
            Score.addScore(100);

            Sound.playSound("res/crushed.wav");
        }

        // Check collision with enemy spaceships
        Updatable collidingEnemy = isColliding(this, "enemy");
        if (collidingEnemy != null) {
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);

            Updater.removeUpdatable(collidingEnemy);
            Renderer.removeRenderableObject(collidingEnemy.getRenderable());

            // Add score for destroying enemy
            Score.addScore(200);

            Sound.playSound("res/crushed.wav");
        }
    }
}
