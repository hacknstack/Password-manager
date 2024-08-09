
public class Box {
    private int charSize = 3;

    public int width;
    public int height;
    public int topX;
    public int topY;
    public String message;
    public Box(String message,int topX,int topY,int width,int height){
        this.message = message;
        this.width = width;
        this.height = height;
        this.topX = topX;
        this.topY = topY;
    }
    public boolean isPositionOnTheBox(int x,int y){
        return x>=topX && x<=width+topX && y>=topY && y<=topY+height;
    }
    public int[] messageCenter(){
        int centerX= topX+width/2 - message.length()*charSize;
        int centerY= topY+height/2;
        int[] out = {centerX,centerY};
        return out;
    }
    
}
