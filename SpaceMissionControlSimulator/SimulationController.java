import java.util.ArrayList;

public final class SimulationController
{
    private int frameWidth;
    private int frameHeight;
    private SpacePanel spacePanel;
    private StarField stars;
    private Earth earth;
    private Moon moon;
    private Spacecraft currentCraft;
    private ArrayList<Spacecraft> crafts = new ArrayList<Spacecraft>();
    
    public SpacePanel initializeSpacePanel(int frameWidth, int frameHeight){
        
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        
        spacePanel = new SpacePanel(frameWidth, frameHeight);
        stars = new StarField(frameWidth, frameHeight);
        earth = new Earth(frameWidth, frameHeight);
        moon = new Moon(frameWidth, frameHeight);
        
        spacePanel.addObject(stars);
        spacePanel.addObject(earth);
        spacePanel.addObject(moon);
        
        return spacePanel;
    }
    
    public void createNewSpacecraft(SpacecraftModel model){
        Spacecraft newCraft = model.create(frameWidth, frameHeight, earth, moon);
        for (Spacecraft craft : crafts){
            if (!(newCraft.getClass() == craft.getClass())) continue; // Check if user picked a spacecraft that already exist on screen
            if (craft.canChangeSpacecraft()){
                spacePanel.removeObject(craft); // Remove the existing identical spacecraft from getting drawn
                crafts.remove(craft); // Remove the existing identical spacecraft from the list of spacecrafts on screen
                break;
            } else{
                return; // The existing spacecraft cannot be switched
            }
        }
        currentCraft = newCraft; // Adding a new spacecraft to be drawn
        crafts.add(currentCraft); // Adding a new spacecraft to the list of spacecrafts on screen
        spacePanel.addObject(currentCraft);
    }
    
    public boolean shouldDisplayDistance(){
        for (Spacecraft craft : crafts){
            if (craft instanceof Rocket){
                return true;
            }
        }
        return false;
    }
    
    public void launchSpacecraft(){
      for (Spacecraft craft : crafts){
            if (craft.onEarth()){
                craft.setCommand(SpacecraftCommand.LAUNCH);
            }
        }
    }
    
    public void returnSpacecraft(){
        for (Spacecraft craft : crafts){
            if (craft instanceof Rocket && craft.onMoon()){
                craft.setCommand(SpacecraftCommand.RETURN);
            }
        }
    }
    
    public double getDistance(){
        for (Spacecraft craft : crafts){
            if (craft instanceof Rocket){
                return craft.getDistance();
            }
        }
        return -1.0;
    }
}