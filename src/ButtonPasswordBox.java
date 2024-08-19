import java.awt.Color;
import java.awt.Graphics;

public class ButtonPasswordBox extends Box{


    public ButtonPasswordBox(String message,int topX,int topY,int width,int height){
        super(message, topX, topY, width, height);
    }
    public ButtonPasswordBox(String message,int topX,int topY,int width,int height,Color backgroundColor,Color textColor) {
        super(message, topX, topY, width, height, backgroundColor, textColor);
    }

    public PasswordBox newPasswordBox(String message,String website,int spacing,int copyToClipboardWidth){
        PasswordBox out = new PasswordBox(message,topX,topY,width,height,website,copyToClipboardWidth);
        this.topY += spacing;
        return out;
    }
}
