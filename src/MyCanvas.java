import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Optional;
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
    private PasswordManager manager; 

	private InputBoxCrypted inputBoxCrypted;
    private PasswordBox[] passwordBoxes;
    private ButtonPasswordBox messageBox;
    private Optional<PasswordBoxDraft> temp = Optional.empty();
    private String password="babe10ued";
    private String cryptedMessage = "###########";
    
    
    private int inputBoxHeight = 35;
    private int numberOfPasses = 0;
    private int passwordBoxesSpacing = 25;
    private int firstpasswordBoxTopX = 55;
    private int firstpasswordBoxTopY = 50;
    private int passwordBoxesWidth = 250;
    private int passwordBoxesHeight = 50;
    private int copyToClipboardWidth = passwordBoxesHeight;
    private int shift = inputBoxHeight+passwordBoxesSpacing;

    public MyCanvas() throws NoSuchAlgorithmException {
        inputBoxCrypted = new InputBoxCrypted(password,firstpasswordBoxTopX-copyToClipboardWidth,0,passwordBoxesWidth+copyToClipboardWidth,inputBoxHeight);
    	manager = new PasswordManager(password);
    	passwordBoxes = new PasswordBox[numberOfPasses];
        messageBox = new ButtonPasswordBox("Click here to add!",firstpasswordBoxTopX,firstpasswordBoxTopY+shift,passwordBoxesWidth,passwordBoxesHeight,Color.WHITE,Color.BLACK);
        password=inputBoxCrypted.showMessageNonCrypted();
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
                            exceptionHandler(e1);
                        }
                        messageBox.editMessage( "Click here to add!");
                        passwordBox.drawBox(getGraphics());
                        break;
                    }
                    else if(passwordBox.copyToClipboardBox().isPositionOnTheBox(x,y)){
                        try {
                            copyToClipboard(manager.unveil(password,passwordBox.getWebsite()));
                        } catch (Exception e1) {
                            exceptionHandler(e1);
                        }
                        try {
                            if(manager.canUnveil(password, passwordBox.getWebsite())){
                                messageBox.editMessage( "Copied!");
                            }
                            else{
                                messageBox.editMessage( "Invalid action!");
                            }
                        } catch (NoSuchAlgorithmException e1) {
                            exceptionHandler(e1);
                        }
                        break;
                    }
                    else if(inputBoxCrypted.isPositionOnTheBox(x, y)){
                        messageBox.editMessage("Write something !");
                        break;
                    }
                    else{
                        messageBox.editMessage("Click here to add!");
                        
                    }
            	}
                if (messageBox.isPositionOnTheBox(x, y)){
                    if(!temp.isPresent()){
                        prt("I'm not present");
                    }
                    if(!temp.isPresent()){
                        temp = Optional.of(messageBox.newPasswordBoxDraft( passwordBoxesSpacing+passwordBoxesHeight, copyToClipboardWidth));
                        temp.get().drawBox(getGraphics());
                    }
                }
                temp.ifPresent(
                    draft ->{ if(draft.isPositionOnTheBox(x, y)){
                        messageBox.editMessage("click checkmark to confirm");
                        if(draft.validationBox().isPositionOnTheBox(x, y)){
                            PasswordBox toAdd=  draft.createPasswordBox();
                            numberOfPasses+=1;
                            passwordBoxes = Arrays.copyOf(passwordBoxes,numberOfPasses);
                            passwordBoxes[numberOfPasses-1]=toAdd;
                            try {
                                manager.addLocalPasscode(toAdd.getWebsite(),toAdd.showMessage(),password);
                            } catch (Exception e1) {
                                exceptionHandler(e1);
                            }
                            temp = Optional.empty();
                            repaint();
                        }
                }});
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
                    inputBoxCrypted.addChar(ch);
                    temp.ifPresent(draft -> draft.addChar(ch));
                    password=inputBoxCrypted.showMessageNonCrypted();
                    // Repaint the canvas to reflect the change
                    inputBoxCrypted.drawBox(getGraphics());
                    temp.ifPresent(draft -> draft.drawBox(getGraphics()));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_BACK_SPACE) {
                    // Handle backspace to remove the last character
                    inputBoxCrypted.deleteChar();
                    temp.ifPresent(draft -> draft.deleteChar());
                    password=inputBoxCrypted.showMessageNonCrypted();
                    // Repaint the canvas to reflect the change
                    inputBoxCrypted.drawBox(getGraphics());
                    temp.ifPresent(draft -> draft.drawBox(getGraphics()));
                }
            }
        });
    }

    // A helper method to check if a character is printable
    private static boolean isPrintableChar(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return !Character.isISOControl(c) && block != null && block != Character.UnicodeBlock.SPECIALS;
    }
    //helper method to copy any string to the user clipboard
    public static void copyToClipboard(String input) {
        StringSelection stringSelection = new StringSelection(input);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
    //helper method to print exceptions in the messagebox
    public void exceptionHandler(Exception e){
        System.out.printf("here's an error you might want to get an eye to :"+ " " + e.getMessage());
        messageBox.editMessage("Error : "+e.getMessage());
    }
    
     

    @Override
    public void paint(Graphics g) {

        inputBoxCrypted.drawBox(g);
        // Set the color for the drawing
        // Draw thoses rectangles
        for(PasswordBox passwordBox : passwordBoxes) {

            //draw password box
        	passwordBox.drawBox(g);
        }
        if(temp.isPresent()){
            temp.get().drawBox(g);
        }
        messageBox.drawBox(g);
    }
    public int[] minimumNeededResolution() {
    	int[] out = {messageBox.width+messageBox.topX,messageBox.height+messageBox.topY};
    	return out;
    }
    public static void prt(String message){
        System.out.printf(message);
    }
}
