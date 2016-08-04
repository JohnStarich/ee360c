

public class SquareRootGradeFunction implements GradeFunction{
	private int numClasses;
	private int maxGrade;

	public SquareRootGradeFunction(int numClasses, int maxGrade) {
		this.numClasses = numClasses;
		this.maxGrade = maxGrade;
	}

	/*
	 * This grade function gives diminishing returns as
	 * you put in more hours
	 */
	public int grade(int classID, int hours){
		int root = (int) Math.round(Math.sqrt(hours));
		return Math.min(root,maxGrade);
	}
}
