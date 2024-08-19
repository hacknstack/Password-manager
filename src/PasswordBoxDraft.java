import java.awt.Color;

public class PasswordBoxDraft extends InputBox{
    protected int copyToClipboardWidth;
    public PasswordBoxDraft(int topX, int topY, int width, int height,Color backgroundColor,Color textColor, int copyToClipboardWidth){
        super("",topX,topY,width,height,backgroundColor,textColor);
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible (take into account extra space for the width because topX is for the normal button)");
        }
    }
    public PasswordBoxDraft(int topX, int topY, int width, int height,int copyToClipboardWidth){
        super("",topX,topY,width,height);
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible (take into account extra space for the width because topX is for the normal button)");
        }
    }
    public InputBox websiteBoxDraft(){
        return new InputBox("",topX+width,topY,height,height,Color.white,Color.RED);
    }
    public Box copyToClipboardBox(){
        return new Box("new password :", topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,Color.CYAN,this.textColor);
    }
    public Box copyToClipboardBox(String message,Color backgroundColor,Color textColor){
        return new Box(message, topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,backgroundColor,textColor);
    }
}