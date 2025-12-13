package crsAppPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Model data untuk Mahasiswa (Student). 
 * Menyimpan informasi mahasiswa dan mengelola operasi file.
 */
public class Student {
    private static final String FILE_PATH = "Resources/Data/Student.txt";
    private static final int NUM_OF_COLUMNS = 7; // Asumsi: NPM, Name, Gender, Program, Course, Grade, Status

    private String npm;
    private String name;
    private String gender;
    private String program;
    private String course;
    private String grade;
    private String status;

    // --- Konstanta ---
    public static class GENDER {
        public static final String MALE = "Male";
        public static final String FEMALE = "Female";
    }

    public static class STATUS {
        public static final String COMPLETED = "Completed";
        public static final String FAILED = "Failed";
    }

    // --- Konstruktor ---

    public Student(String npm, String name, String gender, String program, String course, String grade, String status) {
        this.npm = npm;
        this.name = name;
        this.gender = gender;
        this.program = program;
        this.course = course;
        this.grade = grade;
        this.status = status;
    }

    public Student(String npm) {
        // Digunakan untuk mencari mahasiswa berdasarkan NPM (Nomor Pokok Mahasiswa)
        if (npm == null || npm.isBlank()) {
            throw new RuntimeException("NPM cannot be blank.");
        }
        
        ArrayList<String> studentData;
        try {
            studentData = getStudentData(npm);

            // Berhasil ditemukan
            this.npm = studentData.get(0);
            this.name = studentData.get(1);
            this.gender = studentData.get(2);
            this.program = studentData.get(3);
            this.course = studentData.get(4);
            this.grade = studentData.get(5);
            this.status = studentData.get(6);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // --- Getters ---

    public String getNpm() {
        return npm;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }
    
    // ... Tambahkan getter lainnya sesuai kebutuhan

    // --- Metode CRUD ---

    /**
     * Menambahkan data mahasiswa baru ke file.
     */
    public void addStudent() {
        // Validasi dasar
        if (npm.isBlank() || name.isBlank() || course.isBlank()) {
            throw new RuntimeException("Input cannot be blank.");
        }

        // Cek NPM unik
        ArrayList<ArrayList<String>> existingStudent = FileManager.readFile(FILE_PATH, NUM_OF_COLUMNS, new HashMap<Integer, String>() {{
            put(0, npm); // Cek kolom 0 (NPM)
        }});

        if (!existingStudent.isEmpty()) {
            throw new RuntimeException("NPM has already been registered.");
        }

        // Simpan ke file
        try {
            FileManager.addData(FILE_PATH, new String[] {npm, name, gender, program, course, grade, status});
        } catch (Exception e) {
            throw new RuntimeException("Failed to add student: " + e.getMessage());
        }
    }

    /**
     * Memperbarui informasi mahasiswa.
     * @return Jumlah baris yang terpengaruh.
     */
    public int updateInformation(String newName, String newGender, String newProgram, String newCourse, String newGrade, String newStatus) {
        // Menggunakan nilai lama jika input null/blank
        String updatedName = (newName == null || newName.isBlank()) ? this.name : newName;
        String updatedGender = (newGender == null || newGender.isBlank()) ? this.gender : newGender;
        String updatedProgram = (newProgram == null || newProgram.isBlank()) ? this.program : newProgram;
        String updatedCourse = (newCourse == null || newCourse.isBlank()) ? this.course : newCourse;
        String updatedGrade = (newGrade == null || newGrade.isBlank()) ? this.grade : newGrade;
        String updatedStatus = (newStatus == null || newStatus.isBlank()) ? this.status : newStatus;

        // Data yang akan disimpan
        String[] newData = new String[] {this.npm, updatedName, updatedGender, updatedProgram, updatedCourse, updatedGrade, updatedStatus};

        int numOfAffectedLine;
        try {
            // Kondisi WHERE: update data di mana kolom 0 (NPM) sama dengan NPM saat ini
            numOfAffectedLine = FileManager.updateData(FILE_PATH, newData, new HashMap<Integer, String>() {
                { put(0, npm); }
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to update student information: " + e.getMessage());
        }

        // Sinkronisasi objek saat ini dengan data yang baru diupdate
        this.name = updatedName;
        this.gender = updatedGender;
        this.program = updatedProgram;
        this.course = updatedCourse;
        this.grade = updatedGrade;
        this.status = updatedStatus;

        return numOfAffectedLine;
    }

    /**
     * Menghapus data mahasiswa dari file.
     * @return Jumlah baris yang terpengaruh.
     */
    public int deleteStudent() {
        int numOfAffectedLine;
        try {
            numOfAffectedLine = FileManager.deleteData(FILE_PATH, new HashMap<Integer, String>() {
                { put(0, npm); }
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete student: " + e.getMessage());
        }
        // Kosongkan objek setelah penghapusan berhasil
        this.npm = null;
        return numOfAffectedLine;
    }

    // --- Metode Pencarian Statis ---

    /**
     * Mengambil semua data mahasiswa dari file.
     * @return List semua data mahasiswa dalam format List<List<String>>
     */
    public static ArrayList<ArrayList<String>> getAllStudentsData() {
        ArrayList<ArrayList<String>> students;
        try {
            students = FileManager.readFile(FILE_PATH, NUM_OF_COLUMNS);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return students;
    }

    private static ArrayList<String> getStudentData(String npm) {
        ArrayList<ArrayList<String>> student;
        try {
            // Cari data di mana kolom 0 (NPM) sama dengan nilai npm yang dicari
            student = FileManager.readFile(FILE_PATH, NUM_OF_COLUMNS, new HashMap<Integer, String>() {{
                put(0, npm);
            }});

            if (student.isEmpty()) {
                throw new RuntimeException("Student with NPM " + npm + " is not found.");
            }
            if (student.size() > 1) {
                throw new RuntimeException("Corrupted data: Multiple students found for NPM " + npm);
            }

            // Data Mahasiswa ada di baris pertama
            return student.getFirst();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // --- Integrasi EmailNotificationPanel ---

    /**
     * Mengambil semua alamat email mahasiswa.
     * Catatan: Karena email tidak ada di Student.txt (asumsi 7 kolom), 
     * kita akan menggunakan placeholder atau mengasumsikan NPM dapat digunakan
     * sebagai bagian dari email akademik (e.g., [NPM]@academics.com)
     */
    public static List<String> getAllStudentEmails() {
        ArrayList<ArrayList<String>> studentsData = getAllStudentsData();
        List<String> emails = new ArrayList<>();
        
        // Asumsi kolom 0 adalah NPM
        for (int i = 1; i < studentsData.size(); i++) {
             // Menggunakan format placeholder: [NPM]@student.com
             String npm = studentsData.get(i).get(0);
             emails.add(npm + "@student.academics.com");
        }
        return emails;
    }
    
    /**
     * Mengambil alamat email mahasiswa yang memiliki status "FAILED" (membutuhkan Recovery).
     */
    public static List<String> getFailedStudentEmails() {
        ArrayList<ArrayList<String>> studentsData = getAllStudentsData();
        
        // Asumsi kolom 6 adalah Status
        return studentsData.stream()
            .skip(1) // Lewati header
            .filter(row -> row.size() > 6 && STATUS.FAILED.equals(row.get(6)))
            .map(row -> row.get(0) + "@student.academics.com") // Mengambil NPM (kolom 0) untuk email
            .collect(Collectors.toList());
    }
}
