package core;

public class Entry {
    public static void main(String[] args) {
        Window window = new Window("Space War");

        boolean runGame = true;

        FPS.calcBeginTime();
        while (runGame) {
            //update the game state

            //render the game
            System.out.printf("%f\n", FPS.getDeltaTime());
            //recalculate delta time
            FPS.calcDeltaTime();
        }

    }
}
