import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Test Program 3: Dynamic Programming
 * EID: js68634
 * Created by John Starich on 8/3/16.
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

	public void testMyGradeFunction() throws Exception {
		Program3 program = new Program3();
		int n = 10;
		int H = 40;
		int maxGrade = 10;
		program.initialize(n, maxGrade, new MyGradeFunction(n, maxGrade));
		assertEquals(H, sum(program.computeHours(H)));
		assertEquals(49, sum(program.computeGrades(H)));
	}

	/** Test successive calls to Program3 don't corrupt the state of the instance */
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

		n = 20;
		H = 40;
		maxGrade = 10;
		program.initialize(n, maxGrade, new SquareRootGradeFunction(n, maxGrade));
		assertEquals(H, sum(program.computeHours(H)));
		assertEquals(30, sum(program.computeGrades(H)));

		H = 20;
		assertEquals(20, sum(program.computeGrades(H)));

		H = 80;
		assertEquals(45, sum(program.computeGrades(H)));
	}

	/** Test case where no work can be done to improve the overall grade */
	public void testAllZeros() throws Exception {
		Program3 program = new Program3();
		int n = 10;
		int H = 40;
		int maxGrade = 10;
		program.initialize(n, maxGrade, new GradeFunction() {
			@Override
			public int grade(int classID, int hours) {
				return 0;
			}
		});
		assertEquals(0, sum(program.computeHours(H)));
		assertEquals(0, sum(program.computeGrades(H)));
	}

	/** Test another custom grade function for completeness sake. */
	public void testIncreaseOnClassId() throws Exception {
		Program3 program = new Program3();
		final int n = 10;
		int H = 40;
		final int maxGrade = 10;
		program.initialize(n, maxGrade, new GradeFunction() {
			@Override
			public int grade(int classID, int hours) {
				if(hours == n - classID) return Math.min(maxGrade, classID);
				return 0;
			}
		});
		assertEquals(40, sum(program.computeHours(H)));
		assertEquals(44, sum(program.computeGrades(H)));
	}

	/** Ensure that if every class is an easy-A then no work is done at all. */
	public void testAllMax() throws Exception {
		Program3 program = new Program3();
		int n = 10;
		int H = 40;
		final int maxGrade = 10;
		program.initialize(n, maxGrade, new GradeFunction() {
			@Override
			public int grade(int classID, int hours) {
				return maxGrade;
			}
		});
		assertEquals(0, sum(program.computeHours(H)));
		assertEquals(100, sum(program.computeGrades(H)));
	}

	public void testSquareRootMore() throws Exception {
		Program3 program = new Program3();
		int[][] solutions = {
			//n, H, maxGrade, sumHours, sumGrades
			{2, 400, 10, 182, 20},
			{2, 2, 10, 2, 2},
			{4, 100, 8, 100, 21},
		};
		for(int[] solution : solutions) {
			assertEquals("Error in solution for testSquareRootMore: length of solution is not 5", 5, solution.length);
			int n = solution[0];
			int H = solution[1];
			int maxGrade = solution[2];
			System.out.printf("Initializing program with n = %d, H = %d, maxGrade = %d\n", n, H, maxGrade);
			program.initialize(n, maxGrade, new SquareRootGradeFunction(n, maxGrade));
			int hours[] = program.computeHours(H);
			int grades[] = program.computeGrades(H);
			System.out.printf("hours = %s\ngrades = %s\n", Arrays.toString(hours), Arrays.toString(grades));
			assertEquals("Sum of hours is incorrect", solution[3], sum(hours));
			assertEquals("Sum of grades is incorrect", solution[4], sum(grades));
		}
	}
}