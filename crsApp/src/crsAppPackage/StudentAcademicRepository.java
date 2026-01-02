package crsAppPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class StudentAcademicRepository {

    public static List<StudentAcademicProfile> loadAllProfiles() {


        Map<String, StudentInfo> studentInfoMap = new HashMap<>();

        ArrayList<ArrayList<String>> studentRows =
            FileManager.readFile("Resources/Data/student_info.txt", 6);


        for (int i = 1; i < studentRows.size(); i++) {
            ArrayList<String> row = studentRows.get(i);

            String studentId = row.get(0).trim();
            String fullName  = row.get(1).trim() + " " + row.get(2).trim();
            String major     = row.get(3).trim();
            String year      = row.get(4).trim();
            String email     = row.get(5).trim();

            studentInfoMap.put(
                studentId,
                new StudentInfo(studentId, fullName, major, year, email)
            );
        }

        Map<String, Integer> courseCredits = new HashMap<>();

        ArrayList<ArrayList<String>> courseRows =
            FileManager.readFile("Resources/Data/course_information.txt", 6);

        for (int i = 1; i < courseRows.size(); i++) {
            ArrayList<String> row = courseRows.get(i);

            courseCredits.put(
                row.get(0).trim(),
                Integer.parseInt(row.get(2).trim())
            );
        }

        Map<String, StudentAcademicProfile> profiles = new HashMap<>();

        ArrayList<ArrayList<String>> resultRows =
            FileManager.readFile("Resources/Data/student_results.txt", 3);

        for (int i = 1; i < resultRows.size(); i++) {
            ArrayList<String> row = resultRows.get(i);

            String studentId = row.get(0).trim();
            String courseId  = row.get(1).trim();
            double grade     = Double.parseDouble(row.get(2).trim());
            int creditHours  = courseCredits.get(courseId);

            StudentInfo info = studentInfoMap.get(studentId);

            StudentAcademicProfile profile =
            profiles.computeIfAbsent(
                studentId,
                id -> new StudentAcademicProfile(
                    id,
                    info.getFullName(),
                    info.getMajor(),
                    info.getYear(),
                    info.getEmail()
                )
            );

            profile.addRecord(
                new AcademicRecord(courseId, creditHours, grade)
            );
        }

        return new ArrayList<>(profiles.values());
    }
}
