package tracker;

public enum Courses {
    JAVA,
    DSA,
    DATABASES,
    SPRING;

    public String nameAsString() {
        if (this != DSA) {
            return this.name().charAt(0) + this.name().substring(1).toLowerCase();
        } else {
            return DSA.name();
        }
    }
}
