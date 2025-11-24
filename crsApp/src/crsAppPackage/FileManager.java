package crsAppPackage;

import java.io.*;
import java.util.*;

public class FileManager {
    private static final String DELIMITER = "?";

    // Reading File
    public static ArrayList<ArrayList<String>> readFile(String path, int mumOfColumns) {
        return readFile(path, mumOfColumns, new HashMap<Integer, String>() {}, true);
    }

    public static ArrayList<ArrayList<String>> readFile(String path, int mumOfColumns, Map<Integer, String> conditions) {
        return readFile(path, mumOfColumns, conditions, false);
    }

    public static ArrayList<ArrayList<String>> readFile(String path, int numOfColumns, Map<Integer, String> conditions, boolean returnColumn) {
        /*
        A method to read and search from the file

        Parameters:
        path: the path to the file
        conditions:
            key: the index of the column
            value: compare if the value matches the data in the given index
        */

        ArrayList<ArrayList<String>> lines = new ArrayList<>();
        File myObj = new File(path);

        if (!myObj.exists()) {
            throw new RuntimeException("No file at " + path);
        }

        try (Scanner myReader = new Scanner(myObj)) {
            // the header
            String header = myReader.nextLine();
            if (returnColumn) {
                ArrayList<String> line = new ArrayList<>();
                StringTokenizer st = new StringTokenizer(header, DELIMITER);
                while (st.hasMoreTokens()){
                    line.add(st.nextToken());
                }

                // Check the number of columns
                if (line.size() != numOfColumns) {
                    throw new RuntimeException("Corrupted data: Unmatched number of columns");
                }

                lines.add(line);
            }

            // Loop through the data
            while(myReader.hasNextLine()) {
                String data = myReader.nextLine();
                StringTokenizer st = new StringTokenizer(data, DELIMITER);

                // Get the current line data
                ArrayList<String> line = new ArrayList<>();
                while (st.hasMoreTokens()){
                    line.add(st.nextToken());
                }

                // Check the number of data
                if (line.size() != numOfColumns) {
                    throw new RuntimeException("Corrupted data: Unmatched number of columns");
                }

                // Check the conditions
                boolean add = true;
                for (Map.Entry<Integer, String> condition : conditions.entrySet()) {
                    if (!line.get(condition.getKey()).equals(condition.getValue())) {
                        add = false;
                    }
                }

                // Add the current line into the output
                if (add) {
                    lines.add(line);
                }
            }
        } catch(IOException e) {
            throw new RuntimeException("Something went wrong when trying to read file at " + path);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Corrupted data at " + path);
        } catch(RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An error occured");
        }
        return lines;
    }

    // Adding Data
    public static void addData(String path, String[] line) {
        String data = "";
        for (String datum : line) {
            if (datum.contains(DELIMITER)) {
                throw new RuntimeException("Input cannot contain " + DELIMITER);
            }
            data += datum + DELIMITER;
        }
        if (data.isBlank()) {
            throw new RuntimeException("Added data cannot be blank");
        }

        data = data.substring(0, data.length() - 1) + "\n";

        File myObj = new File(path);
        try (FileWriter myWriter = new FileWriter(myObj, true)) {
            myWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong when trying to append file at " + path);
        } catch (Exception e) {
            throw new RuntimeException("An error occured");
        }
    }

    // Updating Data
    public static int updateData(String path, String[] line, Map<Integer, String> conditions) {
        int numOfLinesAffected = 0;

        String updated_data = "";
        for (String datum : line) {
            if (datum.contains(DELIMITER)) {
                throw new RuntimeException("Input cannot contain " + DELIMITER);
            }
            updated_data += datum + DELIMITER;
        }

        if (!updated_data.isBlank()) {
            updated_data = updated_data.substring(0, updated_data.length() - 1) + "\n";
        }

        String lines = "";
        File myObj = new File(path);

        if (!myObj.exists()) {
            throw new RuntimeException("No file at " + path);
        }

        try (Scanner myReader = new Scanner(myObj)) {
            lines += myReader.nextLine() + "\n";

            // Loop through the data
            while(myReader.hasNextLine()) {
                String data = myReader.nextLine();
                StringTokenizer st = new StringTokenizer(data, DELIMITER);

                // Get the current line data
                ArrayList<String> current_line = new ArrayList<>();
                while (st.hasMoreTokens()){
                    current_line.add(st.nextToken());
                }

                // Check the conditions
                boolean change = true;
                for (Map.Entry<Integer, String> condition : conditions.entrySet()) {
                    if (!current_line.get(condition.getKey()).equals(condition.getValue())) {
                        change = false;
                    }
                }

                if (change) {
                    // add the changed value to the output
                    lines += updated_data;
                    numOfLinesAffected += 1;
                } else {
                    // Add the current line into the output
                    lines += data + "\n";
                }
            }
        } catch(IOException e) {
            throw new RuntimeException("Something went wrong when trying to read file at " + path);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Corrupted data at " + path);
        } catch(RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An error occured");
        }

        try (FileWriter myWriter = new FileWriter(myObj)) {
            myWriter.write(lines);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong when trying to rewrite file at " + path);
        } catch (Exception e) {
            throw new RuntimeException("An error occured");
        }
        return numOfLinesAffected;
    }

    // Delete Data
    public static int deleteData(String path, Map<Integer, String> conditions) {
        return updateData(path, new String[] {}, conditions); // replace the row with blank string
    }
}
