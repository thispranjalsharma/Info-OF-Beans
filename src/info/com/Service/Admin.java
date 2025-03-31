package info.com.Service;

import info.com.Dao.Daoo;
import info.com.Model.Modelss;
import info.com.Model.StudentModel;
import info.com.Model.TeacherModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static info.com.Dao.Daoo.findStudentByName;


public class Admin {
    private final Scanner sc = new Scanner(System.in);
    private final Daoo daoo;

    public Admin() {
        this.daoo = new Daoo();
    }


    public void aDMin() {
        System.out.println("Welcome to the Admin Section");
        try {
            boolean authenticated = false;
            while (!authenticated) {
//                System.out.print("Enter the ID: ");
                Integer id = parseIntInput("ID");

                System.out.print("Enter the password: ");
                String password = sc.nextLine().trim();

                try {
                    if (daoo.Admin(id, password)) {
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
            System.out.println("-----------You to do Following this with Teacher ---------\n");
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
        int n = getPositiveIntInput("Enter number of teachers to add: ");
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Teacher Name: " + (i + 1) + ":");
            String tname = sc.nextLine().trim();
            String tNum = getValidContact("Contact Number");
            System.out.print("Enter Expertise : ");
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
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving teachers: " + e.getMessage());
        }
    }

    // For teacher search
    private void findTeacher() {

        System.out.print("Entre the name of teacher to Search : ");
        String name = sc.nextLine();

        try {
            if (!daoo.findTeacherByName(name)) {
                System.out.println("No teachers found with name: \" + name");
            } else {
                int c = 0;
                System.out.println(c++);
                daoo.findTeacherByName(name);
            }
        } catch (SQLException e) {
            System.out.println("Error searching teachers: " + e.getMessage());
        }


//        System.out.print("Enter teacher name: ");
//        String name = sc.nextLine().trim();
//        try {
//            List<Modelss> results = daoo.findTeacherByName(name);
//            if (results.isEmpty()) {
//                System.out.println("No teachers found with name: " + name);
//            } else {
//                System.out.println("\nFound Teachers:");
//                results.forEach(t -> System.out.println(
//                        t.gettId() + " | " + t.getTname() + " | " +
//                                t.getTmobileNum() + " | " + t.gettExperties()));
//            }
//        } catch (SQLException e) {
//            System.out.println("Error searching teachers: " + e.getMessage());
//        }
    }

    private void handleStudentSection() throws SQLException {
        boolean isStudentSectionRunning = true;
        while (isStudentSectionRunning) {
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
                case 3 -> findStudent();
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
//                System.out.println("Error saving student: " + e.getMessage());
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
                findStudentByName(name);
            }
        } catch (SQLException e) {
            System.out.println("Error searching students: " + e.getMessage());
        }
        // For student search
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
