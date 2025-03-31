package info.com.Service;

import info.com.Dao.Daoo;
import info.com.Model.StudentModel;
import info.com.Model.TeacherModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static info.com.Dao.Daoo.addAssignment;
import static info.com.Dao.Daoo.findStudentByName;

public class Teacher {
    private final Scanner sc = new Scanner(System.in);
    private final Daoo daoo;

    public Teacher() {
        this.daoo = new Daoo();
    }


    public void teach() {
        System.out.println("Welcome to the Teacher Section");

        try {
            boolean authenticated = false;
            while (!authenticated) {
                System.out.print("Enter the name : ");
                String name = sc.nextLine().trim();

                System.out.print("Enter the password: ");
                String password = sc.nextLine().trim();

                try {
                    if (daoo.Teacher(name, password)) {
                        authenticated = true;
                    } else {
                        System.out.println("❌❌ Wrong ID or Password. Try again.");
                    }
                } catch (SQLException e) {
                    System.out.println("Database error during authentication: " + e.getMessage());
                    return;
                }
            }

            boolean isRunning = true;
            while (isRunning) {
                System.out.println("\n1. Student");
                System.out.println("2.   Add Assignment ");
                System.out.println("3.   Exit");
                int choice = parseIntInput("choice");

                switch (choice) {
                    case 1 -> handleStudentSection();
                    case 2 -> handleAssignmentSection();
                    case 3 -> isRunning = false;
                    default -> System.out.println("Invalid choice!");
                }
            }
            System.out.println("Exiting Admin Section...");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

    }

    private void handleStudentSection() throws SQLException {
        boolean isStudentSectionRunning = true;
        while (isStudentSectionRunning) {
            System.out.println("\nStudent Section:");
            System.out.println("1. Add Student");
            System.out.println("2. Retrieve Students");
            System.out.println("3. Find Student");
            System.out.println("4. 🔙 Back");
            int sChoice = parseIntInput("choice");

            switch (sChoice) {
                case 1 -> retrieveStudents();
                case 2 -> findStudent();
//                case 3 -> findStudent();
                case 3 -> isStudentSectionRunning = false;
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void retrieveStudents() {
        try {
            List<StudentModel> students = daoo.getAllStudent();
            if (students.isEmpty()) {
                System.out.println("No students found.");
            } else {
                System.out.println("\nStudent List:");
                students.forEach(std -> System.out.println(
                        std.getsId() + " | " + std.getsName() + " | " +
                                std.getsNum() + " | " + std.getsAssignment())
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
    }

    private void findStudent() {
        System.out.println("Entre the name of Student to Search :");
        String name = sc.nextLine().trim();

        try {
            if (!findStudentByName(name)) {
                System.out.println("No students found with name: " + name);
            } else {
                int c = 0;
                System.out.println(c++);
                findStudentByName(name);
            }
        } catch (SQLException e) {
            System.out.println("Error searching students: " + e.getMessage());
        }
    }


    private void handleAssignmentSection() throws SQLException {
        System.out.println("Please Entre the Assignment");
        String hw = sc.nextLine();
        addAssignment(new TeacherModel(hw));
    }



    // Utility methods for input handling
    private int parseIntInput(String prompt) {
        while (true) {
            try {
                System.out.print("Enter your " + prompt + ": ");
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    private int getPositiveIntInput(String message) {
        while (true) {
            int value = parseIntInput(message);
            if (value > 0) {
                return value;
            }
            System.out.println("Please enter a positive number.");
        }
    }

    private String getValidContact(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = sc.nextLine().trim();
            if (!input.isEmpty() && input.length() >= 10) {
                return input;
            }
            System.out.println("Invalid contact! Must be at least 10 characters.");
        }
    }


}