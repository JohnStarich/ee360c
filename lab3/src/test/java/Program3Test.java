import junit.framework.TestCase;

/**
 * Test Program 3: Dynamic Programming
 * Created by johnstarich on 8/3/16.
 */
public class Program3Test extends TestCase {
	private int sum(int[] integers) {
		int total = 0;
		for(int i : integers) total += i;
		return total;
	}

	public void testInitialize() throws Exception {
		Program3 program = new Program3();
		int numClasses = 10, maxGrade = 3;
		GradeFunction gradeFunction = new SquareRootGradeFunction(numClasses, maxGrade);
		program.initialize(numClasses, maxGrade, gradeFunction);
		assertEquals(numClasses, program.getNumClasses());
		assertEquals(maxGrade, program.getMaxGrade());
		assertSame(gradeFunction, program.getGradeFunction());
	}

	public void testComputeHoursSquareRoot() throws Exception {
		Program3 program = new Program3();
		int n = 10;
		int H = 40;
		program.initialize(n, 3, new SquareRootGradeFunction(n, 3));

		assertEquals(H, sum(program.computeHours(H)));
	}

	public void testComputeGradesSquareRoot() throws Exception {
		Program3 program = new Program3();
		int n = 10;
		int H = 40;
		program.initialize(n, 3, new SquareRootGradeFunction(n, 3));
		assertEquals(22, sum(program.computeGrades(H)));
	}

	public void testComputeGradesCustom() {
		Program3 program = new Program3();
		program.initialize(10, 20, new CustomGradeFunction(10, 20));

		assertEquals(35, sum(program.computeGrades(20)));
	}
}