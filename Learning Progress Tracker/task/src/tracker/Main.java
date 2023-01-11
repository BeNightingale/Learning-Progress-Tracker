package tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Learning Progress Tracker");
        String input = scanner.nextLine();
        CliUtil.addStudents(input, scanner);
    }
}

