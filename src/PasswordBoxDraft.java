import java.awt.Color;
import java.awt.Graphics;

public class PasswordBoxDraft extends InputBox{
    protected int copyToClipboardWidth;
    private InputBox websiteBoxDraft;
    private ImageBox validBox;
    private String relativePath = "pictures/checkmark.png";
    public PasswordBoxDraft(int topX, int topY, int width, int height,Color backgroundColor,Color textColor, int copyToClipboardWidth){
        super("",topX,topY,width,height,backgroundColor,textColor);
        websiteBoxDraft= new InputBox("",topX+width,topY,height,height,Color.gray,Color.RED);
        validBox=new ImageBox(relativePath, topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,()->System.out.printf("swag"));
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
        validBox=new ImageBox(relativePath, topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,()->System.out.printf("swag"));
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
        validBox=new ImageBox(relativePath, topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,()->System.out.printf("swag"));
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible (take into account extra space for the width because topX is for the normal button)");
        }
    }
    public PasswordBox createPasswordBox(){
        return new PasswordBox(showMessage(), topX, topY, width, height, websiteBoxDraft.showMessage(), copyToClipboardWidth);
    }
    public ImageBox validationBox(){
        return validBox;
    }
    /* */
    @Override
    public boolean isPositionOnTheBox(int x,int y){
        boolean boxClicked = super.isPositionOnTheBox(x, y);
        boolean websiteBoxClicked = websiteBoxDraft.isPositionOnTheBox(x, y);
        boolean copyToClipboardBoxClicked = validBox.isPositionOnTheBox(x, y);
        return boxClicked||websiteBoxClicked||copyToClipboardBoxClicked;
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
        validBox.drawBox(g);
        websiteBoxDraft.drawBox(g);

    }
    @Override
    public Boolean canEdit(){
        return super.editable||websiteBoxDraft.canEdit();
    }

}