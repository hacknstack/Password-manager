import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;
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
    private Box messageBox;
    
    private int numberOfPasses = 10;
    private int passwordBoxesSpacing = 25;
    private int firstpasswordBoxTopX = 50;
    private int firstpasswordBoxTopY = 50;
    private int passwordBoxesWidth = 300;
    private int passwordBoxesHeight = 60;
    private int copyToClipboardWidth = 40;

    public MyCanvas() {
    	manager = new PasswordManager(password);
    	passwordBoxes = new PasswordBox[numberOfPasses];
        int shift = 0;
        for(int i =0;i<numberOfPasses;i++) {
            String websiteEx = "dooglydoo" + String.valueOf(i) + ".com";
            String passwordEx = "test " + String.valueOf(i);
            shift = (passwordBoxesSpacing+passwordBoxesHeight)*i;
        	manager.addLocalPasscode(websiteEx,passwordEx);
        	passwordBoxes[i] = new PasswordBox(cryptedMessage,firstpasswordBoxTopX,firstpasswordBoxTopY+shift,passwordBoxesWidth,passwordBoxesHeight,websiteEx,copyToClipboardWidth);
        }
        shift+=passwordBoxesSpacing+passwordBoxesHeight;
        messageBox = new Box("",firstpasswordBoxTopX,firstpasswordBoxTopY+shift,passwordBoxesWidth,passwordBoxesHeight,Color.WHITE,Color.BLACK);
        // Add a mouse listener to handle mouse clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
            	for(PasswordBox passwordBox : passwordBoxes) {
                	if(passwordBox.isPositionOnTheBox(x,y)) {
                		passwordBox.message = manager.unveil(password,passwordBox.getWebsite());
                        messageBox.message = "";
                        break;
                    }
                    else if(passwordBox.copyToClipboardBox().isPositionOnTheBox(x,y)){
                        copyToClipboard(manager.unveil(password,passwordBox.getWebsite()));
                        messageBox.message = "Copied!";
                        break;
                    }
                    else{
                        messageBox.message = "Mouse position : ("+String.valueOf(x)+","+String.valueOf(y) + ")";
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
        // Draw thoses rectangles
        for(PasswordBox passwordBox : passwordBoxes) {

            //draw password boxes
        	drawBox(g, passwordBox);

            //Draw copy To clipboard boxes
            Box copyBox = passwordBox.copyToClipboardBox();
            drawBox(g, copyBox);
        }
        //Draw message Box:
        drawBox(g, messageBox);
    }
    public void drawBox(Graphics g,Box box) {
        g.setColor(box.backgroundColor);
        g.fillRect(box.topX, box.topY, box.width, box.height);
        g.setColor(box.textColor);
        int[] center = box.messageCenter();
        g.drawString(box.message, center[0], center[1]);
    }
    public int[] minimumNeededResolution() {
    	int Width = firstpasswordBoxTopX + passwordBoxesWidth;
    	int Height = firstpasswordBoxTopY + passwordBoxesHeight*(numberOfPasses+1) + passwordBoxesSpacing*numberOfPasses;
    	int[] out = {Width,Height};
    	return out;
    }
}
