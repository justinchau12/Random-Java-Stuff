public class Sphere
{
    private double diameter;
    
    // Default constructor
    public Sphere()
    {
        this.diameter = 1.0;    
    }
    
    public Sphere(double diameter)
    {
        if (diameter < 0)
        {
            throw new ArithmeticException("Sphere creation failed - diameter cannot be negative.\n");
        }
        this.diameter = diameter;
    }
    
    public Sphere(int radius)
    {
        if (radius < 0)
        {
            throw new ArithmeticException("Sphere creation failed - radius cannot be negative.\n");
        }
        this.diameter = 2.0 * radius;
    }
    
    public Sphere(Sphere originalSphere)
    {
        this.diameter = originalSphere.getDiameter();
    }
    
    public double getDiameter()
    {
        return this.diameter;
    }
    
    public void setDiameter(double diameter)
    {
        if (diameter < 0)
        {
            throw new ArithmeticException("Setting new diameter failed - diameter cannot be negative.\n");
        }
        this.diameter = diameter;
    }
    
    public double getRadius()
    {
        return this.diameter/2;
    }
    
    public double getVolume()
    {
        return Math.round(4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3.0) * 100.0) / 100.0;
    }
    
    public double getSurfaceArea()
    {
        return Math.round(4.0 * Math.PI * Math.pow(getRadius(), 2.0) * 100.0) / 100.0;
    }
    
    public void scale(double factor)
    {
        this.diameter *= factor;    
    }
    
    // Comparing the sizes of spheres using their radii
    public static String compare(Sphere formerSphere, Sphere latterSphere)
    {
        if (formerSphere.getRadius() > latterSphere.getRadius())
        {
            return "The former sphere is larger.\n";
        }
        else if (formerSphere.getRadius() == latterSphere.getRadius())
        {
            return "Both spheres are equal in size.\n";
        }
        else
        {
            return "The latter sphere is larger.\n";
        }
    }
    
    @Override
    public String toString()
    {
        return "Diameter: " + this.diameter + "\nRadius: " + getRadius() + "\nVolume: " + Math.round(getVolume() * 100.0) / 100.0 + "\nSurface Area: " + Math.round(getSurfaceArea() * 100.0) / 100.0 + "\n";
    }
}