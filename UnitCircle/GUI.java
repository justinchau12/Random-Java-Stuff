import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame{
    
    private UnitCircle uc;
    private JPanel pane;
    private JLabel coordLabel;
    private int x;
    private int y;
    
    public GUI(){
        super();
        setSize(350, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        pane = new JPanel(new BorderLayout());
        uc = new UnitCircle();
        
        JComboBox<String> angleSelection = new JComboBox<String>();
        for (Angle angle : Angle.values()){
            angleSelection.addItem(angle.getDescription());
        }
        angleSelection.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                uc.angleSelected(angleSelection.getSelectedItem().toString());
                coordLabel.setText("Coordinates: (" + uc.getcoordX() + " , " + uc.getcoordY() + ")");
            }
        });
        
        coordLabel = new JLabel("Coordinates: ", SwingConstants.CENTER);
        uc.angleSelected(angleSelection.getSelectedItem().toString());
        coordLabel.setText("Coordinates: (" + uc.getcoordX() + " , " + uc.getcoordY() + ")");
        
        pane.add(angleSelection, BorderLayout.NORTH);
        pane.add(uc, BorderLayout.CENTER);
        pane.add(coordLabel, BorderLayout.SOUTH);
        
        add(pane);
        setVisible(true);
    }
}