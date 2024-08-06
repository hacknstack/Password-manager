import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.*;
public class MyCanvas extends Canvas {
	
    private String[] messages;
    private String password = "babe10ued";
    private String cryptedMessage = "************";
    private Password[] passes; 
    
    private int numberOfPasses = 5;
    
    private int boxWidth = 200;
    private int boxHeight = 100;
    private int boxSpacing = 150;
    private int boxTopX = 50;
    private int boxTopY = 50;
    

    public MyCanvas() {
    	passes = new Password[numberOfPasses];
    	messages = new String[numberOfPasses];
        for(int i =0;i<passes.length;i++) {
        	passes[i] = new Password(password,"test "+String.valueOf(i));
        	messages[i]=cryptedMessage;
        	
        }
        // Add a mouse listener to handle mouse clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	for(int i =0;i<passes.length;i++) {
                	int shift = boxSpacing*i;
                	if(e.getX()>=boxTopX && e.getX()<=boxWidth+boxTopX && e.getY()>=boxTopY+shift && e.getY()<=boxTopY+shift+boxHeight) {
                		
                		if(messages[i] == passes[i].Unveil(password)) {
                			messages[i] = cryptedMessage;
                		}
                		else {
                			messages[i] = passes[i].Unveil(password);
                		}
                	}
                
            	}
            	// Repaint the canvas to reflect the change
            	repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	for(int i =0;i<passes.length;i++) {
                	messages[i]=cryptedMessage;
                	
                }
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
        for(int i =0;i<passes.length;i++) {
        	g.setColor(Color.RED);
        	int shift = boxSpacing*i;
        	g.fillRect(boxTopX, boxTopY+shift, boxWidth, boxHeight);
        	g.setColor(Color.BLACK);
        	g.drawString(messages[i], 125, 100+shift);
        }
        
    }
    public int[] minimumNeededResolution() {
    	int Width = boxTopX + boxWidth;
    	int Height = boxTopY + boxHeight*numberOfPasses + boxSpacing*(numberOfPasses-1);
    	int[] out = {Width,Height};
    	System.out.print(Height);
    	return out;
    }
}
