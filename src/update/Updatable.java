package update;

import render.Renderable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public interface Updatable {
    public void update() throws IOException;
    public String getID();
    public Renderable getRenderable();

    public default Updatable isColliding(Renderable thisObject, String otherObjectID){
        ArrayList<Updatable> objects = Updater.getUpdatableObjects();
        for (Updatable object: objects)
            if(Objects.equals(object.getID(), otherObjectID))
                if(thisObject.getX() < object.getRenderable().getX() + object.getRenderable().getWidth() && thisObject.getX() + thisObject.getWidth() > object.getRenderable().getX())
                    if(thisObject.getY() < object.getRenderable().getY() + object.getRenderable().getHeight() && thisObject.getY() + thisObject.getHeight() > object.getRenderable().getY())
                        return object;
        return null;
    }
}
