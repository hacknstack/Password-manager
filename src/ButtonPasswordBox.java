import java.awt.Color;
import java.awt.Graphics;

public class ButtonPasswordBox extends InputBox{


    public ButtonPasswordBox(String message,int topX,int topY,int width,int height){
        super(message, topX, topY, width, height);
    }
    public ButtonPasswordBox(String message,int topX,int topY,int width,int height,Color backgroundColor,Color textColor) {
        super(message, topX, topY, width, height, backgroundColor, textColor);
    }

    public PasswordBoxDraft newPasswordBoxDraft(int spacing,int copyToClipboardWidth){
        PasswordBoxDraft out = new PasswordBoxDraft(topX,topY,width,height,copyToClipboardWidth);
        this.topY += spacing;
        return out;
    }
}
