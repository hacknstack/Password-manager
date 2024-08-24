import java.awt.Color;
import java.awt.Graphics;
public class Box {
    private float charSize = 78.0f/11.0f;
    private String message;

    public int width;
    public int height;
    public int topX;
    public int topY;
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
    protected int[] stringCenters(String s){
        int centerX= Math.round(topX+width/2 - s.length()*charSize/2);
        int lines = stringLines(s);
        int[] out = new int[lines*2];
        for(int i=1;i<=lines;i++){
            int centerY = topY+i*height/(lines+1);
            out[2*i-2]=centerX;
            out[2*i-1]=centerY;
        }
        return out;
    }
    private int maxCharPerLine(){
        return Math.round(width/charSize);
    }
    private int stringLines(String s){
        int lines = 1;
        while(s.length()>maxCharPerLine()*lines){
            lines++;
        }
        return lines;
    }
    public String showMessage(){
        return message;
    }
    public Boolean editMessage(String input){
        message = input;
        return true;
    }
    protected void drawText(String s,Graphics g){
        int[] centers = stringCenters(s);
        for(int k=0;2*k<centers.length;k++){
            g.setColor(textColor);
            g.drawString(s, centers[2*k], centers[2*k+1]);
        }
    }
    public void drawBox(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(topX, topY, width, height);
        drawText(message, g);
        
    }
    
}
