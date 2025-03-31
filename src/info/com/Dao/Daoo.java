package info.com.Dao;

import info.com.Model.AssignmentModel;
import info.com.Model.Modelss;
import info.com.Model.StudentModel;
import info.com.Model.TeacherModel;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class Daoo {
    private static final String URL = "jdbc:mysql://localhost:3306/infobeansMS";
    private static final String user = "root";
    private static final String password = "root";

    private static Connection con;

    static {
        try {
            con = DriverManager.getConnection(URL, user, password);
        } catch (SQLException e) {
            System.out.println("Not connected to database ");
            System.out.println(e.getMessage());
        }
    }

    public static boolean Admin(Integer id, String password) throws SQLException {
        String query = "select * from admin where id =" + id + " and password = '" + password + "'";
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        boolean right = false;
        if (rs.next()) {
            right = true;
        }
        return right;
    }


    //  Admin Panel
    public static void AddTeacher(TeacherModel teacher) throws SQLException {
        String query = "insert into addTeacher( name, mobilenum,experties )values (?,?,?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, teacher.getTname());
        pst.setString(2, teacher.getTmobileNum());
        pst.setString(3, teacher.gettExperties());
        pst.executeUpdate();
        System.out.println("Teacher Add...");
    }

    public List<TeacherModel> getAllTeacher() throws SQLException {
        List<TeacherModel> teacher = new ArrayList<>();
        String query = "select * from AddTeacher";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String num = rs.getString(3);
            String expert = rs.getString(4);
            String password = rs.getString(5);
            String assignment = rs.getString(6);
            String time = String.valueOf(rs.getTimestamp(7));
            teacher.add(new TeacherModel(id, name, num, expert, password, assignment, time));
        }
        return teacher;
    }

    public static boolean findTeacherByName(String name) throws SQLException {
        String query = "select * from addteacher where name='" + name + "'";
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        boolean userFound = false;
        if (rs.next()) {
            userFound = true;
            System.out.println(rs.getInt(1)
                    + " | " + rs.getString(2)
                    + " | " + rs.getString(3)
                    + " | " + rs.getString(4)
                    + " | " + rs.getString(5)
                    + " | " + rs.getString(6)
                    + " | " + rs.getTimestamp(7)
            );
        }

//        List<Modelss> teacher = new ArrayList<>();
//        while (rs.next()){
//            teacher.add(new Modelss(rs.getString(1), rs.getInt(2) ,rs.getString(3),rs.getString(4)));
//        }

        return userFound;
    }


    public static void AddStudent(Modelss modelss) throws SQLException {
        String query = "insert into addStudent( name, num )values ( ?,?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, modelss.getsName());
        pst.setString(2, modelss.getsNum());
        pst.executeUpdate();
        System.out.println("Student  Add...");
    }

    public static List<StudentModel> getAllStudent() throws SQLException {
        List<StudentModel> student = new ArrayList<>();
        String query = "select * from AddStudent";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String assignment = rs.getString(3);
            String num = rs.getString(4);
//            String  password = rs.getString(5);
            student.add(new StudentModel(id, name, num, assignment));
        }
        return student;
    }

    public static boolean findStudentByName(String name) throws SQLException {
        String query = "select * from addStudent where name='" + name + "'";
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        boolean userFound = false;

        if (rs.next()) {
            userFound = true;
            System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4));
        }
        return userFound;
    }


    // Teacher session information (should ideally be in a session manager class)
    private static int currentTeacherId = 0;
    private static String currentTeacherExpertise = "";
    private static boolean isTeacherAuthenticated = false;


    static String tname = "";
    static String tpassword = "";

    // Teacher Panel
    public boolean Teacher(String name, String password) throws SQLException {
        this.tname = name;
        this.tpassword = password;

        String query = "select * from addTeacher where name ='" + name + "' and password = '" + password + "'";
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        boolean right = false;
        if (rs.next()) {
            right = true;
        }
        return right;
    }


    // Teacher Authentication
    public boolean authenticateTeacher(String name, String password) throws SQLException {
        String query = "SELECT id, experties FROM addTeacher WHERE name = ? AND password = ?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    currentTeacherId = rs.getInt("id");
                    currentTeacherExpertise = rs.getString("experties");
                    isTeacherAuthenticated = true;
                    return true;
                }
            }
        }
        return false;
    }

    // Add Assignment with proper authentication check
    public static void addAssignment(TeacherModel teacherModel) throws SQLException {
        if (!isTeacherAuthenticated) {
            throw new SQLException("Teacher not authenticated. Please login first.");
        }

        String query = "INSERT INTO assignment (assignment, subject, teacher_id) VALUES (?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, teacherModel.gettAssignment());
            pst.setString(2, currentTeacherExpertise);
            pst.setInt(3, currentTeacherId);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Failed to add assignment");
            }
        }
    }

//    // Teacher Logout
//    public void teacherLogout() {
//        currentTeacherId = 0;
//        currentTeacherExpertise = "";
//        isTeacherAuthenticated = false;
//    }

    public boolean Student(String name, String password) throws SQLException {
        this.tname = name;
        this.tpassword = password;

        String query = "select * from addStudent where name ='" + name + "' and password = '" + password + "'";
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        boolean right = false;
        if (rs.next()) {
            right = true;
        }
        return right;
    }

//    public List<TeacherModel> getAssignementOfTechnical(TeacherModel teacherModel) throws SQLException {
//        String query = "select * from assignment where subject = 'Computer science' ";
//        PreparedStatement pst = con.prepareStatement(query);
//        ResultSet rs = pst.executeQuery();
//        List <TeacherModel> list= new ArrayList<>();
//        boolean right = false;
//        while (rs.next()) {
//            String asMent = rs.getString(1);
//            list.add(new TeacherModel(asMent));
//        }
//        return list;
//    }
//
//
//    public List<TeacherModel> getAssignementOfSoftSkill(TeacherModel teacherModel) throws SQLException {
//        String query = "select * from assignment where subject = 'soft' ";
//        PreparedStatement pst = con.prepareStatement(query);
//        ResultSet rs = pst.executeQuery();
//        List <TeacherModel> list= new ArrayList<>();
//        boolean right = false;
//        while (rs.next()) {
//            String asMent = rs.getString(1);
//            list.add(new TeacherModel(asMent));
//        }
//        return list;
//    }


    public List<AssignmentModel> getAssignmentsBySubject(String subject) throws SQLException {
        String query = "SELECT a.id, a.assignment, a.created_at, t.name " +
                "FROM assignment a " +
                "JOIN addTeacher t ON a.teacher_id = t.id " +
                "WHERE a.subject = ?";

        List<AssignmentModel> assignments = new ArrayList<>();

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, subject);

            try (ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(rs.getTimestamp("created_at"));
                    assignments.add(new AssignmentModel(

                            rs.getInt("id"),
                            rs.getString("assignment"),

                            formattedDate,
                            rs.getString("name")
                    ));
                }
            }


        }

        return assignments;


    }


}
