import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame
{
    public GUI(){
        super();
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Drawing d = new Drawing(getWidth(), getHeight());
        for (int i = 0; i < 8; i++){
            d.addDot();
        }
        getContentPane().add(d);
        setVisible(true);
    }
}