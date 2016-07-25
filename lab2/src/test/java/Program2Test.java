import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by johnstarich on 7/10/16.
 */
public class Program2Test extends TestCase {
	public Map<Integer, Vector<Integer>> parseSolution(String fileName) throws Exception {
		Map<Integer, Vector<Integer>> solution = new HashMap<>();
		Pattern linePattern = Pattern.compile("(?x)" +
			"(?<start>\\d+) \\s-\\s" +
			"(?<end> (\\d+,)* \\d+ )+" +
			".*");
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		for(String line = reader.readLine(); line != null; line = reader.readLine()) {
			Matcher m = linePattern.matcher(line);
			if(m.matches()) {
				Integer start = Integer.parseInt(m.group("start"));
				String[] ends = m.group("end").split(",");
				Vector<Integer> intEnds = new Vector<>(ends.length);
				for (String end : ends) { intEnds.add(Integer.parseInt(end)); }
				solution.put(start, intEnds);
			}
		}

		return solution;
	}

	public void testGpsr() throws Exception {
		Map<Integer, Vector<Integer>> expectedSolution = parseSolution("data/in1_solution.txt");

		Map<Integer, Vector<Integer>> programSolution = new HashMap<>(expectedSolution.size());
		Program2 program = new Program2("data/in1.txt");
		Vector<Vertex> location = program.location;
		int numSuccesses = 0;
		long totalTimeSuccesses = 0;
		System.out.println("Paths between all pairs of vertices using the GPSR algorithm:");
		for (int start = 0; start < location.size(); start++) {
			for (int end = 0; end < location.size(); end++) {
				if(start == end) continue;

				long startTime = System.nanoTime();
				Vector<Vertex> path = program.gpsrPath(start, end);
				long endTime = System.nanoTime();
				if (! path.isEmpty()) {
					numSuccesses++;
					if(programSolution.containsKey(start)) {
						programSolution.get(start).add(end);
					}
					else {
						Vector<Integer> v = new Vector<>(1);
						v.add(end);
						programSolution.put(start, v);
					}
					totalTimeSuccesses += (endTime - startTime);
				}
				System.out.println("I = " + start + " J = " + end + " : " + path.toString());
			}
		}
		System.out.println("The GPSR algorithm is successful " + numSuccesses + "/" + location.size()*location.size() + " times.");
		if (numSuccesses != 0) {
			System.out.println("The average time taken by the GPSR algorithm on successful runs is " + totalTimeSuccesses/numSuccesses + " nanoseconds.");
		} else {
			System.out.println("The average time taken by the GPSR algorithm on successful runs is N/A nanoseconds.");
		}
		System.out.println();

		for (int i = 0; i < location.size(); i++) {
			System.out.println("------------------------------------------");
			System.out.println("expected[" + i + "] = " + expectedSolution.get(i));
			System.out.println("  actual[" + i + "] = " + programSolution.get(i));
			System.out.println("              " + (expectedSolution.get(i).equals(programSolution.get(i)) ? "success" : "FAILURE"));
		}
		assertEquals(expectedSolution, programSolution);
	}

	public void testDijkstraPathLatency() throws Exception {
		Map<Integer, Vector<Integer>> expectedSolution = new HashMap<>(8);
		Vector<Integer> fullSolution = new Vector<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
		for(int i = 0; i < 8; i++) expectedSolution.put(i, fullSolution);

		for(int range = 3; range >= 2; range--) {
			Map<Integer, Vector<Integer>> programSolution = new HashMap<>(expectedSolution.size());
			Map<Integer, Vector<Double>> programCosts = new HashMap<>(expectedSolution.size());
			Program2 program = new Program2("data/in2.txt");
			program.setTransmissionRange(range);
			Vector<Vertex> location = program.location;
			int numSuccesses = 0;
			long totalTimeSuccesses = 0;
			System.out.println("Paths between all pairs of vertices using the Dijkstra latency algorithm:");

			for (int start = 0; start < location.size(); start++) {
				for (int end = 0; end < location.size(); end++) {
					long startTime = System.nanoTime();
					Vector<Vertex> path = program.dijkstraPathLatency(start, end);
					long endTime = System.nanoTime();
					if (! path.isEmpty()) {
						numSuccesses++;
						if (programSolution.containsKey(start)) {
							programSolution.get(start).add(end);
						} else {
							Vector<Integer> v = new Vector<>(1);
							v.add(end);
							programSolution.put(start, v);
						}
						totalTimeSuccesses += (endTime - startTime);
						double totalCost = 0;
						for(int i = 0; i < path.size() - 1; i++) {
							int vertexIndex1 = program.location.indexOf(path.get(i));
							int vertexIndex2 = program.location.indexOf(path.get(i + 1));
							Edge edgeUsed = null;
							for (Edge edge : program.edges) {
								if((vertexIndex1 == edge.getU() && vertexIndex2 == edge.getV()) || (vertexIndex1 == edge.getV() && vertexIndex2 == edge.getU()))
									edgeUsed = edge;
							}
							totalCost += edgeUsed.getW();
						}
						if(programCosts.containsKey(start)) {
							programCosts.get(start).add(totalCost);
						}
						else {
							Vector<Double> v = new Vector<>(1);
							v.add(totalCost);
							programCosts.put(start, v);
						}
					}
					System.out.println("I = " + start + " J = " + end + " : " + path.toString());
				}
			}
			System.out.println("The Dijkstra latency algorithm is successful " + numSuccesses + "/" + location.size() * location.size() + " times.");
			if (numSuccesses != 0) {
				System.out.println("The average time taken by the Dijkstra latency algorithm on successful runs is " + totalTimeSuccesses / numSuccesses + " nanoseconds.");
			} else {
				System.out.println("The average time taken by the Dijkstra latency algorithm on successful runs is N/A nanoseconds.");
			}
			System.out.println();

			for (int i = 0; i < location.size(); i++) {
				System.out.println("------------------------------------------");
				System.out.println("expected[" + i + "] = " + expectedSolution.get(i));
				System.out.println("  actual[" + i + "] = " + programSolution.get(i));
				System.out.println("              " + (expectedSolution.get(i).equals(programSolution.get(i)) ? "success" : "FAILURE"));
			}
			assertEquals(expectedSolution, programSolution);

			System.out.println("| Start | End   | Cost  |");
			System.out.println("|-----------------------|");
			for(int start = 0; start < location.size(); start++) {
				for (int end = 0; end < programCosts.get(start).size(); end++) {
					double cost = programCosts.get(start).get(end);
					System.out.printf("| %5d | %5d | %5.1f |\n", start, end, cost);
				}
			}
			double cost = programCosts.get(0).get(7);
			switch (range) {
				case 3:
					assertEquals("Cost must equal 5.0", 5.0, cost);
					break;
				case 2:
					assertEquals("Cost must equal 104.0", 104.0, cost);
					break;
			}
		}
	}

