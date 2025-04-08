package info.com.Service;

import info.com.Dao.Daoo;
import info.com.Model.AssignmentModel;
import info.com.Model.StudentModel;
import info.com.Model.TeacherModel;

import java.security.Timestamp;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Teacher {
    private final Scanner sc = new Scanner(System.in);
    private final Daoo teacherDao;
    private boolean isAuthenticated = false;

    public Teacher() {
        this.teacherDao = new Daoo();
    }

    public void startTeacherSession() {
        System.out.println("\n✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨");
        System.out.println("✨      TEACHER PORTAL       ✨");
        System.out.println("✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨\n");

        authenticateTeacher();

        if (isAuthenticated) {
            showTeacherMenu();
        }
    }

    private void authenticateTeacher() {
        System.out.println("\n🔐 TEACHER LOGIN 🔐");
        int attempts = 3;

        while (attempts > 0 && !isAuthenticated) {
            System.out.print("\nEnter username: ");
            String username = sc.nextLine().trim();

            System.out.print("Enter password: ");
            String password = sc.nextLine().trim();

            isAuthenticated = teacherDao.teacherLogin(username, password);

            if (!isAuthenticated) {
                attempts--;
                System.out.println("\n❌ Invalid credentials! Attempts remaining: " + attempts);
            } else {
                System.out.println("\n✅ Login successful! Welcome, " + username + "!");
            }
        }

        if (!isAuthenticated) {
            System.out.println("\n⚠️ Maximum login attempts reached. Returning to main menu.");
        }
    }

    private void showTeacherMenu() {
        boolean continueSession = true;

        while (continueSession) {
            System.out.println("\n📋 TEACHER MENU 📋");
            System.out.println("1. 👨‍🎓 Student Management");
            System.out.println("2. 📝 Assignment Management");
            System.out.println("3. 🔄 Update Password");
            System.out.println("4. 🚪 Logout");

            int choice = getIntInput("Enter your choice", 1, 4);

            switch (choice) {
                case 1 -> handleStudentManagement();
                case 2 -> handleAssignmentManagement();
//                case 3 -> handlePasswordUpdate();
                case 4 -> {
                    teacherDao.teacherLogout();
                    System.out.println("\n👋 Logged out successfully!");
                    continueSession = false;
                }
            }
        }
    }

    private void handleStudentManagement() {
        boolean inStudentMenu = true;

        while (inStudentMenu) {
            System.out.println("\n👨‍🎓 STUDENT MANAGEMENT");
            System.out.println("1. 👀 View All Students");
            System.out.println("2. 🔍 Search Student");
            System.out.println("3. ↩️ Back");

            int choice = getIntInput("Enter your choice", 1, 3);

            switch (choice) {
                case 1 -> viewAllStudents();
                case 2 -> searchStudent();
                case 3 -> inStudentMenu = false;
            }
        }
    }

    private void viewAllStudents() {
        try {
            List<StudentModel> students = teacherDao.getAllStudent();

            System.out.println("\n📋 STUDENT ASSIGNMENT STATUS");
            System.out.println("----------------------------------------------------");
            System.out.printf("%-5s %-20s %-15s %-15s%n", "ID", "Name", "Contact", "Status");
            System.out.println("----------------------------------------------------");

            for (StudentModel student : students) {
                String statusIcon = switch (student.getsAssignment()) {
                    case "Completed" -> "✅";
                    case "In Progress" -> "⏳";
                    case "Not Started" -> "🛑";
                    default -> "❓";
                };

                System.out.printf("%-5d %-20s %-15s %-2s %-13s%n",
                        student.getsId(),
                        student.getsName(),
                        student.getsNum(),
                        statusIcon,
                        student.getsAssignment());
            }
        } catch (SQLException e) {
            System.out.println("\n❌ Error retrieving students: " + e.getMessage());
        }
    }

    private void searchStudent() {
        System.out.print("\n🔍 Enter student name to search: ");
        String name = sc.nextLine().trim();

        try {
            List<StudentModel> results = teacherDao.findStudentByName(name);

            if (results.isEmpty()) {
                System.out.println("\nℹ️ No students found with name: " + name);
            } else {
                System.out.println("\n🔎 SEARCH RESULTS");
                System.out.println("------------------------------------------------");
                System.out.printf("%-5s %-20s %-15s %-30s%n", "ID", "Name", "Contact", "Assignment");
                System.out.println("------------------------------------------------");

                results.forEach(student ->
                        System.out.printf("%-5d %-20s %-15s %-30s%n",
                                student.getsId(),
                                student.getsName(),
                                student.getsNum(),
                                truncate(student.getsAssignment(), 30))
                );
            }
        } catch (SQLException e) {
            System.out.println("\n❌ Error searching students: " + e.getMessage());
        }
    }

    private void handleAssignmentManagement() {
        boolean inAssignmentMenu = true;

        while (inAssignmentMenu) {
            System.out.println("\n📝 ASSIGNMENT MANAGEMENT");
            System.out.println("1. ➕ Add New Assignment");
            System.out.println("2. 👀 View Assignments");
            System.out.println("3. ↩️ Back");

            int choice = getIntInput("Enter your choice", 1, 2);

            switch (choice) {
                case 1 -> addAssignment();
                case 2 -> viewMyAssignments();
                case 3 -> inAssignmentMenu = false;
            }
        }
    }

    private void addAssignment() {
        System.out.println("\n➕ ADD NEW ASSIGNMENT");
        System.out.print("Enter assignment description: ");
        String description = sc.nextLine().trim();

        TeacherModel assignment = new TeacherModel();
        assignment.settAssignment(description);

        if (teacherDao.addAssignment(assignment)) {
            System.out.println("\n✅ Assignment added successfully!");
        } else {
            System.out.println("\n❌ Failed to add assignment");
        }
    }

//    private void displayAssignments(List<AssignmentModel> assignments, String title) {
//        if (assignments.isEmpty()) {
//            System.out.println("No assignments found for this subject.");
//            return;
//        }
//
//        System.out.println("\n" + title);
//        System.out.println("-----------------------------------------------------------------------------------------------");
//        System.out.printf("%-5s %-40s %-20s %s%n", "ID", "Assignment", "Teacher");
//        System.out.println("-----------------------------------------------------------------------------------------------");
//
//        for (AssignmentModel assignment : assignments) {
//            System.out.printf("%-5d %-40s %-20s ",
//                    assignment.getId(),
//                    truncate(assignment.getAssignment(), 40)

    /// /                    assignment.getTeacherName()
    /// /                    assignment.getCreatedAt()
//            );
//            System.out.println("\n");
//        }
//
//        System.out.println();
//    }

//    private void handlePasswordUpdate() {
//        System.out.println("\n🔄 PASSWORD UPDATE");
//        System.out.print("Enter new password: ");
//        String newPassword = sc.nextLine().trim();
//
//        if (teacherDao.updateTeacherPassword(newPassword)) {
//            System.out.println("\n✅ Password updated successfully!");
//        } else {
//            System.out.println("\n❌ Failed to update password");
//        }
//    }
    private void viewMyAssignments() {
        try {
            List<AssignmentModel> assignments = teacherDao.getAssignmentsByTeacher(
                    teacherDao.getCurrentTeacherId()
            );

            displayTeacherAssignments(assignments);
        } catch (SQLException e) {
            System.out.println("\n❌ Error retrieving assignments: " + e.getMessage());
        }
    }

    private void displayTeacherAssignments(List<AssignmentModel> assignments) {
        if (assignments.isEmpty()) {
            System.out.println("\n📭 You haven't created any assignments yet!");
            return;
        }

        System.out.println("\n📜 YOUR ASSIGNMENTS");
        System.out.println("============================================================================");
        System.out.printf("%-5s %-50s %-20s%n", "ID", "ASSIGNMENT", "DATE CREATED");
        System.out.println("============================================================================");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");

        for (AssignmentModel assignment : assignments) {
            String formattedDate = dateFormat.format(assignment.getCreatedAt());
            System.out.printf("%-5d %-50s %-20s%n",
                    assignment.getId(),
                    truncate(assignment.getAssignment(), 50),
                    formattedDate
            );
        }

        System.out.println("==============================================================================");
        System.out.println("Total assignments: " + assignments.size());
    }

    // Utility methods
    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt + " (" + min + "-" + max + "): ");
                int value = Integer.parseInt(sc.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private String truncate(String str, int length) {
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }

    private String formatDate(Timestamp timestamp) {
        return new SimpleDateFormat("dd-MM-yyyy").format(timestamp);
    }
}