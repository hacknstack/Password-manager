public class PasswordBox extends Box{
    private int copyToClipboardWidth;
    private String website;
    public PasswordBox(String website, String message, int topX, int topY, int width, int height,int copyToClipboardWidth) {
        super(message, topX, topY, width, height);
        this.website=website;
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible (take into account extra space for the width because topX is for the normal button)");
        }
    }
    public Box copyToClipboardBox(String message){
        return new Box(message, topX-copyToClipboardWidth, topY, copyToClipboardWidth, height);
    }
    public Box copyToClipboardBox(){
        return new Box("copy", topX-copyToClipboardWidth, topY, copyToClipboardWidth, height);
    }
    public String getWebsite(){
        return website;
    }

}
