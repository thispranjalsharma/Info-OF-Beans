package info.com.Main;

import info.com.Service.Admin;
import info.com.Service.Student;
import info.com.Service.Teacher;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Admin admin = new Admin();
        Teacher teacher = new Teacher();
        Student student = new Student();
        Scanner sc = new Scanner(System.in);

        boolean flag = true;
        while (flag) {
            System.out.println("Welcome to the Info-of-Beans 😊🙂");
            System.out.println("1. Admin login");
            System.out.println("2. Teacher login");
            System.out.println("3. Student login");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    admin.aDMin();
                    break;
                case 2:
                    teacher.teach();
                    break;
                case 3:
                    student.sweets();
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid input! Please enter a number between 1 and 4.");
            }
        }
        sc.close();
    }
}
