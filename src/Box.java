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
        int centerX= Math.round(topX+width/2 - Math.min(s.length(),maxCharPerLine())*charSize/2);
        int lines = stringLines(s);
        int[] out = new int[lines+1];
        out[0]=centerX;
        for(int i=1;i<=lines;i++){
            int centerY = topY+i*height/(lines+1);
            out[i]=centerY;
        }
        return out;
    }
    private int maxCharPerLine(){
        return Math.round(width/charSize);
    }
    private int stringLines(String s){ // maybe not needed for drawing after (only use maxcharperline maybe)
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
        int x = centers[0];
        for(int k=1;k<centers.length;k++){
            g.setColor(textColor);
            int y = centers[k];
            g.drawString(s.substring((k-1)*maxCharPerLine(),+Math.min(s.length(),k*maxCharPerLine())), x ,y);
        }
    }
    public void drawBox(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(topX, topY, width, height);
        drawText(showMessage(), g);
        
    }
    
}
