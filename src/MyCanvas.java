import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
public class MyCanvas extends Canvas {
	
    private PasswordBox[] passwordBoxes;
    private String password = "babe10ued";
    private String cryptedMessage = "###########";
    private PasswordManager manager; 
    
    private int numberOfPasses = 5;
    
    
    private int passwordBoxesSpacing = 25;

    private int firstpasswordBoxTopX = 50;
    private int firstpasswordBoxTopY = 50;
    private int passwordBoxesWidth = 200;
    private int passwordBoxesHeight = 60;
    
    private int copyToClipboardWidth = 40;

    public MyCanvas() {
    	manager = new PasswordManager(password);
    	passwordBoxes = new PasswordBox[numberOfPasses];
        for(int i =0;i<numberOfPasses;i++) {
            String websiteEx = "dooglydoo" + String.valueOf(i) + ".com";
            String passwordEx = "test " + String.valueOf(i);
            int shift = (passwordBoxesSpacing+passwordBoxesHeight)*i;
        	manager.addLocalPasscode(websiteEx,passwordEx);
        	passwordBoxes[i] = new PasswordBox(websiteEx,cryptedMessage,firstpasswordBoxTopX,firstpasswordBoxTopY+shift,passwordBoxesWidth,passwordBoxesHeight,copyToClipboardWidth);
        }
        // Add a mouse listener to handle mouse clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	for(PasswordBox passwordBox : passwordBoxes) {
                	if(passwordBox.isPositionOnTheBox(e.getX(), e.getY())) {
                		passwordBox.message = manager.unveil(password,passwordBox.getWebsite());
                    }
            	}
            	// Repaint the canvas to reflect the change
            	repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	for(PasswordBox passwordBox : passwordBoxes) {
                	passwordBox.message=cryptedMessage;
                	
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
    private static boolean isPrintableChar(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return !Character.isISOControl(c) && block != null && block != Character.UnicodeBlock.SPECIALS;
    }
    public static void copyToClipboard(String input) {
        StringSelection stringSelection = new StringSelection(input);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @Override
    public void paint(Graphics g) {
        // Set the color for the drawing
        
        
        // Draw a rectangle
        for(PasswordBox passwordBox : passwordBoxes) {
        	g.setColor(Color.lightGray);
        	g.fillRect(passwordBox.topX, passwordBox.topY, passwordBoxesWidth, passwordBoxesHeight);
        	g.setColor(Color.BLACK);
            int[] center = passwordBox.messageCenter();
        	g.drawString(passwordBox.message, center[0], center[1]);
        }
        
    }
    public int[] minimumNeededResolution() {
    	int Width = firstpasswordBoxTopX + passwordBoxesWidth;
    	int Height = firstpasswordBoxTopY + passwordBoxesHeight*numberOfPasses + passwordBoxesSpacing*(numberOfPasses-1);
    	int[] out = {Width,Height};
    	System.out.print(Height);
    	return out;
    }
}
