/**
 * Program 3: Dynamic Programming
 * @author johnstarich
 */
public class Program3 implements IProgram3 {
	private int numClasses;
	private int maxGrade;
	GradeFunction gf;

	public Program3() {
		this.numClasses = 0;
		this.maxGrade = 0;
		this.gf = null;
	}

	public int getNumClasses() { return numClasses; }

	public int getMaxGrade() { return maxGrade; }

	public GradeFunction getGradeFunction() { return gf; }

	public void initialize(int n, int g, GradeFunction gf) {
		this.numClasses = n;
		this.maxGrade = g;
		this.gf = gf;
	}

	public int[] computeHours(int totalHours) {
		int[] computeHours = new int[numClasses];
		return computeHours;
	}

	public int[] computeGrades(int totalHours) {
		int[] computeGrades = new int[numClasses];
		return computeGrades;
	}
}
