import junit.framework.TestCase;

/**
 * Test Program 3: Dynamic Programming
 * Created by johnstarich on 8/3/16.
 */
public class Program3Test extends TestCase {
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
	}

	public void testComputeGrades() throws Exception {
	}
}