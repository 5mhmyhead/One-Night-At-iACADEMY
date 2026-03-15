package components.office;

import main.GamePanel;
import utilities.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VentView
{
    private BufferedImage ventImage;

    public VentView()
    {
        init();
    }

    public void init()
    {
        ventImage   = Utility.loadImage("/office/vent.png");
    }

    public void draw(Graphics2D g)
    {
        if(ventImage != null)
            g.drawImage(ventImage, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        else
            drawPlaceholder(g, new Color(30, 25, 20));
    }

    private void drawPlaceholder(Graphics2D g, Color bg)
    {
        g.setColor(bg);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        g.setColor(new Color(150, 130, 100));
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        Utility.drawCentered(g, "[ " + "VENT VIEW" + " ]", GamePanel.HEIGHT / 2);

        g.setColor(new Color(100, 90, 70));
        g.setFont(new Font("Monospaced", Font.PLAIN, 13));
        Utility.drawCentered(g, "add /office/" + "VENT VIEW".toLowerCase().replace(" ", "") + ".png", GamePanel.HEIGHT / 2 + 30);
    }
}
