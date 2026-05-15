import java.awt.*;
import java.util.*;
import java.awt.geom.*;

public class StarField implements Drawable
{
            
    private static class Star {
        int x, y, size;
        float brightness;

        Star(int x, int y, int size, float brightness) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.brightness = brightness;
        }
    }
    
    // Constants
    private int starCount = 100;
    private int maxStarSize = 3;
    
    private final ArrayList<Star> stars = new ArrayList<>();
    
    public StarField(int frameWidth, int frameHeight)
    {
        Random rand = new Random();

        for (int i = 0; i < starCount; i++) {
            int x = rand.nextInt(frameWidth);
            int y = rand.nextInt(frameHeight);
            int size = rand.nextInt(maxStarSize) + 1;
            float brightness = rand.nextFloat() * 0.5f + 0.5f;

            stars.add(new Star(x, y, size, brightness));
        }
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        for (Star s : stars) {
            g2d.setColor(new Color(1f, 1f, 1f, s.brightness));
            g2d.fillOval(s.x, s.y, s.size, s.size);
        }
    }
}