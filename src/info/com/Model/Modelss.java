package info.com.Model;

public class Modelss {
    private int aId;
    private String aPassword;

// teacher
    private int tId;
    private String tname;
    private String tmobileNum;
    private String experties;
    private String tPassword ;
    private String tAssignment;

    // Student
    private int sId;
    private String sName;
    private String sAssignment;
    private String sNum;
    private String sPassword;


    // This is for STudent sEctionl

    public Modelss(int sId, String sName, String sAssignment, String sNum, String sPassword) {
        this.sId = sId;
        this.sName = sName;
        this.sAssignment = sAssignment;
        this.sNum = sNum;
        this.sPassword = sPassword;
    }

    public Modelss(String sName, String sNum){
        this.sName = sName;
        this.sNum = sNum;
    }
//    public Modelss( String sName, int sId, String sAssignment, String sNum){
//        this.sId=sId;
//        this.sName = sName;
//        this.sAssignment = sAssignment;
//        this.sNum = sNum;
//    }
//    //    setter for the student section
//    public void setsId(int sId) {
//        this.sId = sId;
//    }
//
//    public void setsName(String sName) {
//        this.sName = sName;
//    }
//
//    public void setsAssignment(String sAssignment) {
//        this.sAssignment = sAssignment;
//    }
//
//    public void setsNum(String sNum) {
//        this.sNum = sNum;
//    }
//
//    public void setsPassword(String sPassword) {
//        this.sPassword = sPassword;
//    }
//
//    // getter for the student section
//
//    public int getsId() {
//        return sId;
//    }
//
    public String getsName() {
        return sName;
    }
//
//    public String getsAssignment() {
//        return sAssignment;
//    }
//
    public String getsNum() {
        return sNum;
    }
//
//    public String getsPassword() {
//        return sPassword;
//    }








    // Below section is for teacher


//    public Modelss( String tname, String tmobileNum,String experties) {
//        this.tname = tname;
//        this.tmobileNum = tmobileNum;
////        this.tPassword = tPassword;
//    }


//    // getter for the teacher
//    public int gettId() {
//        return tId;
//    }
//
//    public String getTname() {
//        return tname;
//    }
//
//    public String getTmobileNum() {
//        return tmobileNum;
//    }
//
//    public String gettExperties() {
//        return experties;
//    }
//    public String gettPassword(){
//        return tPassword;
//    }
//    public String gettAssignment(){
//        return tAssignment;
//    }
//
//
//    // setter for the teacher
//
//    public void settId(int tId) {
//        this.tId = tId;
//    }
//
//    public void settPassword(String tPassword){
//        this.tPassword= tPassword;
//    }
//
//    public void setTname(String tname) {
//        this.tname = tname;
//    }
//
//    public void setTmobileNum(String tmobileNum) {
//        this.tmobileNum = tmobileNum;
//    }
//
//    public void settExperties(String experties) {
//        this.experties = experties;
//    }
//
//    public void settAssignment (String tAssignment){
//        this.tAssignment = tAssignment;
//    }







    // This is for the Admin section

    public void setaId(int aId) {
        this.aId = aId;
    }

    public void setaPassword(String aPassword) {
        this.aPassword = aPassword;
    }

    public String getaPassword() {
        return aPassword;
    }
    public int getaId() {
        return aId;
    }




}
