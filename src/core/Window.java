package core;

import javax.swing.*;

public class Window extends JFrame {
    public static int winWidth = 500;
    public static int winHeight = 600;

    public Window(String winName) {
        super(winName);

        setSize(winWidth, winHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
