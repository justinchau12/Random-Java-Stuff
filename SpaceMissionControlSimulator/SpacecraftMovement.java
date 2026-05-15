public abstract class SpacecraftMovement{
    
    // Constants
    protected enum Phase{
        ON_EARTH,
        LAUNCHING_TO_MOON_ORBIT,
        LANDING_ON_MOON,
        IN_CONSTANT_MOON_ORBIT,
        ON_MOON,
        RETURNING_TO_EARTH_ORBIT,
        LANDING_ON_EARTH, 
    }
    
    protected final double ARRIVAL_THRESHOLD = 1.0;
    
    protected SpacecraftCommand command;
    protected Phase currentPhase;
    protected double rocketX, rocketY;
    protected final int earthCentX, earthCentY, earthSiteX, earthSiteY, earthRadius, moonCentX, moonCentY, moonSiteX, moonSiteY, moonRadius;
    
    public SpacecraftMovement(Earth earth, Moon moon){
        command = SpacecraftCommand.IDLE;
        currentPhase = Phase.ON_EARTH;
        
        earthCentX = earth.getCentX();
        earthCentY = earth.getCentY();
        earthSiteX = earth.getSiteX();
        earthSiteY = earth.getSiteY();
        earthRadius = earth.getRadius();
        
        moonCentX = moon.getCentX();
        moonCentY = moon.getCentY();
        moonSiteX = moon.getSiteX();
        moonSiteY = moon.getSiteY();
        moonRadius = moon.getRadius();
        
        rocketX = earthSiteX;
        rocketY = earthSiteY;
    }
    
    public abstract void launchFlight();
    public abstract void returnFlight();
    public abstract void setValues();
    
    public double getCentX(){
        return rocketX;
    }
    
    public double getCentY(){
        return rocketY;
    }
    
    public void setCommand(SpacecraftCommand newCommand){
        command = newCommand;
    }
        
    public void runCommand(){
        switch (command){
            case IDLE -> {}
            case LAUNCH -> launchFlight();
            case RETURN -> returnFlight();
        }
    }
    
    public SpacecraftCommand getCurrentCommand(){
        return command;
    }
    
    public boolean onEarth(){
        return (currentPhase == Phase.ON_EARTH);
    }
    
    public boolean onMoon(){
        return (currentPhase == Phase.ON_MOON);
    }
    
    public boolean canChangeSpacecraft(){
        return (currentPhase == Phase.ON_EARTH || currentPhase == Phase.ON_MOON || currentPhase == Phase.IN_CONSTANT_MOON_ORBIT);
    }
    
    // Rockets need to override this, -1 should never show up for other spacecrafts as the label showing the value would be set invisible.
    public double getDistance(){
        return -1.0;
    }
    
    // Vector Normalization
    public void moveTo(double targetX, double targetY, double speed, Phase newPhase){
        double dx = targetX - rocketX;
        double dy = targetY - rocketY;
        double distance = Math.sqrt(dx * dx + dy * dy);
                
        if (distance > ARRIVAL_THRESHOLD){
            dx /= distance;
            dy /= distance;
                
            rocketX += dx * speed;
            rocketY += dy * speed;
        }else {
            rocketX = targetX;  
            rocketY = targetY;
            if (newPhase == Phase.ON_MOON || newPhase == Phase.ON_EARTH){
                command = SpacecraftCommand.IDLE;
            }
            currentPhase = newPhase;
        }
    }
    
    public void orbitTo(double targetX, double targetY, double orbitRadius, double orbitAngle, Phase newPhase){
        double dx = targetX - rocketX;
        double dy = targetY - rocketY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance > ARRIVAL_THRESHOLD){
            rocketX = targetX + orbitRadius * Math.cos(orbitAngle);
            rocketY = targetY + orbitRadius * Math.sin(orbitAngle);
        }else {
            rocketX = targetX;  
            rocketY = targetY;
            if (newPhase == Phase.ON_MOON || newPhase == Phase.ON_EARTH){
                command = SpacecraftCommand.IDLE;
            }
            currentPhase = newPhase;
        }
    }
}

class RocketMovement extends SpacecraftMovement{
    
