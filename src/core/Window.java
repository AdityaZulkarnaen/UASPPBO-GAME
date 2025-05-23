package core;

import javax.swing.*;

public class Window extends JFrame {
    private static double winWidth = 500;
    private static double winHeight = 600;
    private static String winNames;

    public Window(String winName, double winWidth, double winHeight) {
        super(winName);

        Window.winWidth = winWidth;
        Window.winHeight = winHeight;
        Window.winNames = winName;

        setWindowAttributes();
    }
    public void packWindow(){
        pack();
        setResizable(false);
    }

    public void setWindowAttributes(){
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static double getWinWidth() {
        return winWidth;
    }
    public static double getWinHeight() {
        return winHeight;
    }
    public static String getWinNames() {
        return winNames;
    }
}
