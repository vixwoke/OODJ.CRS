import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class aaaaaaaaaaaaa {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};

        try {
            // 'true' means append mode
            PrintWriter output = new PrintWriter(new FileWriter("temp.txt", true));

            // write each number to the file
            for (int n : nums) {
                output.println(n);
            }

            // always close the writer
            output.close();
            System.out.println("Data written to temp.txt successfully!");
        } 
        catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
