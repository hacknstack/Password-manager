import java.awt.Color;
import java.awt.Graphics;

public class PasswordBoxDraft extends InputBox{
    protected int copyToClipboardWidth;
    private InputBox websiteBoxDraft;
    public PasswordBoxDraft(int topX, int topY, int width, int height,Color backgroundColor,Color textColor, int copyToClipboardWidth){
        super("",topX,topY,width,height,backgroundColor,textColor);
        websiteBoxDraft= new InputBox("",topX+width,topY,height,height,Color.gray,Color.RED);
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible (take into account extra space for the width because topX is for the normal button)");
        }
    }
    public PasswordBoxDraft(String message,int topX, int topY, int width, int height,Color backgroundColor,Color textColor, int copyToClipboardWidth){
        super(message,topX,topY,width,height,backgroundColor,textColor);
        websiteBoxDraft= new InputBox("",topX+width,topY,height,height,Color.gray,Color.RED);
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible (take into account extra space for the width because topX is for the normal button)");
        }
    }
    public PasswordBoxDraft(int topX, int topY, int width, int height,int copyToClipboardWidth){
        super("",topX,topY,width,height);
        websiteBoxDraft= new InputBox("",topX+width,topY,height,height,Color.gray,Color.RED);
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible (take into account extra space for the width because topX is for the normal button)");
        }
    }
    private Box copyToClipboardBox(){
        return new WebsiteBox("GetAccept", topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,Color.CYAN,this.textColor);
    }
    public Box copyToClipboardBox(String message,Color backgroundColor,Color textColor){
        return new Box(message, topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,backgroundColor,textColor);
    }
    /* */
    @Override
    public boolean isPositionOnTheBox(int x,int y){
        return super.isPositionOnTheBox(x, y)||websiteBoxDraft.isPositionOnTheBox(x, y)||copyToClipboardBox().isPositionOnTheBox(x, y);
    }
    @Override
    public String showMessage(){
        return showMessageNonCrypted();
    }
    @Override
    public void addChar(char c){
        super.addChar(c);
        websiteBoxDraft.addChar(c);
    }
    @Override
    public void deleteChar(){
        super.deleteChar();
        websiteBoxDraft.deleteChar();
    }
    @Override
    public void drawBox(Graphics g){
        super.drawBox(g);
        copyToClipboardBox().drawBox(g);
        websiteBoxDraft.drawBox(g);

    }

}