/**
 * Program 3: Dynamic Programming
 * @author johnstarich
 */
public class Program3 implements IProgram3 {
	private int numClasses;
	private int maxGrade;
	private GradeFunction gradeFunction;
	private int[][] allPossibleGrades = null;

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

	/**
	 * Calculate all possible grades for a given class and number of hours
	 * @param totalHours total possible hours to spend on classes
	 * @return all possible grades for all classes with a max number of hours
	 */
	private int[][] computeAllGrades(int totalHours) {
		if(allPossibleGrades != null && totalHours == allPossibleGrades[0].length - 1) {
			return allPossibleGrades;
		}
		allPossibleGrades = new int[numClasses][totalHours + 1];
		for (int classIndex = 0; classIndex < numClasses; classIndex++) {
			for (int hourIndex = 0; hourIndex < totalHours + 1; hourIndex++) {
				allPossibleGrades[classIndex][hourIndex] = gradeFunction.grade(classIndex, hourIndex);
			}
		}
		return allPossibleGrades;
	}

	public int getNumClasses() { return numClasses; }

	public int getMaxGrade() { return maxGrade; }

	public GradeFunction getGradeFunction() { return gradeFunction; }

	public int[] computeHours(int totalHours) {
		// create array of hours to take per class
		int[] hours = new int[numClasses];
		// initialize hours array
		for(int hourIndex = 0; hourIndex < numClasses; hourIndex++) {
			hours[hourIndex] = 0;
		}
		// ensure that all possible grades are computed before-hand
		computeAllGrades(totalHours);
		// find the best class to spend an hour on and spend it until there aren't any hours left
		for(int currentHours = 0; currentHours < totalHours; currentHours += 1) {
			int bestClassGradeAddition = 0;
			int bestClassToWorkOn = -1;
			// if the best class to work on is not found because no class can improve their grade with just one hour,
			// keep looking ahead an additional hour until an improvement is found
			for(int hoursOffset = 0; hoursOffset < totalHours && bestClassToWorkOn == -1; hoursOffset++) {
				for (int classIndex = 0; classIndex < numClasses; classIndex++) {
					int currentClassHours = hours[classIndex];
					int gradeAfterWorkingAnotherHour = allPossibleGrades[classIndex][currentClassHours + 1 + hoursOffset] - allPossibleGrades[classIndex][currentClassHours + hoursOffset];

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
		// create array of grades per class if optimal hours are taken
		int[] computeGrades = new int[numClasses];
		// compute and store all possible grades for this many hours
		computeAllGrades(totalHours);
		// compute the optimal hours to take per class
		int[] classHours = computeHours(totalHours);
		for(int classIndex = 0; classIndex < numClasses; classIndex++) {
			computeGrades[classIndex] = allPossibleGrades[classIndex][classHours[classIndex]];
		}
		return computeGrades;
	}
}
