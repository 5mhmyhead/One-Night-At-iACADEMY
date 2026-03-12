package components;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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
        try
        {
            image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream(imagePath)));
        }
        catch(IOException | NullPointerException e)
        {
            System.out.println("Camera image not found: " + imagePath);
        }
    }

    public String getName() { return name; }
    public BufferedImage getImage() { return image; }
}
