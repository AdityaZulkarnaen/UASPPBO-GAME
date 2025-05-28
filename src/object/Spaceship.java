package object;

import core.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import render.Renderable;
import render.Renderer;
import update.Updatable;
import update.Updater;

public class Spaceship implements Renderable, Updatable {
    public static double width = 75;
    private static double height = 75;
    private double x;
    private double y;

    private int layer = 2;

    private static BufferedImage spaceShip;

    private double speed = 200;

    private static int shootTimerMillis = 500;
    Timer timer = new Timer(shootTimerMillis);

    public Spaceship(double x, double y) throws Exception {
        this.x = x;
        this.y = y;

        spaceShip = ImageIO.read(new File("res/Spaceships.png"));
        Renderer.addRenderableObject(this);
        Updater.addUpdatableObjects(this);
    }

    public double getHeight() {
        return height;
    }

    @Override
    public boolean drawCollisionBox() {
        return true;
    }

    @Override
    public BufferedImage getBufferedImage() {
        return spaceShip;
    }

    public double getWidth() {
        return width;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if(Input.keys[Input.RIGHT] && x <= Window.getWinWidth() - width)
            x += speed * FPS.getDeltaTime();
        if (Input.keys[Input.LEFT] && x >= 0)
            x -= speed * FPS.getDeltaTime();
        if (Input.keys[Input.UP] && y >= 0)
            y -= speed * FPS.getDeltaTime();
        if (Input.keys[Input.DOWN] && y <= Window.getWinHeight() - height)
            y += speed * FPS.getDeltaTime();
        if (Input.keys[Input.SPACE] && timer.isRinging()) {
            new Bullet(x + (getWidth() / 2), y);
            timer.resetTimer();

            Sound.playSound("res/laser.wav");
        }

        Updatable collidingObject = isColliding(this, "asteroid");
        if (collidingObject != null) {
            handleGameOver();
        }

        // Check collision with enemy bullets
        Updatable collidingEnemyBullet = isColliding(this, "enemyBullet");
        if (collidingEnemyBullet != null) {
            handleGameOver();
        }

        // Check collision with enemy ships
        Updatable collidingEnemy = isColliding(this, "enemy");
        if (collidingEnemy != null) {
            handleGameOver();
        }
    }

    private void handleGameOver() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        // Save high score and reset game
        Score.gameOver();
        
        Updater.removeUpdatable(this);
        Renderer.removeRenderableObject(this);

        Sound.playSound("res/crushed.wav");
        
        System.out.println("Game Over! Final Score: " + Score.getCurrentScore());
        System.out.println("High Scores:");
        for (int i = 0; i < Math.min(5, Score.getHighScores().size()); i++) {
            System.out.println((i + 1) + ". " + Score.getHighScores().get(i));
        }
        
        // Reset the game
        Score.resetGame();
        
        // Restart the game after a short delay
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                try {
                    new Spaceship(Window.getWinWidth() / 2 - (Spaceship.width / 2), Window.getWinHeight() - 150);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000); // 2 second delay
    }

    @Override
    public String getID() {
        return "spaceship";
    }

    @Override
    public Renderable getRenderable() {
        return this;
    }
}
