package tracker;

import java.util.Comparator;

class StudentComparatorById implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        long s1Id = Long.parseLong(s1.getId());
        long s2Id = Long.parseLong(s2.getId());
        return Long.compare(s1Id,s2Id);
    }
}
