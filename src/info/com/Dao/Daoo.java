package info.com.Dao;

import info.com.Model.AssignmentModel;
import info.com.Model.Modelss;
import info.com.Model.StudentModel;
import info.com.Model.TeacherModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Daoo {
    // Database configuration
    private static final String URL = "jdbc:mysql://localhost:3306/infobeansMS";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection con;

    // Session management
    private static int currentTeacherId = 0;
    private static String currentTeacherExpertise = "";
    private static boolean isTeacherAuthenticated = false;
    public static int currentStudentId = 0;
    private static boolean isStudentAuthenticated = false;

    static {
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            handleException("Database connection failed", e);
        }
    }

    // ADMIN OPERATIONS
    public boolean adminLogin(int id, String password) {
        String query = "SELECT * FROM admin WHERE id = ? AND password = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            handleException("Admin login failed", e);
            return false;
        }
    }

    // Generic exception handler
    private static void handleException(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
    }


    // TEACHER OPERATIONS
    public boolean teacherLogin(String name, String password) {
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
                return false;
            }
        } catch (SQLException e) {
            handleException("Teacher login failed", e);
            return false;
        }
    }

    public static void AddTeacher(TeacherModel teacher) throws SQLException {
        String query = "insert into addTeacher( name, mobilenum,experties )values (?,?,?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, teacher.getTname());
        pst.setString(2, teacher.getTmobileNum());
        pst.setString(3, teacher.gettExperties());
        pst.executeUpdate();
//        System.out.println("Teacher Add...");
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

    // STUDENT OPERATIONS
    public boolean studentLogin(String name, String password) {
        String query = "SELECT id FROM addStudent WHERE name = ? AND password = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    currentStudentId = rs.getInt("id");
                    isStudentAuthenticated = true;
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            handleException("Student login failed", e);
            return false;
        }
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
//            String password = rs.getString(4);
            String num = rs.getString(5);
//            String  password = rs.getString(5);
            student.add(new StudentModel(id, name, assignment, num));
        }
        return student;
    }

    public List<StudentModel> findStudentByName(String name) throws SQLException {
        String query = "SELECT * FROM addStudent WHERE name LIKE ?";
        List<StudentModel> students = new ArrayList<>();

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, "%" + name + "%"); // Using LIKE for partial matches

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    students.add(new StudentModel(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("num"),
                            rs.getString("assignment")
                    ));
                }
            }
        }
        return students;
    }

    public List<TeacherModel> findTeacherByName(String name) throws SQLException {
        String query = "SELECT * FROM addteacher WHERE name LIKE ?";
        List<TeacherModel> teachers = new ArrayList<>();

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, "%" + name + "%"); // Using LIKE for partial matches

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    teachers.add(new TeacherModel(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("mobileNum"),
                            rs.getString("experties")
//                            rs.getString("createDate")
                    ));
                }
            }
        }
        return teachers;
    }

    public boolean updateStudentPassword(String newPassword) {
        if (!isStudentAuthenticated) return false;

        String query = "UPDATE addStudent SET password = ? WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, newPassword);
            pst.setInt(2, currentStudentId);
            int rows = pst.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            handleException("Password update failed", e);
            return false;
        }
    }

    // ASSIGNMENT OPERATIONS
    public boolean addAssignment(TeacherModel assignment) {
        if (!isTeacherAuthenticated) return false;

        String query = "INSERT INTO assignment (assignment, subject, teacher_id) VALUES (?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, assignment.gettAssignment());
            pst.setString(2, currentTeacherExpertise);
            pst.setInt(3, currentTeacherId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            handleException("Assignment creation failed", e);
            return false;
        }
    }

    public List<AssignmentModel> getAssignmentsByTeacher(int teacherId) throws SQLException {
        String query = "SELECT a.id, a.assignment, a.created_at " +
                "FROM assignment a " +
                "WHERE a.teacher_id = ?";

        List<AssignmentModel> assignments = new ArrayList<>();

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, teacherId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    assignments.add(new AssignmentModel(
                            rs.getInt("id"),
                            rs.getString("assignment"),
                            rs.getTimestamp("created_at"),
                            "" // Teacher name not needed here
                    ));
                }
            }
        }
        return assignments;
    }

    public int getCurrentTeacherId() {
        return isTeacherAuthenticated ? currentTeacherId : 0;
    }

    // DATA RETRIEVAL
    public List<AssignmentModel> getAssignmentsBySubject(String subject) {
        List<AssignmentModel> assignments = new ArrayList<>();
        String query = "SELECT a.id, a.assignment, a.created_at, t.name " +
                "FROM assignment a " +
                "JOIN addTeacher t ON a.teacher_id = t.id " +
                "WHERE a.subject = ?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, subject);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    assignments.add(new AssignmentModel(
                            rs.getInt("id"),
                            rs.getString("assignment"),
                            rs.getTimestamp("created_at"),
                            rs.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            handleException("Failed to retrieve assignments", e);
        }
        return assignments;
    }

    public boolean updateAssignmentStatus(int studentId, String status) throws SQLException {
        // Validate status first
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid status value");
        }

        String query = "UPDATE addstudent SET assignment = ? WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, status);
            pst.setInt(2, studentId);
            return pst.executeUpdate() > 0;
        }
    }

    private boolean isValidStatus(String status) {
        return status != null &&
                (status.equals("Not Started") ||
                        status.equals("In Progress") ||
                        status.equals("Completed"));
    }


    public int getCurrentStudentId() {
        if (isStudentAuthenticated) {
            return currentStudentId;
        }
        return 0; // or throw an exception if preferred
    }

    public String getAssignmentStatus(int studentId) throws SQLException {
        String query = "SELECT assignment FROM addstudent WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, studentId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? rs.getString("assignment") : "Not Found";
            }
        }
    }

    // UTILITY METHODS
    public void teacherLogout() {
        currentTeacherId = 0;
        currentTeacherExpertise = "";
        isTeacherAuthenticated = false;
    }

    public void studentLogout() {
        currentStudentId = 0;
        isStudentAuthenticated = false;
    }
}