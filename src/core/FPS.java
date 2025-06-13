package core;

import java.time.Duration;
import java.time.Instant;

public class FPS {
    private static Duration fpsDeltatime = Duration.ZERO;
    private static Duration lastTime = Duration.ZERO;
    private static Instant beginTime = Instant.now();
    private static double deltaTime = fpsDeltatime.toMillis() - lastTime.toMillis();

    public static void calcBeginTime(){
        beginTime = Instant.now();
        fpsDeltatime = Duration.ZERO;
    }
    public static void calcDeltaTime(){
        fpsDeltatime = Duration.between(beginTime, Instant.now());
        deltaTime = (double) fpsDeltatime.toMillis() - lastTime.toMillis();
        lastTime = fpsDeltatime;
    }
    public static double getDeltaTime() {
        return deltaTime / 1000;
    }
}
