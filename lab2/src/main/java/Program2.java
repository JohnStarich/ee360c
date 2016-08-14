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
		return gpsrPathIterative(sourceIndex, sinkIndex);
	}

	public Vector<Vertex> gpsrPathIterative(int sourceIndex, int sinkIndex) {
		/*
		while our current index is not the destination; do
			add current vertex to path
			look up all adjacent edges excluding edges we came from
			find distances to sink
			if all distances are infinity
				then return empty path
			choose vertex with shortest distance to sink
			set current index to that vertex's index
		done
		return path
		 */
		Vector<Vertex> path = new Vector<>();
		Vertex endVertex = location.get(sinkIndex);
		int currentIndex = sourceIndex;
		while(currentIndex != sinkIndex) {
			Vertex currentVertex = location.get(currentIndex);
			path.add(currentVertex);

			int smallestDistanceIndex = -1;
			double smallestDistance = currentVertex.distance(endVertex);
			for(int endIndex = 0; endIndex < location.size(); endIndex++) {
				if(endIndex != currentIndex) {
					Vertex next = location.get(endIndex);
					double distanceFromNextToEnd = endVertex.distance(next);
					if (distanceFromNextToEnd < smallestDistance &&
						currentVertex.distance(next) <= transmissionRange
						) {
						smallestDistance = distanceFromNextToEnd;
						smallestDistanceIndex = endIndex;
					}
				}
			}

			// if there is no way to the sink from here, return no path
			if(smallestDistanceIndex == -1) return new Vector<>(0);

			currentIndex = smallestDistanceIndex;
		}
		path.add(endVertex);
		return path;
	}

	public Vector<Vertex> gpsrPathRecursive(int sourceIndex, int sinkIndex) {
		if (sourceIndex == sinkIndex) {
			Vector<Vertex> path = new Vector<>(1);
			path.add(location.get(sinkIndex));
			return path;
		}

		Vertex sinkVertex = location.get(sinkIndex);
		Vertex currentVertex = location.get(sourceIndex);

		int smallestDistanceIndex = -1;
		double smallestDistance = currentVertex.distance(sinkVertex);
		for(int endIndex = 0; endIndex < location.size(); endIndex++) {
			if(endIndex != sourceIndex) {
				Vertex next = location.get(endIndex);
				double distanceFromNextToEnd = sinkVertex.distance(next);
				if (distanceFromNextToEnd < smallestDistance && currentVertex.distance(next) <= transmissionRange) {
					smallestDistance = distanceFromNextToEnd;
					smallestDistanceIndex = endIndex;
				}
			}
		}

		// if there is no way to the sink from here, return no path
		if (smallestDistanceIndex == -1) return new Vector<>(0);

		Vector<Vertex> path = new Vector<>(1);
		Vector<Vertex> nextPath = gpsrPath(smallestDistanceIndex, sinkIndex);
		if(nextPath.contains(sinkVertex)) {
			path.add(currentVertex);
			path.addAll(nextPath);
		}
		return path;
	}

	public PriorityQueue<DijkstraNode> dijkstraInitializeSingleSource(Vertex startVertex) {
		PriorityQueue<DijkstraNode> pathLatencies = new PriorityQueue<>(location.size());
		for(int vertexIndex = 0; vertexIndex < location.size(); vertexIndex++) {
			pathLatencies.add(new DijkstraNode(vertexIndex));
		}
		pathLatencies.peek().cost = 0D;
		return pathLatencies;
	}

	public Vector<Edge> dijkstraAdjacentVertices(int vertex) {
		Vector<Edge> adjacentEdges = new Vector<>();
		for(Edge edge : edges) {
			if(vertex == edge.getV()) {
				adjacentEdges.add(new Edge(edge.getV(), edge.getU(), edge.getW()));
			}
			if(vertex == edge.getU()) {
				adjacentEdges.add(edge);
			}
		}
		return adjacentEdges;
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
		/*
		DIJKSTRA(G, w, s):
			INITIALIZE-SINGLE-SOURCE(G, s)
			S = []
			Q = G.V
			while Q != []:
				u = EXTRACT-MIN(Q)
				S = S + {u}
				for each vertex in Adj[u]:
					RELAX(u, v, w)
		 */
		Vector<Vertex> path = new Vector<>();
		// initialize single source
		Vector<DijkstraNode> nodes = new Vector<>(location.size());
		PriorityQueue<DijkstraNode> pathLatencies = new PriorityQueue<>(location.size());
		for(int vertexIndex = 0; vertexIndex < location.size(); vertexIndex++) {
			DijkstraNode node = new DijkstraNode(vertexIndex);
			nodes.add(node);
			pathLatencies.add(node);
		}
		nodes.get(sourceIndex).cost = 0D;

		while (! pathLatencies.isEmpty()) {
			DijkstraNode currentNode = pathLatencies.poll();
			Vertex currentVertex = location.get(currentNode.nodeIndex);
			for (Edge edge : dijkstraAdjacentVertices(currentNode.nodeIndex)) {
				Vertex adjacentVertex = location.get(edge.getV());
				double cost = edge.getW();
				DijkstraNode adjacentDijkstraNode = nodes.get(edge.getV());
				if (currentNode.cost + cost < adjacentDijkstraNode.cost && adjacentVertex.distance(currentVertex) <= transmissionRange) {
					pathLatencies.remove(adjacentDijkstraNode);
					adjacentDijkstraNode.cost = currentNode.cost + cost;
					adjacentDijkstraNode.predecessor = currentNode;
					pathLatencies.add(adjacentDijkstraNode);
				}
			}
		}

		// reverse and return the current path = O(V)
		DijkstraNode currentNode = nodes.get(sinkIndex);
		// if the last node doesn't have a predecessor and it should, then return non-existent path
		if(currentNode.predecessor == null && sourceIndex != sinkIndex) {
			return new Vector<>(0);
		}
		Stack<DijkstraNode> reversedPath = new Stack<>();
		while(currentNode != null) {
			reversedPath.add(currentNode);
			currentNode = currentNode.predecessor;
		}
		while(! reversedPath.isEmpty()) {
			path.add(location.get(reversedPath.pop().nodeIndex));
		}
		return path;
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
		Vector<Vertex> path = new Vector<>();
		// initialize single source
		Vector<DijkstraNode> nodes = new Vector<>(location.size());
		PriorityQueue<DijkstraNode> pathLatencies = new PriorityQueue<>(location.size());
		for(int vertexIndex = 0; vertexIndex < location.size(); vertexIndex++) {
			DijkstraNode node = new DijkstraNode(vertexIndex);
			nodes.add(node);
			pathLatencies.add(node);
		}
		nodes.get(sourceIndex).cost = 0D;

		while (! pathLatencies.isEmpty()) {
			DijkstraNode currentNode = pathLatencies.poll();
			Vertex currentVertex = location.get(currentNode.nodeIndex);
			for (Edge edge : dijkstraAdjacentVertices(currentNode.nodeIndex)) {
				Vertex adjacentVertex = location.get(edge.getV());
				double cost = 1;
				DijkstraNode adjacentDijkstraNode = nodes.get(edge.getV());
				if (currentNode.cost + cost < adjacentDijkstraNode.cost && adjacentVertex.distance(currentVertex) <= transmissionRange) {
					pathLatencies.remove(adjacentDijkstraNode);
					adjacentDijkstraNode.cost = currentNode.cost + cost;
					adjacentDijkstraNode.predecessor = currentNode;
					pathLatencies.add(adjacentDijkstraNode);
				}
			}
		}

		// reverse and return the current path = O(V)
		DijkstraNode currentNode = nodes.get(sinkIndex);
		// if the last node doesn't have a predecessor and it should, then return non-existent path
		if(currentNode.predecessor == null && sourceIndex != sinkIndex) {
			return new Vector<>(0);
		}
		Stack<DijkstraNode> reversedPath = new Stack<>();
		while(currentNode != null) {
			reversedPath.add(currentNode);
			currentNode = currentNode.predecessor;
		}
		while(! reversedPath.isEmpty()) {
			path.add(location.get(reversedPath.pop().nodeIndex));
		}
		return path;
	}
}

