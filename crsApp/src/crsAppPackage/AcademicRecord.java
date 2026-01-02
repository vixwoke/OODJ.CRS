package crsAppPackage;


public class AcademicRecord {
    private String courseId;
    private int creditHours;
    private double grade;

    public AcademicRecord(String courseId, int creditHours, double grade) {
        this.courseId = courseId;
        this.creditHours = creditHours;
        this.grade = grade;
    }
 
    public String getCourseId(){
        return courseId;
    }
    
    
    public double getGradePoints() {
        return grade;
    }

    public boolean isPass() {
        return grade >= 2.0;
    }

    public int getCreditHours() {
        return creditHours;
    }
   
    
}
