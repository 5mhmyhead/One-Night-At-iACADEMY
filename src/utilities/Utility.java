package utilities;

import main.GamePanel;
import java.awt.*;

public class Utility
{
    public static void drawCentered(Graphics2D g, String text, int y)
    {
        int x = (GamePanel.WIDTH - g.getFontMetrics().stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }
}