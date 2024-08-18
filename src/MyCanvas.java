import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
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
    private ButtonPasswordBox messageBox;
    private int inputBoxHeight = 35;
    private int numberOfPasses = 7;
    private int passwordBoxesSpacing = 25;
    private int firstpasswordBoxTopX = 50;
    private int firstpasswordBoxTopY = 50;
    private int passwordBoxesWidth = 250;
    private int passwordBoxesHeight = 50;
    private int copyToClipboardWidth = 40;
    private int shift = inputBoxHeight+passwordBoxesSpacing;

    public MyCanvas() throws NoSuchAlgorithmException {
        inputBox = new InputBox(password,firstpasswordBoxTopX-copyToClipboardWidth,0,passwordBoxesWidth+copyToClipboardWidth,inputBoxHeight);
    	manager = new PasswordManager(password);
    	passwordBoxes = new PasswordBox[numberOfPasses];
        
        for(int i =0;i<numberOfPasses;i++) {
            String appExample = "google"+ String.valueOf(i);
            String passwordEx = "test " + String.valueOf(i);
            
        	try {
                manager.addLocalPasscode(appExample,passwordEx,password);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        	passwordBoxes[i] = new PasswordBox(cryptedMessage,firstpasswordBoxTopX,firstpasswordBoxTopY+shift,passwordBoxesWidth,passwordBoxesHeight,appExample,copyToClipboardWidth);
            shift += (passwordBoxesSpacing+passwordBoxesHeight);
        }
        shift+=passwordBoxesSpacing+passwordBoxesHeight;
        messageBox = new ButtonPasswordBox("",firstpasswordBoxTopX,firstpasswordBoxTopY+shift,passwordBoxesWidth,passwordBoxesHeight,Color.WHITE,Color.BLACK);
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
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        messageBox.editMessage( "");
                        passwordBox.drawBox(getGraphics());
                        break;
                    }
                    else if(passwordBox.copyToClipboardBox().isPositionOnTheBox(x,y)){
                        try {
                            copyToClipboard(manager.unveil(password,passwordBox.getWebsite()));
                        } catch (Exception e1) {
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
                if (messageBox.isPositionOnTheBox(x, y)){
                    numberOfPasses+=1;
                    passwordBoxes=Arrays.copyOf(passwordBoxes, numberOfPasses);
                    
                    passwordBoxes[numberOfPasses-1]= messageBox.newPasswordBox(cryptedMessage, "google", shift, copyToClipboardWidth);
                    passwordBoxes[numberOfPasses-1].drawBox(getGraphics());
                }
                messageBox.drawBox(getGraphics());
            	// Repaint the canvas to reflect the change
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	for(PasswordBox passwordBox : passwordBoxes) {
                    if(passwordBox.showMessage()!=cryptedMessage){
                        passwordBox.editMessage(cryptedMessage);
                        passwordBox.drawBox(getGraphics());
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
                    inputBox.drawBox(getGraphics());
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
                    inputBox.drawBox(getGraphics());
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
    
     

    @Override
    public void paint(Graphics g) {

        inputBox.drawBox(g);
        // Set the color for the drawing
        // Draw thoses rectangles
        for(PasswordBox passwordBox : passwordBoxes) {

            //draw password boxes
        	passwordBox.drawBox(g);
            //Draw message Box:
        }
        
        messageBox.drawBox(g);
    }
    public int[] minimumNeededResolution() {
    	int[] out = {messageBox.width+messageBox.topX,messageBox.height+messageBox.topY};
    	return out;
    }
}
