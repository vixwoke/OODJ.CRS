package crsAppPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException; 

public class AcademicReport {

    
    public void generateReport(String studentName, String studentID, String program, String[][] courseData) 
            throws FileNotFoundException {
        
        String filename = "AcademicReport_" + studentID + ".txt";
        
        try (FileWriter fileWriter = new FileWriter(filename);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            
            // 1. Header
            printWriter.println("==================================================");
            printWriter.println("          ACADEMIC PERFORMANCE REPORT             ");
            printWriter.println("==================================================");
            printWriter.println("Student Name: " + studentName);
            printWriter.println("Student ID:   " + studentID);
            printWriter.println("Program:      " + program);
            printWriter.println("--------------------------------------------------");

            // 2. Table Header
            printWriter.printf("| %-15s | %-25s | %-5s | %-5s | %-5s |\n", 
                               "Course Code", "Course Title", "Cred", "Grade", "Point");
            printWriter.println("--------------------------------------------------");
            
            double totalPoints = 0;
            double totalCredits = 0;

            // 3. Loop and Calculate
            for (String[] course : courseData) {
                // Write row
                printWriter.printf("| %-15s | %-25s | %-5s | %-5s | %-5s |\n", 
                                   course[0], course[1], course[2], course[3], course[4]);

                // Calculate CGPA
                try {
                    double credit = Double.parseDouble(course[2]);
                    double point = Double.parseDouble(course[4]);
                    totalPoints += (point * credit);
                    totalCredits += credit;
                } catch (NumberFormatException e) {
                    // Skip invalid data
                }
            }
            printWriter.println("--------------------------------------------------");

            // 4. Footer & CGPA
            double cgpa = totalCredits > 0 ? totalPoints / totalCredits : 0.0;
            String cgpaString = String.format("CGPA: %.2f", cgpa);
            printWriter.println("                                      " + cgpaString);
            printWriter.println("==================================================");

            System.out.println("✅ Report generated: " + filename);
            
        } catch (IOException e) {
            throw new FileNotFoundException("Error creating report file: " + e.getMessage());
        }
    }

}
