import java.awt.Frame;
import java.security.NoSuchAlgorithmException;

public class CanvasExample {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Create a frame
        Frame frame = new Frame("Canvas Example");

        // Create an instance of the custom canvas
        MyCanvas canvas = new MyCanvas();
        int [] minRes = canvas.minimumNeededResolution();
        canvas.setSize(minRes[0],minRes[1]);

        // Add the canvas to the frame
        frame.add(canvas);

        // Set the size of the frame
        frame.setSize(minRes[0]+150, minRes[1]+300);

        // Make the frame visible
        frame.setVisible(true);
        // Add a window listener to close the window when the close button is clicked
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }
}
