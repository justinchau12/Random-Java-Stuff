import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class GUI extends JFrame
{
    public GUI(){
        super();
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        getContentPane().add(new Spiral(getWidth(), getHeight(), 20));
        setVisible(true);
    }
}