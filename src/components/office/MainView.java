package components.office;

import utilities.Utility;

import java.awt.image.BufferedImage;
import static utilities.Utility.loadImage;

public class MainView
{
    private BufferedImage mainImage;
    private BufferedImage blurredMain;

    public void init()
    {
        mainImage = loadImage("/office/mainImage.png");
        if(mainImage != null) blurredMain = Utility.applyBlur(mainImage);
    }
}
