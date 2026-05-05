import javax.swing.*;
import java.awt.*;
import java.awt.BasicStroke;

public class UnitCircle extends JPanel{
    
    // Constant
    private double circleRadius = 125;
    private double pointRadius = 10;
    private double angle = 0;
    private int arcRadius = 20;
    float[] dash = new float[]{4.0f, 4.0f};
    BasicStroke solidStroke = new BasicStroke(1.0f);
    BasicStroke dashStroke = new BasicStroke(
        1.0f,
        BasicStroke.CAP_BUTT,
        BasicStroke.JOIN_BEVEL,
        0.0f,
        dash, 0);

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
        Graphics2D g2d = (Graphics2D) g;
        
        centerX = getWidth() / 2.0;
        centerY = getHeight() / 2.0;
        
        // Unit circle
        g.setColor(Color.BLACK);
        g.drawOval((int)(centerX - circleRadius), (int)(centerY - circleRadius), (int)(circleRadius * 2.0), (int)(circleRadius * 2.0));
        
        pointX = centerX + Math.cos(angle) * circleRadius;
        pointY = centerY - Math.sin(angle) * circleRadius;
        
        // Origin
        g.setColor(Color.BLACK);
        g.fillOval((int)(centerX - pointRadius), (int)(centerY - pointRadius), (int)(pointRadius * 2.0), (int)(pointRadius * 2.0));
        
        // Point on circumference
        g.setColor(Color.BLUE);
        g.fillOval((int)(pointX - pointRadius), (int)(pointY - pointRadius), (int)(pointRadius * 2.0), (int)(pointRadius * 2.0));
        
        // Line from origin to point on circumference
        g.setColor(Color.BLACK);
        g.drawLine((int)(centerX), (int)(centerY), (int)(pointX), (int)(pointY));
        
        // Line from origin to 0 rad point
        g2d.setStroke(dashStroke);
        g.setColor(Color.BLACK);
        g.drawLine((int)(centerX), (int)(centerY), (int)(centerX + circleRadius), (int)(centerY));
        g2d.setStroke(solidStroke);
        
        // Angle arc 
        g.setColor(Color.RED);
        int deg = (int) Math.toDegrees(angle) % 360;
        g.drawArc((int)(centerX - pointRadius - arcRadius), (int)(centerY - pointRadius - arcRadius), (int)((pointRadius + arcRadius) * 2), (int)((pointRadius + arcRadius) * 2), 0, deg);
        
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