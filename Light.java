import java.util.ArrayList;

public class Light {
	Vector pos;
	ArrayList<Ray> rays, rays2;
	ArrayList<Wall> walls;

	public Light(ArrayList<Wall> walls) {
		pos = new Vector(900, 600);
		this.walls = walls;
		createRays(walls);
	}

	public Light(ArrayList<Wall> walls, int x, int y) {
		pos = new Vector(x, y);
		this.walls = walls;
		createRays(walls);
	}

	public void update(int x, int y) {
		pos = new Vector(x, y);
		createRays(walls);
	}

	public void createRays(ArrayList<Wall> walls) {
		rays = new ArrayList<Ray>();
		for (Wall wall : walls) {
			rays.add(new Ray(pos, pos.getAngle(wall.a)));
			rays.add(new Ray(pos, .00001 + pos.getAngle(wall.a)));
			rays.add(new Ray(pos, -.00001 + pos.getAngle(wall.a)));
			rays.add(new Ray(pos, pos.getAngle(wall.b)));
			rays.add(new Ray(pos, .00001 + pos.getAngle(wall.b)));
			rays.add(new Ray(pos, -.00001 + pos.getAngle(wall.b)));
		}
		sortRays();
	}

	public void sortRays() {
		ArrayList<Ray> sortedList = new ArrayList<Ray>();
		while (rays.size() > 0) {
			double smallest = 10;
			int index = -1;
			for (int i = 0; i < rays.size(); i++) {
				if (rays.get(i).angle < smallest) {
					smallest = rays.get(i).angle;
					index = i;
				}
			}
			sortedList.add(rays.remove(index));
		}
		rays = sortedList;
	}

	public ArrayList<Ray> getRays() {
		return rays;
	}
}
