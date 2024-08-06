
public class Box {
    public int boxWidth;
    public int boxHeight;
    public int boxSpacing;
    public int boxTopX;
    public int boxTopY;
    public String message;
    public Box(String message,int boxTopX,int boxTopY,int boxWidth,int boxHeight,int boxSpacing){
        this.message = message;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.boxSpacing = boxSpacing;
        this.boxTopX = boxTopX;
        this.boxTopY = boxTopY;
    }
    public boolean isPositionOnTheBox(int x,int y){
        return x>=boxTopX && x<=boxWidth+boxTopX && y>=boxTopY && y<=boxTopY+boxHeight;
    }
    
}