    // Constants
    private final double speed = 1.5;
    private final double distanceAboveMoonSurface = 20.0;
    private final double distanceAboveEarthSurface = 50.0;
    private final double initialMoonOrbitAngle = Math.toRadians(180);
    private final double initialEarthOrbitAngle = Math.toRadians(0);
    private final double deltaMoonOrbitRadius = 0.15;
    private final double deltaEarthOrbitRadius = 0.60;
    private final double deltaMoonOrbitAngle = 2.0;
    private final double deltaEarthOrbitAngle = 0.5;

    private double moonOrbitAngle, earthOrbitAngle;
    private double moonOrbitRadius, earthOrbitRadius;
    private double orbitMoonX, orbitMoonY, orbitEarthX, orbitEarthY;
    
    public RocketMovement(Earth earth, Moon moon){
        super(earth, moon);
        setValues();
    }

    public void launchFlight(){
        double dx;
        double dy;
        double distance;
        
        switch (currentPhase){
            case ON_EARTH:
                setValues();
                currentPhase = Phase.LAUNCHING_TO_MOON_ORBIT;
                break;
            
            case LAUNCHING_TO_MOON_ORBIT:
                moveTo(orbitMoonX, orbitMoonY, speed, Phase.LANDING_ON_MOON);
                break;
            
            case LANDING_ON_MOON:
                moonOrbitRadius = Math.max(moonOrbitRadius - deltaMoonOrbitRadius, 0);
                moonOrbitAngle += Math.toRadians(deltaMoonOrbitAngle);
                orbitTo(moonSiteX, moonSiteY, moonOrbitRadius, moonOrbitAngle, Phase.ON_MOON);
                break;
        }
    }
    
    public void returnFlight(){
        switch (currentPhase){
            case ON_MOON:
                currentPhase = Phase.RETURNING_TO_EARTH_ORBIT;
                break;
            
            case RETURNING_TO_EARTH_ORBIT:
                moveTo(orbitEarthX, orbitEarthY, speed, Phase.LANDING_ON_EARTH);
                break;
                
            case LANDING_ON_EARTH:
                earthOrbitRadius = Math.max(earthOrbitRadius - deltaEarthOrbitRadius, 0);
                earthOrbitAngle += Math.toRadians(deltaEarthOrbitAngle);
                orbitTo(earthSiteX, earthSiteY, earthOrbitRadius, earthOrbitAngle, Phase.ON_EARTH);
                break;
        }
    }
    
    @Override
    public double getDistance(){
        return 1200.0 * Math.sqrt((double)(earthSiteX - rocketX)*(earthSiteX - rocketX) +  (double)(earthSiteY - rocketY)*(earthSiteY - rocketY));
    }
    
    public void setValues(){
        moonOrbitAngle = initialMoonOrbitAngle;
        earthOrbitAngle = initialEarthOrbitAngle;
        
        moonOrbitRadius = moonRadius + distanceAboveMoonSurface;
        earthOrbitRadius = earthRadius + distanceAboveEarthSurface;
        
        orbitMoonX = moonSiteX + moonOrbitRadius * Math.cos(initialMoonOrbitAngle);
        orbitMoonY = moonSiteY + moonOrbitRadius * Math.sin(initialMoonOrbitAngle);
            
        orbitEarthX = earthSiteX + earthOrbitRadius * Math.cos(initialEarthOrbitAngle);
        orbitEarthY = earthSiteY + earthOrbitRadius * Math.sin(initialEarthOrbitAngle);
    }
}

class ShuttleMovement extends SpacecraftMovement{
    
    // Constants
    private final double speed = 0.8;
    private final double distanceAboveMoonSurface = 20.0;
    private final double initialMoonOrbitAngle = Math.toRadians(180);
    private final double deltaMoonOrbitRadius = 0.15;
    private final double deltaMoonOrbitAngle = 2.0;

    private double moonOrbitAngle;
    private double moonOrbitRadius;
    private double orbitMoonX, orbitMoonY;
    
    public ShuttleMovement(Earth earth, Moon moon){
        super(earth, moon);
        setValues();
    }
    
    public void launchFlight(){
        switch (currentPhase){
            case ON_EARTH:
                setValues();
                currentPhase = Phase.LAUNCHING_TO_MOON_ORBIT;
                break;
            
            case LAUNCHING_TO_MOON_ORBIT:
                moveTo(orbitMoonX, orbitMoonY, speed, Phase.LANDING_ON_MOON);
                break;
            
            case LANDING_ON_MOON:
                moonOrbitRadius = Math.max(moonOrbitRadius - deltaMoonOrbitRadius, 0);
                moonOrbitAngle += Math.toRadians(deltaMoonOrbitAngle);
                orbitTo(moonSiteX, moonSiteY, moonOrbitRadius, moonOrbitAngle, Phase.ON_MOON);
                break;
        }
    }
    
