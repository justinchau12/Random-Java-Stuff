import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter ("students.txt"));
            for (int i = 0; i < 5; i++){
                String studentName = input.nextLine();
                bw.write(studentName);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e){
            System.out.println("Error writing the file.");
        }
        
        Scanner reader = new Scanner("students.txt");
        int lineNum = 0;
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            System.out.println(lineNum + ". " + data);
            lineNum ++;
        }
    }
}