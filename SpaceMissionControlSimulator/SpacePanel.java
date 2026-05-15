    import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SpacePanel extends JPanel implements ActionListener{

    // Constants
    private final int timerMS = 16;
    private final Color universeColor = Color.BLACK;
    
    private Timer t;
    private ArrayList<Drawable> objects;

    public SpacePanel(int frameWidth, int frameHeight) {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        objects = new ArrayList<Drawable>();
        t = new Timer(timerMS, this);
        t.start();
        setBackground(universeColor);
    }

    public void addObject(Drawable obj) {
        objects.add(obj);
    }
    
    public void removeObject(Drawable obj){
        objects.remove(obj);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Drawable obj : objects) {
            obj.draw(g2d);
            if (obj instanceof Spacecraft){
                ((Spacecraft)obj).updateMovement();
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}