package crsAppPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    //read file
    public static List<String[]> readFile(String filePath, String delimiter) {
        List<String[]> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(delimiter);
                result.add(parts);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    //write file
    public static void writeFile(String filePath, List<String[]> rows, String delimiter) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {

            for (String[] row : rows) {
                pw.println(String.join(delimiter, row));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

