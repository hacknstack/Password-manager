import java.awt.Color;
import java.awt.Graphics;
public class PasswordBox extends Box{
    protected int copyToClipboardWidth;
    private String website;
    public PasswordBox(String message, int topX, int topY, int width, int height,Color backgroundColor,Color textColor,String website, int copyToClipboardWidth) {
        super(message, topX, topY, width, height,backgroundColor,textColor);
        this.website=website;
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible (take into account extra space for the width because topX is for the normal button)");
        }
    }
    public PasswordBox(String message, int topX, int topY, int width, int height,String website, int copyToClipboardWidth) {
        super(message, topX, topY, width, height);
        this.website=website;
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible (take into account extra space for the width because topX is for the normal button)");
        }
    }
    public Box copyToClipboardBox(String message,Color backgroundColor,Color textColor){
        return new Box(message, topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,backgroundColor,textColor);
    }
    public Box copyToClipboardBox(){
        return new Box("copy", topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,Color.CYAN,this.textColor);
    }
    public Box websiteBox(){
        return new WebsiteBox(website,topX+width,topY,height,height,Color.white,Color.RED);
    }
    public String getWebsite(){
        return website;
    }
    @Override
    public void drawBox(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(topX, topY, width, height);
        g.setColor(textColor);
        int[] center = messageCenter();
        g.drawString(showMessage(), center[0], center[1]);
        copyToClipboardBox().drawBox(g);
        websiteBox().drawBox(g);
    }

}
