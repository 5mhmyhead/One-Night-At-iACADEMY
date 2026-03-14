package components.office;

import utilities.Utility;

import java.awt.image.BufferedImage;

public class VentView
{
    private BufferedImage ventImage;
    private BufferedImage blurredVent;

    public void init()
    {
        ventImage   = Utility.loadImage("/office/vent.png");
        if(ventImage != null) blurredVent = Utility.applyBlur(ventImage);
    }
}
