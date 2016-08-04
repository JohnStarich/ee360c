/**
 * Program 3: Dynamic Programming
 * @author johnstarich
 */
public class Program3 implements IProgram3 {
	private int numClasses;
	private int maxGrade;
	private GradeFunction gradeFunction;

	public Program3() {
		this.numClasses = 0;
		this.maxGrade = 0;
		this.gradeFunction = null;
	}

	public void initialize(int numClasses, int maxGrade, GradeFunction gradeFunction) {
		this.numClasses = numClasses;
		this.maxGrade = maxGrade;
		this.gradeFunction = gradeFunction;
	}

	public int getNumClasses() { return numClasses; }

	public int getMaxGrade() { return maxGrade; }

	public GradeFunction getGradeFunction() { return gradeFunction; }

	public int[] computeHours(int totalHours) {
		int[] computeHours = new int[numClasses];
		return computeHours;
	}

	public int[] computeGrades(int totalHours) {
		int[] computeGrades = new int[numClasses];
		return computeGrades;
	}
}
