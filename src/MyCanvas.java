import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.*;
public class MyCanvas extends Canvas {
	
    private Box[] boxes;
    private String password = "babe10ued";
    private String cryptedMessage = "###########";
    private PasswordManager manager; 
    
    private int numberOfPasses = 5;
    
    private int boxesWidth = 200;
    private int boxesHeight = 75;
    private int boxesSpacing = 150;
    private int firstBoxTopX = 50;
    private int firstBoxTopY = 50;
    

    public MyCanvas() {
    	manager = new PasswordManager(password);
    	boxes = new Box[numberOfPasses];
        for(int i =0;i<numberOfPasses;i++) {
            String websiteEx = "dooglydoo" + String.valueOf(i) + ".com";
            String passwordEx = "test " + String.valueOf(i);
            int shift = boxesSpacing*i;
        	manager.addLocalPasscode(websiteEx,passwordEx);
        	boxes[i] = new Box(websiteEx,cryptedMessage,firstBoxTopX,firstBoxTopY+shift,boxesWidth,boxesHeight,boxesSpacing);
        }
        // Add a mouse listener to handle mouse clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	for(Box box : boxes) {
                	if(box.isPositionOnTheBox(e.getX(), e.getY())) {
                		box.message = manager.unveil(password,box.getWebsite());
                    }
            	}
            	// Repaint the canvas to reflect the change
            	repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	for(Box box : boxes) {
                	box.message=cryptedMessage;
                	
                }
                repaint();
            }
        });
     /* Add a key listener to handle key events
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (Character.isLetterOrDigit(ch) || Character.isSpaceChar(ch) || isPrintableChar(ch)) {
                    // Only append if it's a printable character
                    message += ch;
                    // Repaint the canvas to reflect the change
                    repaint();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_BACK_SPACE && message.length() > 0) {
                    // Handle backspace to remove the last character
                    message = message.substring(0, message.length() - 1);
                    // Repaint the canvas to reflect the change
                    repaint();
                }
            }
        });*/
    }

    // A helper method to check if a character is printable
    private boolean isPrintableChar(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return !Character.isISOControl(c) && block != null && block != Character.UnicodeBlock.SPECIALS;
    }

    @Override
    public void paint(Graphics g) {
        // Set the color for the drawing
        
        
        // Draw a rectangle
        for(Box box : boxes) {
        	g.setColor(Color.RED);
        	g.fillRect(box.boxTopX, box.boxTopY, boxesWidth, boxesHeight);
        	g.setColor(Color.BLACK);
            int[] center = box.messageCenter();
        	g.drawString(box.message, center[0], center[1]);
        }
        
    }
    public int[] minimumNeededResolution() {
    	int Width = firstBoxTopX + boxesWidth;
    	int Height = firstBoxTopY + boxesHeight*numberOfPasses + boxesSpacing*(numberOfPasses-1);
    	int[] out = {Width,Height};
    	System.out.print(Height);
    	return out;
    }
}
