public class PasswordBox extends Box{
    private int copyToClipboardWidth;
    public PasswordBox(String website, String message, int topX, int topY, int width, int height,int copyToClipboardWidth) {
        super(website, message, topX, topY, width, height);
        if(topX-copyToClipboardWidth>0){
            this.copyToClipboardWidth = copyToClipboardWidth;
        }
        else{
            throw new IllegalArgumentException("copy to clipboard button must still entirely be visible (take into account extra space for the width because topX is for the normal button)");
        }
    }
    public boolean isPositionOnTheCopyToClipboardBox(int x,int y){
        return x<=topX && x>= topX-copyToClipboardWidth && y>=topY && y<=topY+height;
    }

}
