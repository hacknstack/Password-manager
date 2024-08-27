import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageBox extends Box{
    private Box parentBox;
    private BufferedImage image;
    public ImageBox(String message, int topX, int topY, int width, int height,Box parentBox) {
        super(message, topX, topY, width, height);
        this.parentBox = parentBox;
    }
    @Override
    public void drawBox(Graphics g){
        if(image==null){
            try {
                // Load the image from the file system
                image = ImageIO.read(new File(showMessage()));
            } catch (IOException e) {
                super.drawBox(g);
                return;
            }
        }
        int side = height;
        g.drawImage(image, topX, topY,side,side, null);
    }
    
}