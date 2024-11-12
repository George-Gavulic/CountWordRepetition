import java.io.*;
import java.util.*;
import java.util.regex.*;

public class WordCount {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n\nEnter the name of the input file (or press Enter to use the default jokes file): ");
        String inputFilePath = scanner.nextLine().trim();

        if (inputFilePath.isEmpty()) {
            inputFilePath = "jokes.txt"; // Assuming jokes.txt is in the root project directory
            System.out.println("No input file provided. Using default file: jokes.txt");
        }

        System.out.print("Enter the name of the output file: ");
        String outputFilePath = scanner.nextLine().trim();

        List<String> words = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();

        Pattern pattern = Pattern.compile("[a-zA-Z]+");  // Regex to match only alphabetic characters

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase(); // Convert entire line to lowercase
                String[] tokens = line.split("\\s+"); // Split the line by whitespace

                for (String token : tokens) {
                    Matcher matcher = pattern.matcher(token);
                    if (matcher.matches()) {  // Check if token contains only alphabetic characters
                        int index = words.indexOf(token);  // Check if word is already in the list
                        if (index != -1) {
                            counts.set(index, counts.get(index) + 1);  // Increment the count
                        } else {
                            words.add(token);  // Add the new word
                            counts.add(1);  // Initialize the count to 1
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (int i = 0; i < words.size(); i++) {
                bw.write(words.get(i) + " " + counts.get(i));
                bw.newLine();
            }
            System.out.println("Word count has been written to: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing to the output file: " + e.getMessage());
        }
        scanner.close();
    }
}
