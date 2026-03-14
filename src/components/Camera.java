package components;

import utilities.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

// CLASS THAT HOLDS A SINGLE CAMERA FOR THE CAMERA SYSTEM
public class Camera
{
    private final String name;
    private BufferedImage image;

    public Camera(String name, String imagePath)
    {
        this.name = name;
        loadImage(imagePath);
    }

    private void loadImage(String imagePath)
    {
        image = Utility.loadImage(imagePath);
    }

    public String getName() { return name; }
    public BufferedImage getImage() { return image; }
}
