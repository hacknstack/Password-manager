import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
public class InputBoxCrypted extends InputBox{
    private String eyeLogo = "cbs";
    private WebsiteBox show;
    public InputBoxCrypted(String message,int topX,int topY,int width,int height,Color backgroundColor,Color textColor){
        
        super(message, topX, topY, width, height,backgroundColor,textColor);
        show = new WebsiteBox(eyeLogo, topX+width, topY, height, height, Color.white, Color.red);
    }
    public InputBoxCrypted(String message,int topX,int topY,int width,int height){
        super(message,topX,topX,width,height);
        show = new WebsiteBox(eyeLogo, topX+width, topY, height, height, Color.white, Color.red);
    }
    @Override
    public void addChar(char c){
        if (editable){
            editMessage(showMessageNonCrypted()+c);
        }
    }
    @Override
    public void deleteChar(){
        String tempMessage = showMessageNonCrypted();
        if (editable && tempMessage.length()>0){
            editMessage(tempMessage.substring(0, tempMessage.length() - 1));
        }
    }
    @Override
    public String showMessage(){
        return new String(new char[super.showMessage().length()]).replace('\0', '#');
    }
    public String showMessageNonCrypted(){
        System.out.printf(super.showMessage());
        return super.showMessage();
    }
    public WebsiteBox showPasswordBox(){
        return show;
    }
    @Override
    public void drawBox(Graphics g){
        super.drawBox(g);
        show.drawBox(g);
    }
    
}
