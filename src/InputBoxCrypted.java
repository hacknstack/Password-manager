import java.awt.Color;
import java.awt.Graphics;
public class InputBoxCrypted extends InputBox{
    private String eyeLogoOpened = "pictures/eye.jpg";
    private String eyeLogoClosed = "pictures/eye-closed.jpg";
    private ImageBox show;
    public boolean viewable=false;
    public InputBoxCrypted(String message,int topX,int topY,int width,int height,Color backgroundColor,Color textColor){
        
        super(message, topX, topY, width, height,backgroundColor,textColor);
        show = new ImageBox(eyeLogoOpened, topX+width, topY, height, height, (Box b)-> {this.viewable =!this.viewable; System.out.printf("swag");
         if(viewable){ b.editMessage(eyeLogoClosed);}
        else{
            b.editMessage(eyeLogoOpened);
        }});
    }
    public InputBoxCrypted(String message,int topX,int topY,int width,int height){
        super(message,topX,topX,width,height);
        show = new ImageBox(eyeLogoOpened, topX+width, topY, height, height, (Box b)-> {this.viewable =!this.viewable; System.out.printf("swag");
        if(viewable){ b.editMessage(eyeLogoClosed);}
       else{
           b.editMessage(eyeLogoOpened);
       }});
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
        return super.showMessage();
    }
    public ImageBox showPasswordBox(){
        return show;
    }
    @Override
    public boolean isPositionOnTheBox(int x,int y){
        System.out.printf("clicked on -_-    ");
        boolean stuff = show.isPositionOnTheBox(x, y);
        return super.isPositionOnTheBox(x, y)||stuff;
    }
    @Override
    public void drawBox(Graphics g){
        super.drawBox(g);
        show.drawBox(g);
    }
    
}
