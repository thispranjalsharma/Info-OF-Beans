package info.com.Model;

public class TeacherModel {
    // Teacher
    private int tId;
    private String tname;
    private String tmobileNum;
    private String tExperties;
    private String tPassword ;
    private String tAssignment;
    private String tTime;

    public TeacherModel(){

    }

    public TeacherModel(int tId,String tname, String tmobileNum,String tExperties){
        this.tId=tId;
        this.tname = tname;
        this.tmobileNum = tmobileNum;
        this.tExperties = tExperties;
    }
    public TeacherModel(String tname, String tmobileNum,String tExperties){
        this.tname = tname;
        this.tmobileNum = tmobileNum;
        this.tExperties = tExperties;
    }
    public TeacherModel(String tAssignment){
        this.tAssignment=tAssignment;
    }

    public TeacherModel(int tId, String tname, String tmobileNum, String tExperties, String tPassword, String tAssignment,String tTime) {
        this.tId = tId;
        this.tname = tname;
        this.tmobileNum = tmobileNum;
        this.tExperties = tExperties;
        this.tPassword = tPassword;
        this.tAssignment = tAssignment;
        this.tTime = tTime;
    }

    // getter for the teacher
    public int gettId() {
        return tId;
    }

    public String getTname() {
        return tname;
    }

    public String getTmobileNum() {
        return tmobileNum;
    }

    public String gettExperties() {
        return tExperties;
    }
    public String gettPassword(){
        return tPassword;
    }
    public String gettAssignment(){
        return tAssignment;
    }
    public String gettTime(){
        return tTime;
    }


    // setter for the teacher

    public void settId(int tId) {
        this.tId = tId;
    }

    public void settPassword(String tPassword){
        this.tPassword= tPassword;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void setTmobileNum(String tmobileNum) {
        this.tmobileNum = tmobileNum;
    }

    public void settExperties(String tExperties) {
        this.tExperties = tExperties;
    }

    public void settAssignment (String tAssignment){
        this.tAssignment = tAssignment;
    }

    public void settTime(String tTime){
        this.tTime = tTime;
    }
}
