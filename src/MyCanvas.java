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
import java.security.spec.ECFieldF2m;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
public class MyCanvas extends Canvas {
    private PasswordManager manager; 

	private InputBoxCrypted inputBoxCrypted;
    private Box[] passwordBoxes;
    private ButtonPasswordBox messageBox;
    private PasswordBoxDraft draft;
    private String password = "password";
    private String cryptedMessage = "##########";
    private int inputBoxHeight = 35;
    private int numberOfPasses;
    private int passwordBoxesSpacing = 25;
    private int firstpasswordBoxTopX = 55;
    private int firstpasswordBoxTopY = 50;
    private int passwordBoxesWidth = 250;
    private int passwordBoxesHeight = 50;
    private int copyToClipboardWidth = passwordBoxesHeight;
    private int shift = inputBoxHeight+passwordBoxesSpacing;
    private int draftIndex =-1;
    private boolean isTempPresent = false;
    public MyCanvas() throws NoSuchAlgorithmException {
        inputBoxCrypted = new InputBoxCrypted(password,firstpasswordBoxTopX-copyToClipboardWidth,0,passwordBoxesWidth+copyToClipboardWidth,inputBoxHeight);
    	manager = new PasswordManager(password);
        manager.dataIn();
    	passwordBoxes =manager.passwordBoxes(shift,passwordBoxesSpacing+passwordBoxesHeight,firstpasswordBoxTopX,firstpasswordBoxTopY,passwordBoxesWidth,passwordBoxesHeight,copyToClipboardWidth);
        
        numberOfPasses=passwordBoxes.length;
        shift += (passwordBoxesSpacing+passwordBoxesHeight)*numberOfPasses;
        messageBox = new ButtonPasswordBox("Click here to add!",firstpasswordBoxTopX,firstpasswordBoxTopY+shift,passwordBoxesWidth,passwordBoxesHeight,Color.WHITE,Color.BLACK);
        password=inputBoxCrypted.showMessageNonCrypted();
        
        // Add a mouse listener to handle mouse clicks
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                messageBox.editMessage( "Click here to add!");
            	
                    if(inputBoxCrypted.isPositionOnTheBox(x, y)){
                        inputBoxCrypted.drawBox(getGraphics());
                        messageBox.editMessage("Write something !");
                        
                    }
                    
                    else if(isTempPresent){
                        if(draft.isPositionOnTheBox(x, y)){
                            messageBox.editMessage("click checkmark to confirm");
                            if(draft.validationBox().isPositionOnTheBox(x, y)){
                                try {
                                    fromDraftToPass();
                                    isTempPresent=false;
                                } catch (NoSuchAlgorithmException e1) {
                                    exceptionHandler(e1);
                                }
                            }
                            else if(draft.crossBox().isPositionOnTheBox(x, y)){
                                System.out.printf("safwafwaf");
                                draft=null;
                                isTempPresent=false;
                                if(draftIndex!=-1){
                                    // Create a new array of size one less than the original
                                    Box[] newArray = new PasswordBox[numberOfPasses - 1];
                                    numberOfPasses-=1;

                                    // Copy elements before the index
                                    for (int i = 0; i < draftIndex; i++) {
                                        newArray[i] = passwordBoxes[i];
                                    }

                                    // Copy elements after the index
                                    for (int i = draftIndex; i < numberOfPasses; i++) {
                                        PasswordBox newer = (PasswordBox) passwordBoxes[i + 1];
                                        newer.shiftY(-(passwordBoxesSpacing+passwordBoxesHeight));
                                        newArray[i] = passwordBoxes[i + 1];
                                    }
                                    passwordBoxes=newArray;
                                }
                                messageBox.topY-=passwordBoxesSpacing+passwordBoxesHeight;
                                draft=null;
                                manager.dataOut();
                                
                            }
                            draftIndex=-1;
                            repaint();
                        }
                    }
                    else if (messageBox.isPositionOnTheBox(x, y)){
                        if(!isTempPresent){
                            draft = messageBox.newPasswordBoxDraft( passwordBoxesSpacing+passwordBoxesHeight, copyToClipboardWidth);
                            draft.drawBox(getGraphics());
                            isTempPresent=true;
                        }
                    }
                    for(Box box : passwordBoxes) {
                        if(box instanceof PasswordBox){
                            PasswordBox passwordBox = (PasswordBox) box;
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
                            else if(passwordBox.getEditButton().isPositionOnTheBox(x, y)){
                                boxDraftAdd(passwordBox);
                            }
                            
                        }
                    }
                    messageBox.drawBox(getGraphics());
                    // Repaint the canvas to reflect the change
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	for(Box box : passwordBoxes) {
                    if(box instanceof PasswordBox){
                        PasswordBox passwordBox = (PasswordBox) box;
                        if(passwordBox.showMessage()!=cryptedMessage){
                            passwordBox.editMessage(cryptedMessage);
                            passwordBox.drawBox(getGraphics());
                            break;
                        }
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
                    if(inputBoxCrypted.canEdit()){
                        password=inputBoxCrypted.showMessageNonCrypted();
                        inputBoxCrypted.drawBox(getGraphics());
                    }
                    if(isTempPresent){
                        draft.addChar(ch);
                    }
                    if(draft.canEdit()){
                        draft.drawBox(getGraphics());
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_BACK_SPACE) {
                    
                    // Handle backspace to remove the last character
                    inputBoxCrypted.deleteChar();
                    if(inputBoxCrypted.canEdit()){
                        password=inputBoxCrypted.showMessageNonCrypted();
                        inputBoxCrypted.drawBox(getGraphics());
                    }
                    if(isTempPresent){
                        draft.deleteChar();
                        if(draft.canEdit()){
                            draft.drawBox(getGraphics());
                        }
                    }
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
    
    private void fromDraftToPass() throws NoSuchAlgorithmException{
        
        if(manager.validPassword(password)){
            PasswordBox toAdd=  draft.createPasswordBox();
            if(draftIndex==-1){
                
                numberOfPasses+=1;
                passwordBoxes = Arrays.copyOf(passwordBoxes,numberOfPasses);
                passwordBoxes[numberOfPasses-1]=toAdd;
            }
            else{
                passwordBoxes[draftIndex] = toAdd;
            }
            
            try {
                manager.addLocalPasscode(toAdd.getWebsite(),toAdd.showMessage(),password);
            } catch (Exception e1) {
                exceptionHandler(e1);
            }
            manager.dataOut();
        }
        else{
            messageBox.editMessage("main password is wrong, can't add password");
        }
    }
    private void boxDraftAdd(PasswordBox p){
        if(!isTempPresent){
            int index=-1;
            for (int i=0;i<numberOfPasses;i++){
                if (passwordBoxes[i]==p){
                    index =i;
                    break;
                }
            }
            if(index!=-1){
                try {
                    if(manager.canUnveil(password, p.getWebsite())){
                        String clearPassword=manager.unveil(password, p.getWebsite());
                        System.out.println("the type is " + p.toDraft(clearPassword).getClass().getName());
                        draft=p.toDraft(clearPassword);
                        isTempPresent=true;
                        draftIndex=index;
                        passwordBoxes[index]=null;
                        manager.removeLocalPasscode(p.getWebsite(), password);
                        repaint();
                    }
                    else{
                        exceptionHandler(new Exception("error in main password"));
                    }
                } catch (Exception e) {
                    exceptionHandler(e);
                }
                
                
                
            }
            else{
                exceptionHandler(new Exception("error in flow of state"));
            }
        }
    }

    @Override
    public void paint(Graphics g) {

        inputBoxCrypted.drawBox(g);
        // Set the color for the drawing
        // Draw thoses rectangles
        for(Box box : passwordBoxes) {

            //draw password box
            if(box instanceof PasswordBox){
                box.drawBox(g);
            }
        	
        }
        if(isTempPresent){
            draft.drawBox(g);
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
