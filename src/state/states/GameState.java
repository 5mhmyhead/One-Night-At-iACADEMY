package state.states;

import components.MaskSystem;
import main.GamePanel;
import state.State;
import state.StateManager;
import components.CameraSystem;
import components.office.OfficeView;

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
    private OfficeView office;

    public GameState(StateManager stateManager)
    {
        super(stateManager);
        init();
    }

    @Override
    public void init()
    {
        cameraSystem = new CameraSystem();
        office = new OfficeView();
        mask = new MaskSystem();
    }

    @Override
    public void update()
    {
        cameraSystem.update();
        mask.update();
        office.update();
    }

    @Override
    public void draw(Graphics2D g)
    {
        g.setColor(new Color(30, 25, 20));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        boolean showHints = !office.isVentMode();

        if(!cameraSystem.isMonitorUp() && !mask.isMaskUp()) office.draw(g);
        if(!cameraSystem.isMonitorUp()) mask.draw(g, showHints);
        if(!mask.isMaskUp()) cameraSystem.draw(g, showHints);
    }

    @Override
    public void keyPressed(int key)
    {
        cameraSystem.keyPressed(key);
    }

    @Override public void mouseMoved(int x, int y)
    {
        boolean wasCameraUp = cameraSystem.isMonitorUp();
        boolean wasMaskUp   = mask.isMaskUp();

        boolean mainView = !cameraSystem.isMonitorUp() && !mask.isMaskUp();

        if(!mask.isMaskUp() && !office.isVentMode()) cameraSystem.mouseMoved(x, y);
        if(!cameraSystem.isMonitorUp() && !office.isVentMode()) mask.mouseMoved(x, y);
        if(mainView) office.mouseMoved(x, y);

        if(cameraSystem.isMonitorUp() && !wasCameraUp) office.forceNormalView();
        if(mask.isMaskUp() && !wasMaskUp) office.forceNormalView();
    }

    @Override public void mouseClicked(int x, int y) { cameraSystem.mouseClicked(x, y); }

    @Override
    public void keyReleased(int key) {}
}