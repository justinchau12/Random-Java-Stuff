import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame
{
    public GUI(){
        super();
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(new Spiral(getWidth(), getHeight()));
        setVisible(true);
    }
}