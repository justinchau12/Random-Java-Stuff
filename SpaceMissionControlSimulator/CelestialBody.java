import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public abstract class CelestialBody implements Drawable
{
    private int x, y, dx, dy, r;
    private Color color;
    
    public CelestialBody(int x, int y, int dx, int dy, int r, Color color){
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.r = r;
        this.color = color;
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Point2D center = new Point2D.Float(x + r * 2f, y); // This makes the gradient start on the top right corner
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {Color.WHITE, color};
        RadialGradientPaint p = new RadialGradientPaint(center, r * 2, dist, colors);
        
        g2d.setPaint(p);
        g2d.fillOval(x, y, r * 2, r * 2);
    }
        
    public int getRadius(){
        return r;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getCentX(){ // Center of the sphere
        return x + r;
    }
    
    public int getCentY(){
        return y + r;
    }
    
    public int getSiteX(){ // Actual landing site
        return x + r + dx;
    }
    
    public int getSiteY(){
        return y + r + dy;
    }
}

class Earth extends CelestialBody
{
    // Constants
    private static final int earthRadius = 100;
    private static final int earthPosDeltaX = -150;
    private static final int earthPosDeltaY = 120;
    private static final int siteDeltaX = 0;
    private static final int siteDeltaY = -100;
    private static final Color color = Color.BLUE;
    
    public Earth(int panelWidth, int panelHeight){
        super((panelWidth / 2)  - earthRadius + earthPosDeltaX, (panelHeight / 2) - earthRadius + earthPosDeltaY, siteDeltaX, siteDeltaY, earthRadius, color);
    }
}

class Moon extends CelestialBody
 {
    // Constants
    private static final int moonRadius = 25;
    private static final int moonPosDeltaX = 150;
    private static final int moonPosDeltaY = -40;
    private static final int siteDeltaX = 0;
    private static final int siteDeltaY = -15;
    private static final Color color = Color.GRAY;
    
    public Moon(int panelWidth, int panelHeight){
        super((panelWidth / 2) - moonRadius + moonPosDeltaX,  (panelHeight / 2) - moonRadius + moonPosDeltaY, siteDeltaX, siteDeltaY, moonRadius, color);
    }
}