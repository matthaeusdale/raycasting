import java.util.ArrayList;

public class Square {
	ArrayList<Wall> boundaries;
	Vector p1, p2, p3, p4;
	int height;

	public Square(ArrayList<Wall> a, int h) {
		boundaries = new ArrayList<Wall>();
		boundaries.addAll(a);
		p1 = boundaries.get(0).a;
		p2 = boundaries.get(1).a;
		p3 = boundaries.get(2).a;
		p4 = boundaries.get(3).a;
		height = h;
	}

	public boolean overlaps(Square square) {
		boolean overlaps = false;
		for (Wall wall1 : this.boundaries) {
			for (Wall wall2 : square.getBoundaries()) {
				if (wall1.intersects(wall2)) {
					overlaps = true;
				}
			}
		}
		return overlaps;
	}

	public boolean insideOf(Square square) {
		if (overlaps(square)) {
			return true;
		}
		Vector p = boundaries.get(0).a;
		Vector p1 = square.getBoundaries().get(0).a;
		Vector p2 = square.getBoundaries().get(2).a;
		return (p.x > p1.x && p.x < p2.x && p.y > p1.y && p.y < p2.y);
	}

	public ArrayList<Wall> getBoundaries() {
		return boundaries;
	}
}
