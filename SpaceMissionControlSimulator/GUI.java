import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

public class GUI extends JFrame
{
   // JComponents
    private SpacePanel upperPanel;
    private JPanel lowerPanel;
    private JButton[] spacecraftButtons;
    private JLabel distanceLabel;
    private JLabel distanceFromEarthLabel;
    private JButton launchButton;
    private JButton returnButton;

    private final SimulationController sc = new SimulationController();
    
    // Constants
    private final int frameWidth = 600;
    private final int frameheight = 400;
    private final int upperPanelHeight = 300;
    private final int lowerPanelHeight = 100;
    private final int timerMS = 16;
    private final Color buttonColor = new Color(212, 231, 255);
    
    public GUI(){
        super();
        setSize(frameWidth, frameheight);
        setTitle("Space Mission Control Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.NORMAL);
        createAndShowGUI();
    }
    
    private JButton[] initializeSpacecraftButtons(){
        SpacecraftModel[] models = SpacecraftModel.values();

        JButton[] buttons = new JButton[models.length];
        for (int i = 0; i < models.length; i++){
            final SpacecraftModel model = models[i];
            ImageIcon icon = loadIcon(model.getIconPath());
            buttons[i] = new JButton(model.getDisplayName(), icon);
            buttons[i].setFocusPainted(false);
            buttons[i].setBackground(buttonColor);
            buttons[i].addActionListener(e -> {
                sc.createNewSpacecraft(model);
                distanceLabel.setVisible(sc.shouldDisplayDistance());
                distanceFromEarthLabel.setVisible(sc.shouldDisplayDistance());
                repaint();
            });
        }
        
        return buttons;
    }
    
    private ImageIcon loadIcon(String path) {
        Image img = new ImageIcon(path).getImage();
        return new ImageIcon(img.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
    }

    private void createAndShowGUI(){
        JPanel mainPanel = new JPanel();
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.BLACK);
        
        upperPanel = createUpperPanel();
        lowerPanel = createLowerPanel();
        
        upperPanel.setMaximumSize(new Dimension(frameWidth, upperPanelHeight));
        lowerPanel.setMaximumSize(new Dimension(frameWidth, lowerPanelHeight));
        upperPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        lowerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(upperPanel);
        mainPanel.add(lowerPanel);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private SpacePanel createUpperPanel(){
        return sc.initializeSpacePanel(frameWidth, upperPanelHeight);
    }
    
    private JPanel createLowerPanel(){
        JPanel lowerPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        
        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));
        Border b = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.CYAN);
        lowerPanel.setPreferredSize(new Dimension(frameWidth, lowerPanelHeight));
        
        leftPanel.setLayout(new GridLayout(0, 2, 5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder(b, "Spacecraft"));
        rightPanel.setLayout(new GridLayout(0, 2, 5, 5));
        rightPanel.setBorder(BorderFactory.createTitledBorder(b, "Console"));
        
        spacecraftButtons = initializeSpacecraftButtons();
        
        for (int i = 0; i < spacecraftButtons.length; i++){
            leftPanel.add(spacecraftButtons[i]);
        }
        
        distanceLabel = new JLabel("Distance: ", SwingConstants.CENTER);
        distanceFromEarthLabel = new JLabel("", SwingConstants.CENTER);
        distanceFromEarthLabel.setPreferredSize(distanceFromEarthLabel.getPreferredSize()); // Very important, this makes sure the label doesn't expand endlessly with big numbers
        new Timer(timerMS, e ->
            distanceFromEarthLabel.setText(String.format("%,.2f km", sc.getDistance())) 
        ).start();
        distanceLabel.setVisible(sc.shouldDisplayDistance());
        distanceFromEarthLabel.setVisible(sc.shouldDisplayDistance());
        
        launchButton = new JButton("Launch");
        launchButton.setFocusPainted(false);
        launchButton.setBackground(buttonColor);
        launchButton.addActionListener(e -> sc.launchSpacecraft());
        
        returnButton = new JButton("Return");
        returnButton.setFocusPainted(false);
        returnButton.setBackground(buttonColor);
        returnButton.addActionListener(e -> sc.returnSpacecraft());
        
        rightPanel.add(distanceLabel);
        rightPanel.add(distanceFromEarthLabel);
        rightPanel.add(launchButton);
        rightPanel.add(returnButton);
        
        lowerPanel.add(leftPanel);
        lowerPanel.add(rightPanel);
        
        return lowerPanel;
    }
}