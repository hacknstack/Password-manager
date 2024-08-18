import java.awt.Color;
import java.awt.Graphics;
public class Box {
    private float charSize = 78.0f/11.0f;

    public int width;
    public int height;
    public int topX;
    public int topY;
    private String message;
    public Color backgroundColor;
    public Color textColor;
    public Box(String message,int topX,int topY,int width,int height,Color backgroundColor,Color textColor){
        if(width>0&&height>0&&topX>=0&&topY>=0){
            this.message = message;
            this.width = width;
            this.height = height;
            this.topX = topX;
            this.topY = topY;
            this.backgroundColor=backgroundColor;
            this.textColor=textColor;
        }
        else{
            throw new IllegalArgumentException("parts of the box are negative integers");
        }
    }
    public Box(String message,int topX,int topY,int width,int height){
        this(message,topX,topY,width,height,Color.lightGray,Color.BLACK);
    }
    public boolean isPositionOnTheBox(int x,int y){
        return x>=topX && x<=width+topX && y>=topY && y<=topY+height;
    }
    protected int[] messageCenter(){
        int centerX= Math.round(topX+width/2 - message.length()*charSize/2);
        int centerY= topY+height/2;
        int[] out = {centerX,centerY};
        return out;
    }
    public String showMessage(){
        return message;
    }
    public Boolean editMessage(String input){
        message = input;
        return true;
    }
    public void drawBox(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(topX, topY, width, height);
        g.setColor(textColor);
        int[] center = messageCenter();
        g.drawString(showMessage(), center[0], center[1]);
    }
    
}
