import javax.swing.*;
import java.awt.*;

public abstract class SpacecraftRender{
    public abstract void render(Graphics2D g2d, int centX, int centY);
}

class RocketRender extends SpacecraftRender{
    // Constants
    private final int rocketBodyWidth = 30;
    private final int rocketBodyHeight = 50;
    private final int arcLength = 15;
    private final int payLoadHeight = 20;
    private final int cupolaRadius = 10;
    private final int engineHeight = 5;
    
    public void render(Graphics2D g2d, int rocketCentX, int rocketCentY){
        // Body
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(rocketCentX - rocketBodyWidth / 2, rocketCentY - rocketBodyHeight / 2, rocketBodyWidth, rocketBodyHeight, arcLength, arcLength);
        
        // Payload
        g2d.setColor(Color.RED);
        int[] payloadPointXs = {rocketCentX, rocketCentX - rocketBodyWidth / 2, rocketCentX + rocketBodyWidth / 2};
        int[] payloadPointYs = {rocketCentY - rocketBodyHeight / 2 - payLoadHeight, rocketCentY  - rocketBodyHeight / 2, rocketCentY - rocketBodyHeight / 2};
        g2d.fillPolygon(payloadPointXs, payloadPointYs, 3); // top left right
        
        // Cupola
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawOval(rocketCentX - cupolaRadius, rocketCentY - cupolaRadius, cupolaRadius * 2, cupolaRadius * 2);
        g2d.setColor(Color.CYAN);
        g2d.fillOval(rocketCentX - cupolaRadius, rocketCentY - cupolaRadius, cupolaRadius * 2, cupolaRadius * 2);
        
        // Engine
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(rocketCentX - rocketBodyWidth / 2, rocketCentY + rocketBodyHeight / 2, rocketBodyWidth, engineHeight);
    }
}

class ShuttleRender extends SpacecraftRender{
    
    private final int overlap = 1;
    private final int shuttleBodyWidth = 20;
    private final int shuttleUpperBodyHeight = 20;
    private final int shuttleLowerBodyHeight = 15;
    private final int payLoadHeight = 15;
    private final int cupolaRadius = 5;
    private final int engineHeight = 3;
    private final int wingInnerLength = 25;
    private final int wingOuterLength = 10;
    private final int wingWidth = 10;
    
    public void render(Graphics2D g2d, int shuttleCentX, int shuttleCentY){
        
        // Body
        g2d.setColor(Color.WHITE);
        g2d.fillRect(shuttleCentX - shuttleBodyWidth / 2, shuttleCentY - shuttleUpperBodyHeight, shuttleBodyWidth, shuttleUpperBodyHeight);
        g2d.setColor(Color.RED);
        g2d.fillRect(shuttleCentX - shuttleBodyWidth / 2, shuttleCentY, shuttleBodyWidth, shuttleLowerBodyHeight);
        
        // Payload
        g2d.setColor(Color.WHITE);
        int[] payloadPointXs = {shuttleCentX, shuttleCentX - shuttleBodyWidth / 2, shuttleCentX + shuttleBodyWidth / 2};
        int[] payloadPointYs = {shuttleCentY - shuttleUpperBodyHeight - payLoadHeight, shuttleCentY - shuttleUpperBodyHeight + overlap, shuttleCentY - shuttleUpperBodyHeight + overlap};
        g2d.fillPolygon(payloadPointXs, payloadPointYs, 3); // top left right
        
        // Cupola
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawOval(shuttleCentX - cupolaRadius , shuttleCentY - cupolaRadius - shuttleUpperBodyHeight / 2, cupolaRadius * 2, cupolaRadius * 2);
        g2d.setColor(Color.CYAN);
        g2d.fillOval(shuttleCentX - cupolaRadius, shuttleCentY - cupolaRadius - shuttleUpperBodyHeight / 2 , cupolaRadius * 2, cupolaRadius * 2);
        
        // Engine
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(shuttleCentX - shuttleBodyWidth / 2, shuttleCentY + shuttleLowerBodyHeight, shuttleBodyWidth, engineHeight);
        
        // Left wing - top left, bottom left, bottom right, top right
        g2d.setColor(new Color(38, 133, 250));
        int[] wingPointXs = new int[] {shuttleCentX - shuttleBodyWidth / 2 - wingWidth, shuttleCentX - shuttleBodyWidth / 2 - wingWidth, shuttleCentX - shuttleBodyWidth / 2, shuttleCentX - shuttleBodyWidth / 2};
        int[] wingPointYs = new int[] {shuttleCentY - wingOuterLength / 2, shuttleCentY + wingOuterLength / 2, shuttleCentY + wingInnerLength / 2, shuttleCentY - wingInnerLength / 2};
        g2d.fillPolygon(wingPointXs, wingPointYs, 4);
        
        // Right wing - top left, bottom left, bottom right, top right
        g2d.setColor(new Color(38, 133, 250));
        wingPointXs = new int[] {shuttleCentX + shuttleBodyWidth / 2, shuttleCentX + shuttleBodyWidth / 2, shuttleCentX + shuttleBodyWidth / 2 + wingWidth, shuttleCentX + shuttleBodyWidth / 2 + wingWidth};
        wingPointYs = new int[] {shuttleCentY - wingInnerLength / 2, shuttleCentY + wingInnerLength / 2, shuttleCentY + wingOuterLength / 2, shuttleCentY - wingOuterLength / 2};
        g2d.fillPolygon(wingPointXs, wingPointYs, 4);
        
    }
}

