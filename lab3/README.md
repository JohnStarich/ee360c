# Lab 3: Dyanamic Programming

written by John Starich (UT eID: js68634)

## Goal

To choose the most optimal number of hours to spend on each class, I designed the algorithm such that it would choose to add one hour of work to the class that would produce the largest increase in the total grade.

## Algorithm

This algorithm provides optimal substructure because it chooses the best possible next hour's placement at each iteration. (Each iteration handles one less remaining hour from the maximum number of hours.)

The recurrence relation is `T(H) = T(H - 1) + O(1)` where each recurrence the algorithm reduces the number of remaining hours to find an optimal solution for, performing constant time work outside of that.

A pseudo-code representation of this algorithm in its (currently implemented) iterative form:

```
// first compute optimal hours

int hours[numClasses]; // create array of hours to take per class
set all hours locations to = 0

// compute all possible grades for given totalHours
int[][] grades = new int[numClasses][totalHours + 1];
for every class c in numClasses:
    for every hour h from 0 up to totalHours + 1:
        grades[c][h] = gradeFunction.grade(c, h);

// find the best class to spend an hour on and spend it until there aren't any hours left
while there are still hours left to spend on increasing one's grade:
    int bestClassGradeAddition = 0;
    int bestClassToWorkOn = NOT_FOUND;
    // if the best class to work on is not found because no class can improve their grade with just one hour,
    // keep looking ahead an additional hour until an improvement is found
    for every hourOffset from 0 up to totalHours as long as we haven't
        found a class where an hour could improve the total grade:
        for every classIndex from 0 up to numClasses:
            // find grade increase if we added another hour to this class
            int gradeIncrease = grades[classIndex][hours[classIndex] + hourOffset + 1] - grades[classIndex][hours[classIndex] + hourOffset]

            // if the grade can improve with this class over the last best improvement
            // then mark this class index down and remember the increase value
            if gradeAfterWorkingAnotherHour > bestClassGradeAddition:
                bestClassGradeAddition = gradeAfterWorkingAnotherHour
                bestClassToWorkOn = classIndex

    // if there are no more hours in which one can improve their grade, just don't work anymore
    if bestClassToWorkOn == NOT_FOUND:
        return hours
    // else increase this class's number of hours by one
    hours[bestClassToWorkOn] += 1

// computed hours is stored in the hours array

// now compute the grades for the optimal hours
int computedGrades[numClasses]
for every classIndex from 0 up to numClasses:
    computedGrades[classIndex] = grades[classIndex][ hours[classIndex] ]
```

## Testing Methodology

During the design of this algorithm several tests came to mind, as well as several edge cases that must be tested for as well.

The first two tests I created simply checked the output of using the `SquareRootGradingFunction` and the `CustomGradeFunction` with the sample output's results to ensure basic functionality.

In addition to these tests I also tested basic functionality of `initialize` in `Program3` to ensure it always instantiated correctly.
The second basic functionality test was for my own `MyGradeFunction`.
The third tested successive calls to an instance of `Program3` to make sure the state did not become corrupted.

Next I tested the case where no work could increase the grade in any class. In doing this I found a bug where if no optimal choice could be found, the program would crash. I added an early return statement to account for this case such that "if no more work can increase the overall grade, then don't try to work anymore and return".

My last test case checked to ensure that no hours would be worked if no work was required in any class to get a perfect score. This essentially checked for no crashes in a solution where every solution is to not work at all.
