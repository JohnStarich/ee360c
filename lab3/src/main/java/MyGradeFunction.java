/**
 * Custom grade function for Lab 3
 * EID: js68634
 * Created by John Starich on 8/7/16.
 */
public class MyGradeFunction implements GradeFunction {
	private int numClasses;
	private int maxGrade;

	public MyGradeFunction(int numClasses, int maxGrade) {
		this.numClasses = numClasses;
		this.maxGrade = maxGrade;
	}

	@Override
	public int grade(int classID, int hours) {
		// place upper bound on the grade
		return Math.min(maxGrade, gradeUnbounded(classID, hours));
	}

	/**
	 * If the class has an even class ID then multiply by 3/2 for the grade, else use hours
	 * @param classID the class index
	 * @param hours the number of hours spent on the class
	 * @return the grade, without an upper bound of the maximum grade
	 */
	private int gradeUnbounded(int classID, int hours) {
		if(classID % 2 == 0) {
			return hours * 3 / 2;
		}
		return hours;
	}
}
