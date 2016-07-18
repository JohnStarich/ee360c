
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

		System.out.println(matching);

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

	public void testStableBruteForce() throws Exception {
		Program1 program = new Program1();
		Matching matching, result;

		for(int i = 4; i <= 10; i++) {
			matching = Driver.parseMatchingProblem("small_inputs/" + i + ".in");
			result = program.stableMatchingBruteForce(matching);
			System.out.println(i + ".in\n" + result + "\n\n");
			assertTrue("Should be stable", program.isStableMatching(result));
		}
	}

	public void testStableGaleShapley() throws Exception {
		Program1 program = new Program1();
		Matching matching, result;

		for(int i = 4; i <= 10; i++) {
			matching = Driver.parseMatchingProblem("small_inputs/" + i + ".in");
			result = program.stableMatchingGaleShapley(matching);
			System.out.println(i + ".in\n" + result + "\n\n");
			assertTrue("Should be stable", program.isStableMatching(result));
		}
	}

	public void testStableGaleShapleyLargeInput() throws Exception {
		Program1 program = new Program1();
		Matching matching, result;

		for(int i = 320; i <= 1280; i *= 2) {
			matching = Driver.parseMatchingProblem("large_inputs/" + i + ".in");
			result = program.stableMatchingGaleShapley(matching);
			System.out.println(i + ".in\n" + result + "\n\n");
			assertTrue("Should be stable", program.isStableMatching(result));
		}
	}
}