package tak.com;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import tak.config.ConfigLoader;
import tak.window.MenuWindow;
import tak.window.TakTakSingleplayerWindow;

public class Piece {

    private ArrayList<Piece> stack = new ArrayList<Piece>();
    private int value;
    private int totalStackValue;
    private Color backColor;
    private Color frontColor;
    private ImageIcon white = new ImageIcon(Piece.class.getResource("/tak/assets/white.png"));
    private ImageIcon black = new ImageIcon(Piece.class.getResource("/tak/assets/black.png"));
    private ImageIcon blue = new ImageIcon(Piece.class.getResource("/tak/assets/blue.png"));
    private ImageIcon green = new ImageIcon(Piece.class.getResource("/tak/assets/green.png"));
    private ImageIcon orange = new ImageIcon(Piece.class.getResource("/tak/assets/orange.png"));
    private ImageIcon fade = new ImageIcon(Piece.class.getResource("/tak/assets/fade.png"));
    private ImageIcon crown = new ImageIcon(Piece.class.getResource((ConfigLoader.johnCena ? "/tak/assets/cenaking.png" : ConfigLoader.shrek ? "/tak/assets/shrekking.png" : "/tak/assets/crown.png")));
    private boolean isKing;

    public Piece() {
        value = 0;
        backColor = Color.white;
        frontColor = Color.orange;
        stack.add(this);
        totalStackValue = value;
    }

    public Piece(int _value, Color _frontColor, Color _backColor) {
        value = _value;
        frontColor = _frontColor;
        backColor = _backColor;
        stack.add(this);
        update();
        totalStackValue = value;
    }

    public void setValue(int _value) {
        value = _value;

    }

    public void addValue(int _value) {
        totalStackValue += _value;
    }

    public int getValue() {
        return value;
    }
    
    public int getStackValue() {
        return totalStackValue;
    }

    public void addStackToStack(ArrayList<Piece> _stack) {
        for (int index = 0; index < _stack.size(); index++) {
            stack.add(_stack.get(index));
            addValue(_stack.get(index).getValue());
        }
    }

    public void update() {
    	crown = new ImageIcon(Piece.class.getResource((ConfigLoader.johnCena ? "/tak/assets/cenaking.png" : ConfigLoader.shrek ? "/tak/assets/shrekking.png" : "/tak/assets/crown.png")));
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

        if (stack.size() > 1) {
            g.setFont(new Font("Arial Bold", Font.PLAIN, 12));
            g.setColor(new Color(255 - getTopPiece().backColor.getRed(),
                    255 - getTopPiece().backColor.getGreen(), 255 - getTopPiece().backColor.getBlue()));
            g.drawString(String.valueOf(stack.size()), x + 68, y + 23);
        }
    }
    
    public void drawFade(Graphics2D g, int x, int y) {
        g.drawImage(fade.getImage(), x + 10, y + 8, 75, 75, null);
    }

    public void drawLilWinda(Graphics2D g, int mousex, int mousey) {
        if (mousex + 105 > TakTakSingleplayerWindow.WINDOW_WIDTH) {
            mousex -= 105;
        }
        if (mousey + 65 + (stack.size() * 8) > TakTakSingleplayerWindow.WINDOW_HEIGHT) {
            mousey -= 65 + (stack.size() * 8);
        }

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(mousex, mousey, 105, 65 + (stack.size() * 8));
        g.setColor(new Color(50, 50, 50));
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial Bold", Font.BOLD, 11));
        g.drawString("Total value is " + totalStackValue, mousex + 8, mousey + 14);
        g.setFont(new Font("Arial Bold", Font.BOLD, 11));
        if (stack.size() == 1) {
            g.drawString(stack.size() + " piece in stack", mousex + 8, mousey + 60 + (stack.size() * 8));
        } else {
            g.drawString(stack.size() + " pieces in stack", mousex + 8, mousey + 60 + (stack.size() * 8));
        }

        int counter = 0;
        for (Piece temp : stack) {
            g.setColor(temp.backColor);
            g.drawImage((temp.backColor == Color.black ? black.getImage() : white.getImage()), mousex + 30, mousey + 23 + (stack.size() * 8) - (counter * 8), 45, 25, null);
            g.drawImage((temp.backColor == Color.black ? black.getImage() : white.getImage()), mousex + 30, mousey + 15 + (stack.size() * 8) - (counter * 8), 45, 25, null);

            if (!temp.isKing) {
                g.drawImage((temp.frontColor == Color.orange ? orange.getImage()
                        : temp.frontColor == Color.blue ? blue.getImage() : green.getImage()), mousex + 30, mousey + 15 + (stack.size() * 8) - (counter * 8), 45, 25, null);
            } else {
                g.drawImage(crown.getImage(), mousex + 30, mousey + 15 + (stack.size() * 8) - (counter * 8), 45, 25, null);
            }
            counter++;
        }

    }
}
