
import junit.framework.TestCase;

import java.util.Vector;

/**
 * Created by johnstarich on 6/26/16.
 */
public class Program1Test extends TestCase {
	public void testStable1() throws Exception {
		Program1 program = new Program1();
		Matching matching = Driver.parseMatchingProblem("small_inputs/4.in");

		Vector<Integer> newMatches = new Vector<>();
		newMatches.add(2);
		newMatches.add(0);
		newMatches.add(1);
		newMatches.add(3);
		matching = new Matching(matching, newMatches);

//		System.out.println(matching);

		assertTrue("Should be stable", program.isStableMatching(matching));
	}

	public void testStable2() throws Exception {
		Program1 program = new Program1();
		Matching matching = Driver.parseMatchingProblem("small_inputs/4.in");

		Vector<Integer> newMatches = new Vector<>();
		newMatches.add(3);
		newMatches.add(2);
		newMatches.add(1);
		newMatches.add(0);
		matching = new Matching(matching, newMatches);

		System.out.println(matching);

		assertTrue("Should be stable", program.isStableMatching(matching));
	}

	public void testUnstable() throws Exception {
		Program1 program = new Program1();
		Matching matching = Driver.parseMatchingProblem("small_inputs/4.in");

		Vector<Integer> newMatches = new Vector<>();
		newMatches.add(0);
		newMatches.add(2);
		newMatches.add(3);
		newMatches.add(1);
		matching = new Matching(matching, newMatches);

		System.out.println(matching);

		assertFalse("Should not be stable", program.isStableMatching(matching));
	}

	public boolean checkBruteStability(String fileName) throws Exception {
		Program1 program = new Program1();
		Matching matching = Driver.parseMatchingProblem(fileName);
		long start = System.nanoTime();
		Matching result = program.stableMatchingBruteForce(matching);
		long end = System.nanoTime();
		System.out.printf("\n%s:\n%s\n\n", fileName, result.toString());
		System.out.printf("BF - Run time of %20s: %10.8f seconds\n", fileName, (end - start) / 1.0e9);
		return program.isStableMatching(result);
	}

	public boolean checkGSStability(String fileName) throws Exception {
		Program1 program = new Program1();
		Matching matching = Driver.parseMatchingProblem(fileName);
		long start = System.nanoTime();
		Matching result = program.stableMatchingGaleShapley(matching);
		long end = System.nanoTime();
		System.out.printf("\n%s:\n%s\n\n", fileName, result.toString());
		System.out.printf("GS - Run time of %20s: %10.8f seconds\n", fileName, (end - start) / 1.0e9);
		return program.isStableMatching(result);
	}

	public void testStableBruteForce() throws Exception {
		for(int i = 4; i <= 10; i++) {
			assertTrue("Should be stable", checkBruteStability("small_inputs/" + i + ".in"));
		}
	}

	public void testStableGaleShapley() throws Exception {
		for(int i = 4; i <= 10; i++) {
			assertTrue("Should be stable", checkGSStability("small_inputs/" + i + ".in"));
		}
	}

	public void testStableGaleShapleyLargeInput() throws Exception {
		for(int i = 320; i <= 1280; i *= 2) {
			assertTrue("Should be stable", checkGSStability("large_inputs/" + i + ".in"));
		}
	}
}