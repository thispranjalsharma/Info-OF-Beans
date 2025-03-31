package info.com.Main;

import info.com.Service.Admin;
import info.com.Service.Servicee;
import info.com.Service.Student;
import info.com.Service.Teacher;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Info-of-Beans 😊🙂");

        System.out.println("1. Admin login");
        System.out.println("2. Teacher login");
        System.out.println("3. Student login");

        int choice = Integer.parseInt(sc.nextLine());
        boolean flag= true;

        while (flag) {

            switch (choice) {
                case 1: {
                    Admin admin = new Admin();
                    admin.aDMin();
                    break;
                }
                case 2: {
                    Teacher teacher = new Teacher();
                    teacher.teach();
                    break;
                }
                case 3: {
                    Student student = new Student();
                    student.sweets();
                    break;
                }
                case 4: {
                    flag = false;
                }
                default:
                    System.out.println("please entre the Invalid input");
            }
        }






    }
}