    public void returnFlight(){
        return; // Shuttle does not return to Earth
    }
    
    public void setValues(){
        moonOrbitAngle = initialMoonOrbitAngle;
        moonOrbitRadius = moonRadius + distanceAboveMoonSurface;
        orbitMoonX = moonSiteX + moonOrbitRadius * Math.cos(initialMoonOrbitAngle);
        orbitMoonY = moonSiteY + moonOrbitRadius * Math.sin(initialMoonOrbitAngle);
    }
}

class ProbeMovement extends SpacecraftMovement{
    
    // Constants
    private final double speed = 1.0;
    private final double distanceAboveMoonSurface = 30.0;
    private final double initialMoonOrbitAngle = Math.toRadians(180);
    private final double deltaMoonOrbitAngle = 1.5;
    
    private double moonOrbitAngle;
    private double moonOrbitRadius;
    private double orbitMoonX, orbitMoonY;
    
    public ProbeMovement(Earth earth, Moon moon){
        super(earth, moon);
        setValues();
    }
    
    public void launchFlight(){
        switch (currentPhase){
            case ON_EARTH:
                setValues();
                currentPhase = Phase.LAUNCHING_TO_MOON_ORBIT;
                break;
            
            case LAUNCHING_TO_MOON_ORBIT:
                moveTo(orbitMoonX, orbitMoonY, speed, Phase.IN_CONSTANT_MOON_ORBIT);
                break;
            
            case IN_CONSTANT_MOON_ORBIT:
                moonOrbitAngle += Math.toRadians(deltaMoonOrbitAngle);
                orbitTo(moonSiteX, moonSiteY, moonOrbitRadius, moonOrbitAngle, Phase.IN_CONSTANT_MOON_ORBIT);
                break;
        }
    }
    public void returnFlight(){
        return; // Probe orbits the moon and never returns
    }
    public void setValues(){
        moonOrbitAngle = initialMoonOrbitAngle;
        moonOrbitRadius = moonRadius + distanceAboveMoonSurface;
        orbitMoonX = moonSiteX + moonOrbitRadius * Math.cos(initialMoonOrbitAngle);
        orbitMoonY = moonSiteY + moonOrbitRadius * Math.sin(initialMoonOrbitAngle);
    }
}

class LanderMovement extends SpacecraftMovement{
    
    // Constants
    private final double speed = 0.5;
    private final double distanceAboveMoonSurface = 30.0;
    private final double initialMoonOrbitAngle = Math.toRadians(180);
    private final double deltaMoonOrbitRadius = 0.15;
    private final double deltaMoonOrbitAngle = 1.5;
    
    private double moonOrbitAngle;
    private double moonOrbitRadius;
    private double orbitMoonX, orbitMoonY;
    
    public LanderMovement(Earth earth, Moon moon){
        super(earth, moon);
        setValues();
    }
    
    public void launchFlight(){
        switch (currentPhase){
            case ON_EARTH:
                setValues();
                currentPhase = Phase.LAUNCHING_TO_MOON_ORBIT;
                break;
            
            case LAUNCHING_TO_MOON_ORBIT:
                moveTo(orbitMoonX, orbitMoonY, speed, Phase.LANDING_ON_MOON);
                break;
            
            case LANDING_ON_MOON:
                moonOrbitRadius = Math.max(moonOrbitRadius - deltaMoonOrbitRadius, 0);
                moonOrbitAngle += Math.toRadians(deltaMoonOrbitAngle);
                orbitTo(moonSiteX, moonSiteY, moonOrbitRadius, moonOrbitAngle, Phase.ON_MOON);
                break;
        }
    }
    public void returnFlight(){
        return; // Lander doesn't return to Earth
    }
    public void setValues(){
        moonOrbitAngle = initialMoonOrbitAngle;
        moonOrbitRadius = moonRadius + distanceAboveMoonSurface;
        orbitMoonX = moonSiteX + moonOrbitRadius * Math.cos(initialMoonOrbitAngle);
        orbitMoonY = moonSiteY + moonOrbitRadius * Math.sin(initialMoonOrbitAngle);
    }
}