package tracker;

import java.util.Comparator;

class StudentComparatorByBranchPoints implements Comparator<Student> {
    Courses coursesName;

    public StudentComparatorByBranchPoints(Courses coursesName) {
        this.coursesName = coursesName;
    }
    @Override
    public int compare(Student s1, Student s2) {
        Long points1 = s1.getCoursesMap().get(this.coursesName);
        Long points2 = s2.getCoursesMap().get(this.coursesName);
        return Long.compare(points1,points2);
    }
}
