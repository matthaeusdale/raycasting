import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Panel extends JPanel {
	Art frame;
	ArrayList<Wall> walls;
	ArrayList<Ray> rays;
	ArrayList<Light> lights;
	ArrayList<Square> squares;

	public Panel(Art frame) {
		this.frame = frame;
	}

	public Polygon createPolygon(Light light) {
		rays = light.getRays();
		Polygon poly = new Polygon();
		for (int i = 0; i < rays.size(); i++) {
			double least = 100000;
			Vector small = null;
			Wall hitWall = null;
			for (Wall wall : walls) {
				Vector v = rays.get(i).cast(wall);
				if (v != null) {
					double dist = v.distance(light.pos);
					if (dist < least) {
						least = dist;
						small = v;
						hitWall = wall;
					}
				}
			}
			if (small != null) {
				poly.addPoint((int) small.x, (int) small.y);
				hitWall.addPoint(small);
				hitWall.isHit = true;
			}
		}
		return poly;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
		lights = frame.getLights();
		squares = frame.getSquares();
		walls = frame.getWalls();

		for (Wall wall : walls) {
			wall.reset();
		}

		if (lights != null && lights.size() > 0) {

			// LIGHT
			g.setColor(new Color(255, 255, 255, 10));
			for (int i = lights.size() - 1; i >= 0; i--) {
				Polygon p = createPolygon(lights.get(i));
				g.fillPolygon(p);
			}

			// TILES
			boolean black = true;
			int size = 50;
			for (int i = 0; i < frame.width; i += size) {
				black = !black;
				for (int j = 0; j < frame.height; j += size) {
					if (black) {
						g.setColor(new Color(20, 20, 20, 50));
						g.fillRect(i, j, size, size);
						black = !black;
					} else {
						g.setColor(new Color(100, 100, 100, 50));
						g.fillRect(i, j, size, size);
						black = !black;
					}
				}
			}

			// CUBES & SQUARES
			g.setColor(new Color(0, 0, 0));
			int centerX = 960;
			int centerY = 590;
			for (Square square : squares) {
				if (frame.threeD) {
					// CUBES
					int h = square.height;
					for (int i = 0; i < h / 5; i++) {
						double scale = i;
						int x = (int) (square.p1.x - scale - (((centerX - square.p1.x) * i) / 300));
						int y = (int) (square.p1.y - scale - (((centerY - square.p1.y) * i) / 300));
						g.fillRect(x, y, h + (int) (scale * 2), h + (int) (scale * 2));
					}
				} else {
					// SQUARES
					int length = (int) (square.p2.x - square.p1.x);
					g.fillRect((int) square.p1.x, (int) square.p1.y, length, length);
				}
			}
			// TEST
			/*
			 * g.setColor(new Color(255, 255, 255, 5)); for (Wall wall : walls) { if
			 * (wall.isHit) { for (int i = 0; i < wall.pointsHit.size() - 2; i++) { Vector
			 * p1 = wall.pointsHit.get(i); Vector p2 = wall.pointsHit.get(i + 1);
			 * g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y); } } }
			 */
		}
	}
}
