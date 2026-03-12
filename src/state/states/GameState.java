package state.states;

import main.GamePanel;
import state.State;
import state.StateManager;
import game.CameraSystem;
import utilities.Utility;

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
    private CameraSystem cameraSystem;

    public GameState(StateManager stateManager)
    {
        super(stateManager);
        init();
    }

    @Override
    public void init()
    {
        cameraSystem = new CameraSystem();
    }

    @Override
    public void update()
    {
        cameraSystem.update();
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

        // Title
        g.setColor(new Color(180, 0, 0));
        g.setFont(new Font("Serif", Font.BOLD, 24));
        Utility.drawCentered(g, "OFFICE VIEW", h / 2 - 30);

        // Prompt
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, 12));
        Utility.drawCentered(g, "Hover at the bottom to check the Cameras.", h / 2 + 10);


        cameraSystem.draw(g);
    }

    @Override
    public void keyPressed(int key)
    {
        if(key == KeyEvent.VK_F1)
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

        cameraSystem.keyPressed(key);
    }

    @Override public void mouseMoved(int x, int y) { cameraSystem.mouseMoved(x, y); }
    @Override public void mouseClicked(int x, int y) { cameraSystem.mouseClicked(x, y); }

    @Override
    public void keyReleased(int key) {}
}