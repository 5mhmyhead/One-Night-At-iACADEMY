package main;

import javax.swing.*;

public class Main {

    public static JFrame window;

    public static void main(String[] args) { new Main().startGame(); }

    public void startGame() {

        window = new JFrame("FAMILY MATTERS");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // TODO: ADD ICON FOR GAME
        window.setResizable(false);

        window.add(new GamePanel());
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
