package tracker;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class Statistics {

    private static final List<Long> popularities = List.of(
            Main.javaCourse.getPopularity(), Main.dsaCourse.getPopularity(),
            Main.databasesCourse.getPopularity(), Main.springCourse.getPopularity());
    private static final List<Long> activities = List.of(
            Main.javaCourse.getActivity(), Main.dsaCourse.getActivity(),
            Main.databasesCourse.getActivity(), Main.springCourse.getActivity());
    private static final List<Double> averages = List.of(
            Main.javaCourse.getAverage(), Main.dsaCourse.getAverage(),
            Main.databasesCourse.getAverage(), Main.springCourse.getAverage());
    protected static final List<Course> courseList = List.of(Main.javaCourse, Main.dsaCourse, Main.databasesCourse, Main.springCourse);

    private Statistics() {
    }

    private static String getMostPopularCourses() {
        OptionalLong mostPopularCourse = popularities.stream()
                .mapToLong(x -> x).max();
        return mostPopularCourse.isPresent() ? courseList.stream()
                .filter(course -> course.getPopularity() == mostPopularCourse.getAsLong())
                .map(course -> course.getCourseName().nameAsString())
                .collect(Collectors.joining(", "))
                : "";
    }

    private static String getLeastPopularCourses() {
        OptionalLong leastPopularCourse = popularities.stream()
                .mapToLong(x -> x).min();
        return leastPopularCourse.isPresent() ? courseList.stream()
                .filter(course -> course.getPopularity() == leastPopularCourse.getAsLong())
                .map(course -> course.getCourseName().nameAsString())
                .collect(Collectors.joining(", "))
                : "";
    }

    private static String getHighestActivityCourses() {
        OptionalLong highActivityCourse = activities.stream()
                .mapToLong(x -> x).max();
        return highActivityCourse.isPresent() ? courseList.stream()
                .filter(course -> course.getActivity() == highActivityCourse.getAsLong())
                .map(course -> course.getCourseName().nameAsString())
                .collect(Collectors.joining(", "))
                : "";
    }

    private static String getLowestActivityCourses() {
        OptionalLong lowActivityCourse = activities.stream()
                .mapToLong(x -> x).min();
        return lowActivityCourse.isPresent() ? courseList.stream()
                .filter(course -> course.getActivity() == lowActivityCourse.getAsLong())
                .map(course -> course.getCourseName().nameAsString())
                .collect(Collectors.joining(", "))
                : "";
    }

    private static String getHardestCourses() {
        OptionalDouble hardestCourse = averages.stream()
                .filter(x -> x != 0.0)
                .mapToDouble(x -> x).min();
        return hardestCourse.isPresent() ? courseList.stream()
                .filter(course -> course.getPopularity() != 0L)
                .filter(course -> course.getAverage() == hardestCourse.getAsDouble())
                .map(course -> course.getCourseName().nameAsString())
                .collect(Collectors.joining(", "))
                : "";
    }

    private static String getEasiestCourses() {
        OptionalDouble easiestCourse = averages.stream()
                .filter(x -> x != 0.0)
                .mapToDouble(x -> x).max();
        return easiestCourse.isPresent() ? courseList.stream()
                .filter(course -> course.getPopularity() != 0L)
                .filter(course -> course.getAverage() == easiestCourse.getAsDouble())
                .map(course -> course.getCourseName().nameAsString())
                .collect(Collectors.joining(", "))
                : "";
    }

    public static String showStatistics() {
        if (Main.javaCourse.getPopularity() == 0 && Main.dsaCourse.getPopularity() == 0
                && Main.databasesCourse.getPopularity() == 0 && Main.springCourse.getPopularity() == 0) {
            return """
                    Most popular: n/a
                    Least popular: n/a
                    Highest activity: n/a
                    Lowest activity: n/a
                    Easiest course: n/a
                    Hardest course: n/a""";
        }
        return String.format(
                "Most popular: %s%n" +
                        "Least popular: %s%n" +
                        "Highest activity: %s%n" +
                        "Lowest activity: %s%n" +
                        "Easiest course: %s%n" +
                        "Hardest course: %s",
                getMostPopularCourses(),
                isTheSamePopularity() ? "n/a" : getLeastPopularCourses(),
                getHighestActivityCourses(),
                isTheSameActivity() ? "n/a" : getLowestActivityCourses(),
                getEasiestCourses(), getHardestCourses());
    }

    private static boolean isTheSamePopularity() {
        return Objects.equals(getMostPopularCourses(), getLeastPopularCourses());
    }

    private static boolean isTheSameActivity() {
        return Objects.equals(getHighestActivityCourses(), getLowestActivityCourses());
    }
}
