package tracker;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static tracker.Courses.*;


public class Student {
    private final Map<Courses, Double> maxCoursesValues = Map.of(
            JAVA, 600.0,
            DSA, 400.0,
            DATABASES, 480.0,
            SPRING, 550.0);

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Map<Courses, Long> coursesMap; // mapa, w której key = nazwa kursu, value = łączna liczba punktów studenta na tym kursie
    private long score; // łączna liczba wszystkich punktów studenta z wszystkich kursów
    private final List<Courses> enrolledInCoursesList = new ArrayList<>(); // Nazwy kursów, na które student jest zapisany, tzn. takie, na których nie ma zero punktów

    public String getId() {
        return id;
    }

    public Map<Courses, Long> getCoursesMap() {
        return coursesMap;
    }

    public void setCoursesMap() {
        Map<Courses, Long> map = new LinkedHashMap<>();
        map.put(JAVA, 0L);
        map.put(DSA, 0L);
        map.put(DATABASES, 0L);
        map.put(SPRING, 0L);
        this.coursesMap = map;
    }

    public static Student buildStudent(String firstName, String lastName, String email) {
        Student student = new Student();
        if (isNameInvalid(firstName)) {
            System.out.println("Incorrect first name.");
            return null;
        } else {
            student.firstName = firstName;
        }
        if (isNameInvalid(lastName)) {
            System.out.println("Incorrect last name.");
            return null;
        } else {
            student.firstName = lastName;
        }
        if (isEmailInvalid(email)) {
            System.out.println("Incorrect email.");
            return null;
        } else if (CliUtil.emailAddresses.contains(email)) {
            System.out.println("This email is already taken.");
            return null;
        } else {
            student.email = email;
            CliUtil.emailAddresses.add(email);
        }
        long randomNumber = (long) (Math.random() * 100_000L);
        student.id = String.valueOf(randomNumber);
        CliUtil.studentsIdsList.add(String.valueOf(randomNumber));
        student.setCoursesMap();
        return student;
    }

    public void setScore() {
        long sum = 0;
        for (Map.Entry<Courses, Long> entry : coursesMap.entrySet()) {
            sum += entry.getValue();
        }
        this.score = sum;
    }

    public void updateScore(long java, long dsa, long databases, long spring) {
        enrollStudentInCourse(java, dsa, databases, spring);
        this.coursesMap.replace(JAVA, this.coursesMap.get(JAVA) + java);
        this.coursesMap.replace(DSA, this.coursesMap.get(DSA) + dsa);
        this.coursesMap.replace(DATABASES, this.coursesMap.get(DATABASES) + databases);
        this.coursesMap.replace(SPRING, this.coursesMap.get(SPRING) + spring);
        updateCourseStatistics(Main.javaCourse, java);
        updateCourseStatistics(Main.dsaCourse, dsa);
        updateCourseStatistics(Main.databasesCourse, databases);
        updateCourseStatistics(Main.springCourse, spring);
        setScore();
    }

    private static void updateCourseStatistics(Course course, long points) {
        course.getInputs().add(points);
        course.setActivity();
        course.setAverage();
    }

    private static boolean isNameInvalid(String name) {
        String pattern = "[-'a-zA-Z ]*";
        return name.length() < 2
                || name.startsWith("-")
                || name.startsWith("'")
                || name.endsWith("-")
                || name.endsWith("'")
                || name.contains("-'")
                || name.contains("'-")
                || name.contains("--")
                || name.contains("''")
                || !name.matches(pattern);
    }

    private static boolean isEmailInvalid(String email) {
        String pattern = "[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-z0-9]+";
        return !email.matches(pattern);
    }

    @Override
    public String toString() {
        return String.format("%s points: Java=%s; DSA=%s; Databases=%s; Spring=%s",
                this.id, this.coursesMap.get(JAVA), this.coursesMap.get(DSA),
                this.coursesMap.get(DATABASES), this.coursesMap.get(SPRING));
    }

    public void enrollStudentInCourse(long java, long dsa, long databases, long spring) {
        if (enrolledInCoursesList != null && enrolledInCoursesList.size() == 4) {
            return;
        }
        if (!isEnrolledInCourse(JAVA.name()) && java != 0L) {
            enrolledInCoursesList.add(JAVA);
            Main.javaCourse.getEnrolledStudentsIds().add(this.id);
            Main.javaCourse.setPopularity();
        }
        if (!isEnrolledInCourse(DSA.name()) && dsa != 0L) {
            enrolledInCoursesList.add(DSA);
            Main.dsaCourse.getEnrolledStudentsIds().add(this.id);
            Main.dsaCourse.setPopularity();
        }
        if (!isEnrolledInCourse(DATABASES.name()) && databases != 0L) {
            enrolledInCoursesList.add(DATABASES);
            Main.databasesCourse.getEnrolledStudentsIds().add(this.id);
            Main.databasesCourse.setPopularity();
        }
        if (!isEnrolledInCourse(SPRING.name()) && spring != 0L) {
            enrolledInCoursesList.add(SPRING);
            Main.springCourse.getEnrolledStudentsIds().add(this.id);
            Main.springCourse.setPopularity();
        }
    }

    public void printStudentGeneralCourseScore(Courses course) {
        System.out.printf(
                "%-6s%-10s%.1f%s%n",
                this.id, this.coursesMap.get(course), countCompletionPercentage(course), "%"
        );
    }

    private boolean isEnrolledInCourse(String courseName) {
        return coursesMap.get(Courses.valueOf(courseName)) != 0L;
    }

    private double countCompletionPercentage(Courses course) {
        return (coursesMap.get(course) / maxCoursesValues.get(course)) * 100;
    }
}
