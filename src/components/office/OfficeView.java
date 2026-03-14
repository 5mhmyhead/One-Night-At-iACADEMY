package components.office;

import main.GamePanel;
import utilities.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Office.java
 * Manages the two office views:
 *   - MAIN VIEW:  the player's default forward-facing office
 *   - VENT VIEW:  looking right at the vent
 *
 * TRANSITIONING: Hover zone on the right edge toggles vent mode.
 *                A short blur effect plays during the switch.
 *
 * TO ADD REAL IMAGES:
 *   1. Drop office.png and vent.png into your resources/office/ folder
 *   2. The loadImage() calls in init() will pick them up automatically
 *   3. Remove drawPlaceholder() calls from drawMainView() and drawVentView()
 */
public class OfficeView
{
    // --- VIEW STATE ---
    private boolean ventMode = false;
    private boolean wasInHoverZone = false;

    // --- BLUR TRANSITION ---
    private static final int BLUR_FRAMES = 8;
    private int blurTimer = 0;

    // MAIN VIEW HOVER ZONE — RIGHT SIDE (look right toward vent)
    private static final int MAIN_HOVER_X_MIN = GamePanel.WIDTH - 60;
    private static final int MAIN_HOVER_X_MAX = GamePanel.WIDTH;

    // VENT VIEW HOVER ZONE — LEFT SIDE (look back left toward office)
    private static final int VENT_HOVER_X_MIN = 0;
    private static final int VENT_HOVER_X_MAX = 60;

    // BOTH ZONES USE THE SAME Y RANGE
    private static final int HOVER_ZONE_Y_MIN = (int)(GamePanel.HEIGHT * 0.20);
    private static final int HOVER_ZONE_Y_MAX = (int)(GamePanel.HEIGHT * 0.80);

    private static final Color HOVER_HINT_COLOR = new Color(255, 255, 255, 40);

    // --- IMAGES ---
    private BufferedImage officeImage;
    private BufferedImage ventImage;
    private BufferedImage blurredOffice;
    private BufferedImage blurredVent;

    public OfficeView()
    {
        init();
    }

    public void init()
    {
        officeImage   = loadImage("/office/office.png");
        ventImage     = loadImage("/office/vent.png");

        // PRE-COMPUTE BLURRED VERSIONS SO WE DON'T BLUR EVERY FRAME
        if(officeImage != null) blurredOffice = Utility.applyBlur(officeImage);
        if(ventImage   != null) blurredVent   = Utility.applyBlur(ventImage);
    }

    private BufferedImage loadImage(String path)
    {
        try
        {
            return ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream(path)));
        }
        catch(IOException | NullPointerException e)
        {
            System.out.println("Office image not found: " + path);
            return null;
        }
    }

    // --- UPDATE ---

    public void update()
    {
        if(blurTimer > 0) blurTimer--;
    }

    // --- INPUT ---
    public void mouseMoved(int mouseX, int mouseY)
    {
        int zoneXMin = ventMode ? VENT_HOVER_X_MIN : MAIN_HOVER_X_MIN;
        int zoneXMax = ventMode ? VENT_HOVER_X_MAX : MAIN_HOVER_X_MAX;

        boolean inHoverZone = mouseX >= zoneXMin
                && mouseX <= zoneXMax
                && mouseY >= HOVER_ZONE_Y_MIN
                && mouseY <= HOVER_ZONE_Y_MAX;

        if(inHoverZone && !wasInHoverZone)
        {
            ventMode  = !ventMode;
            blurTimer = BLUR_FRAMES;
        }

        wasInHoverZone = inHoverZone;
    }

    // --- DRAWING ---

    public void draw(Graphics2D g)
    {
        if(blurTimer > 0)
            drawBlurFrame(g);
        else if(ventMode)
            drawVentView(g);
        else
            drawMainView(g);

        drawHoverZone(g);
    }

    private void drawMainView(Graphics2D g)
    {
        if(officeImage != null)
            g.drawImage(officeImage, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        else
            drawPlaceholder(g, "MAIN OFFICE", new Color(30, 25, 20));
    }

    private void drawVentView(Graphics2D g)
    {
        if(ventImage != null)
            g.drawImage(ventImage, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        else
            drawPlaceholder(g, "VENT VIEW", new Color(15, 20, 15));
    }

    private void drawBlurFrame(Graphics2D g)
    {
        // SHOW BLURRED VERSION OF WHERE WE'RE COMING FROM
        // FIRST HALF OF BLUR FRAMES: SHOW BLURRED OLD VIEW
        // SECOND HALF: SHOW BLURRED NEW VIEW
        BufferedImage blurSource = ventMode ? blurredVent : blurredOffice;

        if(blurSource != null)
            g.drawImage(blurSource, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        else
        {
            // PLACEHOLDER BLUR — JUST A DARKENED FILL
            g.setColor(new Color(10, 10, 10, 200));
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }

        // MOTION LINES TO SELL THE QUICK-LOOK FEEL
        g.setColor(new Color(255, 255, 255, 20));
        for(int x = 0; x < GamePanel.WIDTH; x += 40)
            g.drawLine(x, 0, x + 80, GamePanel.HEIGHT);
    }

    // PLACEHOLDER DRAWN WHEN IMAGES AREN'T LOADED YET
    private void drawPlaceholder(Graphics2D g, String label, Color bg)
    {
        g.setColor(bg);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        g.setColor(new Color(150, 130, 100));
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        Utility.drawCentered(g, "[ " + label + " ]", GamePanel.HEIGHT / 2);

        g.setColor(new Color(100, 90, 70));
        g.setFont(new Font("Monospaced", Font.PLAIN, 13));
        Utility.drawCentered(g, "add /office/" + label.toLowerCase().replace(" ", "") + ".png", GamePanel.HEIGHT / 2 + 30);
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

    // CALLED BY GameState WHEN MASK OR CAMERA COMES UP
    public void forceNormalView()
    {
        ventMode       = false;
        wasInHoverZone = false;
        blurTimer      = 0;
    }

    // --- GETTERS ---
    public boolean isVentMode() { return ventMode; }
}