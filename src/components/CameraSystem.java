package components;

import main.GamePanel;
import utilities.Utility;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * CameraSystem.java
 * Owns everything related to the camera tablet:
 *   - Whether the monitor is up or down (monitorUp)
 *   - Which camera feed is currently displayed (currentCamera)
 *   - Drawing the camera feed and the camera selector UI
 *
 * TOGGLING:    Mouse hovering over the bottom strip of the screen
 * SWITCHING:   Click a camera button on the UI, OR press number keys
 *
 * TO ADD MORE CAMERAS:
 *   1. Add a new Camera to the cameras[] array in init()
 *   2. Add a new button position to BUTTON_X[] and BUTTON_Y[]
 *   That's it — draw() and handleClick() scale automatically.
 */
public class CameraSystem
{
    private boolean monitorUp = false;
    private boolean wasInHoverZone = false;

    // HOVER ZONE DIMENSIONS
    private static final int HOVER_ZONE_Y      = GamePanel.HEIGHT - 60;
    private static final int HOVER_ZONE_X_MIN  = (int)(GamePanel.WIDTH * 0.05);
    private static final int HOVER_ZONE_X_MAX  = (int)(GamePanel.WIDTH * 0.45);

    // --- CAMERA STATE ---
    private Camera[] cameras;
    private int currentCamera = 0; // INDEX INTO cameras[]

    // BUTTON POSITIONS FOR THE CAMERA SELECTOR UI
    // ADD A NEW ENTRY HERE WHEN YOU ADD A NEW CAMERA
    private static final int[] BUTTON_X = { 275, 150, 275, 150, 275, 175, 75 };
    private static final int[] BUTTON_Y = { 580, 540, 500, 460, 380, 360, 380 };
    private static final int BUTTON_W   = 60;
    private static final int BUTTON_H   = 30;

    // --- COLOURS ---
    private static final Color OVERLAY_COLOR   = new Color(0, 20, 0, 200);
    private static final Color BUTTON_IDLE     = new Color(0, 80, 0, 180);
    private static final Color BUTTON_SELECTED = new Color(0, 180, 0, 220);
    private static final Color BUTTON_TEXT     = new Color(180, 255, 180);
    private static final Color SCANLINE_COLOR  = new Color(0, 0, 0, 30);
    private static final Color HOVER_HINT_COLOR = new Color(255, 255, 255, 60);

    public CameraSystem()
    {
        init();
    }

    public void init()
    {
        // ADD YOUR CAMERAS HERE
        // IMAGE PATHS ARE RELATIVE TO YOUR RESOURCES ROOT (src/resources or res/)
        // IF AN IMAGE IS MISSING THE CAMERA WILL SHOW A PLACEHOLDER - NO CRASH
        cameras = new Camera[]
                {
                        new Camera("CAM 1 - THE HIVE",  "/cameras/theHive.png"),
                        new Camera("CAM 2 - ANIMATION STUDIO", "/cameras/animationStudio.png"),
                        new Camera("CAM 3 - HALLS RIGHT", "/cameras/hallsRight.png"),
                        new Camera("CAM 4 - STUDENT LOUNGE", "/cameras/studentLounge.png"),
                        new Camera("CAM 5 - HALLS LEFT", "/cameras/hallsLeft.png"),
                        new Camera("CAM 6 - EMERGENCY EXIT", "/cameras/emergencyExit.png"),
                        new Camera("CAM 7 - LIBRARY", "/cameras/library.png"),
                };

        currentCamera = 0;
    }

    public void update()
    {

    }

    public void mouseMoved(int mouseX, int mouseY)
    {
        boolean inHoverZone = mouseX >= HOVER_ZONE_X_MIN
                && mouseX <= HOVER_ZONE_X_MAX
                && mouseY >= HOVER_ZONE_Y;

        if(inHoverZone && !wasInHoverZone)
            monitorUp = !monitorUp;

        wasInHoverZone = inHoverZone;
    }

    public void mouseClicked(int mouseX, int mouseY)
    {
        if(!monitorUp) return;

        // CHECK IF ANY CAMERA BUTTON WAS CLICKED
        for(int i = 0; i < cameras.length; i++)
        {
            if(mouseX >= BUTTON_X[i] && mouseX <= BUTTON_X[i] + BUTTON_W
                    && mouseY >= BUTTON_Y[i] && mouseY <= BUTTON_Y[i] + BUTTON_H)
            {
                currentCamera = i;
                return;
            }
        }
    }