class ProbeRender extends SpacecraftRender{
    
    private final int probeWidth = 12;
    private final int probeHeight = 12;
    private final int probeRadius = 16; // Slight bigger than width and height to connect with the antennas
    private final int antennaWidth = 8;
    private final int antennaHeight = 2;
    
    public void render(Graphics2D g2d, int probeCentX, int probeCentY){
        
        // Antennas - top left, bottom left, top right, bottom right
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(probeCentX - probeWidth / 2 - antennaWidth, probeCentY - probeHeight / 2, antennaWidth, antennaHeight);
        g2d.fillRect(probeCentX - probeWidth / 2 - antennaWidth, probeCentY + probeHeight / 2 - antennaHeight, antennaWidth, antennaHeight);
        g2d.fillRect(probeCentX + probeWidth / 2, probeCentY - probeHeight / 2, antennaWidth, antennaHeight);
        g2d.fillRect(probeCentX + probeWidth / 2, probeCentY + probeHeight / 2 - antennaHeight, antennaWidth, antennaHeight);
        
        // Body
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(probeCentX - probeRadius / 2, probeCentY - probeRadius / 2, probeRadius, probeRadius);
    }
}

class LanderRender extends SpacecraftRender{
    
    private final int upperBodyHeight = 10;
    private final int upperBodyWidth = 10;
    private final int upperBodyRadius = 10;
    private final int lowerBodyHeight = 6;
    private final int lowerBodyWidth = 16;
    private final int legExtensionWidth = 6;
    private final int legExtensionHeight = 2;
    private final int legWidth = 2;
    private final int legHeight = 10;
    private final int feetWidth = 6;
    private final int feetHeight = 2;
    
    public void render(Graphics2D g2d, int landerCentX, int landerCentY){
        
        // Upper body
        g2d.setColor(new Color(92, 101, 112));
        g2d.fillOval(landerCentX - upperBodyWidth / 2, landerCentY - lowerBodyHeight / 2 - upperBodyHeight / 2, upperBodyRadius, upperBodyRadius);
        
        // Lower body
        g2d.setColor(new Color(63, 106, 235));
        g2d.fillRect(landerCentX - lowerBodyWidth / 2, landerCentY - lowerBodyHeight / 2, lowerBodyWidth, lowerBodyHeight);
        
        // LR leg extension
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(landerCentX - lowerBodyWidth / 2 - legExtensionWidth, landerCentY - lowerBodyHeight / 2 + legExtensionHeight, legExtensionWidth, legExtensionHeight);
        g2d.fillRect(landerCentX + lowerBodyWidth / 2, landerCentY - lowerBodyHeight / 2 + legExtensionHeight, legExtensionWidth, legExtensionHeight);
        
        // Legs - left, middle, right
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(landerCentX - lowerBodyWidth/ 2 - legExtensionWidth, landerCentY - lowerBodyHeight / 2 + legExtensionHeight, legWidth, legHeight);
        g2d.fillRect(landerCentX - legWidth / 2, landerCentY - lowerBodyHeight / 2 + legExtensionHeight, legWidth, legHeight);
        g2d.fillRect(landerCentX + lowerBodyWidth / 2 + legExtensionWidth - legWidth, landerCentY - lowerBodyHeight / 2 + legExtensionHeight, legWidth, legHeight);
        
        // Feet - left, middle, right
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(landerCentX - lowerBodyWidth / 2 - legExtensionWidth - (feetWidth - legWidth) / 2, landerCentY - lowerBodyHeight / 2 + legExtensionHeight + legHeight, feetWidth, feetHeight);
        g2d.fillRect(landerCentX - legWidth / 2 - (feetWidth - legWidth) / 2, landerCentY - lowerBodyHeight / 2 + legExtensionHeight + legHeight, feetWidth, feetHeight);
        g2d.fillRect(landerCentX + lowerBodyWidth / 2 + legExtensionWidth - legWidth - (feetWidth - legWidth) / 2, landerCentY - lowerBodyHeight / 2 + legExtensionHeight + legHeight, feetWidth, feetHeight);
    }
}