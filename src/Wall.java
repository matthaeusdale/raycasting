import java.util.ArrayList;

public class Wall {
	Vector a, b;
	ArrayList<Vector> pointsHit;
	boolean isHit;

	public Wall(int x1, int y1, int x2, int y2) {
		a = new Vector(x1, y1);
		b = new Vector(x2, y2);
		pointsHit = new ArrayList<Vector>();
	}

	public boolean intersects(Wall wall) {
		double x1 = wall.a.x;
		double y1 = wall.a.y;
		double x2 = wall.b.x;
		double y2 = wall.b.y;

		double x3 = a.x;
		double y3 = a.y;
		double x4 = b.x;
		double y4 = b.y;

		double den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (den == 0) {
			return false;
		}
		double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
		double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den;

		return (0 < t && t < 1 && 0 < u && u < 1);
	}

	public void addPoint(Vector v) {
		pointsHit.add(v);
	}

	public void reset() {
		pointsHit = new ArrayList<Vector>();
		isHit = false;
	}

	public boolean outOfBounds(int w, int h) {
		return (a.x < 0 || a.y < 0 || a.x > w || a.y > h || b.x < 0 || b.y < 0 || b.x > w || b.y > h);
	}
}
