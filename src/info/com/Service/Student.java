package info.com.Service;

import info.com.Dao.Daoo;
import info.com.Model.AssignmentModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Student {
    private final Scanner sc = new Scanner(System.in);
    private final Daoo daoo;

    public Student() {
        this.daoo = new Daoo();
    }

    public void sweets() {
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
                System.out.println("\n1. View Assignments ");
//                System.out.println("2.not working now ");
                System.out.println("2.🔙 Back");
                System.out.println("3. STOP ");
                int choice = parseIntInput("choice");

                switch (choice) {
                    case 1 -> handleAssignmentView();
//                    case 2 -> handleTechnicalSection();
                    case 2 -> isRunning = false;
                    case 3 -> System.exit(1000003);
                    default -> System.out.println("Invalid choice!");
                }
            }
            System.out.println("Exiting Student  Section...");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

//    private void handleSoftSkillSection() {
//        try {
//            List<TeacherModel> assign = daoo.getAssignementOfSoftSkill(new TeacherModel("soft"));
//            if (assign.isEmpty()) {
//                System.out.println("No students found.");
//            } else {
//                System.out.println("\nStudent List:");
//                assign.forEach(std -> System.out.println(std.gettId()+1+" | "+std.gettAssignment() +" | "+ std.gettTime() ) );
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving students: " + e.getMessage());
//        }
//    }
//
//    private void handleTechnicalSection() throws SQLException {
//        try {
//            List<TeacherModel> assign = daoo.getAssignementOfTechnical(new TeacherModel("Technical" ));
//            if (assign.isEmpty()) {
//                System.out.println("No students found.");
//            } else {
//                System.out.println("\nStudent List:");
//                assign.forEach(std -> System.out.println(std.gettId()+1+" | "+  std.gettAssignment()+" | "+ std.gettTime()));
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving students: " + e.getMessage());
//        }
//    }


    private void handleAssignmentView() {
        System.out.println("\nView Assignments by Subject:");
        System.out.println("1. Technical (Computer Science)");
        System.out.println("2. Soft Skills");
        System.out.println("3. Back");
        System.out.print("Enter your choice: ");

        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            return;
        }

        switch (choice) {
            case 1 -> viewTechnicalAssignments();
            case 2 -> viewSoftSkillAssignments();
            case 3 -> {
                return;
            }
            default -> System.out.println("Invalid choice!");
        }
    }

    private void viewTechnicalAssignments() {
        try {
            List<AssignmentModel> assignments = daoo.getAssignmentsBySubject("Computer science");
            displayAssignments(assignments, "Technical Assignments");
        } catch (SQLException e) {
            System.out.println("Error retrieving assignments: " + e.getMessage());
        }
    }

    private void viewSoftSkillAssignments() {
        try {
            List<AssignmentModel> assignments = daoo.getAssignmentsBySubject("soft");
            displayAssignments(assignments, "Soft Skill Assignments");
        } catch (SQLException e) {
            System.out.println("Error retrieving assignments: " + e.getMessage());
        }
    }

    private void displayAssignments(List<AssignmentModel> assignments, String title) {
        if (assignments.isEmpty()) {
            System.out.println("No assignments found for this subject.");
            return;
        }

        System.out.println("\n" + title);
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.printf("%-5s %-40s %-20s %s%n", "ID", "Assignment", "Teacher", "Date Posted");
        System.out.println("-----------------------------------------------------------------------------------------------");

        for (AssignmentModel assignment : assignments) {
            System.out.printf("%-5d %-40s %-20s ",
                    assignment.getId(),
                    truncate(assignment.getAssignment(), 40),
                    assignment.getTeacherName(),
                    assignment.getCreatedAt());
            System.out.println();
        }

        System.out.println();
    }

    private String truncate(String str, int length) {
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
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
