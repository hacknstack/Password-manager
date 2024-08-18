import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class WebsiteBox extends Box{

    public WebsiteBox(String message, int topX, int topY, int width, int height,Color backgroundColor,Color textColor) {
        super(message, topX, topY, width, height,backgroundColor, textColor);
    }
    private BufferedImage downloadImageFromTitle(String title) {
        String imageUrl = generateImageUrl(title);
        BufferedImage img = null;
        try {
            URL url = new URL(imageUrl);
            img = ImageIO.read(url);  // Downloads and reads the image
        } catch (IOException e) {
            System.out.println("Image not found for title: " + title);
            e.printStackTrace();
        }
        return img;
    }
    // Generate a URL for the image related to the title
    private String generateImageUrl(String title) {
        // Basic URL generation for simplicity; you can replace it with any logic or API call
        // Example of using a simple search pattern
        return "https://logo.clearbit.com/" + title.toLowerCase() + ".com";
    }
    @Override
    public void drawBox(Graphics g) {
        String title = showMessage();
        int x = topX;
        int y = topY;
        int side = height;
        BufferedImage image = downloadImageFromTitle(title);
        if (image != null) {
            // Get panel dimensions

            // Resize and draw the image in the square
            g.drawImage(image, x, y, side, side, null);
        } else {
            // Fallback if image was not found
            //BufferedImage image = new BufferedImage(side, side, y);
            super.drawBox(g);
        }
    }
}
