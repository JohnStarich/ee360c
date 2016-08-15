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
	private Map<Integer, Edge[]> adjacentEdgesCache = null;

	Program2() {
		super();
		dijkstraAdjacentEdges();
	}

	Program2(String locationFile) {
		super(locationFile);
		dijkstraAdjacentEdges();
	}

	Program2(String locationFile, double transmissionRange) {
		super(locationFile, transmissionRange);
		dijkstraAdjacentEdges();
	}

	Program2(double transmissionRange, String locationFile) {
		super(transmissionRange, locationFile);
		dijkstraAdjacentEdges();
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

	public Map<Integer, Edge[]> dijkstraAdjacentEdges() {
		if(adjacentEdgesCache != null) return adjacentEdgesCache;
		Map<Integer, Vector<Edge>> adjacentEdges = new HashMap<>(location.size());

		for(Edge edge : edges) {
			if(! adjacentEdges.containsKey(edge.getU())) {
				adjacentEdges.put(edge.getU(), new Vector<Edge>());
			}
			adjacentEdges.get(edge.getU()).add(edge);

			if(! adjacentEdges.containsKey(edge.getV())) {
				adjacentEdges.put(edge.getV(), new Vector<Edge>());
			}
			adjacentEdges.get(edge.getV()).add(edge);
		}
		adjacentEdgesCache = new HashMap<>();
		for(Map.Entry<Integer, Vector<Edge>> entry : adjacentEdges.entrySet()) {
			Edge[] edgesToSort = new Edge[entry.getValue().size()];
			edgesToSort = entry.getValue().toArray(edgesToSort);
			Arrays.sort(edgesToSort, new Comparator<Edge>() {
				@Override
				public int compare(Edge edge1, Edge edge2) {
					return (int) (edge2.getW() - edge1.getW());
				}
			});
			adjacentEdgesCache.put(entry.getKey(), edgesToSort);
		}
		return adjacentEdgesCache;
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
		// initialize single source
		Vector<DijkstraNode> nodes = new Vector<>(location.size());
		PriorityQueue<DijkstraNode> pathLatencies = new PriorityQueue<>(location.size());
		for(int vertexIndex = 0; vertexIndex < location.size(); vertexIndex++) {
			DijkstraNode node = new DijkstraNode(vertexIndex);
			nodes.add(node);
			pathLatencies.add(node);
		}
		nodes.get(sourceIndex).cost = 0D;

		Map<Integer, Edge[]> adjacentEdges = dijkstraAdjacentEdges();

		while (! pathLatencies.isEmpty()) {
			DijkstraNode currentNode = pathLatencies.poll();
			Vertex currentVertex = location.get(currentNode.nodeIndex);
			for (Edge edge : adjacentEdges.get(currentNode.nodeIndex)) {
				int adjacentIndex = edge.getU() == currentNode.nodeIndex ? edge.getV() : edge.getU();
				Vertex adjacentVertex = location.get(adjacentIndex);
				double cost = edge.getW();
				DijkstraNode adjacentDijkstraNode = nodes.get(adjacentIndex);
				if (currentNode.cost + cost < adjacentDijkstraNode.cost && adjacentVertex.distance(currentVertex) <= transmissionRange) {
					pathLatencies.remove(adjacentDijkstraNode);
					adjacentDijkstraNode.cost = currentNode.cost + cost;
					adjacentDijkstraNode.predecessor = currentNode;
					pathLatencies.add(adjacentDijkstraNode);
				}
			}
		}

		// reverse and return the current path = O(V)
		return pathFromDijkstraNode(nodes.get(sinkIndex), sourceIndex, sinkIndex);
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
		// initialize single source
		Vector<DijkstraNode> nodes = new Vector<>(location.size());
		PriorityQueue<DijkstraNode> pathLatencies = new PriorityQueue<>(location.size());
		for(int vertexIndex = 0; vertexIndex < location.size(); vertexIndex++) {
			DijkstraNode node = new DijkstraNode(vertexIndex);
			nodes.add(node);
			pathLatencies.add(node);
		}
		nodes.get(sourceIndex).cost = 0D;

		Map<Integer, Edge[]> adjacentEdges = dijkstraAdjacentEdges();

		while (! pathLatencies.isEmpty()) {
			DijkstraNode currentNode = pathLatencies.poll();
			Vertex currentVertex = location.get(currentNode.nodeIndex);
			for (Edge edge : adjacentEdges.get(currentNode.nodeIndex)) {
				int adjacentIndex = edge.getU() == currentNode.nodeIndex ? edge.getV() : edge.getU();
				Vertex adjacentVertex = location.get(adjacentIndex);
				double cost = 1;
				DijkstraNode adjacentDijkstraNode = nodes.get(adjacentIndex);
				if (currentNode.cost + cost < adjacentDijkstraNode.cost && adjacentVertex.distance(currentVertex) <= transmissionRange) {
					pathLatencies.remove(adjacentDijkstraNode);
					adjacentDijkstraNode.cost = currentNode.cost + cost;
					adjacentDijkstraNode.predecessor = currentNode;
					pathLatencies.add(adjacentDijkstraNode);
				}
			}
		}

		// reverse and return the current path = O(V)
		return pathFromDijkstraNode(nodes.get(sinkIndex), sourceIndex, sinkIndex);
	}

	private Vector<Vertex> pathFromDijkstraNode(DijkstraNode node, int sourceIndex, int sinkIndex) {
		// if the last node doesn't have a predecessor and it should, then return non-existent path
		if(node.predecessor == null && sourceIndex != sinkIndex) {
			return new Vector<>(0);
		}
		Stack<DijkstraNode> reversedPath = new Stack<>();
		while(node != null) {
			reversedPath.add(node);
			node = node.predecessor;
		}
		Vector<Vertex> path = new Vector<>();
		while(! reversedPath.isEmpty()) {
			path.add(location.get(reversedPath.pop().nodeIndex));
		}
		return path;
	}
}

