package info.com.Service;

import info.com.Dao.Daoo;
import info.com.Model.AssignmentModel;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Student {
    private final Scanner sc = new Scanner(System.in);
    private final Daoo studentDao;
    private int currentStudentId = 0;

    public Student() {
        this.studentDao = new Daoo();
    }

    public void startStudentSession() {
        System.out.println("\n🎓 STUDENT PORTAL 🎓");
        if (authenticateStudent()) {
            showStudentMenu();
        }
    }

    private boolean authenticateStudent() {
        System.out.print("\nEnter your name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = sc.nextLine().trim();

        if (studentDao.studentLogin(name, password)) {
            currentStudentId = studentDao.getCurrentStudentId();
            System.out.println("\n✅ Login successful! Welcome, " + name + "!");
            return true;
        } else {
            System.out.println("\n❌ Invalid credentials!");
            return false;
        }
    }

    private void showStudentMenu() {
        boolean continueSession = true;

        while (continueSession) {
            System.out.println("\n📋 STUDENT MENU 📋");
            System.out.println("\n1. View Assignments ");
            System.out.println("2. 📝 Update Assignment Status");
//            System.out.println("3. 👀 View My Status");
            System.out.println("3.Update Password");
            System.out.println("4. 🚪 Logout");

            int choice = getIntInput("Enter your choice", 1, 5);

            switch (choice) {

                case 1 -> handleAssignmentView();
                case 2 -> updateAssignmentStatus();
//                case 3 -> viewMyStatus();
                case 3 -> handleUpdatePassword();
                case 4 -> {
                    studentDao.studentLogout();
                    System.out.println("\n👋 Logged out successfully!");
                    continueSession = false;
                }
            }
        }
    }


    private void handleAssignmentView() {
        System.out.println("\nView Assignments by Subject:");
        System.out.println("1. Technical ");
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


    private void handleUpdatePassword() {
        System.out.println("Entre Your new Password. ");
        String newPassword = sc.nextLine();
        if (newPassword != "") {
            studentDao.updateStudentPassword(newPassword);
            System.out.println("Your Password Updated Successfully ✅");

        } else {
            System.out.println("Do not be Over Smart .. ❌❌");
        }

    }

    private void updateAssignmentStatus() {
        System.out.println("\n📝 UPDATE ASSIGNMENT STATUS");
        System.out.println("1. Not Started");
        System.out.println("2. In Progress");
        System.out.println("3. Completed");

        int choice = getIntInput("Select your status", 1, 3);
        String status = switch (choice) {
            case 1 -> "Not Started";
            case 2 -> "In Progress";
            case 3 -> "Completed";
            default -> "Not Started";
        };

        try {
            if (studentDao.updateAssignmentStatus(currentStudentId, status)) {
                System.out.println("\n✅ Status updated to: " + status);
            } else {
                System.out.println("\n❌ Failed to update status");
            }
        } catch (SQLException e) {
            System.out.println("\n❌ Database error: " + e.getMessage());
        }
    }

//    private void viewMyStatus() {
//        try {
//            String status = studentDao.getAssignmentStatus(currentStudentId);
//            System.out.println("\n📊 YOUR ASSIGNMENT STATUS");
//            System.out.println("Current Status: " + getStatusEmoji(status) + " " + status);
//        } catch (SQLException e) {
//            System.out.println("\n❌ Database error: " + e.getMessage());
//        }
//    }

    private void viewTechnicalAssignments() {
        List<AssignmentModel> assignments = studentDao.getAssignmentsBySubject("Technical");
        displayAssignments(assignments, "Technical Assignments");
    }

    private void viewSoftSkillAssignments() {
        List<AssignmentModel> assignments = studentDao.getAssignmentsBySubject("soft skills");
        displayAssignments(assignments, "Soft Skill Assignments");
    }

    private void displayAssignments(List<AssignmentModel> assignments, String title) {
        if (assignments.isEmpty()) {
            System.out.println("No assignments found for this subject.");
            return;
        }

        System.out.println("\n" + title);
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.printf("%-5s %-40s %-20s%n", "ID", "Assignment", "Teacher", "DATE CREATED");
        System.out.println("-----------------------------------------------------------------------------------------------");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");

        for (AssignmentModel assignment : assignments) {
            String formattedDate = dateFormat.format(assignment.getCreatedAt());

            System.out.printf("%-5d %-40s %-20s%n",
                    assignment.getId(),
                    truncate(assignment.getAssignment(), 40),
                    assignment.getTeacherName(),
                    formattedDate
            );
        }

        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    private String truncate(String str, int length) {
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }

    private String getStatusEmoji(String status) {
        return switch (status) {
            case "Completed" -> "✅";
            case "In Progress" -> "⏳";
            case "Not Started" -> "🛑";
            default -> "❓";
        };
    }

    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt + " (" + min + "-" + max + "): ");
                int value = Integer.parseInt(sc.nextLine());
                if (value >= min && value <= max) return value;
                System.out.println("Please enter between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}