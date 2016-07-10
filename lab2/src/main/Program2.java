/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.util.Vector;

/**
 * Your solution goes in this file.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 *
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 *
 * DO NOT FORGET to add a graph representation and
 * any other fields and/or methods that you think
 * will be useful.
 * DO NOT FORGET to modify the constructors when you
 * add new fields to the Program2 class.
 */
public class Program2 extends VertexNetwork {
	Program2() {
		super();
	}

	Program2(String locationFile) {
		super(locationFile);
	}

	Program2(String locationFile, double transmissionRange) {
		super(locationFile, transmissionRange);
	}

	Program2(double transmissionRange, String locationFile) {
		super(transmissionRange, locationFile);
	}

	/**
	 * This method returns a path from a source at location sourceIndex
	 * and a sink at location sinkIndex using the GPSR algorithm. An empty
	 * path is returned if the GPSR algorithm fails to find a path.
	 * The following code is meant to be a placeholder that simply
	 * returns an empty path. Replace it with your own code that
	 * implements the GPSR algorithm.
	 */
	public Vector<Vertex> gpsrPath(int sourceIndex, int sinkIndex) {
		return new Vector<>(0);
	}

	/**
	 * This method returns a path (shortest in terms of latency) from a source at
	 * location sourceIndex and a sink at location sinkIndex using Dijkstra's algorithm.
	 * An empty path is returned if Dijkstra's algorithm fails to find a path.
	 *
	 * The following code is meant to be a placeholder that simply
	 * returns an empty path. Replace it with your own code that
	 * implements Dijkstra's algorithm.
	 */
	public Vector<Vertex> dijkstraPathLatency(int sourceIndex, int sinkIndex) {
		return new Vector<>(0);
	}

	/**
	 * This method returns a path (shortest in terms of hops) from a source at
	 * location sourceIndex and a sink at location sinkIndex using Dijkstra's algorithm.
	 * An empty path is returned if Dijkstra's algorithm fails to find a path.
	 *
	 * The following code is meant to be a placeholder that simply
	 * returns an empty path. Replace it with your own code that
	 * implements Dijkstra's algorithm.
	 */
	public Vector<Vertex> dijkstraPathHops(int sourceIndex, int sinkIndex) {
		return new Vector<>(0);
	}
}

