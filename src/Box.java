
public class Box {
    public int boxWidth;
    public int boxHeight;
    public int boxSpacing;
    public int boxTopX;
    public int boxTopY;
    public String message;
    private String website;
    private int charSize = 3;
    public Box(String website,String message,int boxTopX,int boxTopY,int boxWidth,int boxHeight,int boxSpacing){
        this.website = website;
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
    public String getWebsite(){
        return website;
    }
    public int[] messageCenter(){
        int centerX= boxTopX+boxWidth/2 - message.length()*charSize;
        int centerY= boxTopY+boxHeight/2;
        int[] out = {centerX,centerY};
        return out;
    }
    
}
