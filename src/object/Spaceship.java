package object;

import core.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import render.Renderer;
import update.Updatable;
import update.Updater;

public class Spaceship extends UpdatableRenderableObj {
    private int shootTimerMillis = 500;
    Timer timer = new Timer(shootTimerMillis);
    
    public Spaceship(double x, double y) throws Exception {
        BufferedImage spaceShip = ImageIO.read(new File("res/Spaceships.png"));
        addData("spaceship", x, y, 75, 75, 2, 400, spaceShip);

        Renderer.addRenderableObject(this);
        Updater.addUpdatableObjects(this);
    }

    @Override
    public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if(Input.keys[Input.RIGHT] && getX() <= Window.getWinWidth() - getWidth())
            ChangeX(getX() + getSpeed() * FPS.getDeltaTime());
        if (Input.keys[Input.LEFT] && getX() >= 0)
            ChangeX(getX() - getSpeed() * FPS.getDeltaTime());
        if (Input.keys[Input.UP] && getY() >= 0)
            ChangeY(getY() - getSpeed() * FPS.getDeltaTime());
        if (Input.keys[Input.DOWN] && getY() <= Window.getWinHeight() - getHeight())
            ChangeY(getY() + getSpeed() * FPS.getDeltaTime());
        if (Input.keys[Input.SPACE] && timer.isRinging()) {
            new Bullet(getX() + (getWidth() / 2), getY());
            timer.resetTimer();
            Sound.playSound("res/laser.wav");
        }

        Updatable collidingObject = isColliding(this, "asteroid");
        if (collidingObject != null) {
            Sound.playSound("res/dead.wav");
            handleGameOver();
        }

        // Check collision with enemy bullets
        Updatable collidingEnemyBullet = isColliding(this, "enemyBullet");
        if (collidingEnemyBullet != null) {
            Sound.playSound("res/dead.wav");
            handleGameOver();
        }

        // Check collision with enemy ships
        Updatable collidingEnemy = isColliding(this, "enemy");
        if (collidingEnemy != null) {
            Sound.playSound("res/dead.wav");
            handleGameOver();
        }
    }

    private void handleGameOver() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Updater.removeUpdatable(this);
        Renderer.removeRenderableObject(this);
        
        GameManager.handleGameOver();
    }
}