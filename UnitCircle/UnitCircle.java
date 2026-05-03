import javax.swing.*;
import java.awt.*;

public class UnitCircle extends JPanel{
    
    // Constant
    private double circleRadius = 125;
    private double pointRadius = 10;
    private double angle = 0;

    private double centerX;
    private double centerY;
    private double pointX;
    private double pointY;
    
    public UnitCircle(){
        setOpaque(false);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        centerX = getWidth() / 2.0;
        centerY = getHeight() / 2.0;
        
        g.setColor(Color.BLACK);
        g.drawOval((int)(centerX - circleRadius), (int)(centerY - circleRadius), (int)(circleRadius * 2.0), (int)(circleRadius * 2.0));
        
        pointX = centerX + Math.cos(angle) * circleRadius;
        pointY =  centerY - Math.sin(angle) * circleRadius;
        
        g.setColor(Color.BLUE);
        g.fillOval((int)(pointX - pointRadius), (int)(pointY - pointRadius), (int)(pointRadius * 2.0), (int)(pointRadius * 2.0));
    }
    
    public void angleSelected(String selection){
        for (Angle a : Angle.values()) {
            if (selection.equals(a.getDescription())) {
                this.angle = a.getRadians();
                repaint();
                return;
            }
        }
    }
    
    public int getcoordX(){
        return (int) (Math.cos(angle) * circleRadius);
    }
    
    public int getcoordY(){
        return (int) (-1 * Math.sin(angle) * circleRadius); // Flip the Y-Axis to follow Cartesian plane's rule
    }
}