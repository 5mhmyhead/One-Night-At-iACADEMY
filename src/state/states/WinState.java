package state.states;

import main.GamePanel;
import state.State;
import state.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * WinState.java
 * Shown when the player survives until 6 AM.
 * Press ENTER to return to the title screen (or later, advance the night).
 */
public class WinState extends State {

    public WinState(StateManager stateManager)
    {
        super(stateManager);
        init();
    }

    @Override
    public void init()
    {
        // TODO: Play 6 AM bell sound here
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics2D g)
    {
        int w = GamePanel.WIDTH;
        int h = GamePanel.HEIGHT;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w, h);

        g.setColor(new Color(255, 215, 0));
        g.setFont(new Font("Serif", Font.BOLD, 24));
        drawCentered(g, "6 AM - YOU SURVIVED!", h / 2 - 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, 12));
        drawCentered(g, "Press ENTER to continue", h / 2 + 20);
    }

    @Override
    public void keyPressed(int key)
    {
        if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_F1)
        {
            stateManager.setState(StateManager.TITLE_STATE);
        }

        if(key == KeyEvent.VK_F2)
        {
            stateManager.setState(StateManager.GAME_STATE);
        }

        if(key == KeyEvent.VK_F3)
        {
            stateManager.setState(StateManager.LOSE_STATE);
        }
    }

    @Override
    public void keyReleased(int key) {}

    private void drawCentered(Graphics2D g, String text, int y)
    {
        int x = (GamePanel.WIDTH - g.getFontMetrics().stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }
}