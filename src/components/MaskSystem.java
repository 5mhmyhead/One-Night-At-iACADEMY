package components;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class MaskSystem
{
    private boolean maskUp = false;
    private boolean wasInHoverZone = false;

    // HOVER ZONE DIMENSIONS
    private static final int HOVER_ZONE_Y      = GamePanel.HEIGHT - 60;
    private static final int HOVER_ZONE_X_MIN  = (int)(GamePanel.WIDTH * 0.55);
    private static final int HOVER_ZONE_X_MAX  = (int)(GamePanel.WIDTH * 0.95);

    private static final Color HOVER_HINT_COLOR = new Color(255, 255, 255, 60);

    public MaskSystem()
    {
        init();
    }

    public void init()
    {

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
            maskUp = !maskUp;

        wasInHoverZone = inHoverZone;
    }

    public void draw(Graphics2D g, boolean showHints)
    {
        if(maskUp) drawMask(g);
        if(showHints) drawHoverZone(g);
    }

    private void drawMask(Graphics2D g)
    {
        int centerX = GamePanel.WIDTH / 2;
        int centerY = GamePanel.HEIGHT / 2;

        // DEFINE THE FULL SCREEN RECTANGLE AS THE MASK SHAPE
        Area maskArea = new Area(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT));

        // SUBTRACT THE EYEHOLES FROM THE MASK SHAPE
        maskArea.subtract(new Area(new Ellipse2D.Double(centerX - 280, centerY - 200, 220, 180)));
        maskArea.subtract(new Area(new Ellipse2D.Double(centerX + 60,  centerY - 200, 220, 180)));

        // DRAW THE MASK WITH HOLES — BACKGROUND SHOWS THROUGH THE CUTOUTS
        g.setColor(new Color(40, 20, 10));
        g.fill(maskArea);

        // BROW RIDGES
        g.setColor(new Color(55, 30, 12));
        g.setStroke(new BasicStroke(10));
        g.drawArc(centerX - 290, centerY - 220, 240, 120, 10, 160);
        g.drawArc(centerX + 50,  centerY - 220, 240, 120, 10, 160);
        g.setStroke(new BasicStroke(1));

        // DEBUG LABEL
        g.setColor(new Color(255, 255, 255, 80));
        g.setFont(new Font("Monospaced", Font.BOLD, 13));
        g.drawString("[ MASK ON ]", centerX - 45, GamePanel.HEIGHT - 40);
    }

    // HOVER ZONE AT THE BOTTOM OF THE SCREEN
    private void drawHoverZone(Graphics2D g)
    {
        g.setColor(HOVER_HINT_COLOR);
        g.fillRect(HOVER_ZONE_X_MIN, HOVER_ZONE_Y,
                HOVER_ZONE_X_MAX - HOVER_ZONE_X_MIN,
                GamePanel.HEIGHT - HOVER_ZONE_Y);
    }

    // FORCES THE MASK DOWN
    public void forceDown()
    {
        maskUp = false;
        wasInHoverZone = false;
    }

    // GETTERS
    public boolean isMaskUp() { return maskUp; }
}
