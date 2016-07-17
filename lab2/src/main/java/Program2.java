/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.util.*;

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
		/*
		while our current index is not the destination; do
			add current vertex to path
			look up all adjacent edges excluding edges we came from
			find distances to sink
			if all distances are infinity
			choose vertex with shortest distance to sink
			set current index to that vertex's index
		done
		 */
		Vector<Vertex> path = new Vector<>();
		Vertex endVertex = location.get(sinkIndex);
		int currentIndex = sourceIndex;
		while(currentIndex != sinkIndex) {
			Vertex currentVertex = location.get(currentIndex);
			path.add(currentVertex);

			int smallestDistanceIndex = -1;
			double smallestDistance = Double.POSITIVE_INFINITY;
			for (Edge edge : edges) {
				int startIndex = edge.getU();
				int endIndex = edge.getV();
				if(endIndex == currentIndex) {
					// swap
					int tmp = startIndex;
					startIndex = endIndex;
					endIndex = tmp;
				}
				// if this edge starts from the current node and we haven't visited it before
				if(startIndex == currentIndex && ! path.contains(location.get(endIndex))) {
					// find smallest distance toward the end, hold onto the index
					Vertex next = location.get(endIndex);
					double distanceToNext = currentVertex.distance(next);
					double distanceFromNextToEnd = endVertex.distance(next);
					if(distanceToNext <= transmissionRange && distanceFromNextToEnd < smallestDistance) {
						smallestDistance = distanceFromNextToEnd;
						smallestDistanceIndex = endIndex;
					}
				}
			}

			// if there is no way to the sink from here, return no path
			if(smallestDistance == Double.POSITIVE_INFINITY || smallestDistanceIndex == -1) return new Vector<>(0);

			currentIndex = smallestDistanceIndex;
		}
		path.add(endVertex);
		return path;
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

