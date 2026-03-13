package state.states;

import main.GamePanel;
import state.State;
import state.StateManager;
import utilities.Utility;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TitleState extends State
{
    public TitleState(StateManager stateManager)
    {
        super(stateManager);
        init();
    }

    @Override
    public void init() {
        // Nothing to load yet — will add background image later
    }

    @Override
    public void update() {
        // Could add a blinking prompt animation here using a frame counter
    }

    @Override
    public void draw(Graphics2D g)
    {
        // DRAW THE TITLE SCREEN
        int w = GamePanel.WIDTH;
        int h = GamePanel.HEIGHT;

        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w, h);

        // Title
        g.setColor(new Color(180, 0, 0));
        g.setFont(new Font("Serif", Font.BOLD, 24));
        Utility.drawCentered(g, "FIVE NIGHTS AT FREDDY'S", h / 2 - 30);

        // Prompt
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, 12));
        Utility.drawCentered(g, "Press ENTER to start", h / 2 + 10);

        // Night label
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Monospaced", Font.PLAIN, 9));
        Utility.drawCentered(g, "Night 1", h - 20);
    }

    @Override
    public void keyPressed(int key)
    {
        if(key == KeyEvent.VK_ENTER)
            stateManager.setState(StateManager.GAME_STATE);
    }

    @Override
    public void keyReleased(int key) {}

    @Override public void mouseMoved(int x, int y) {}
    @Override public void mouseClicked(int x, int y) {}
}
