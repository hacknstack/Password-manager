import java.awt.Color;
public class InputBox extends Box{
    protected Boolean editable = false;
    public InputBox(String message,int topX,int topY,int width,int height,Color backgroundColor,Color textColor){
        super(message, topX, topY, width, height,backgroundColor,textColor);
    }
    public InputBox(String message,int topX,int topY,int width,int height){
        super(message, topX, topY, width, height,new Color(220, 220, 220),Color.BLACK);
    }
    @Override
    public boolean isPositionOnTheBox(int x,int y){
        editable = x>=topX && x<=width+topX && y>=topY && y<=topY+height;
        return editable;
    }
    
    public Boolean canEdit(){
        return editable;
    }
    public void addChar(char c){
        if (editable){
            editMessage(showMessage()+c);
        }
    }
    public void deleteChar(){
        String tempMessage = showMessage();
        if (editable && tempMessage.length()>0){
            editMessage(tempMessage.substring(0, tempMessage.length() - 1));
        }
    }
}
