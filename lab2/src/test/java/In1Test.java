import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by johnstarich on 7/10/16.
 */
public class In1Test extends TestCase {
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
}
