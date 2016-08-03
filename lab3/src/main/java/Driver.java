public class Driver {

	public static void main(String[] args) {

	if (args.length == 0) {
			System.err.println("usage: java Driver -n <num_courses> -h <total_num_hours>");
			System.exit(1);
		}

		Program3 program = new Program3();
	int n = Integer.parseInt(args[1]);
	int h = Integer.parseInt(args[3]);
		int sum_grade;
		int maxgrade = 100;
		SquareRootGradeFunction gf = new SquareRootGradeFunction(n, maxgrade);
//		CustomGradeFunction gf = new CustomGradeFunction(n, maxgrade);

		program.initialize(n, maxgrade, gf);
		int[] hours_array = program.computeHours(h);
		int[] grades_array = program.computeGrades(h);
		sum_grade = 0;
		for (int i=0; i<hours_array.length; i++) {
			System.out.println("Class " + i + " Hours " + hours_array[i] + " Grade " + grades_array[i]);
			sum_grade += grades_array[i];
		}
		System.out.println("Total Grade " + sum_grade);
	}
}
