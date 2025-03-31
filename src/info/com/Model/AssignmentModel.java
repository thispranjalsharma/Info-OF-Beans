package info.com.Model;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class AssignmentModel {
    private int id;
    private String assignment;
//    private Timestamp createdAt;
    private String teacherName;
//    private LocalDateTime createdAt;
private String createdAt;

    // Constructor
    public AssignmentModel(int id, String assignment,  String createdAt,  String teacherName) {
        this.id = id;
        this.assignment = assignment;
        this.createdAt = createdAt;
        this.teacherName = teacherName;
    }

    // In AssignmentModel:

    // Getters
    public int getId() { return id; }
    public String getAssignment() { return assignment; }
    public String getCreatedAt() { return createdAt; }
    public String getTeacherName() { return teacherName; }

    // Formatted date string
    public String getFormattedDate() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(createdAt);
    }
}