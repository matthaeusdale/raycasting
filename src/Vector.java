
public class Vector {
	double x, y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double distance(Vector b) {
		double x2 = b.x;
		double y2 = b.y;
		return (Math.sqrt(Math.pow((x2 - x), 2) + Math.pow(y2 - y, 2)));
	}

	public double getAngle(Vector b) {
		return Math.atan2(b.y - y, b.x - x);
	}
}
