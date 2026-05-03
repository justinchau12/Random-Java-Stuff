import javax.swing.*;
import java.awt.*;
import javax.swing.JComponent;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Drawing extends JPanel implements ActionListener{
    private int fw;
    private int fh;
    private ArrayList<Dot> dlist = new ArrayList<Dot>();
    private Timer timer;
    
    public Drawing(int fw, int fh){
        this.fw = fw;
        this.fh = fh;
        this.timer = new Timer(40, this);
        this.timer.start();
    }
    
    public void addDot(){
        Dot d = new Dot(fw, fh);
        dlist.add(d);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < dlist.size(); i++){
            g.setColor(dlist.get(i).getColor());
            g.fillOval(dlist.get(i).getX(), dlist.get(i).getY(), dlist.get(i).getRadius(), dlist.get(i).getRadius());
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < dlist.size(); i++){

        dlist.get(i).setX(dlist.get(i).getX() + dlist.get(i).getDX());
        dlist.get(i).setY(dlist.get(i).getY() + dlist.get(i).getDY());

        if (dlist.get(i).getX() < 0 || dlist.get(i).getX() > fw - dlist.get(i).getRadius()) dlist.get(i).setDX(dlist.get(i).getDX() * -1);
        if (dlist.get(i).getY() < 0 || dlist.get(i).getY() > fh - dlist.get(i).getRadius()) dlist.get(i).setDY(dlist.get(i).getDY() * -1);
        }
        repaint();
    }
}

class Dot{
    private int radius = 40;
    private int x;
    private int y;
    private int dx = (int)(Math.random()*2+2);
    private int dy = (int)(Math.random()*2+2);
    private int R = (int)(Math.random()*256);
    private int G = (int)(Math.random()*256);
    private int B = (int)(Math.random()*256);
    private Color color = new Color(R, G, B);
    
    public Dot(int fw, int fh){
        this.x = ThreadLocalRandom.current().nextInt(this.radius, (fw-this.radius) + 1);
        this.y = ThreadLocalRandom.current().nextInt(this.radius, (fh-this.radius) + 1);
    }
    
    public Color getColor(){
        return color;
    }
    
    public int getX(){
        return x;
    }
    
    public void setX(int newX){
        x = newX;
        return;
    }
    
    public int getY(){
        return y;
    }
    
    public void setY(int newY){
        y = newY;
        return;
    }
    
    public int getDX(){
        return dx;
    }
    
    public void setDX(int newDX){
        dx = newDX;
        return;
    }
    
    public int getDY(){
        return dy;
    }
    
    public void setDY(int newDY){
        dy = newDY;
        return;
    }
    
    public int getRadius(){
        return radius;
    }
}