	public void testDijkstraPathHops() throws Exception {
		Map<Integer, Vector<Integer>> expectedSolution = new HashMap<>(8);
		Vector<Integer> fullSolution = new Vector<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
		for(int i = 0; i < 8; i++) expectedSolution.put(i, fullSolution);

		for(int range = 3; range >= 2; range--) {
			Map<Integer, Vector<Integer>> programSolution = new HashMap<>(expectedSolution.size());
			Map<Integer, Vector<Integer>> programHops = new HashMap<>(expectedSolution.size());
			Program2 program = new Program2("data/in2.txt");
			program.setTransmissionRange(range);
			Vector<Vertex> location = program.location;
			int numSuccesses = 0;
			long totalTimeSuccesses = 0;
			System.out.println("Paths between all pairs of vertices using the Dijkstra latency algorithm:");

			for (int start = 0; start < location.size(); start++) {
				for (int end = 0; end < location.size(); end++) {
					long startTime = System.nanoTime();
					Vector<Vertex> path = program.dijkstraPathHops(start, end);
					long endTime = System.nanoTime();
					if (! path.isEmpty()) {
						numSuccesses++;
						if (programSolution.containsKey(start)) {
							programSolution.get(start).add(end);
						} else {
							Vector<Integer> v = new Vector<>(1);
							v.add(end);
							programSolution.put(start, v);
						}
						totalTimeSuccesses += (endTime - startTime);
						if(programHops.containsKey(start)) {
							programHops.get(start).add(path.size() - 1);
						}
						else {
							Vector<Integer> v = new Vector<>(1);
							v.add(path.size() - 1);
							programHops.put(start, v);
						}
					}
					System.out.println("I = " + start + " J = " + end + " : " + path.toString());
				}
			}
			System.out.println("The Dijkstra latency algorithm is successful " + numSuccesses + "/" + location.size() * location.size() + " times.");
			if (numSuccesses != 0) {
				System.out.println("The average time taken by the Dijkstra latency algorithm on successful runs is " + totalTimeSuccesses / numSuccesses + " nanoseconds.");
			} else {
				System.out.println("The average time taken by the Dijkstra latency algorithm on successful runs is N/A nanoseconds.");
			}
			System.out.println();

			for (int i = 0; i < location.size(); i++) {
				System.out.println("------------------------------------------");
				System.out.println("expected[" + i + "] = " + expectedSolution.get(i));
				System.out.println("  actual[" + i + "] = " + programSolution.get(i));
				System.out.println("              " + (expectedSolution.get(i).equals(programSolution.get(i)) ? "success" : "FAILURE"));
			}
			assertEquals(expectedSolution, programSolution);

			System.out.println("| Start | End   | Hops  |");
			System.out.println("|-----------------------|");
			for(int start = 0; start < location.size(); start++) {
				for (int end = 0; end < programHops.get(start).size(); end++) {
					int hops = programHops.get(start).get(end);
					System.out.printf("| %5d | %5d | %5d |\n", start, end, hops);
				}
			}
			int hops = programHops.get(0).get(7);
			System.out.println("Hops = " + hops);
			switch (range) {
				case 3:
					assertEquals("Hops must equal 3", 3, hops);
					break;
				case 2:
					assertEquals("Hops must equal 5", 5, hops);
					break;
			}
		}
	}

}
