package utilities;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Utility
{
    // DRAW STRING TO THE CENTER OF SCREEN
    public static void drawCentered(Graphics2D g, String text, int y)
    {
        int x = (GamePanel.WIDTH - g.getFontMetrics().stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }

    // LOAD IMAGE FROM FILE
    public static BufferedImage loadImage(String path)
    {
        try
        {
            return ImageIO.read(Objects.requireNonNull(
                    Utility.class.getResourceAsStream(path)));
        }
        catch(IOException | NullPointerException e)
        {
            System.out.println("Image not found: " + path);
            return null;
        }
    }
}