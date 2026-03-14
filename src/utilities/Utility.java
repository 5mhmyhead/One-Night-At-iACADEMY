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
    public static void drawCentered(Graphics2D g, String text, int y)
    {
        int x = (GamePanel.WIDTH - g.getFontMetrics().stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }

    public static BufferedImage applyBlur(BufferedImage image)
    {
        float[] matrix = new float[9];
        Arrays.fill(matrix, 1.0f / 9.0f);
        Kernel kernel     = new Kernel(3, 3, matrix);
        ConvolveOp op     = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return op.filter(image, null);
    }

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