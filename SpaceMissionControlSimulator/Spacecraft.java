import javax.swing.*;
import java.awt.*;

public abstract class Spacecraft implements Drawable{
    protected SpacecraftMovement craftMovement;
    protected SpacecraftRender craftRender;
    
    public Spacecraft(SpacecraftMovement craftMovement, SpacecraftRender craftRender){
        this.craftMovement = craftMovement;
        this.craftRender = craftRender;
    }
    
    public void draw(Graphics2D g2d){
        craftRender.render(g2d, (int) craftMovement.getCentX(), (int) craftMovement.getCentY());
    }
    
    public void setCommand(SpacecraftCommand command){
        craftMovement.setCommand(command);
    }
    public void updateMovement(){
        craftMovement.runCommand();
    }
    public SpacecraftCommand getCurrentCommand(){
        return craftMovement.getCurrentCommand();
    }
    public boolean canChangeSpacecraft(){
        return craftMovement.canChangeSpacecraft();
    }
    public boolean onEarth(){
        return craftMovement.onEarth();
    }
    public boolean onMoon(){
        return craftMovement.onMoon();
    }
    public double getDistance(){
        return craftMovement.getDistance();
    }
}

class Rocket extends Spacecraft{
    public Rocket(int panelWidth, int panelHeight, Earth earth, Moon moon, SpacecraftMovement craftMovement){
        super(craftMovement, new RocketRender());
    }
}

class Shuttle extends Spacecraft{
    public Shuttle(int panelWidth, int panelHeight, Earth earth, Moon moon, SpacecraftMovement craftMovement){
        super(craftMovement, new ShuttleRender());
    }
}

class Probe extends Spacecraft{
    public Probe(int panelWidth, int panelHeight, Earth earth, Moon moon, SpacecraftMovement craftMovement){
        super(craftMovement, new ProbeRender());
    }
}

class Lander extends Spacecraft{
    public Lander(int panelWidth, int panelHeight, Earth earth, Moon moon, SpacecraftMovement craftMovement){
        super(craftMovement, new LanderRender());
    }
}