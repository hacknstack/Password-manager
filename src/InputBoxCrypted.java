import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
public class InputBoxCrypted extends InputBox{
    private String eyeLogo = "pictures/eye.jpg";
    private ImageBox show;
    boolean viewable=false;
    public InputBoxCrypted(String message,int topX,int topY,int width,int height,Color backgroundColor,Color textColor){
        
        super(message, topX, topY, width, height,backgroundColor,textColor);
        show = new ImageBox(eyeLogo, topX+width, topY, height, height, ()-> {this.viewable =!this.viewable; System.out.printf("clicked");});
    }
    public InputBoxCrypted(String message,int topX,int topY,int width,int height){
        super(message,topX,topX,width,height);
        show = new ImageBox(eyeLogo, topX+width, topY, height, height, ()-> {this.viewable =!this.viewable; System.out.printf("clicked");});
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
        if(viewable){
            return showMessageNonCrypted();
        }
        else{
            return new String(new char[super.showMessage().length()]).replace('\0', '#');
        }
    }
    public String showMessageNonCrypted(){
        System.out.printf(super.showMessage());
        return super.showMessage();
    }
    public ImageBox showPasswordBox(){
        return show;
    }
    @Override
    public void drawBox(Graphics g){
        super.drawBox(g);
        show.drawBox(g);
    }
    
}
