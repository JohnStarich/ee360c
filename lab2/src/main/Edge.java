public class Edge {

	/** index of vertex u */
	private int u;
	/** index of vertex v */
	private int v;
	/** weight of undirected edge {u,v} */
	private double w;

	/** This constructor sets vertex u to 0, v to 1, and w to 0.0 */
	Edge() {
		this(0, 1, 0.0);
	}

	/** This constructor sets the u-vertex to u,
	   v-vertex to v, and weight to w */
	Edge(int u, int v, double w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}

	/** This method sets the u-vertex to u. */
	public void setU(int u) {
		this.u = u;
	}

	/** This method sets the v-vertex to v. */
	public void setV(int v) {
		this.v = v;
	}

	/** This method sets the weight to w. */
	public void setW(double w) {
		this.w = w;
	}

	/** This method returns the u-vertex. */
	public int getU() {
		return u;
	}

	/** This method returns the v-vertex. */
	public int getV() {
		return v;
	}

	/** This method returns the weight. */
	public double getW() {
		return w;
	}

	/** This method returns a string representation
	   of the edge. */
	public String toString() {
		return "(" + u + "," + v + "," + w + ")";
	}
}

