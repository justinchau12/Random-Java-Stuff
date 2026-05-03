import java.util.Scanner;

public class KaprekarRunner
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a positive integer: ");
        int num = input.nextInt();
        
        boolean isKaprekar = Methods.isKaprekar(num);
        
        if (isKaprekar){
            System.out.println(num + " is a Kaprekar's Number.");
        } else{
            System.out.println(num + " is not a Kaprekar's Number.");
        }
    }
}
