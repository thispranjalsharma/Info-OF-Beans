package info.com.Service;

import info.com.Dao.Daoo;
import info.com.Model.StudentModel;
import info.com.Model.TeacherModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Student {
    private final Scanner sc = new Scanner(System.in);
    private final Daoo daoo;

    public Student() {
        this.daoo = new Daoo();
    }

    public void sweets(){
        System.out.println("Welcome to the Student Section");

        try {
            boolean authenticated = false;
            while (!authenticated) {
                System.out.print("Enter the name : ");
                String name = sc.nextLine().trim();

                System.out.print("Enter the password: ");
                String password = sc.nextLine().trim();

                try {
                    if (daoo.Student(name, password)) {
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
                System.out.println("\n1. Soft Skill ");
                System.out.println("2.Technical ");
                System.out.println("3.Exit");
                int choice = parseIntInput("choice");

                switch (choice) {
                    case 1 -> handleTechnicalSection();
                    case 2 -> handleSoftSkillSection();
                    case 3 -> isRunning = false;
                    default -> System.out.println("Invalid choice!");
                }
            }
            System.out.println("Exiting Admin Section...");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleSoftSkillSection() {
        try {
            List<TeacherModel> assign = daoo.getAssignementOfSoftSkill(new TeacherModel("soft"));
            if (assign.isEmpty()) {
                System.out.println("No students found.");
            } else {
                System.out.println("\nStudent List:");
                assign.forEach(std -> System.out.println(std.gettId()+1+" | "+std.gettAssignment() +" | "+ std.gettTime() ) );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
    }

    private void handleTechnicalSection() throws SQLException {
        try {
            List<TeacherModel> assign = daoo.getAssignementOfTechnical(new TeacherModel("Technical" ));
            if (assign.isEmpty()) {
                System.out.println("No students found.");
            } else {
                System.out.println("\nStudent List:");
                assign.forEach(std -> System.out.println(std.gettId()+1+" | "+  std.gettAssignment()+" | "+ std.gettTime()));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
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
