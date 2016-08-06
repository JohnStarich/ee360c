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
		int[] hours = new int[numClasses];
		// initialize hours array
		for(int hourIndex = 0; hourIndex < numClasses; hourIndex++) {
			hours[hourIndex] = 0;
		}
		// TODO move this into a different function
		// calculate all possible grades for a given class and number of hours
		int[][] grades = new int[numClasses][totalHours + 1];
		for (int classIndex = 0; classIndex < numClasses; classIndex++) {
			for (int hourIndex = 0; hourIndex < totalHours + 1; hourIndex++) {
				grades[classIndex][hourIndex] = gradeFunction.grade(classIndex, hourIndex);
			}
		}
		// find the best class to spend an hour on and spend it until there aren't any hours left
		for(int currentHours = 0; currentHours < totalHours; currentHours += 1) {
			int bestClassGradeAddition = 0;
			int bestClassToWorkOn = -1;
			for(int hoursOffset = 0; hoursOffset < totalHours && bestClassToWorkOn == -1; hoursOffset++) {
				for (int classIndex = 0; classIndex < numClasses; classIndex++) {
					int currentClassHours = hours[classIndex];
					int gradeAfterWorkingAnotherHour = grades[classIndex][currentClassHours + 1 + hoursOffset] - grades[classIndex][currentClassHours + hoursOffset];

					if (gradeAfterWorkingAnotherHour > bestClassGradeAddition) {
						bestClassGradeAddition = gradeAfterWorkingAnotherHour;
						bestClassToWorkOn = classIndex;
					}
				}
			}
			hours[bestClassToWorkOn] += 1;
		}
		return hours;
	}

	public int[] computeGrades(int totalHours) {
		int[] computeGrades = new int[numClasses];
		int[] classHours = computeHours(totalHours);
		for(int classIndex = 0; classIndex < numClasses; classIndex++) {
			computeGrades[classIndex] = gradeFunction.grade(classIndex, classHours[classIndex]);
		}
		return computeGrades;
	}
}
