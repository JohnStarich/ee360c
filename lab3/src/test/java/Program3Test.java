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
		int maxGrade = 3;
		program.initialize(n, maxGrade, new SquareRootGradeFunction(n, maxGrade));

		assertEquals(H, sum(program.computeHours(H)));
	}

	public void testComputeGradesSquareRoot() throws Exception {
		Program3 program = new Program3();
		int n = 10;
		int H = 40;
		int maxGrade = 3;
		program.initialize(n, maxGrade, new SquareRootGradeFunction(n, maxGrade));
		assertEquals(22, sum(program.computeGrades(H)));
	}

	public void testComputeGradesCustom() throws Exception {
		Program3 program = new Program3();
		int n = 10;
		int H = 20;
		int maxGrade = 10;
		program.initialize(n, maxGrade, new CustomGradeFunction(n, maxGrade));

		assertEquals(35, sum(program.computeGrades(H)));
	}

	public void testSuccessiveRunsOnProgram() throws Exception {
		Program3 program = new Program3();
		int n = 10;
		int H = 40;
		int maxGrade = 10;
		program.initialize(n, maxGrade, new SquareRootGradeFunction(n, maxGrade));
		assertEquals(H, sum(program.computeHours(H)));
		assertEquals(22, sum(program.computeGrades(H)));

		H = 20;
		assertEquals(15, sum(program.computeGrades(H)));

		H = 80;
		assertEquals(31, sum(program.computeGrades(H)));
	}
}