package components.office;

import main.GamePanel;
import utilities.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OfficeView
{
    private boolean ventMode = false;
    private boolean wasInHoverZone = false;

    // LEFT AND RIGHT HOVER ZONES THAT SWITCH FROM MAIN TO VENT VIEW
    private static final int MAIN_HOVER_X_MIN = GamePanel.WIDTH - 60;
    private static final int MAIN_HOVER_X_MAX = GamePanel.WIDTH;

    private static final int VENT_HOVER_X_MIN = 0;
    private static final int VENT_HOVER_X_MAX = 60;

    private static final int HOVER_ZONE_Y_MIN = (int)(GamePanel.HEIGHT * 0.20);
    private static final int HOVER_ZONE_Y_MAX = (int)(GamePanel.HEIGHT * 0.80);

    private static final Color HOVER_HINT_COLOR = new Color(255, 255, 255, 40);

    private MainView mainView;
    private VentView ventView;

    public OfficeView()
    {
        init();
    }

    public void init()
    {
        mainView = new MainView();
        ventView = new VentView();
    }

    public void update()
    {

    }

    public void mouseMoved(int mouseX, int mouseY)
    {
        int zoneXMin = ventMode ? VENT_HOVER_X_MIN : MAIN_HOVER_X_MIN;
        int zoneXMax = ventMode ? VENT_HOVER_X_MAX : MAIN_HOVER_X_MAX;

        boolean inHoverZone = mouseX >= zoneXMin
                && mouseX <= zoneXMax
                && mouseY >= HOVER_ZONE_Y_MIN
                && mouseY <= HOVER_ZONE_Y_MAX;

        if(inHoverZone && !wasInHoverZone)
            ventMode  = !ventMode;

        wasInHoverZone = inHoverZone;
    }

    public void draw(Graphics2D g)
    {
        if(ventMode)
            ventView.draw(g);
        else
            mainView.draw(g);

        drawHoverZone(g);
    }

    // HOVER ZONE AT THE RIGHT OF THE SCREEN
    private void drawHoverZone(Graphics2D g)
    {
        int zoneXMin = ventMode ? VENT_HOVER_X_MIN : MAIN_HOVER_X_MIN;
        int zoneXMax = ventMode ? VENT_HOVER_X_MAX : MAIN_HOVER_X_MAX;

        g.setColor(HOVER_HINT_COLOR);
        g.fillRect(zoneXMin, HOVER_ZONE_Y_MIN,
                zoneXMax - zoneXMin,
                HOVER_ZONE_Y_MAX - HOVER_ZONE_Y_MIN);
    }

    // CALLED BY GAME STATE WHEN MASK OR CAMERA COMES UP
    public void forceNormalView()
    {
        ventMode       = false;
        wasInHoverZone = false;
    }

    public boolean isVentMode() { return ventMode; }
}