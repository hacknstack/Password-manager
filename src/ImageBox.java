import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.server.Operation;

import javax.imageio.ImageIO;

public class ImageBox extends Box{
    private Action action;
    private BufferedImage image;
    public boolean toDraw;
    public ImageBox(String message, int topX, int topY, int width, int height,Action action) {
        
        super(message, topX, topY, width, height);
        this.toDraw=true;
        this.action = action;
    }
    @Override
    public void drawBox(Graphics g){
        if(image==null ||toDraw){
            try {
                // Load the image from the file system
                image = ImageIO.read(new File(showMessage()));
            } catch (IOException e) {
                super.drawBox(g);
                return;
            }
            toDraw=false;
            
        }
        int side = height;
        g.drawImage(image, topX, topY,side,side, null);
    }
    @Override
    public Boolean editMessage(String input){
        toDraw=true;
        return super.editMessage(input);
    }
    @Override
    public boolean isPositionOnTheBox(int x,int y){
        boolean isOnBox = super.isPositionOnTheBox(x, y);
        if(isOnBox){
            try{
                (action).boxAction(this);
            }
            catch(Exception e){
                System.out.printf("undefined image action");
            }
        }
        return isOnBox;
    }
}