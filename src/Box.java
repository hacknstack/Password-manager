import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;
public class Box {
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
    private int stringSize(String s,Graphics g){
        return g.getFontMetrics().stringWidth(s);
    }
    public String showMessage(){
        return message;
    }
    public Boolean editMessage(String input){
        message = input;
        return true;
    }
    protected void drawText(String s,Graphics g){
        int start =0;
        int end = 1;
        Stack<String> toDraw = new Stack<String>();
        Stack<Integer> xPos = new Stack<Integer>();
        while(end<=s.length()){
            while( end+1<=s.length()){
                if(stringSize(s.substring(start,end+1),g)<width){
                    end+=1;
                }
                else{
                    break;
                }
                
            }
            toDraw.push(s.substring(start,end));
            System.out.printf(s.substring(start,end)+" to be drawn \n");
            xPos.push(topX+width/2-stringSize(s.substring(start,end),g)/2);
            start=end;
            end+=1;
        }
        int lines = toDraw.size();
        int linePrinted = 1;
        while(!toDraw.isEmpty()){
            int x = xPos.pop();
            int y = Math.round(topY+height*(lines-linePrinted+1)/(lines+1));
            System.out.printf("Here are the coordinates ("+String.valueOf(x)+ " ,"+String.valueOf(y)+ ")\n");
            g.setColor(textColor);
            g.drawString(toDraw.pop(),x,y);
            linePrinted+=1;
        }
        
    }
    public void drawBox(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(topX, topY, width, height);
        drawText(showMessage(), g);
        
    }
    
}
