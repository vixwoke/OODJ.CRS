    package crsAppPackage;
    import java.util.ArrayList;
    import java.util.List;

    public class StudentAcademicProfile {
        private String studentID;
        private String studentName;
        private String studentMajor;
        private String studentYear;
        private String studentEmail;
        private List<AcademicRecord> records;

        public StudentAcademicProfile(String studentID, String studentName) {
            this.studentID = studentID;
            this.studentName = studentName;
            this.records = new ArrayList<>();
        }
        
        public StudentAcademicProfile(String studentID, String studentName, 
                String studentMajor, String studentYear, String studentEmail) {
            this.studentID = studentID;
            this.studentName = studentName;
            this.studentMajor = studentMajor;
            this.studentYear = studentYear;
            this.studentEmail = studentEmail;
            this.records = new ArrayList<>();
        }
        public void addRecord(AcademicRecord record) {
            records.add(record);
        }

        public List<AcademicRecord> getRecords() {
            return records;
        }

        public double calculateCGPA() {
            double totalPoints = 0;
            int totalCredits = 0;
            for(AcademicRecord r : records) {
                totalPoints += (r.getGradePoints() * r.getCreditHours());
                totalCredits += r.getCreditHours();
            }
            if(totalCredits == 0) return 0.0;
            return totalPoints/totalCredits;
        }

        public int getFailedCourseCount() {
            int failCount = 0;
            for(AcademicRecord r : records) {
                if(!r.isPass()) {
                    failCount++;
                }
            }
            return failCount;
        }

        public boolean checkEligibility() {
            return calculateCGPA() >= 2.0 && getFailedCourseCount() <3;
        }

        public String getStudentID() {
            return studentID;
        }

        public String getStudentName() {
            return studentName;
        }
        
        public String getStudentMajor() {
            return studentMajor;
        }
        public String getStudentYear() {
            return studentYear;
        }
        public String getStudentEmail() {
            return studentEmail;
        }

        public AcademicRecord getRecordByCourseId(String courseId) {
            for (AcademicRecord r : records) {
                if (r.getCourseId().equals(courseId)) {
                    return r;
                }
            }
            return null;
        }
    }
