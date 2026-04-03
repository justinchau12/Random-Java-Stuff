import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.swing.JFrame;

public class HeartHealthProfile
{
    private String name;
    private int age;
    private double weight; // in kg
    private double height; // in meters
    
    public HeartHealthProfile(String name, int age, double weight, double height){
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }
    
    public double calculateMaxHeartRate(){
        double MHR = 208 - (0.7 * age);
        return MHR;
    }
    
    public double calculateBMI(){
        double BMI = weight / (height * height);
        return BMI;
    }
    
    public double getFatBurnMin(){
        return calculateMaxHeartRate() * 0.6;
    }
    public double getFatBurnMax(){
        return calculateMaxHeartRate() * 0.7;
    }
    
    public double getCardioMin(){
        return calculateMaxHeartRate() * 0.7;
    }
    public double getCardioMax(){
        return calculateMaxHeartRate() * 0.85;
    }
    
    public String getBMIStatus(){
        double BMI = calculateBMI();
        if (age <= 60){
            if (BMI < 18.5){
                return "Underweight, (BMI < 18.5)";
            }
            else if (18.5 < BMI && BMI < 24.9){
                return "Normal (18.5 < BMI < 24.9)";
            }
            else if (25 < BMI && BMI < 29.9){
                return "Overweight (25 < BMI < 29.9)";
            }
            else if (BMI >= 30){
                return "Obese (BMI > 30)";
            }
        }
        else{ // Age > 60
            if (23 < BMI && BMI < 28){
                return "Healthy (23 < BMI < 28)";
            }
            else if (BMI < 23){
                return "Unhealthy (BMI < 23)";
            }
            else if (BMI < 28){
                return "Unhealthy (BMI > 28)";
            }
        }
        return "Error";
    }
    
    public String returnCaseForCalculation(){
        if (age <= 0){
            return "Error: age cannot be less than or equal to 0.";
        }
        else if (height <= 0){
            return "Error: height cannot be less than or eqaul to 0.";
        }
        else if (weight <= 0){
            return "Error: weight cannot be less than or equal to 0.";
        }
        else if (age >= Integer.MAX_VALUE){
            return "Error: age is too large.";
        }
        else if (weight >= Integer.MAX_VALUE){
            return "Error: weight is too large.";
        }
        else if (height >= Integer.MAX_VALUE){
            return "Error: height is too large.";
        }
        
        return "Valid";
    }
    public String returnReport(){
        String text;
        String caseType = returnCaseForCalculation();
        if (caseType == "Valid") {
            text = String.format("=== HEART HEALTH REPORT===\n\nName: %s\nAge: %d\n\nMaximum Heart Rate: %s bpm\n\nFat Burn Zone: \n%s - %s bpm\n\nCardio Zone: \n%s - %s bpm\n\nBMI: %s\nStatus: %s", this.name, this.age, roundToTwoDP(calculateMaxHeartRate()), roundToTwoDP(getFatBurnMin()), roundToTwoDP(getFatBurnMax()), roundToTwoDP(getCardioMin()), roundToTwoDP(getCardioMax()), roundToTwoDP(calculateBMI()), getBMIStatus());
            
            //text = "=== HEART HEALTH REPORT ===\n\nName: " + this.name + "\nAge: " + String.valueOf(this.age) + "\n\nMaximum Heart Rate: " + roundToTwoDP(calculateMaxHeartRate()) + " bpm\n\n" + "Fat Burn Zone: \n" + roundToTwoDP(getFatBurnMin()) + " - " + roundToTwoDP(getFatBurnMax()) + " bpm"+"\n\nCardio Zone: \n" + roundToTwoDP(getCardioMin()) + " - " +roundToTwoDP(getCardioMax()) + " bpm\n\nBMI: " + roundToTwoDP(calculateBMI()) + "\nStatus: " + getBMIStatus();
            // This is the more confusing one, incase the optimized one broke, use this one
        }
        else{
            text = caseType;
        }
        
        return text;
    }
    public String roundToTwoDP(double val){ //This function should be used at the LAST step of calculation!
        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal bd = new BigDecimal(val);
        String roundValAsString = df.format(bd);
        return roundValAsString;
    }
}