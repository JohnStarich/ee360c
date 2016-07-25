import java.util.Objects;

/**
 * Created by johnstarich on 7/20/16.
 */
public class DijkstraNode implements Comparable<DijkstraNode> {
	public int nodeIndex;
	public DijkstraNode predecessor;
	public Double cost;

	public DijkstraNode(int nodeIndex) {
		this.nodeIndex = nodeIndex;
		predecessor = null;
		cost = Double.POSITIVE_INFINITY;
	}

	public DijkstraNode(int nodeIndex, DijkstraNode predecessor, Double cost) {
		this.nodeIndex = nodeIndex;
		this.predecessor = predecessor;
		this.cost = cost;
	}

	@Override
	public boolean equals(Object other) {
		if(other instanceof DijkstraNode) {
			return nodeIndex == ((DijkstraNode)other).nodeIndex;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return nodeIndex;
	}

	@Override
	public int compareTo(DijkstraNode other) {
		if (Objects.equals(this.cost, other.cost)) return 0;
		if (this.cost > other.cost) return 1;
		else return -1;
	}

	@Override
	public String toString() {
		return String.format("[%s|%s|%s]", nodeIndex, cost, predecessor.nodeIndex);
	}
}
