package tracker;

import java.util.LinkedHashMap;
import java.util.Map;

import static tracker.Courses.*;


public class Student {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Map<Courses, Integer> coursesMap;
    private int score;

    public String getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public Map<Courses, Integer> getCoursesMap() {
        return coursesMap;
    }

    public void setCoursesMap() {
        Map<Courses, Integer> map = new LinkedHashMap<>();
        map.put(JAVA, 0);
        map.put(DSA, 0);
        map.put(DATABASES, 0);
        map.put(SPRING, 0);
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
        int sum = 0;
        for (Map.Entry<Courses, Integer> entry: coursesMap.entrySet()) {
            sum += entry.getValue();
        }
        this.score = sum;
    }

    public void updateScore(int java, int dsa, int databases, int spring) {
        this.coursesMap.replace(JAVA, this.coursesMap.get(JAVA) + java);
        this.coursesMap.replace(DSA, this.coursesMap.get(DSA) + dsa);
        this.coursesMap.replace(DATABASES, this.coursesMap.get(DATABASES) + databases);
        this.coursesMap.replace(SPRING, this.coursesMap.get(SPRING) + spring);
        setScore();
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
}
