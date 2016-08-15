/**
 * Program 3: Dynamic Programming
 *
 * EID: js68634
 * @author John Starich
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
		allPossibleGrades = null;
	}

	/**
	 * Calculate all possible grades for a given class and number of hours
	 * @param totalHours total possible hours to spend on classes
	 * @return all possible grades for all classes with a max number of hours
	 */
	private int[][] computeAllGrades(int totalHours) {
		if(allPossibleGrades != null) {
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
					int hourGradeIndex = currentClassHours + hoursOffset;
					// if somehow this puts over the maximum number of hours into a single course
					// then grade increase from previous hour index is 0
					// else calculate grade increase
					int gradeAfterWorkingAnotherHour = 0;
					if(hourGradeIndex + 1 < allPossibleGrades[classIndex].length) {
						gradeAfterWorkingAnotherHour = allPossibleGrades[classIndex][hourGradeIndex + 1] - allPossibleGrades[classIndex][hourGradeIndex];
					}

					// if the grade can improve with this class over the last best improvement
					// then mark this class index down and remember the increase value
					if (gradeAfterWorkingAnotherHour > bestClassGradeAddition) {
						bestClassGradeAddition = gradeAfterWorkingAnotherHour;
						bestClassToWorkOn = classIndex;
					}
				}
			}
			// if there are no more hours in which one can improve their grade, just don't work anymore
			if(bestClassToWorkOn == -1) {
				return hours;
			}
			// else increase this class's number of hours by one
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
