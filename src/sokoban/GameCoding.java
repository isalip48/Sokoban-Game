package sokoban;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GameCoding {
    private BufferedImage img;
    public GameCoding elementOnTop;
    
    public GameCoding(String imageFileName)
    {
        try
        {
            img = ImageIO.read(new File(imageFileName));
        }
        catch (Exception e)
        {
            
        }
    }
    
    BufferedImage getImage()
    {
        return img;
    }
}
