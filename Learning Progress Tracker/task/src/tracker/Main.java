package tracker;

import java.util.Scanner;

public class Main {
    protected static final Course javaCourse = new Course(Courses.JAVA, 0L, 0L, 0.0);
    protected static final Course dsaCourse = new Course(Courses.DSA, 0L, 0L, 0.0);
    protected static final Course databasesCourse = new Course(Courses.DATABASES, 0L, 0L, 0.0);
    protected static final Course springCourse = new Course(Courses.SPRING, 0L, 0L, 0.0);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Learning Progress Tracker");
        String input = scanner.nextLine();
        CliUtil.addStudents(input, scanner);
    }
}

