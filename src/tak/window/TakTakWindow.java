package tak.window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import tak.com.Piece;

@SuppressWarnings("serial")
public class TakTakWindow extends JFrame implements Runnable {

	static final int WINDOW_WIDTH = 590;
	static final int WINDOW_HEIGHT = 740;
	final int XBORDER = 15;
	final int YBORDER = 40;
	final int YTITLE = 25;
	boolean animateFirstTime = true;
	int xsize = -1;
	int ysize = -1;
	Image image;
	Graphics2D g;

	private static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	private static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

	private static final int CENTER_X = (SCREEN_WIDTH / 2) - (WINDOW_WIDTH / 2);
	private static final int CENTER_Y = (SCREEN_HEIGHT / 2) - (WINDOW_HEIGHT / 2);

	public static Random rand = new Random();
	static ImageIcon icon = new ImageIcon(TakTakWindow.class.getResource("/tak/assets/icon.png"));

	//Board of real game is 6x7
	static final int COLUMNS = 6;
	static final int ROWS = 7;
	Piece[][] board;

	private final TakTakWindow frame = this;

	public TakTakWindow() {

		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setTitle("Tak�Tak");
		setLocation(CENTER_X, CENTER_Y);
		setIconImage(icon.getImage());

		addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			public void mousePressed(MouseEvent e) {
				if (e.BUTTON1 == e.getButton()) {

				}
				repaint();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				//TODO - draw a small info tooltip where the mouse is
				//       tooltip should visually indicate how many pieces
				//       are stacked, and the total value of the stack.
				//
				//       if the player has already selected (clicked on)
				//       the piece they're using for their turn, hovering
				//       over other pieces won't do anything. The space where
				//       the piece currently is will be shaded darker, and the
				//       space where the mouse is currently at will be either
				//       red or green, dictating whether or not the player can
				//       move that piece to that particular space.
		    }
		});
		
		addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.VK_ESCAPE == e.getKeyCode()) {
					new MenuWindow();
					frame.dispose();
				}
				repaint();
			}
		});
		init();
		start();
	}

	Thread relaxer;

	////////////////////////////////////////////////////////////////////////////
	public void init() {
		requestFocus();
	}

	////////////////////////////////////////////////////////////////////////////
	public void destroy() {
	}

	////////////////////////////////////////////////////////////////////////////
	public void paint(Graphics gOld) {
		if (image == null || xsize != getSize().width || ysize != getSize().height) {
			xsize = getSize().width;
			ysize = getSize().height;
			image = createImage(xsize, ysize);
			g = (Graphics2D) image.getGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		//Drawing the initial board

		g.setColor(Color.black);
		g.fillRect(0, 0, xsize, ysize);

		int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0) };
		int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0) };
		g.setColor(Color.white);
		g.fillPolygon(x, y, 4);

		//Light green rectangles as the 'safe zones'

		g.setColor(new Color(64, 128, 64, 150));
		g.fillRect(getX(0), getY(0), getWidth2(), 2 * (getHeight2() / ROWS) + 2);
		g.fillRect(getX(0), getY(0) + 5 * (getHeight2() / ROWS) + 5, getWidth2(), 2 * (getHeight2() / ROWS) + 2);

		//Draw a dark grey rectangle over the middle of the board

		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(getX(0), 2 * (getHeight2() / ROWS) + getY(0), getWidth2(), 3 * (getHeight2() / ROWS) + 5);

		g.setColor(Color.black);
		for (int i = 1; i < ROWS; i++) {
			g.drawLine(0, getY(0) + i * getHeight2() / ROWS, getX(getWidth2()), getY(0) + i * getHeight2() / ROWS);
		}

		for (int i = 1; i < COLUMNS; i++) {
			g.drawLine(getX(0) + i * getWidth2() / COLUMNS, getY(0), getX(0) + i * getWidth2() / COLUMNS,
					getY(getHeight2()));
		}

		for (int zRow = 0; zRow < ROWS; zRow++) {
			for (int zColumn = 0; zColumn < COLUMNS; zColumn++) {
				if (board[zRow][zColumn] != null) {
					board[zRow][zColumn].draw(g, getX(0) + zColumn * getWidth2() / COLUMNS,
							getY(0) + zRow * getHeight2() / ROWS);
				}
			}
		}

		if (animateFirstTime) {
			gOld.drawImage(image, 0, 0, null);
			return;
		}

		gOld.drawImage(image, 0, 0, null);
	}

	public void run() {
		while (true) {
			animate();
			repaint();
			double seconds = 0.04;//time that 1 frame takes.
			int miliseconds = (int) (1000.0 * seconds);
			try {
				Thread.sleep(miliseconds);
			} catch (InterruptedException e) {
			}
		}
	}

	public void reset() {
		board = new Piece[ROWS][COLUMNS];
		resetBoard();
	}

	public void animate() {
		if (animateFirstTime) {
			animateFirstTime = false;
			if (xsize != getSize().width || ysize != getSize().height) {
				xsize = getSize().width;
				ysize = getSize().height;
			}
			reset();
		}
	}

	public void resetBoard() {
		
		//This method doesn't work like it should, the board should
		//randomly place down four pieces of each color, each with
		//a different point value. This doesn't know which pieces
		//it has already placed down, so it's completely random.

		Color[] colors = {Color.orange, Color.green, Color.blue};

		for (int x = 0; x < COLUMNS; x++) {
			for (int y = 5; y < ROWS; y++) {
				Color color = colors[rand.nextInt(colors.length)];
				int value = (rand.nextInt(3) + 1) * 10;
				board[y][x] = new Piece(value, color, Color.white);
			}
			for (int y = 0; y < 2; y++) {
				Color color = colors[rand.nextInt(colors.length)];
				int value = (rand.nextInt(3) + 1) * 10;
				board[y][x] = new Piece(value, color, Color.black);
			}
		}
	}
	
	public void start() {
		if (relaxer == null) {
			relaxer = new Thread(this);
			relaxer.start();
		}
	}

	public void stop() {
		if (relaxer.isAlive()) {
			relaxer.stop();
		}
		relaxer = null;
	}

	public int getX(int x) {
		return (x + XBORDER);
	}

	public int getY(int y) {
		return (y + YBORDER + YTITLE);
	}

	public int getYNormal(int y) {
		return (-y + YBORDER + YTITLE + getHeight2());
	}

	public int getWidth2() {
		return (xsize - getX(0) - XBORDER);
	}

	public int getHeight2() {
		return (ysize - getY(0) - YBORDER);
	}
}
