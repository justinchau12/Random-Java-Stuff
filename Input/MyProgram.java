import java.util.Scanner;
import java.io.*;

public class MyProgram
{
    public static void main(String[] args)
    {
        try {
        File myobj = new File("Input.txt");
        Scanner reader = new Scanner(myobj);
        int wordCount = 0;
        while (reader.hasNextLine()){
            String data = reader.nextLine();
            for (int i = 0; i < data.length() - 1; i++) {
                if ((data.charAt(i) == ' ') && (data.charAt(i + 1) != ' '))
                {
                    wordCount++;
                }
            }
        }
        wordCount++;
        reader.close();
        System.out.println("Word Count = " + wordCount);
        } catch (Exception e){
            System.out.println("Error");
        }
    }
}