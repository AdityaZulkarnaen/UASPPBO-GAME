package object;

import core.FPS;
import core.GameManager;
import core.Sound;
import core.Window;
import render.Renderer;
import update.Updatable;
import update.Updater;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EnemyBullet extends UpdatableRenderableObj {
    public EnemyBullet(double x, double y) throws IOException {
        BufferedImage enemyBullet = ImageIO.read(new File("res/EnemyBullet.png"));
        addData("enemyBullet", x - (getWidth() / 2), y, 20, 20, 1, 300, enemyBullet);

        Renderer.addRenderableObject(this);
        Updater.addUpdatableObjects(this);
    }
    @Override
    public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        ChangeY(getY() + getSpeed() * FPS.getDeltaTime());

        // Remove if off screen
        if (getY() > Window.getWinHeight() + getHeight()) {
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);
        }

        // Check collision with player spaceship
        Updatable collidingObject = isColliding(this, "spaceship");
        if (collidingObject != null) {
            // Game Over - remove both objects
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);

            Updater.removeUpdatable(collidingObject);
            Renderer.removeRenderableObject(collidingObject.getRenderable());

            Sound.playSound("res/crushed.wav");
            
            // You can add game over logic here
            System.out.println("Game Over! Enemy bullet hit the player!");
            GameManager.handleGameOver();
        }
        Updatable collidingBullet = isColliding(this, "bullet");
        if (collidingBullet != null) {
            // Remove both enemy bullet and player's bullet
            Updater.removeUpdatable(this);
            Renderer.removeRenderableObject(this);

            Updater.removeUpdatable(collidingBullet);
            Renderer.removeRenderableObject(collidingBullet.getRenderable());

            Sound.playSound("res/EnemyDeath.wav");
        }
    }
}