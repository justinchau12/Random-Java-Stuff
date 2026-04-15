import java.awt.*;
import javax.swing.*;

public abstract class Shape{
    public static String[] variableLabels;
    
    public Shape(String[] variableLabels){
        this.variableLabels = variableLabels;
    }
    
    public abstract double calculateSurfaceArea();
    public abstract double calculateVolume();
}

class Sphere extends Shape{
    public final static String[] variableLabels = {"Radius"};
    private double radius;
    
    public Sphere(double radius){
        super(variableLabels);
        this.radius = radius;    
    }
    
    public double calculateSurfaceArea(){
        return 4.0 * Math.PI * Math.pow(this.radius, 2.0);
    }
    
    public double calculateVolume(){ 
        return 4.0 / 3.0 * Math.PI * Math.pow(this.radius, 3.0);
    }
}

class Cube extends Shape{
    public final static String[] variableLabels = {"Side length"};
    private double sideLength;
    
    public Cube(double sideLength){
        super(variableLabels);
        this.sideLength = sideLength;    
    }
    
    public double calculateSurfaceArea(){
        return 6.0 * Math.pow(this.sideLength, 2.0);
    }
    
    public double calculateVolume(){ 
        return Math.pow(this.sideLength, 3.0);
    }
}

class Cone extends Shape{
    public final static String[] variableLabels = {"Radius", "Height"};
    private double radius;
    private double height;
    
    public Cone(double radius, double height){
        super(variableLabels);
        this.radius = radius;
        this.height = height;
    }
    
    public double calculateSurfaceArea(){
        return Math.PI * this.radius * (this.radius + Math.sqrt(Math.pow(this.height, 2.0) + Math.pow(this.radius, 2.0)));
    }
    
    public double calculateVolume(){ 
        return Math.PI * Math.pow(this.radius, 2.0) * this.height / 3.0;
    }
}

class Cylinder extends Shape{
    public final static String[] variableLabels = {"Radius", "Height"};
    private double radius;
    private double height;
    
    public Cylinder(double radius, double height){
        super(variableLabels);
        this.radius = radius;
        this.height = height;
    }
    
    public double calculateSurfaceArea(){
        return 2.0 * Math.PI * this.radius * (this.radius + this.height);
    }
    
    public double calculateVolume(){ 
        return Math.PI * Math.pow(this.radius, 2.0) * this.height;
    }
}

class Tetrahedron extends Shape{
    public final static String[] variableLabels = {"Side length"};
    private double sideLength;
    
    public Tetrahedron(double sideLength){
        super(variableLabels);
        this.sideLength = sideLength;    
    }
    
    public double calculateSurfaceArea(){
        return Math.sqrt(3.0) * Math.pow(this.sideLength, 2.0);
    }
    
    public double calculateVolume(){ 
        return  Math.pow(this.sideLength, 3.0) / (6.0 * Math.sqrt(2.0));
    }
}