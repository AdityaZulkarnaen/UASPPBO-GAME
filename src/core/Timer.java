package core;

import update.Updatable;
import update.Updater;

import java.io.IOException;

public class Timer implements Updatable {
    int setMillisTime = 0;
    int beggingMillisTime = 0;

    public Timer(int setMillisTime) {
        this.beggingMillisTime = setMillisTime;

        Updater.addUpdatableObjects(this);
    }

    @Override
    public void update() throws IOException {
        setMillisTime -= FPS.getDeltaTime() * 1000;
    }

    public boolean isRinging(){
        if(setMillisTime<0)
            return true;
        return false;
    }
    public void resetTimer(){
        setMillisTime = beggingMillisTime;
    }
}
