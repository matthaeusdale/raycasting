import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Art extends JFrame implements KeyListener, MouseMotionListener {
	Panel panel;
	Timer timer;
	ArrayList<Wall> walls;
	ArrayList<Light> lights;
	ArrayList<Square> squares;
	int height, width;
	int mX = 900;
	int mY = 600;
	boolean threeD = true;

	public Art() {
		super("Art");
	}

	public static void main(String[] args) {
		Art art = new Art();
		art.setup();
		art.createScene();
	}

	public Square createSquare() {
		ArrayList<Wall> sides;
		Square square;
		boolean valid;
		do {
			valid = true;
			sides = new ArrayList<Wall>();
			int size = (int) (Math.random() * 200 + 100);
			int x1 = (int) (Math.random() * (width - size));
			int y1 = (int) (Math.random() * (height - size));
			sides.add(new Wall(x1, y1, x1 + size, y1));
			sides.add(new Wall(x1 + size, y1, x1 + size, y1 + size));
			sides.add(new Wall(x1 + size, y1 + size, x1, y1 + size));
			sides.add(new Wall(x1, y1 + size, x1, y1));
			square = new Square(sides, size);
			for (Square otherSquare : squares) {
				if (square.overlaps(otherSquare)) {
					valid = false;
				}
				if (square.insideOf(otherSquare) || otherSquare.insideOf(square)) {
					valid = false;
				}
			}
		} while (!valid);
		return square;
	}

	public void createScene() {
		squares = new ArrayList<Square>();
		walls = new ArrayList<Wall>();
		walls.add(new Wall(-1, -1, width, -1));
		walls.add(new Wall(-1, -1, -1, height));
		walls.add(new Wall(width, -1, width, height));
		walls.add(new Wall(-1, height, width, height));
		for (int i = 0; i < 3; i++) {
			squares.add(createSquare());
		}
		for (Square square : squares) {
			walls.addAll(square.getBoundaries());
		}
		lights = new ArrayList<Light>();
		for (int i = 0; i < 7; i++) {
			lights.add(new Light(walls, mX, mY));
		}
	}

	public ArrayList<Wall> getWalls() {
		return walls;
	}

	public ArrayList<Light> getLights() {
		return lights;
	}

	public ArrayList<Square> getSquares() {
		return squares;
	}

	public void setup() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		height = screenSize.height;
		width = screenSize.width;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new Panel(this);
		panel.setSize(screenSize.width, screenSize.height);
		panel.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		setContentPane(panel);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
		pack();
		addKeyListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mX = e.getX();
		mY = e.getY();
		if (lights != null && lights.size() > 0) {
			lights.get(0).update(mX, mY);
			lights.get(1).update(mX + 6, mY - 12);
			lights.get(2).update(mX - 9, mY - 12);
			lights.get(3).update(mX - 15, mY + 0);
			lights.get(4).update(mX - 6, mY - 12);
			lights.get(5).update(mX + 6, mY + 12);
			lights.get(6).update(mX + 15, mY + 0);
			panel.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			dispose();
			System.exit(0);
		} else if (e.getKeyChar() == 'r') {
			createScene();
			panel.repaint();
		} else if (e.getKeyChar() == '3') {
			threeD = true;
			panel.repaint();
		} else if (e.getKeyChar() == '2') {
			threeD = false;
			panel.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
