///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package crsAppPackage;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableModel;
//
///**
// *
// * @author gilan
// */
//public class CourseRecoveryPlanDetails {
//        public TableModel refreshTable(string studentID) {
//        // ===== TABLE HEADER =====
//        String[] columns;
//        columns = new String[]{"Week", "Task Description", "Status"};
//
//        // ===== TABLE MODEL =====
//        DefaultTableModel model = new DefaultTableModel(columns, 0) {
//
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//
//        // ===== READ FILES =====
//        ArrayList<ArrayList<String>> student_results =
//                FileManager.readFile("Resources/Data/student_results.txt", 3, new HashMap<>());
//
//        ArrayList<ArrayList<String>> student_info =
//                FileManager.readFile("Resources/Data/student_info.txt", 6, new HashMap<>());
//
//        ArrayList<ArrayList<String>> course_information =
//                FileManager.readFile("Resources/Data/course_information.txt", 6, new HashMap<>());
//
//        // ===== LOOKUP MAPS =====
//        Map<String, String> studentFirstNameMap = new HashMap<>();
//        Map<String, String> studentLastNameMap = new HashMap<>();
//        Map<String, String> studentMajorMap = new HashMap<>();
//
//        for (ArrayList<String> row : student_info) {
//            studentFirstNameMap.put(row.get(0), row.get(1));
//            studentLastNameMap.put(row.get(0), row.get(2));
//            studentMajorMap.put(row.get(0), row.get(3));
//        }
//
//        Map<String, String> courseNameMap = new HashMap<>();
//        for (ArrayList<String> row : course_information) {
//            courseNameMap.put(row.get(0), row.get(1));
//        }
//
//        // ===== FILTER + JOIN =====
//        for (ArrayList<String> row : student_results) {
//
//            String studentId = row.get(0);
//            String courseId = row.get(1);
//            double gradePoint = Double.parseDouble(row.get(2));
//
//            if (gradePoint < 2.0) {
//
//                if (batchMode) {
//                    model.addRow(new Object[]{
//                        false, // checkbox default
//                        studentId,
//                        studentFirstNameMap.get(studentId) + " " + studentLastNameMap.get(studentId),
//                        studentMajorMap.get(studentId),
//                        courseNameMap.get(courseId),
//                        String.format("%.2f", gradePoint)
//                    });
//                } else {
//                    model.addRow(new Object[]{
//                        studentId,
//                        studentFirstNameMap.get(studentId) + " " + studentLastNameMap.get(studentId),
//                        studentMajorMap.get(studentId),
//                        courseNameMap.get(courseId),
//                        String.format("%.2f", gradePoint)
//                    });
//                }
//            }
//        }
//
//        return model;
//    }
//
//}
