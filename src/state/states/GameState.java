package state.states;

import main.GamePanel;
import state.State;
import state.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * PlayState.java
 * The main gameplay screen — the night shift.
 *
 * This will become the largest class. Future additions:
 *   - OfficeView    (draws the office background, doors, lights)
 *   - CameraSystem  (lets player view camera feeds)
 *   - PowerSystem   (drains power, ends game at 0%)
 *   - ClockSystem   (tracks in-game time, triggers WIN at 6 AM)
 *   - Animatronic[] (AI movement logic for each character)
 *
 * Win/lose transitions:
 *   stateManager.setState(StateManager.WIN_STATE);
 *   stateManager.setState(StateManager.DEAD_STATE);
 */
public class GameState extends State
{
    public GameState(StateManager stateManager)
    {
        super(stateManager);
        init();
    }

    @Override
    public void init()
    {
        // TODO: Initialize power, clock, animatronics, office view, cameras
    }

    @Override
    public void update()
    {
        // TODO:
        // power.update();
        // clock.update();
        // for (Animatronic a : animatronics) a.update();
        // if (clock.isSixAM()) stateManager.setState(StateManager.WIN_STATE);
        // if (power.isDepleted()) stateManager.setState(StateManager.DEAD_STATE);
    }

    @Override
    public void draw(Graphics2D g)
    {
        int w = GamePanel.WIDTH;
        int h = GamePanel.HEIGHT;

        // Placeholder — replace with actual office rendering
        g.setColor(new Color(30, 30, 30));
        g.fillRect(0, 0, w, h);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 11));
        drawCentered(g, "Night 1 - Office goes here", h / 2);
    }

    @Override
    public void keyPressed(int key)
    {
        if(key == KeyEvent.VK_F1)
        {
            stateManager.setState(StateManager.TITLE_STATE);
        }

        if(key == KeyEvent.VK_F3)
        {
            stateManager.setState(StateManager.LOSE_STATE);
        }

        if(key == KeyEvent.VK_F4)
        {
            stateManager.setState(StateManager.WIN_STATE);
        }
    }

    @Override
    public void keyReleased(int key)
    {
        // TODO: Release door/light held keys
    }

    private void drawCentered(Graphics2D g, String text, int y)
    {
        int x = (GamePanel.WIDTH - g.getFontMetrics().stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }
}