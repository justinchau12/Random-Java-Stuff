import javax.swing.*;
import java.awt.*;
import javax.swing.JComponent;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Drawing extends JPanel implements ActionListener{
    
    // Constants
    private int dotRadius = 30;
    
    private int frameWidth, frameHeight;
    private double[] pointsX, pointsY;
    private int[] dx, dy;
    private Color[] color;
    private Timer timer;
    private int count;
    
    public Drawing(int frameWidth, int frameHeight, int count){
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.count = count;
        pointsX = new double[count];
        pointsY = new double[count];
        dx = new int[count];
        dy = new int[count];
        color = new Color[count];
        
        for (int i = 0; i < count; i++){
            pointsX[i] = ThreadLocalRandom.current().nextInt(dotRadius, (frameWidth - dotRadius * 2));
            pointsY[i] = ThreadLocalRandom.current().nextInt(dotRadius, (frameHeight - dotRadius * 2));
            dx[i] = (int)(Math.random()*2+2);
            dy[i] = (int)(Math.random()*2+2);
            color[i] = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
        }
        this.timer = new Timer(40, this);
        this.timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < count; i++){
            g.setColor(color[i]);
            g.fillOval((int)(pointsX[i]), (int)(pointsY[i]), (int)(dotRadius * 2.0), (int)(dotRadius * 2.0));
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < count; i++){
        pointsX[i] += dx[i];
        pointsY[i] += dy[i];

        if (pointsX[i] < 0 || pointsX[i] + dotRadius * 2.0 > frameWidth) dx[i] *= -1;
        if (pointsY[i] < 0 || pointsY[i] + dotRadius * 2.0> frameHeight) dy[i] *= -1;
        }
        repaint();
    }
}