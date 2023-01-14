package tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CliUtil {
    //klasa zawiera metody pomocnicze do tworzenia Command Line Interface
    private static final String INCORRECT_CREDENTIALS = "Incorrect credentials.";
    private static final String ENTER_EXIT_TO_EXIT_THE_PROGRAM = "Enter 'exit' to exit the program";
    private static final String RETURN = "return";
    private static final String STATISTICS = "statistics";

    protected static final List<String> emailAddresses = new ArrayList<>();
    protected static final List<String> studentsIdsList = new ArrayList<>();
    protected static final List<Student> studentsList = new ArrayList<>();

    private CliUtil() {
    }

    public static void addStudents(String input, Scanner scanner) {
        if (input.isBlank()) {
            System.out.println("No input.");
            input = scanner.nextLine();
        } else if (STATISTICS.equals(input)) { // potem back lub inne nie ma
            statistics();
            input = scanner.nextLine();
            if ("back".equals(input)) {
                input = scanner.nextLine();
                if ("back".equals(input)) {
                    System.out.println(ENTER_EXIT_TO_EXIT_THE_PROGRAM);
                    input = scanner.nextLine();
                    if ("exit".equals(input)) {
                        System.out.println("Bye!");
                        return;
                    }
                }
                if ("exit".equals(input)) {
                    System.out.println("Bye!");
                    return;
                }
            } else {
                while (!"back".equals(input)) {
                    courseNameCommand(input);
                    input = scanner.nextLine();
                }
                input = scanner.nextLine();
                if ("exit".equals(input)) {
                    System.out.println("Bye!");
                    return;
                }
            }
        } else if ("find".equals(input)) {
            find(scanner);
            input = scanner.nextLine();
            if ("exit".equals(input)) {
                System.out.println("Bye!");
                return;
            }
            input = checkBackAndExit(input, scanner);
            if (RETURN.equals(input))
                return;
        } else if ("back".equals(input)) {
            System.out.println(ENTER_EXIT_TO_EXIT_THE_PROGRAM);
            input = scanner.nextLine();
        } else if ("add points".equals(input)) {
            addPoints(scanner);
            input = scanner.nextLine();
            if ("back".equals(input)) {
                System.out.println(ENTER_EXIT_TO_EXIT_THE_PROGRAM);
                input = scanner.nextLine();
                if ("exit".equals(input)) {
                    System.out.println("Bye!");
                    return;
                }
            } else if ("find".equals(input)) {
                find(scanner);
            } else if (STATISTICS.equals(input)) {
                statistics();
                input = scanner.nextLine(); // potem back lub inne nie ma
                if ("back".equals(input)) {
                    input = scanner.nextLine();
                    if ("exit".equals(input)) {
                        System.out.println("Bye!");
                        return;
                    }
                } else {
                    while (!"back".equals(input)) {
                        courseNameCommand(input);
                        input = scanner.nextLine();
                    }
                    input = scanner.nextLine();
                    if ("exit".equals(input)) {
                        System.out.println("Bye!");
                        return;
                    }
                }
            }
        } else if ("exit".equals(input)) {
            System.out.println("Bye!");
            return;
        } else if (!"add students".equals(input)) {
            System.out.println("Unknown command!");
            input = scanner.nextLine();
            if (STATISTICS.equals(input)) {
                statistics();
                input = scanner.nextLine(); // potem back lub inne nie ma
                if ("back".equals(input)) {
                    input = scanner.nextLine();
                    if ("exit".equals(input)) {
                        System.out.println("Bye!");
                        return;
                    }
                } else {
                    while (!"back".equals(input)) {
                        courseNameCommand(input);
                        input = scanner.nextLine();
                    }
                    input = scanner.nextLine();
                    if ("exit".equals(input)) {
                        System.out.println("Bye!");
                        return;
                    }
                }
            }
        }
        while (!"exit".equals(input)) {
            if (input.equals("add students")) {
                System.out.println("Enter student credentials or 'back' to return:");
                input = scanner.nextLine();
                if ("back".equals(input)) {
                    input = backAfterCredentials(scanner);
                    if (RETURN.equals(input)) {
                        return;
                    }
                } else if ("exit".equals(input)) {
                    System.out.println(INCORRECT_CREDENTIALS);
                    input = scanner.nextLine();
                } else {
                    verifyInputCredentials(input);
                    input = scanner.nextLine();
                }
            }
            if ("back".equals(input)) {
                input = backAfterCredentials(scanner);
                if (RETURN.equals(input)) {
                    return;
                }
                if ("exit".equals(input)) {
                    System.out.println("Bye!");
                    return;
                }
                input = checkBackAndExit(input, scanner);
                if (RETURN.equals(input))
                    return;
            } else {
                verifyInputCredentials(input);
            }
            input = scanner.nextLine();
        }
        System.out.println("Bye!");
    }

    private static void verifyInputCredentials(String input) {
        int firstIndexOfSpace = input.indexOf(" ");
        String firstName;
        String lastNameAndEmail;
        if (firstIndexOfSpace == -1) {
            System.out.println(INCORRECT_CREDENTIALS);
            return;
        } else {
            firstName = input.substring(0, firstIndexOfSpace);
            lastNameAndEmail = input.substring(firstIndexOfSpace + 1);
        }
        int lastIndexOfSpace = lastNameAndEmail.lastIndexOf(" ");
        String lastName;
        String email;
        if (lastIndexOfSpace == -1) {
            System.out.println(INCORRECT_CREDENTIALS);
            return;
        } else {
            lastName = lastNameAndEmail.substring(0, lastIndexOfSpace);
            email = lastNameAndEmail.substring(lastIndexOfSpace + 1);
        }
        Student student = Student.buildStudent(firstName, lastName, email);
        if (student != null) {
            studentsList.add(student);
            System.out.println("The student has been added.");
        }
    }

    private static String backAfterCredentials(Scanner scanner) {
        String input;
        System.out.printf("Total %s students have been added%n", studentsList.size());
        input = scanner.nextLine();
        if ("list".equals(input)) {
            list();
            input = scanner.nextLine();
            if ("add points".equals(input)) {
                addPoints(scanner); // skończy, gdy input == back
            }
            input = scanner.nextLine();
            if ("find".equals(input)) {
                find(scanner);
                input = scanner.nextLine();
            } else if (STATISTICS.equals(input)) {
                statistics();
                input = scanner.nextLine();
                if ("back".equals(input)) {
                    input = scanner.nextLine();
                    if ("exit".equals(input)) {
                        System.out.println("Bye!");
                        return RETURN;
                    }
                } else {
                    while (!"back".equals(input)) {
                        courseNameCommand(input);
                        input = scanner.nextLine();
                    }
                    input = scanner.nextLine();
                    if ("exit".equals(input)) {
                        System.out.println("Bye!");
                        return RETURN;
                    }
                }
            }
        }
        return input;
    }

    private static void find(Scanner scanner) {
        System.out.println("Enter an id or 'back' to return:");
        String input = scanner.nextLine();
        while (!"back".equals(input)) {
            Student student = getStudentById(input);
            if (student == null) {
                System.out.printf("No student is found for id=%s.%n", input);
            } else {
                System.out.println(student);
            }
            input = scanner.nextLine();
        }
    }

    private static void list() {
        if (studentsList.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("Students:");
        for (Student student : studentsList) {
            System.out.println(student.getId());
        }
    }

    private static void addPoints(Scanner scanner) {
        System.out.println("Enter an id and points or 'back' to return:");
        String input = scanner.nextLine();
        while (!"back".equals(input)) {
            updatePoints(input);
            input = scanner.nextLine();
        }
    }

    private static void updatePoints(String numberInput) {
        String[] numbers = numberInput.split(" ");
        if (isPointsInputValid(numbers)) {
            Student student = getStudentById(numbers[0]);
            if (student != null) {
                student.updateScore(Long.parseLong(numbers[1]), Long.parseLong(numbers[2]),
                        Long.parseLong(numbers[3]), Long.parseLong(numbers[4]));
                System.out.println("Points updated.");
            }
        }
    }

    private static boolean isPointsInputValid(String[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return false;
        }
        String studentId = numbers[0];
        if (!studentsIdsList.contains(studentId)) {
            System.out.printf("No student is found for id=%s.%n", studentId);
            return false;
        }
        if (numbers.length != 5) {
            System.out.println("Incorrect points format.");
            return false;
        }
        String pattern = "0|[1-9]+\\d*";
        for (int i = 1; i < numbers.length; i++) {
            if (!numbers[i].matches(pattern)) {
                System.out.println("Incorrect points format.");
                return false;
            }
        }
        return true;
    }

    protected static Student getStudentById(String id) {
        for (Student student : studentsList) {
            if (id.equals(student.getId())) {
                return student;
            }
        }
        return null;
    }

    private static String checkBackAndExit(String input, Scanner scanner) {
        if ("back".equals(input)) {
            System.out.println(ENTER_EXIT_TO_EXIT_THE_PROGRAM);
            input = scanner.nextLine();
            if ("exit".equals(input)) {
                System.out.println("Bye!");
                return RETURN;
            }
        }
        return input;
    }

    private static void statistics() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        System.out.println(Statistics.showStatistics());

    }

    private static void courseNameCommand(String courseName) {  // powtórz aż do back , na końcu weż nowy input
        if (courseName == null || courseName.isEmpty()) {
            System.out.println("Unknown course.");
            return;
        }
        Course courseToShow = null;
        for (Course course : Statistics.courseList) {
            if (courseName.equalsIgnoreCase(course.getCourseName().nameAsString())) {
                courseToShow = course;
                break;
            }
        }
        if (courseToShow == null) {
            System.out.println("Unknown course.");
            return;
        }
        courseToShow.showCourseResults();
    }
}
