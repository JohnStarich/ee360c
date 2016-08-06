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

	public void testComputeHours() throws Exception {
		Program3 program = new Program3();
		program.initialize(10, 3, new SquareRootGradeFunction(10, 3));

		assertEquals(40, sum(program.computeHours(40)));
	}

	public void testComputeGrades() throws Exception {
		Program3 program = new Program3();
		program.initialize(10, 3, new SquareRootGradeFunction(10, 3));

		assertEquals(22, sum(program.computeGrades(40)));
	}
}