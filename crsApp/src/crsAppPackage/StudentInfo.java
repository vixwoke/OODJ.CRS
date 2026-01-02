package crsAppPackage;

public class StudentInfo {

    private String studentId;
    private String fullName;
    private String major;
    private String year;
    private String email;

    public StudentInfo(String studentId, String fullName,
                       String major, String year, String email) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.major = major;
        this.year = year;
        this.email = email;
    }

    public String getFullName() { return fullName; }
    public String getMajor() { return major; }
    public String getYear() { return year; }
    public String getEmail() { return email; }
}
