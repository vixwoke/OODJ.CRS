package crsAppPackage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class EligibilityManager {
    
    public enum SortCriteria {
        ID, NAME, CGPA, FAIL_COUNT
    }

    private ArrayList<String> courseIdList = new ArrayList<>();
    private ArrayList<Integer> courseCreditList = new ArrayList<>();
    private List<StudentAcademicProfile> allProfiles = new ArrayList<>();

    private static final String COURSE_FILE = "Resources/Data/course_information.txt";
    private static final String STUDENT_FILE = "Resources/Data/student_info.txt";
    private static final String RESULTS_FILE = "Resources/Data/student_results.txt";

    public EligibilityManager() {
        loadData();
    }

    private void loadData() {
        //Load Courses
        try(BufferedReader br = new BufferedReader(new FileReader(COURSE_FILE))) {
            String line;
            br.readLine(); 
            while((line = br.readLine()) != null) {
                String[] parts = line.split("\\?"); 
                if(parts.length >= 3) {
                    courseIdList.add(parts[0]);
                    courseCreditList.add(Integer.parseInt(parts[2]));
                }
            }
        } 
        catch(Exception e) { 
            System.err.println("Error loading courses: " + e.getMessage());
        }

        //Load Students
        try(BufferedReader br = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line;
            br.readLine(); 
            while((line = br.readLine()) != null) {
                String[] parts = line.split("\\?");
                if(parts.length >= 3) {
                    String name = parts[1] + " " + parts[2];
                    allProfiles.add(new StudentAcademicProfile(parts[0], name));
                }
            }
        } 
        catch(Exception e) { 
            System.err.println("Error loading students: " + e.getMessage());
        }

        //Load Results
        try(BufferedReader br = new BufferedReader(new FileReader(RESULTS_FILE))) {
            String line;
            br.readLine(); 
            while((line = br.readLine()) != null) {
                String[] parts = line.split("\\?");
                if(parts.length >= 3) {
                    String sID = parts[0].trim();
                    String cID = parts[1].trim();
                    
                    StudentAcademicProfile foundStudent = null;
                    for(int i = 0; i < allProfiles.size(); i++) {
                        if(allProfiles.get(i).getStudentID().equals(sID)) {
                            foundStudent = allProfiles.get(i);
                            break; //Found them
                        }
                    }

                    int foundCredits = -1;
                    for(int i = 0; i < courseIdList.size(); i++) {
                        if(courseIdList.get(i).equals(cID)) {
                            foundCredits = courseCreditList.get(i);
                            break; //Found it
                        }
                    }

                    //Only add if both were found
                    if(foundStudent != null && foundCredits != -1) {
                        double grade = Double.parseDouble(parts[2].trim()); 
                        foundStudent.addRecord(new AcademicRecord(cID, foundCredits, grade));
                    }
                }
            }
        } 
        catch(Exception e) { 
            System.err.println("Error loading results: " + e.getMessage());
        }
    }

    public List<StudentAcademicProfile> getAllStudents() {
        return allProfiles;
    }

    public void sortStudents(SortCriteria criteria, boolean ascending) {
        int n = allProfiles.size();
        
        for(int i = 0; i < n - 1; i++) {
            for(int j = 0; j < n - i - 1; j++) {
                
                StudentAcademicProfile p1 = allProfiles.get(j);
                StudentAcademicProfile p2 = allProfiles.get(j + 1);
                
                boolean swapNeeded = false;

                //Compare based on criteria
                double val1 = 0, val2 = 0;
                String str1 = "", str2 = "";
                boolean isStringComparison = false;

                switch(criteria) {
                    case ID:
                        str1 = p1.getStudentID();
                        str2 = p2.getStudentID();
                        isStringComparison = true;
                        break;
                    case NAME:
                        str1 = p1.getStudentName();
                        str2 = p2.getStudentName();
                        isStringComparison = true;
                        break;
                    case CGPA:
                        val1 = p1.calculateCGPA();
                        val2 = p2.calculateCGPA();
                        break;
                    case FAIL_COUNT:
                        val1 = p1.getFailedCourseCount();
                        val2 = p2.getFailedCourseCount();
                        break;
                }

                //Determine if swap
                if(isStringComparison) {
                    int compareResult = str1.compareTo(str2);
                    if(ascending) {
                        if(compareResult > 0) swapNeeded = true;
                    } 
                    else {
                        if(compareResult < 0) swapNeeded = true;
                    }
                } 
                else {
                    if(ascending) {
                        if(val1 > val2) swapNeeded = true;
                    } 
                    else {
                        if(val1 < val2) swapNeeded = true;
                    }
                }

                //Perform Swap
                if(swapNeeded) {
                    allProfiles.set(j, p2);
                    allProfiles.set(j + 1, p1);
                }
            }
        }
    }
}