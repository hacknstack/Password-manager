import java.awt.Color;
import java.awt.Graphics;

public class PasswordBoxDraft extends InputBox{
    protected int copyToClipboardWidth;
    private InputBox websiteBoxDraft;
    private ImageBox validBox;
    private ImageBox crossBox;
    private String checkmarkPath = "pictures/checkmark.jpg";
    private String crossPath ="pictures/cross.jpg";
    public PasswordBoxDraft(int topX, int topY, int width, int height,Color backgroundColor,Color textColor, int copyToClipboardWidth){
        super("",topX,topY,width,height,backgroundColor,textColor);
        validBox=new ImageBox(checkmarkPath , topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,(Box b)->System.out.printf("swag"));
        websiteBoxDraft= new InputBox("",topX+width,topY,height,height,Color.gray,Color.RED);
        crossBox = new ImageBox(crossPath, topX+width+height, topY, height, height,(Box b)-> {});
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible (take into account extra space for the width because topX is for the normal button)");
        }
    }
    public PasswordBoxDraft(int topX, int topY, int width, int height, int copyToClipboardWidth,String message,String website){
        super(message,topX,topY,width,height);
        validBox=new ImageBox(checkmarkPath , topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,(Box b)->System.out.printf("swag"));
        websiteBoxDraft= new InputBox(website,topX+width,topY,height,height,Color.gray,Color.RED);
        crossBox = new ImageBox(crossPath, topX+width+height, topY, height, height,(Box b)-> {});
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible (take into account extra space for the width because topX is for the normal button)");
        }
    }
    public PasswordBoxDraft(int topX, int topY, int width, int height,int copyToClipboardWidth){
        super("",topX,topY,width,height);
        validBox=new ImageBox(checkmarkPath, topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,(Box b)->System.out.printf("swag"));
        websiteBoxDraft= new InputBox("",topX+width,topY,height,height,Color.gray,Color.RED);
        crossBox = new ImageBox(crossPath, topX+width+height, topY, height, height,(Box b)-> {});
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
    public ImageBox crossBox(){
        return crossBox;
    }
    /* */
    @Override
    public boolean isPositionOnTheBox(int x,int y){
        boolean boxClicked = super.isPositionOnTheBox(x, y);
        boolean websiteBoxClicked = websiteBoxDraft.isPositionOnTheBox(x, y);
        boolean validationBoxClicked = validBox.isPositionOnTheBox(x, y);
        boolean crossBoxClicked = crossBox.isPositionOnTheBox(x, y);
        return boxClicked||websiteBoxClicked||validationBoxClicked||crossBoxClicked;
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
        crossBox.drawBox(g);

    }
    @Override
    public Boolean canEdit(){
        return super.editable||websiteBoxDraft.canEdit();
    }

}