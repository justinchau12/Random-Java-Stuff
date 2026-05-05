public enum Angle {
    ZERO(2 * Math.PI, "2π"),
    ONE_EIGHTH(Math.PI / 4, "π/4"),
    QUARTER(Math.PI / 2, "π/2"),
    THREE_EIGHTHS(3 * Math.PI / 4, "3π/4"),
    HALF(Math.PI, "π"), 
    FIVE_EIGHTHS(5 * Math.PI / 4, "5π/4"),
    THREE_QUARTER(3 * Math.PI / 2, "3π/2"),
    SEVEN_EIGHTHS(7 * Math.PI / 4, "7π/4");
    
    private final double radians;
    private final String description;
    
    Angle(double radians, String description){
        this.radians = radians;
        this.description = description;
    }
    
    public double getRadians(){
        return radians;
    }
    
    public String getDescription(){
        return description;
    }
}