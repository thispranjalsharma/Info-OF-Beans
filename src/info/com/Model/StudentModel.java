package info.com.Model;

public class StudentModel {
    // Student
    private int sId;
    private String sName;
    private String sAssignment;
    private String sNum;
    private String sPassword;

    public StudentModel ( int sId,String sName, String sNum, String sAssignment ){
        this.sId = sId;
        this.sName= sName;
        this.sNum = sNum;
        this.sAssignment=sAssignment;
    }

    public StudentModel( String sName, String sPassword ){
        this.sName= sName;
        this.sPassword = sPassword;
    }


    //    setter for the student section
    public void setsId(int sId) {
        this.sId = sId;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setsAssignment(String sAssignment) {
        this.sAssignment = sAssignment;
    }

    public void setsNum(String sNum) {
        this.sNum = sNum;
    }

    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    // getter for the student section

    public int getsId() {
        return sId;
    }

    public String getsName() {
        return sName;
    }

    public String getsAssignment() {
        return sAssignment;
    }

    public String getsNum() {
        return sNum;
    }

    public String getsPassword() {
        return sPassword;
    }
}
