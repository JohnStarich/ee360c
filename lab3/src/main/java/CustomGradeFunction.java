public class CustomGradeFunction implements GradeFunction{

	private int numClasses;
	private int maxGrade;

	public CustomGradeFunction(int n, int g){
		this.numClasses = n;
		this.maxGrade = g;
	}

	/*
	 * This grade function returns values from a
	 * predefined matrix
	 */
	public int grade(int classID, int hours){

		int [][] gradearray = { {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
				{1, 2, 3, 4, 5, 6, 7, 8, 8, 10},
				{0, 2, 4, 6, 8, 8, 8, 8, 8, 8}};
		int classIndex = classID % 3;
		int hoursIndex = hours %10;
		return Math.min(gradearray[classIndex][hoursIndex],maxGrade);
	}

}
