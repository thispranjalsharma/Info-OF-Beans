package info.com.Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class AssignmentModel {
    private int id;
    private String assignment;
    private Timestamp createdAt;
    private String teacherName;
//    private LocalDateTime createdAt;
//private String createdAt;

    // Constructor
    public AssignmentModel(int id, String assignment, Timestamp createdAt, String teacherName) {
        this.id = id;
        this.assignment = assignment;
        this.createdAt = Timestamp.valueOf(LocalDateTime.parse(String.valueOf( createdAt.toLocalDateTime())));
        this.teacherName = teacherName;
    }

    // Getters
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public int getId() { return id; }

    public String getAssignment() { return assignment; }

//    public java.security.Timestamp getCreatedAt() { return createdAt; }
    public String getTeacherName() { return teacherName; }

    // Formatted date string
//    public String getFormattedDate() {
//        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(createdAt);
//    }
}