import java.awt.Color;
import java.awt.Graphics;
public class PasswordBox extends Box{
    protected int copyToClipboardWidth;
    private WebsiteBox websiteBox;
    private ImageBox editBox;
    private String editPic = "pictures/edit.jpg";
    private Box copyToClipboardBox;
    public PasswordBox(String message, int topX, int topY, int width, int height,Color backgroundColor,Color textColor,String website, int copyToClipboardWidth) {
        super(message, topX, topY, width, height,backgroundColor,textColor);
        this.websiteBox = new WebsiteBox(website,topX+width,topY,height,height,Color.white,Color.RED);
        this.editBox = new ImageBox(editPic, topX+width+websiteBox.width, topY, height, height, null);
        this.copyToClipboardBox = new Box("copy", topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,Color.CYAN,this.textColor);
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible "
            +"(take into account extra space for the width because topX is for the normal button)");
        }
    }
    public PasswordBox(String message, int topX, int topY, int width, int height,String website, int copyToClipboardWidth) {
        super(message, topX, topY, width, height);
        this.websiteBox = new WebsiteBox(website,topX+width,topY,height,height,Color.white,Color.RED);
        this.editBox = new ImageBox(editPic, topX+width+websiteBox.width, topY, height, height, null);
        this.copyToClipboardBox = new Box("copy", topX-copyToClipboardWidth, topY, copyToClipboardWidth, height,Color.CYAN,this.textColor);
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible " 
            +"(take into account extra space for the width because topX is for the normal button)");
        }
    }
    public Box copyToClipboardBox(){
        return copyToClipboardBox;
    }
    public ImageBox getEditButton(){
        return editBox;
    }
    public String getWebsite(){
        return websiteBox.showMessage();
    }
    public PasswordBoxDraft toDraft(String password){
        return new PasswordBoxDraft(topX, topY, width, height, copyToClipboardWidth);
    }
    @Override
    public void drawBox(Graphics g) {
        super.drawBox(g);
        copyToClipboardBox.drawBox(g);
        websiteBox.drawBox(g);
        editBox.drawBox(g);
    }

}
