package tak.com;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Piece {

	/**
	 * Possible values for pieces - 10, 20, 30, 40
	 * Possible front colors - orange, blue, green
	 * Possible back colors - black, white
	 */
	private ArrayList<Piece> stack = new ArrayList<Piece>();

	private int value;
	private Color backColor;
	private Color frontColor;

	static ImageIcon white = new ImageIcon(Piece.class.getResource("/tak/assets/white.png"));
	static ImageIcon black = new ImageIcon(Piece.class.getResource("/tak/assets/black.png"));

	static ImageIcon blue = new ImageIcon(Piece.class.getResource("/tak/assets/blue.png"));
	static ImageIcon green = new ImageIcon(Piece.class.getResource("/tak/assets/green.png"));
	static ImageIcon orange = new ImageIcon(Piece.class.getResource("/tak/assets/orange.png"));
	static ImageIcon crown = new ImageIcon(Piece.class.getResource("/tak/assets/crown.png"));

	// Kings can move backwards, and two spaces forward. However, the stack they are
	// in is limited in value to 100 points, and once the king has been placed on the
	// stack, no other pieces can stack with it - the king will always be on top.
	// The king's stack instantly becomes worth 100 point, no matter how many points
	// it was previously.
	
	private boolean isKing;

	public Piece() {
		value = 0;
		backColor = Color.white;
		frontColor = Color.orange;
                stack.add(this);
	}

	public Piece(int _value, Color _frontColor, Color _backColor) {
		value = _value;
		frontColor = _frontColor;
		backColor = _backColor;
                stack.add(this);
	}

	public void setValue(int _value) {
		value = _value;
	}

	public int getValue() {
		return value;
	}

	public void addStackToStack(ArrayList<Piece> _stack) {
		for (int index = 0; index < _stack.size(); index++) {
			stack.add(_stack.get(index));
		}
	}

	public Piece getTopPiece() {
		return (stack.get(stack.size() - 1));
	}

	public ArrayList<Piece> getWholeStack() {
		return (stack);
	}

	public void setBackgroundColor(Color _backColor) {
		backColor = _backColor;
	}

	public Color getBackgroundColor() {
		return backColor;
	}

	public void setForegroundColor(Color _frontColor) {
		frontColor = _frontColor;
	}

	public Color getForegroundColor() {
		return frontColor;
	}

	public void setKing(boolean _king) {
		isKing = _king;
	}

	public boolean isKing() {
		return isKing;
	}

	public void draw(Graphics2D g, int x, int y) {
		g.setColor(getTopPiece().backColor);
		g.drawImage((getTopPiece().backColor == Color.black ? black.getImage() : white.getImage()), x + 10, y + 8, 75, 75, null);

		if (!getTopPiece().isKing) {
			g.drawImage((getTopPiece().frontColor == Color.orange ? orange.getImage()
					: getTopPiece().frontColor == Color.blue ? blue.getImage() : green.getImage()), x + 10, y + 8, 75, 75, null);
	
			
			g.setFont(new Font("Arial Bold", Font.PLAIN, 18));
			g.drawString(String.valueOf(getTopPiece().value), x + 38, y + 53);
		} else {
			g.drawImage(crown.getImage(), x + 10, y + 8, 75, 75, null);
		}
		
		//Draw the number of pieces in the stack as a small number in the top right corner of the piece
		//Unless there is no stack (1 piece), then don't draw the number
	}

	public void drawLilWinda(Graphics2D g, int mousex, int mousey) {

		g.setColor(new Color(50,50,50, 150));
		g.fillRect(mousex, mousey, 100, 150);
		g.setColor(new Color(50, 50, 50));
		int xcords[] = {mousex, mousex + 100, mousex + 100, mousex };
		int ycords[] = {mousey, mousey, mousey + 150, mousey + 150 };
		g.drawPolyline(xcords, ycords, 4);
                g.setColor(Color.WHITE);
		g.setFont(new Font("Arial Bold", Font.BOLD, 11));
		g.drawString("Total value is "+ value, mousex + 8, mousey+14);
                g.setFont(new Font("Arial Bold", Font.BOLD, 11));
                if(stack.size() == 1)
		g.drawString(stack.size() + " piece in stack", mousex + 8, mousey+145);
                else
                g.drawString(stack.size() + " pieces in stack", mousex + 8, mousey+145);
                
                int counter = 0;
                for(Piece temp : stack)
                { 
                   g.setColor(temp.backColor);
                   g.drawImage((temp.backColor == Color.black ? black.getImage() : white.getImage()), mousex+ 30, (mousey+106)-(counter*30), 45, 25, null);
		   g.drawImage((temp.backColor == Color.black ? black.getImage() : white.getImage()), mousex+ 30, (mousey+100)-(counter*30), 45, 25, null);
                   
                   if (!temp.isKing) {
			g.drawImage((temp.frontColor == Color.orange ? orange.getImage()
					: temp.frontColor == Color.blue ? blue.getImage() : green.getImage()), mousex+ 30, (mousey+100)-(counter*30), 45, 25, null);
		   }
                   else {
			g.drawImage(crown.getImage(), mousex+ 30, (mousey+100)-(counter*30), 45, 25, null);
		   }
                   counter++;
                }
	}
}
