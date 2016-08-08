

public interface IProgram3 {
	/**
	 * This method is used in lieu of a required constructor signature to initialize
	 * your Program3. After calling a default (no-parameter) constructor, we
	 * will use this method to initialize your Program3.
	 * @param numClasses number of classes to compute hours and grades
	 * @param maxGrade maximum possible grade to cap the grade function's output
	 * @param gradeFunction function that computes a grade for a given course and number of hours
	 */
	public void initialize(int numClasses, int maxGrade, GradeFunction gradeFunction);

	/**
	 * This method returns an array that is the size of the number of classes.
	 * Entry i in the array is the (integer) number of hours one should spend on
	 * class i (0 <= i <= n-1) in maximizing all of one's grades.
	 * @param totalHours the maximum number of hours to spend on all classes combined
	 * @return array the size of the number of classes containing the number of hours to take per
	 * class for an optimum average grade
	 */
	public int[] computeHours(int totalHours);

	/**
	 * This method does basically the same thing, but instead of telling the
	 * number of hours to spend on class i, it tells what grade one will get if
	 * one spends the optimal number of hours.
	 * @param totalHours the maximum number of hours to spend on all classes combined
	 * @return array the size of the number of classes containing the grade per course if an optimum
	 * number of hours is chosen
	 */
	public int[] computeGrades(int totalHours);
}