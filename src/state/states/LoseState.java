package state.states;

import main.GamePanel;
import state.State;
import state.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * DeadState.java
 * Shown when an animatronic reaches the player.
 * Press R to return to the title screen.
 */
public class LoseState extends State
{
    public LoseState(StateManager stateManager)
    {
        super(stateManager);
        init();
    }

    @Override
    public void init() {
        // TODO: Trigger jumpscare sound/image here
    }

    @Override
    public void update() {
        // TODO: Animate a jumpscare using a frame counter before showing this screen
    }

    @Override
    public void draw(Graphics2D g)
    {
        int w = GamePanel.WIDTH;
        int h = GamePanel.HEIGHT;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w, h);

        g.setColor(new Color(200, 0, 0));
        g.setFont(new Font("Serif", Font.BOLD, 28));
        drawCentered(g, "GAME OVER", h / 2 - 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, 12));
        drawCentered(g, "Press R to try again", h / 2 + 20);
    }

    @Override
    public void keyPressed(int key)
    {
        if(key == KeyEvent.VK_R || key == KeyEvent.VK_F1)
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

        if(key == KeyEvent.VK_F4)
        {
            stateManager.setState(StateManager.WIN_STATE);
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