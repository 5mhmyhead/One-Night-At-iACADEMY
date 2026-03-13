package state.states;

import components.MaskSystem;
import main.GamePanel;
import state.State;
import state.StateManager;
import components.CameraSystem;
import utilities.Utility;

import java.awt.*;

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
    private MaskSystem mask;

    public GameState(StateManager stateManager)
    {
        super(stateManager);
        init();
    }

    @Override
    public void init()
    {
        cameraSystem = new CameraSystem();
        mask = new MaskSystem();
    }

    @Override
    public void update()
    {
        cameraSystem.update();
        mask.update();
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


        if(!cameraSystem.isMonitorUp()) mask.draw(g);
        if(!mask.isMaskUp()) cameraSystem.draw(g);
    }

    @Override
    public void keyPressed(int key)
    {
        cameraSystem.keyPressed(key);
    }

    @Override public void mouseMoved(int x, int y)
    {
        if(!mask.isMaskUp()) cameraSystem.mouseMoved(x, y);
        if(!cameraSystem.isMonitorUp()) mask.mouseMoved(x, y);
    }

    @Override public void mouseClicked(int x, int y) { cameraSystem.mouseClicked(x, y); }

    @Override
    public void keyReleased(int key) {}
}