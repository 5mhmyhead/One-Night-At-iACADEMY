package main;

import state.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener
{
    // GLOBAL SCREEN SETTINGS
    public static final int WIDTH  = 1280;
    public static final int HEIGHT = 720;

    // THREAD AND FPS SETTINGS
    public static final int FPS = 30;

    private Thread gameThread;
    private boolean running;

    // DOUBLE BUFFER BEFORE DISPLAYING THE RENDERED SCREEN
    private BufferedImage image;
    private Graphics2D g2d;

    // CLASS THAT HANDLES THE CHANGING OF STATES IN THE GAME
    private StateManager stateManager;

    GamePanel()
    {
        super();
        this.setFocusable(true);
        this.requestFocus();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void addNotify()
    {
        super.addNotify();

        if(gameThread == null)
        {
            gameThread = new Thread(this);
            this.addKeyListener(this);
            gameThread.start();
        }
    }

    private void init()
    {
        // SET UP BUFFER TO DRAW ON
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2d = (Graphics2D) image.getGraphics();

        running = true;
        stateManager = new StateManager(); // Boots into TITLE_STATE
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run()
    {
        init();
        long start;
        long elapsed;
        long wait;

        while (running)
        {
            start = System.nanoTime();
            update();
            draw();
            drawToScreen();

            long targetTime = 1000 / FPS;
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if (wait < 0) wait = 5;

            try
            {
                Thread.sleep(wait);
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void update() { stateManager.update(); }
    private void draw() { stateManager.draw(g2d); }

    // Blit the completed BufferedImage to the actual screen, scaled up
    private void drawToScreen()
    {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e != null) stateManager.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e != null) stateManager.keyReleased(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e)
    {}
}
