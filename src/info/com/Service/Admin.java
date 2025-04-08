package info.com.Service;

import info.com.Dao.Daoo;
import info.com.Model.Modelss;
import info.com.Model.StudentModel;
import info.com.Model.TeacherModel;

import java.io.Console;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Admin {
    private final Scanner sc = new Scanner(System.in);
    private final Daoo daoo;

    public Admin() {
        this.daoo = new Daoo();
    }


    public void aDMin() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Welcome to the Admin Section");
        System.out.println("-----------------------------------------------------------------------");
        Console console = System.console();
        try {
            boolean authenticated = false;
            while (!authenticated) {
//                System.out.print("Enter the ID: ");
                Integer id = parseIntInput("ID");

                System.out.print("Enter the password: ");
                String password = sc.nextLine().trim();


                if (daoo.adminLogin(id, password)) {
                    authenticated = true;
                } else {
                    System.out.println("❌❌ Wrong ID or Password. Try again.");
                }
            }

            boolean isRunning = true;
            while (isRunning) {
                System.out.println("\n1. Teacher");
                System.out.println("2. Student");
                System.out.println("3.  🔙 Back");
                System.out.println("4.  STOP");

                int choice = parseIntInput("choice");

                switch (choice) {
                    case 1 -> handleTeacherSection();
                    case 2 -> handleStudentSection();
                    case 3 -> isRunning = false;
                    case 4 -> System.exit(101);
                    default -> System.out.println("Invalid choice!");
                }
            }
            System.out.println("Exiting Admin Section...");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    private void handleTeacherSection() throws SQLException {
        boolean isTeacherSectionRunning = true;
        while (isTeacherSectionRunning) {
            System.out.println();
            System.out.println("-----------------You have Following Operation in  Teacher ---------\n");
            System.out.println("\nTeacher Section:");
            System.out.println("1. Add Teacher");
            System.out.println("2. Retrieve Teachers");
            System.out.println("3. Find Teacher");
            System.out.println("4. 🔙 Back");
            int tChoice = parseIntInput("choice");

            switch (tChoice) {
                case 1 -> addTeachers();
                case 2 -> retrieveTeachers();
                case 3 -> findTeacher();
                case 4 -> isTeacherSectionRunning = false;
                default -> System.out.println("Invalid choice!");
            }
        }
    }


    private void addTeachers() {
        int n = getPositiveIntInput("Enter number of teachers to add: \n ");
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Teacher Name: " + (i + 1) + ":");
            String tname = sc.nextLine().trim();
            String tNum = getValidContact("Contact Number");
            System.out.print("Enter Expertise (Soft Skill  / Technical / All Rounder ) : ");
            String tex = sc.nextLine().trim();
            try {
                daoo.AddTeacher(new TeacherModel(tname, tNum, tex));
                System.out.println("Teacher added successfully!");
            } catch (SQLException e) {
                System.out.println("Error saving teacher: " + e.getMessage());
            }
        }
    }


    private void retrieveTeachers() {
        try {
            List<TeacherModel> teachers = daoo.getAllTeacher();
            if (teachers.isEmpty()) {
                System.out.println("No teachers found.");
            } else {
                System.out.println("\nTeacher List:");
                teachers.forEach(teach -> System.out.println(
                        teach.gettId() + " | " +
                                teach.getTname() + " | " +
                                teach.getTmobileNum() + " | " +
                                teach.gettExperties() + " | " +
                                teach.gettTime() + " | "

                ));
                System.out.println("\n");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving teachers: " + e.getMessage());
        }
    }


    private void findTeacher() {
        System.out.print("\n🔍 Enter Teacher name to search: ");
        String name = sc.nextLine().trim();

        try {
            List<TeacherModel> results = daoo.findTeacherByName(name);

            if (results.isEmpty()) {
                System.out.println("\nℹ️ No Teacher found with name: " + name);
            } else {
                System.out.println("\n🔎 SEARCH RESULTS");
                System.out.println("---------------------------------------------------------");
                System.out.printf("%-5s %-20s %-15s %-30s%n", "ID", "Name", "Contact", "Expertise");
                System.out.println("---------------------------------------------------------");

                results.forEach(teacher ->
                                System.out.printf("%-5d %-20s %-15s %-30s%n",
                                        teacher.gettId(),
                                        teacher.getTname(),
                                        teacher.getTmobileNum(),
                                        teacher.gettExperties())
//                                truncate(teacher.gettTime(), 30))
                );
                System.out.println("\n");
            }
        } catch (SQLException e) {
            System.out.println("\n❌ Error searching Teacher: " + e.getMessage());
        }
    }


    private void handleStudentSection() {
        boolean isStudentSectionRunning = true;
        while (isStudentSectionRunning) {

            System.out.println();
            System.out.println("-----------You to do Following this with Student ---------\n");

            System.out.println("\nStudent Section:");
            System.out.println("1. Add Student");
            System.out.println("2. Retrieve Students");
            System.out.println("3. Find Student");
            System.out.println("4. 🔙 Back");
            int sChoice = parseIntInput("choice");

            switch (sChoice) {
                case 1 -> addStudents();
                case 2 -> retrieveStudents();
                case 3 -> searchStudent();
                case 4 -> isStudentSectionRunning = false;
                default -> System.out.println("Invalid choice!");
            }
        }
    }


    private void addStudents() {
        int n = getPositiveIntInput("Enter number of students to add: ");
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Student ");
            System.out.print("Enter Student Name: " + (i + 1) + ":");
            String sname = sc.nextLine().trim();
            String sNum = getValidContact("Contact Number");

            try {
                daoo.AddStudent(new Modelss(sname, sNum));
                System.out.println("Student added successfully!");
            } catch (SQLException e) {
                System.out.println("Error saving student: " + e.getMessage());
            }
        }
    }


    private void retrieveStudents() {
        try {
            List<StudentModel> students = daoo.getAllStudent();

            System.out.println("\n📋 STUDENT ASSIGNMENT STATUS");
            System.out.println("----------------------------------------------------");
            System.out.printf("%-5s %-20s %-15s %-15s%n", "ID", "Name", "Contact", "Assignment Status");
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
            List<StudentModel> results = daoo.findStudentByName(name);

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


    // Utility methods for input handling

    private String truncate(String str, int length) {
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }

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
