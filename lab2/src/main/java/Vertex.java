public class Vertex {

	/** x-coordinate of the vertex */
	private double x;
	/** y-coordinate of the vertex */
	private double y;

	/** This constructor sets both coordinates to 0.0. */
	Vertex() {
		this(0.0, 0.0);
	}

	/** This constructor sets the x-coordinate to x
	   and the y-coordinate to y. */
	Vertex(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/** This method sets the x-coordinate to x. */
	public void setX(double x) {
		this.x = x;
	}

	/** This method sets the y-coordinate to y. */
	public void setY(double y) {
		this.y = y;
	}

	/** This method sets the x-coordinate to x
	   and the y-coordinate to y. */
	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/** This method returns the x-coordinate. */
	public double getX() {
		return x;
	}

	/** This method returns the y-coordinate. */
	public double getY() {
		return y;
	}

	/** This method returns the Euclidean distance
	   between the vertex and another vertex v. */
	public double distance(Vertex v) {
		return Math.sqrt(Math.pow(x - v.getX(), 2) + Math.pow(y - v.getY(), 2));
	}

	/** This method returns a string representation
	   of the vertex. */
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}

