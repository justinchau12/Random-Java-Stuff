import javax.swing.*;
import java.awt.*;
import javax.swing.JComponent;
import java.awt.event.*;

public class Spiral extends JPanel implements ActionListener{
    
    // Constant
    private double spiralRadius = 0;
    private double angle = 0;
    private double deltaAngle = 0.05;
    private double deltaRadius = 0.05;
    private double particleRadius = 15.0;
    private int timerMS = 16;
    
    private double centerX, centerY, pointX, pointY;
    private Timer timer;
    
    public Spiral(int frameWidth, int frameHeight){
        this.timer = new Timer(timerMS, this);
        this.timer.start();

        centerX = frameWidth / 2.0;
        centerY = frameHeight / 2.0;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval((int)(pointX - particleRadius), (int)(pointY - particleRadius), (int)(particleRadius * 2.0), (int)(particleRadius * 2.0));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        pointX = centerX + Math.cos(angle) * spiralRadius;
        pointY = centerY + Math.sin(angle) * spiralRadius;
        angle %= (2 * Math.PI);
        angle += deltaAngle;
        spiralRadius += deltaRadius;
        
        repaint();
    }
}