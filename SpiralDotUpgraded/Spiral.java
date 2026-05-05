import javax.swing.*;
import java.awt.*;
import javax.swing.JComponent;
import java.awt.event.*;

public class Spiral extends JPanel implements ActionListener{
    
    // Constants
    private double initialSpiralRadiusValue = 120; // Cannot be zero, dot will approach 0 but never actually reach 0
    private double initialAngleValue = -1; // Any negative numbers -> Math.random() * Math.PI * 2;
    private int initialAlphaValue = 255; // 0 - 255
    
    private double deltaAngle = 0.2; // Positive -> clockwise, negative -> anticlockwise, 0 -> no spin
    private double deltaRadius = -0.5; // Positive -> outwards, negative -> inwards, 0 -> no change
    private int deltaAlpha = -1; // Positive -> more opaque, negative -> less opaque, 0 -> no change
    private int timerMS = 16; // Frame delay
    private double boundary = initialSpiralRadiusValue / 2.0; // radius < boundary -> slower spin, radius > boundary -> faster spin
    private Color color = new Color(173, 216, 230);
    private double particleRadius = 15.0;

    private double centerX, centerY, spiralRadius, angle, pointX, pointY;
    private float alpha;
    private Timer timer;
    
    public Spiral(int frameWidth, int frameHeight){
        centerX = frameWidth / 2.0;
        centerY = frameHeight / 2.0;
        timer = new Timer(timerMS, this);
        timer.start();

        spiralRadius = initialSpiralRadiusValue;
        angle =  (initialAngleValue >= 0) ? initialAngleValue: Math.random() * 2.0 * Math.PI;
        alpha = initialAlphaValue;
        
        setOpaque(false); // imporant or else the background would be white
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255f));
        g2.fillOval((int)(pointX - particleRadius), (int)(pointY - particleRadius), (int)(particleRadius * 2.0), (int)(particleRadius * 2.0));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        pointX = centerX + Math.cos(angle) * spiralRadius;
        pointY = centerY + Math.sin(angle) * spiralRadius;
        angle %= (2 * Math.PI);
        double speedFactor = Math.max(spiralRadius, 0.0001); // Prevents divide by 0
        angle += deltaAngle * (boundary / speedFactor); // As spiralRadius(speedFactor) approaches boundary and becomes smaller, rotation becomes faster
            
        // Prevents overflow
        if ((spiralRadius > 0 && deltaRadius <= 0) || (spiralRadius < Double.MAX_VALUE && deltaRadius >= 0)) spiralRadius += deltaRadius;
        if (0 <= alpha + deltaAlpha && alpha + deltaAlpha <= 255) alpha += deltaAlpha;
        repaint();
    }
}