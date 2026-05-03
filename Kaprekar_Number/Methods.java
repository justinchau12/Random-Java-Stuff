public class Methods {
    public static boolean isKaprekar(int num) {
        if (num == 1){ //Edge case
            return true;
        }
        long squaredNum = squareNumber(num);
        return splitAndCheck(num, squaredNum);
    }
    
    public static long squareNumber(int num){
        return (long) Math.pow(num, 2);
    }
    
    public static int countDigits(long num){
        return (int)(Math.log10(num)+1);
    }
    
    public static boolean splitAndCheck(int num, long squaredNum){
        String s = String.valueOf(squaredNum);
        int length = countDigits(squaredNum);
        for (int count = 1; count < length; count++){
            int num1 = Integer.valueOf(s.substring(0, count));
            int num2 = Integer.valueOf(s.substring(count));
            if ((num1 + num2) == num){
                return true;
            }
        }
        return false;
    }
}
