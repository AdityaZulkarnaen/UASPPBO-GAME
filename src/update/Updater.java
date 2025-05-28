package update;

import javax.imageio.IIOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

public class Updater {
    private static ArrayList<Updatable> updatableObjects = new ArrayList<Updatable>();
    private static ArrayList<Updatable> addUpdatableObjects = new ArrayList<Updatable>();
    private static ArrayList<Updatable> removeUpdatableObjects = new ArrayList<Updatable>();

    public static void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        for(Updatable object: updatableObjects)
            object.update();

        updatableObjects.removeAll(removeUpdatableObjects);
        updatableObjects.addAll(addUpdatableObjects);

        addUpdatableObjects.clear();
        removeUpdatableObjects.clear();
    }

    public static void addUpdatableObjects(Updatable object) {

        addUpdatableObjects.add(object);
    }
    public static void removeUpdatable(Updatable object) {

        removeUpdatableObjects.add(object);
    }
    public static ArrayList<Updatable> getUpdatableObjects() {
        return updatableObjects;
    }
}
