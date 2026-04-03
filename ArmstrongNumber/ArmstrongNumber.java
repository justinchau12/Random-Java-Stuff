import java.util.Scanner;

public class ArmstrongNumber
{
    public static void main(String[] args) 
    {
        // Get number from user and initialize variables
        Scanner input = new Scanner(System.in);
        System.out.print("Input a number: ");
        int n = input.nextInt(); // Get the number from user and store it as n
        int numLength = Integer.toString(n).length();
        
        int calNum = CalculateValue(numLength, n); // Variable used to store the total value of each digit raised to the power of the number's total digits
        
        // Check if calculate value is equal to the number
        if (calNum == n) {
            System.out.println(n + " is an Armstrong Number.");
        } else {
            System.out.println(n + " is not an Armstrong Number.");
        }
    }
    
    // Loop until after going through all digits of the number
    public static int CalculateValue(int numLength, int number)
    {
        int calNum = 0;
        for (int count = 0; count < numLength; count++)
        {
            int cur = Character.getNumericValue(Integer.toString(number).charAt(count)); // Get numeric value of each digit
            calNum += Math.pow(cur, numLength); // add the value of the digit raise to the power of numLength to calNum
        }
        return calNum;
    }
}