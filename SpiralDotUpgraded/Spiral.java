import javax.swing.*;
import java.awt.*;
import javax.swing.JComponent;
import java.awt.event.*;

public class Spiral extends JPanel implements ActionListener{
    
    // Constants
    private double initialAngleValue = -1; // Any negative numbers -> Math.random() * Math.PI * 2;
    private int initialAlphaValue = 255; // 0 - 255
    private double initialRadiusValue = 150; // Cannot be zero, dot will approach 0 but never actually reach 0
    private double particleRadius = 15.0;
    private double deltaAngle = 0.5; // Positive -> clockwise, negative -> anticlockwise, 0 -> no spin
    private double deltaRadius = -0.5; // Positive -> outwards, negative -> inwards, 0 -> no change
    private int deltaAlpha = -1; // Positive -> more opaque, negative -> less opaque, 0 -> no change
    private int timerMS = 30; // Cannot be 0 or else no animation plays
    private double boundary = initialRadiusValue / 2.0; // radius < boundary -> slower spin, radius > boundary -> faster spin
    private Color color = new Color(173, 216, 230);

    private double frameWidth;
    private double frameHeight;
    private double centerX;
    private double centerY;
    private Timer timer;
    private double[] x, y, radius, angle;
    private float[] alpha;
    private int count; 
    
    public Spiral(int frameWidth, int frameHeight, int count){
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.count = count;
        centerX = frameWidth / 2.0;
        centerY = frameHeight / 2.0;
        timer = new Timer(timerMS, this);
        timer.start();
        
        x = new double[count];
        y = new double[count];
        radius = new double[count];
        angle = new double[count];
        alpha = new float[count];
        for (int i = 0; i < count; i++) {
            radius[i] = initialRadiusValue;
            angle[i] = (initialAngleValue >= 0) ? initialAngleValue: Math.random() * 2.0 * Math.PI;
            alpha[i] = initialAlphaValue;
        }
        setOpaque(false); // imporant or else the background would be white
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < count; i++){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha[i] / 255f));
            g2.setColor(color);
            g2.fillOval((int)(x[i] - particleRadius), (int)(y[i] - particleRadius), (int)(particleRadius * 2.0), (int)(particleRadius * 2.0));
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < count; i++){
            x[i] = centerX + Math.cos(angle[i]) * radius[i];
            y[i] = centerY + Math.sin(angle[i]) * radius[i];
            angle[i] %= (2 * Math.PI);
            double speedFactor = Math.max(radius[i], 0.0001); // Prevents divide by 0
            angle[i] += deltaAngle * (boundary / speedFactor); // As radius(speedFactor) approaches boundary and becomes smaller, rotation becomes faster
            
            // Prevents overflow
            if ((radius[i] > 0 && deltaRadius <= 0) || (radius[i] < Double.MAX_VALUE && deltaRadius >= 0)) radius[i] += deltaRadius;
            if (0 <= alpha[i] + deltaAlpha && alpha[i] + deltaAlpha <= 255) alpha[i] += deltaAlpha;
        }
        repaint();
    }
}