    public void keyPressed(int key)
    {
        if(!monitorUp) return;

        // NUMBER KEYS 1-9 SWITCH CAMERAS DIRECTLY
        // VK_1 = 49, VK_2 = 50... SO WE SUBTRACT 49 TO GET THE INDEX
        int index = key - KeyEvent.VK_1;
        if(index >= 0 && index < cameras.length)
        {
            currentCamera = index;
        }
    }

    public void draw(Graphics2D g, boolean showHints)
    {
        if(monitorUp)
        {
            drawCameraFeed(g);
            drawScanlines(g);
            drawCameraButtons(g);
            drawCameraLabel(g);
        }

        if(showHints) drawHoverZone(g);
    }

    private void drawCameraFeed(Graphics2D g)
    {
        // DARK GREEN OVERLAY — THE CAMERA MONITOR BACKGROUND
        g.setColor(OVERLAY_COLOR);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        Camera cam = cameras[currentCamera];

        if(cam.getImage() != null)
        {
            // DRAW THE CAMERA IMAGE SCALED TO FILL THE SCREEN
            g.drawImage(cam.getImage(), 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        }
        else
        {
            // PLACEHOLDER IF THE IMAGE HASN'T BEEN ADDED YET
            g.setColor(new Color(0, 40, 0));
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

            g.setColor(new Color(0, 150, 0));
            g.setFont(new Font("Monospaced", Font.BOLD, 20));
            Utility.drawCentered(g, "[ NO SIGNAL ]", GamePanel.HEIGHT / 2);
            Utility.drawCentered(g, cam.getName(), GamePanel.HEIGHT / 2 + 30);
        }
    }

    // DRAWS HORIZONTAL SCANLINE'S OVER THE FEED FOR THE CRT MONITOR EFFECT
    private void drawScanlines(Graphics2D g)
    {
        g.setColor(SCANLINE_COLOR);
        for(int y = 0; y < GamePanel.HEIGHT; y += 4)
        {
            g.drawLine(0, y, GamePanel.WIDTH, y);
        }
    }

    private void drawCameraButtons(Graphics2D g)
    {
        for(int i = 0; i < cameras.length; i++)
        {
            boolean selected = (i == currentCamera);

            // BUTTON BACKGROUND
            g.setColor(selected ? BUTTON_SELECTED : BUTTON_IDLE);
            g.fillRect(BUTTON_X[i], BUTTON_Y[i], BUTTON_W, BUTTON_H);

            // BUTTON BORDER
            g.setColor(BUTTON_TEXT);
            g.drawRect(BUTTON_X[i], BUTTON_Y[i], BUTTON_W, BUTTON_H);

            // BUTTON LABEL - SHOWS NUMBER KEY SHORTCUT + CAMERA NAME
            g.setFont(new Font("Monospaced", Font.BOLD, 11));
            String label = "CAM " + (i + 1);
            int labelX = BUTTON_X[i] + (BUTTON_W - g.getFontMetrics().stringWidth(label)) / 2;
            int labelY = BUTTON_Y[i] + BUTTON_H / 2 + 4;
            g.setColor(BUTTON_TEXT);
            g.drawString(label, labelX, labelY);
        }
    }

    private void drawCameraLabel(Graphics2D g)
    {
        // CURRENT CAMERA NAME IN THE TOP LEFT - MATCHES ORIGINAL FNAF STYLE
        g.setColor(new Color(0, 200, 0, 180));
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString(cameras[currentCamera].getName(), 20, 30);
    }

    // HOVER ZONE AT THE BOTTOM OF THE SCREEN
    private void drawHoverZone(Graphics2D g)
    {
        g.setColor(HOVER_HINT_COLOR);
        g.fillRect(HOVER_ZONE_X_MIN, HOVER_ZONE_Y,
                HOVER_ZONE_X_MAX - HOVER_ZONE_X_MIN,
                GamePanel.HEIGHT - HOVER_ZONE_Y);
    }

    // GETTERS
    public boolean isMonitorUp() { return monitorUp; }
    public int getCurrentCamera() { return currentCamera; }
}