package crsAppPackage;

import java.util.ArrayList;

public class CourseRepository {

    public static String getCourseName(String courseId) {

        ArrayList<ArrayList<String>> rows =
            FileManager.readFile("Resources/Data/course_information.txt", 6);

        for (int i = 1; i < rows.size(); i++) {
            ArrayList<String> row = rows.get(i);
            if (row.get(0).equals(courseId)) {
                return row.get(1);
            }
        }

        return "Unknown Course";
    }
}
