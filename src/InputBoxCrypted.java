import java.awt.Color;
public class InputBoxCrypted extends InputBox{
    public InputBoxCrypted(String message,int topX,int topY,int width,int height,Color backgroundColor,Color textColor){
        super(message, topX, topY, width, height,backgroundColor,textColor);
    }
    public InputBoxCrypted(String message,int topX,int topY,int width,int height){
        super(message,topX,topX,width,height);
    }
    @Override
    public String showMessage(){
        return new String(new char[super.showMessage().length()]).replace('\0', '#');
    }
    public String showMessageNonCrypted(){
        return super.showMessage();
    }
    
}
