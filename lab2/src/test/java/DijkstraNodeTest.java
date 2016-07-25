import junit.framework.TestCase;

import java.util.PriorityQueue;

/**
 * Created by johnstarich on 7/20/16.
 */
public class DijkstraNodeTest extends TestCase {
	public void testCompareTo() throws Exception {
		PriorityQueue<DijkstraNode> nodes = new PriorityQueue<>();
		for(int i = 0; i < 100; i++) nodes.add(new DijkstraNode(i));
		nodes.peek().cost = 0D;

		assertEquals(nodes.poll().cost, 0D);
		while(! nodes.isEmpty()) {
			assertEquals(nodes.poll().cost, Double.POSITIVE_INFINITY);
		}
	}
}