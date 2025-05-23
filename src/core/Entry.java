package core;

public class Entry {
    public static void main(String[] args) {
        Window window = new Window("Space War", Window.getWinWidth(), Window.getWinHeight());
        window.addKeyListener(new Input());

        window.packWindow();
        window.setVisible(true);

        boolean runGame = true;

        FPS.calcBeginTime();
        while (runGame) {
            if(Input.keys[Input.LEFT]){
                System.out.println("Left Key");
            }
            if(Input.keys[Input.RIGHT]){
                System.out.println("Right Key");
            }
            if(Input.keys[Input.UP]){
                System.out.println("Up Key");
            }
            if(Input.keys[Input.DOWN]){
                System.out.println("Down Key");
            }
            if(Input.keys[Input.SPACE]){
                System.out.println("Space Key");
            }
            //update the game state

            //render the game
            System.out.printf("%f\n", FPS.getDeltaTime());
            //recalculate delta time
            FPS.calcDeltaTime();
        }

    }
}
