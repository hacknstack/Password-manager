import java.awt.Color;
public class InputBoxCrypted extends InputBox{
    public InputBoxCrypted(String message,int topX,int topY,int width,int height,Color backgroundColor,Color textColor){
        super(message, topX, topY, width, height,backgroundColor,textColor);
    }
    public InputBoxCrypted(String message,int topX,int topY,int width,int height){
        super(message,topX,topX,width,height);
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
    
}
