package main;

import javax.swing.*;

public class Main
{
    public static JFrame window;
    static void main() { new Main().startGame(); }

    public void startGame()
    {
        window = new JFrame("NIGHTS AT iACADEMY");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(new GamePanel());
        window.setResizable(false);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
