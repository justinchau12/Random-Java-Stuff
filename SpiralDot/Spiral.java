import javax.swing.*;
import java.awt.*;
import javax.swing.JComponent;
import java.awt.event.*;

public class Spiral extends JPanel implements ActionListener{
    
    // Constant
    private double angle = 0;
    private double radius = 0;
    private double deltaAngle = 0.05;
    private double deltaRadius = 0.05;
    private int timerMS = 10;
    
    private double fw;
    private double fh;
    private Dot d;
    private Timer timer;
    
    public Spiral(int fw, int fh){
        this.fw = fw;
        this.fh = fh;
        this.timer = new Timer(timerMS, this);
        this.timer.start();
        d = new Dot(fw, fh);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval((int)d.getX(), (int)d.getY(), (int)d.getRadius(), (int)d.getRadius());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        d.setX((fw - d.getRadius())/2.0 + Math.cos(angle)*radius);
        d.setY((fh - d.getRadius())/2.0 + Math.sin(angle)*radius);
        angle %= (2 * Math.PI);
        angle += deltaAngle;
        radius += deltaRadius;
        
        repaint();
    }
}

class Dot{
    // Constant
    private double radius = 30.0;
    
    private double x;
    private double y;
    
    public Dot(int fw, int fh){
        this.x = fw/2.0;
        this.y = fh/2.0;
    }
    
    public double getX(){
        return x;
    }
    
    public void setX(double newX){
        x = newX;
        return;
    }
    
    public double getY(){
        return y;
    }
    
    public void setY(double newY){
        y = newY;
        return;
    }
    
    public double getRadius(){
        return radius;
    }
}