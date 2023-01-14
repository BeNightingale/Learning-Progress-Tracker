package tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Course {
    private final Courses courseName;
    private final List<String> enrolledStudentsIds = new ArrayList<>();
    private long popularity;                        // liczba zapisanych studentów
    private final List<Long> inputs = new ArrayList<>(); // wszystkie wpisy łącznie z zerami, czyli gdy student podaje swoje wyniki- wpisany wynik z tego kursu może być zerem
    private long activity;                         //liczba wpisów punktów, które nie są zerami
    private Double average;                         // średnia z niezerowych wpisów punktów


    public Course(Courses courseName, long popularity, long activity, Double average) {
        this.courseName = courseName;
        this.popularity = popularity;
        this.activity = activity;
        this.average = average;
    }

    public Courses getCourseName() {
        return courseName;
    }

    public List<String> getEnrolledStudentsIds() {
        return enrolledStudentsIds;
    }

    public long getPopularity() {
        return popularity;
    }

    public List<Long> getInputs() {
        return inputs;
    }

    public long getActivity() {
        return activity;
    }

    public Double getAverage() {
        return average;
    }

    public void setActivity() {
        this.activity = inputs.stream().filter(x -> x != 0).count();
    }

    public void setPopularity() {
        this.popularity = this.enrolledStudentsIds.size();
    }

    public void setAverage() {
        if (inputs.isEmpty()) {
            this.average = 0.0;
            return;
        }
        OptionalDouble average1 = inputs.stream().filter(x -> x != 0).mapToDouble(x -> x).average();
        if (average1.isPresent()) {
            this.average = average1.getAsDouble();
        }
    }

    public void showCourseResults() {
        System.out.println(this.courseName.nameAsString());
        System.out.printf("%-6s%-10s%s%n", "id", "points", "completed");
        if (this.enrolledStudentsIds.isEmpty()) {
            return;
        }
        for (String studentId : enrolledStudentsIds) {
            Student student = CliUtil.getStudentById(studentId);
            if (student == null) {
                System.out.printf("No student is found for id=%s.%n", studentId);
            }
        }
        List<Student> studentsSorted = sortStudentsEnrolledInThisCourse();
        studentsSorted.forEach(student -> student.printStudentGeneralCourseScore(this.courseName));
    }

    public List<Student> sortStudentsEnrolledInThisCourse() {
        return enrolledStudentsIds.stream()
                .map(CliUtil::getStudentById)
                .filter(Objects::nonNull)
                .sorted(new StudentComparatorByBranchPoints(this.courseName)
                        .reversed()
                        .thenComparing(new StudentComparatorById()))
                .collect(Collectors.toList());
    }
}
