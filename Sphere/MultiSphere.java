import java.util.ArrayList;

public class MultiSphere {
    public static void main(String[] args) {
        
        Sphere john = new Sphere();
        Sphere bob = new Sphere(3.0);
        Sphere rutherford = new Sphere(2);
        Sphere imposter = new Sphere(rutherford);
        
        ArrayList<Sphere> spheres = new ArrayList<Sphere>();
        spheres.add(john);
        spheres.add(bob);
        spheres.add(rutherford);
        spheres.add(imposter);
        
        System.out.println("John's old radius: " + john.getRadius());
        System.out.println("John's old volume: " + john.getVolume());
        System.out.println("John's old surface area: " + john.getSurfaceArea());
        System.out.println("Setting John's radius to 6.7...");
        john.setDiameter(6.7);
        System.out.println("John's new radius: " + john.getRadius());
        System.out.println("John's new volume: " + john.getVolume());
        System.out.println("John's new surface area: " + john.getSurfaceArea());
        
        System.out.println();
        
        System.out.println(Sphere.compare(bob, rutherford));
        System.out.println(Sphere.compare(rutherford, imposter));
        System.out.println(Sphere.compare(john, bob));
        
        System.out.println(john);
        System.out.println(bob);
        System.out.println(rutherford);
        System.out.println(imposter);
        
        System.out.println("Number of spheres created: " + spheres.size());
    }
}