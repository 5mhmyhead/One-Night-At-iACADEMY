package components;

import main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Office
{
    // --- PANORAMA SETTINGS ---
    // SET THIS TO YOUR ACTUAL OFFICE IMAGE WIDTH ONCE YOU HAVE IT
    // THE ORIGINAL FNAF OFFICE IS ROUGHLY 3x THE SCREEN WIDTH (~3840px)
    // FOR NOW WE USE A PLACEHOLDER WIDTH
    private static final int PANORAMA_WIDTH = 1620;

    // HOW FAR LEFT/RIGHT THE CAMERA CAN PAN FROM CENTER
    private static final int MAX_OFFSET = (PANORAMA_WIDTH - GamePanel.WIDTH) / 2;

    // LERP SPEED — HIGHER = SNAPPIER, LOWER = LAGGIER
    // 0.08 ROUGHLY MATCHES THE ORIGINAL FNAF FEEL
    private static final double LERP_SPEED = 0.08;

    // --- PAN STATE ---
    private double currentOffsetX = 0; // WHAT WE ACTUALLY DRAW WITH
    private double targetOffsetX  = 0; // WHERE THE MOUSE WANTS US TO BE

    // --- DOOR STATE ---
    private boolean leftDoorClosed  = false;
    private boolean rightDoorClosed = false;

    // --- PLACEHOLDER COLOURS ---
    private static final Color OFFICE_BG       = new Color(30, 25, 20);
    private static final Color OFFICE_DESK      = new Color(60, 45, 30);
    private static final Color DOOR_OPEN_COLOR  = new Color(20, 20, 20);
    private static final Color DOOR_CLOSED_COLOR = new Color(80, 60, 10);
    private static final Color DOOR_LABEL_COLOR = new Color(200, 180, 100);
    private static final Color LIGHT_COLOR      = new Color(255, 240, 180, 40);

    public Office() {}

    // --- UPDATE ---

    public void update()
    {
        // LERP currentOffsetX TOWARD targetOffsetX EACH FRAME
        currentOffsetX += (targetOffsetX - currentOffsetX) * LERP_SPEED;
    }

    // --- INPUT ---

    public void mouseMoved(int mouseX, int mouseY)
    {
        // MAP MOUSE X (0 → WIDTH) TO TARGET OFFSET (-MAX_OFFSET → +MAX_OFFSET)
        double percent = (double) mouseX / GamePanel.WIDTH;
        targetOffsetX = (percent - 0.5) * 2 * MAX_OFFSET;
    }

    public void keyPressed(int key)
    {
        if(key == KeyEvent.VK_A) leftDoorClosed  = !leftDoorClosed;
        if(key == KeyEvent.VK_D) rightDoorClosed = !rightDoorClosed;
    }

    // --- DRAWING ---

    public void draw(Graphics2D g)
    {
        // drawX IS THE LEFT EDGE OF THE PANORAMA IN SCREEN SPACE
        // WHEN currentOffsetX = 0, THE PANORAMA IS CENTERED ON SCREEN
        int drawX = (int)((GamePanel.WIDTH - PANORAMA_WIDTH) / 2.0 - currentOffsetX);

        drawPlaceholderOffice(g, drawX);
        drawDoors(g, drawX);
        drawButtons(g, drawX); // DOOR BUTTONS
        drawDesk(g);
        drawDebugInfo(g);
    }

    private void drawButtons(Graphics2D g, int drawX)
    {
        int leftButtonX = drawX + 250;
        int rightButtonX = drawX - 275 + PANORAMA_WIDTH;

        int drawY = 250;
        int buttonSize = 25;

        g.setColor(Color.RED);
        g.drawRect(leftButtonX, drawY, buttonSize, buttonSize);
        g.drawRect(rightButtonX, drawY, buttonSize, buttonSize);
    }

    // PLACEHOLDER OFFICE — REPLACE WITH g.drawImage(officeImage, drawX, 0, null)
    // ONCE YOU HAVE YOUR ACTUAL OFFICE BACKGROUND IMAGE
    private void drawPlaceholderOffice(Graphics2D g, int drawX)
    {
        // BACKGROUND
        g.setColor(OFFICE_BG);
        g.fillRect(drawX, 0, PANORAMA_WIDTH, GamePanel.HEIGHT);

        // SOME SIMPLE DETAIL LINES TO SHOW THE ROOM DEPTH
        g.setColor(new Color(50, 40, 30));
        // FLOOR LINE
        g.fillRect(drawX, GamePanel.HEIGHT - 180, PANORAMA_WIDTH, 4);
        // CEILING LINE
        g.fillRect(drawX, 120, PANORAMA_WIDTH, 4);

        // CENTRE HALLWAY SUGGESTION
        g.setColor(new Color(25, 20, 15));
        int hallX = drawX + PANORAMA_WIDTH / 2 - 120;
        g.fillRect(hallX, 120, 240, GamePanel.HEIGHT - 180 - 120);
    }

    private void drawDoors(Graphics2D g, int drawX)
    {
        // LEFT DOOR — positioned near the left end of the panorama
        int leftDoorX  = drawX + 100;
        int doorY      = 100;
        int doorW      = 140;
        int doorH      = GamePanel.HEIGHT - 280;

        drawSingleDoor(g, leftDoorX, doorY, doorW, doorH, leftDoorClosed, "LEFT");

        // RIGHT DOOR — positioned near the right end of the panorama
        int rightDoorX = drawX + PANORAMA_WIDTH - 100 - doorW;
        drawSingleDoor(g, rightDoorX, doorY, doorW, doorH, rightDoorClosed, "RIGHT");
    }

    private void drawSingleDoor(Graphics2D g, int x, int y, int w, int h,
                                boolean closed, String label)
    {
        // DOOR BODY
        g.setColor(closed ? DOOR_CLOSED_COLOR : DOOR_OPEN_COLOR);
        g.fillRect(x, y, w, h);

        // DOOR BORDER
        g.setColor(new Color(100, 80, 40));
        g.drawRect(x, y, w, h);

        // DOOR LABEL
        g.setColor(DOOR_LABEL_COLOR);
        g.setFont(new Font("Monospaced", Font.BOLD, 13));
        String text = label + (closed ? " [CLOSED]" : " [OPEN]");
        g.drawString(text, x + 4, y - 8);
    }

    private void drawDesk(Graphics2D g)
    {
        // DESK IS FIXED TO THE SCREEN (DOESN'T PAN) — DRAWN LAST SO IT'S ALWAYS ON TOP
        g.setColor(OFFICE_DESK);
        g.fillRect(0, GamePanel.HEIGHT - 160, GamePanel.WIDTH, 160);

        // DESK EDGE LINE
        g.setColor(new Color(80, 60, 35));
        g.fillRect(0, GamePanel.HEIGHT - 160, GamePanel.WIDTH, 4);
    }

    // TEMPORARY DEBUG INFO — REMOVE ONCE OFFICE IMAGE IS IN
    private void drawDebugInfo(Graphics2D g)
    {
        g.setColor(new Color(255, 255, 255, 120));
        g.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g.drawString("OFFSET: " + (int)currentOffsetX + " / ±" + MAX_OFFSET, 10, GamePanel.HEIGHT - 170);
        g.drawString("A = LEFT DOOR   D = RIGHT DOOR", 10, GamePanel.HEIGHT - 155);
    }

    // --- GETTERS ---
    public boolean isLeftDoorClosed()  { return leftDoorClosed; }
    public boolean isRightDoorClosed() { return rightDoorClosed; }
}
