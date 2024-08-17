import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
public class MyCanvas extends Canvas {
	private InputBox inputBox;
    private PasswordBox[] passwordBoxes;
    private String password="babe10ued";
    private String cryptedMessage = "###########";
    private PasswordManager manager; 
    private Box messageBox;
    private int inputBoxHeight = 35;
    private int numberOfPasses = 7;
    private int passwordBoxesSpacing = 25;
    private int firstpasswordBoxTopX = 50;
    private int firstpasswordBoxTopY = 50;
    private int passwordBoxesWidth = 250;
    private int passwordBoxesHeight = 50;
    private int copyToClipboardWidth = 40;

    public MyCanvas() throws NoSuchAlgorithmException {
        inputBox = new InputBox(password,firstpasswordBoxTopX-copyToClipboardWidth,0,passwordBoxesWidth+copyToClipboardWidth,inputBoxHeight);
    	manager = new PasswordManager(password);
    	passwordBoxes = new PasswordBox[numberOfPasses];
        int shift = inputBoxHeight+passwordBoxesSpacing;
        for(int i =0;i<numberOfPasses;i++) {
            String appExample = "google"+ String.valueOf(i);
            String passwordEx = "test " + String.valueOf(i);
            
        	try {
                manager.addLocalPasscode(appExample,passwordEx);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        	passwordBoxes[i] = new PasswordBox(cryptedMessage,firstpasswordBoxTopX,firstpasswordBoxTopY+shift,passwordBoxesWidth,passwordBoxesHeight,appExample,copyToClipboardWidth);
            shift += (passwordBoxesSpacing+passwordBoxesHeight);
        }
        shift+=passwordBoxesSpacing+passwordBoxesHeight;
        messageBox = new Box("",firstpasswordBoxTopX,firstpasswordBoxTopY+shift,passwordBoxesWidth,passwordBoxesHeight,Color.WHITE,Color.BLACK);
        password=inputBox.showMessageNonCrypted();
        // Add a mouse listener to handle mouse clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
            	for(PasswordBox passwordBox : passwordBoxes) {
                	if(passwordBox.isPositionOnTheBox(x,y)) {
                		try {
                            passwordBox.editMessage( manager.unveil(password,passwordBox.getWebsite()));
                        } catch (NoSuchAlgorithmException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        messageBox.editMessage( "");
                        drawBox(getGraphics(), passwordBox);
                        break;
                    }
                    else if(passwordBox.copyToClipboardBox().isPositionOnTheBox(x,y)){
                        try {
                            copyToClipboard(manager.unveil(password,passwordBox.getWebsite()));
                        } catch (NoSuchAlgorithmException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        try {
                            if(manager.canUnveil(password, passwordBox.getWebsite())){
                                messageBox.editMessage( "Copied!");
                            }
                            else{
                                messageBox.editMessage( "Invalid action!");
                            }
                        } catch (NoSuchAlgorithmException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        break;
                    }
                    else if(inputBox.isPositionOnTheBox(x, y)){
                        messageBox.editMessage("Write something !");
                        break;
                    }
                    else{
                        messageBox.editMessage("Mouse position : ("+String.valueOf(x)+","+String.valueOf(y) + ")");
                        
                    }
            	}
                drawBox(getGraphics(), messageBox);
            	// Repaint the canvas to reflect the change
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	for(PasswordBox passwordBox : passwordBoxes) {
                    if(passwordBox.showMessage()!=cryptedMessage){
                        passwordBox.editMessage(cryptedMessage);
                        drawBox(getGraphics(), passwordBox);
                        break;
                    }
                	
                	
                }
            }
        });
     // Add a key listener to handle key events
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (Character.isLetterOrDigit(ch) || Character.isSpaceChar(ch) || isPrintableChar(ch)) {
                    // Only append if it's a printable character
                    inputBox.addChar(ch);
                    password=inputBox.showMessageNonCrypted();
                    // Repaint the canvas to reflect the change
                    drawBox(getGraphics(), inputBox);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_BACK_SPACE) {
                    // Handle backspace to remove the last character
                    inputBox.deleteChar();
                    password=inputBox.showMessageNonCrypted();
                    // Repaint the canvas to reflect the change
                    drawBox(getGraphics(), inputBox);
                }
            }
        });
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
    private BufferedImage downloadImageFromTitle(String title) {
        String imageUrl = generateImageUrl(title);
        BufferedImage img = null;
        try {
            URL url = new URL(imageUrl);
            img = ImageIO.read(url);  // Downloads and reads the image
        } catch (IOException e) {
            System.out.println("Image not found for title: " + title);
            e.printStackTrace();
        }
        return img;
    }
    // Generate a URL for the image related to the title
    private String generateImageUrl(String title) {
        // Basic URL generation for simplicity; you can replace it with any logic or API call
        // Example of using a simple search pattern
        return "https://logo.clearbit.com/" + title.toLowerCase() + ".com";
    }
     // Helper method to create a JFrame to render the JPanel
     public boolean drawImageBox(Graphics g,Box imageBox) {
        String title = imageBox.showMessage();
        int x = imageBox.topX;
        int y = imageBox.topY;
        int side = imageBox.height;
        BufferedImage image = downloadImageFromTitle(title);
        if (image != null) {
            // Get panel dimensions

            // Resize and draw the image in the square
            g.drawImage(image, x, y, side, side, this);
            return true;
        } else {
            // Fallback if image was not found
            //BufferedImage image = new BufferedImage(side, side, y);
            drawBox(g, imageBox);
            return false;
        }
    }

    @Override
    public void paint(Graphics g) {
        
        drawBox(g, inputBox);
        // Set the color for the drawing
        // Draw thoses rectangles
        for(PasswordBox passwordBox : passwordBoxes) {

            //draw password boxes
        	drawBox(g, passwordBox);

            //Draw copy To clipboard boxes
            Box copyBox = passwordBox.copyToClipboardBox();
            drawBox(g, copyBox);
            Box imageBox = passwordBox.websiteBox();
            drawImageBox(g,imageBox);
        }
        //Draw message Box:
        
        drawBox(g, messageBox);
    }
    public void drawBox(Graphics g,Box box) {
        g.setColor(box.backgroundColor);
        g.fillRect(box.topX, box.topY, box.width, box.height);
        g.setColor(box.textColor);
        int[] center = box.messageCenter();
        g.drawString(box.showMessage(), center[0], center[1]);
    }
    public int[] minimumNeededResolution() {
    	int[] out = {messageBox.width+messageBox.topX,messageBox.height+messageBox.topY};
    	return out;
    }
}
