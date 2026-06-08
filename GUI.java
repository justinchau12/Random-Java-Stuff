import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class GUI extends JFrame{
    
    // Constants
    private final int frameWidth = 960;
    private final int frameHeight = 513;
    private final String windowTitle = "Maze Pathfinding Visualizer";
    
    private Maze maze;
    private Solver solver = new Solver();
    
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton generateMazeButton;
    private JButton clearMazeButton;
    private JLabel mazeSizeLabel;
    private JSlider mazeSizeSlider;
    private JLabel solverSpeedLabel;
    private JSlider solverSpeedSlider;
    private JLabel algorithmLabel;
    private JComboBox<Algorithm> algorithmComboBox;
    private Algorithm selectedAlgo;
    private JButton toggleSolveMazeButton;
    private JButton resetMazeButton;
    
    public GUI(){
        super();
        setSize(frameWidth, frameHeight);
        setMinimumSize(new Dimension(frameWidth, frameHeight));
        setTitle(windowTitle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createAndShowGUI();
    }
    
    private void createAndShowGUI(){
        mainPanel = new JPanel();
        
        mainPanel.setBackground(Color.RED);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        
        leftPanel = getLeftPanel();
        rightPanel = getRightPanel();
        
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel getLeftPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(frameWidth / 2, frameHeight)); 
        Border b = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY);
        panel.setBorder(BorderFactory.createTitledBorder(b, "Maze"));
        
        maze = new Maze(frameWidth / 2, frameHeight);
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(maze, c);
        
        return panel;
    }
    
    private JPanel getRightPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(frameWidth / 2, frameHeight));
        Border b = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY);
        panel.setBorder(BorderFactory.createTitledBorder(b, "Console"));
        
        generateMazeButton = new JButton("Generate");
        generateMazeButton.setBackground(Color.YELLOW);
        generateMazeButton.setForeground(Color.BLUE);
        generateMazeButton.setFont(new java.awt.Font("Consolas", Font.BOLD, 14));
        generateMazeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        generateMazeButton.addActionListener(e -> {
            maze.generateMaze();
            setControlsEnabled(true);
        });
        
        clearMazeButton = new JButton("Clear");
        clearMazeButton.setBackground(Color.YELLOW);
        clearMazeButton.setForeground(Color.BLUE);
        clearMazeButton.setFont(new java.awt.Font("Consolas", Font.BOLD, 14));
        clearMazeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearMazeButton.addActionListener(e -> {
            maze.clearMaze();
            setControlsEnabled(true);
        });
        
        mazeSizeLabel = new JLabel("Maze Size: ", SwingConstants.CENTER);
        mazeSizeLabel.setFont(new java.awt.Font("Consolas", Font.BOLD, 18));
        
        mazeSizeSlider = new JSlider(Maze.getMinSideCellCount(), Maze.getMaxSideCellCount(), Maze.getInitSideCellCount());
        mazeSizeSlider.setPaintTicks(true);
        mazeSizeSlider.setMinorTickSpacing(1);
        mazeSizeSlider.setMajorTickSpacing(2);
        mazeSizeSlider.setPaintLabels(true);
        mazeSizeSlider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mazeSizeSlider.addChangeListener(e -> {
            repaint(); // Important, this clears up visual artifacts on sliders
            maze.updateCellCount(mazeSizeSlider.getValue());
        });
        
        solverSpeedLabel = new JLabel("Solver Speed (cell/s): ", SwingConstants.CENTER);
        solverSpeedLabel.setFont(new java.awt.Font("Consolas", Font.BOLD, 18));
        
        solverSpeedSlider = new JSlider(Solver.getMinSolverSpeed(), Solver.getMaxSolverSpeed(), Solver.getInitSolverSpeed());
        solverSpeedSlider.setPaintTicks(true);
        solverSpeedSlider.setMajorTickSpacing(1);
        solverSpeedSlider.setPaintLabels(true);
        solverSpeedSlider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        solverSpeedSlider.addChangeListener(e -> {
            repaint(); // Important, this clears up visual artifacts on sliders
            solver.updateSolverSpeed(solverSpeedSlider.getValue());
        });
        
        algorithmLabel = new JLabel("Algorithm: ", SwingConstants.CENTER);
        algorithmLabel.setFont(new java.awt.Font("Consolas", Font.BOLD, 18));
        
        algorithmComboBox = new JComboBox<>(Algorithm.values());
        selectedAlgo = (Algorithm) algorithmComboBox.getSelectedItem();
        algorithmComboBox.addActionListener(e -> {
            maze.clearMaze();
            selectedAlgo = (Algorithm) algorithmComboBox.getSelectedItem();
        });
        
        toggleSolveMazeButton = new JButton("Start / Pause Solving");
        toggleSolveMazeButton.setBackground(Color.YELLOW);
        toggleSolveMazeButton.setForeground(Color.BLUE);
        toggleSolveMazeButton.setFont(new java.awt.Font("Consolas", Font.BOLD, 14));
        toggleSolveMazeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toggleSolveMazeButton.addActionListener(e -> {
            solver.toggleSolving(maze, selectedAlgo);
            setControlsEnabled(solver.canSetNewInput());
        });
        
        resetMazeButton = new JButton("Reset");
        resetMazeButton.setBackground(Color.YELLOW);
        resetMazeButton.setForeground(Color.BLUE);
        resetMazeButton.setFont(new java.awt.Font("Consolas", Font.BOLD, 14));
        resetMazeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resetMazeButton.addActionListener(e -> {
            maze.resetMaze();
            setControlsEnabled(true);
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(13, 14, 13, 14);
        c.fill = GridBagConstraints.BOTH;
        panel.add(generateMazeButton, c);
        
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(13, 14, 13, 14);
        c.fill = GridBagConstraints.BOTH;
        panel.add(clearMazeButton, c);
        
        
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.BOTH;
        panel.add(mazeSizeLabel, c);
        
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.BOTH;
        panel.add(mazeSizeSlider, c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.BOTH;
        panel.add(solverSpeedLabel, c);
        
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.BOTH;
        panel.add(solverSpeedSlider, c);
        
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.BOTH;
        panel.add(algorithmLabel, c);
        
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.BOTH;
        panel.add(algorithmComboBox, c);
        
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(13, 14, 13, 14);
        c.fill = GridBagConstraints.BOTH;
        panel.add(toggleSolveMazeButton, c);
        
        c.gridx = 1;
        c.gridy = 4;
        c.insets = new Insets(13, 14, 13, 14);
        c.fill = GridBagConstraints.BOTH;
        panel.add(resetMazeButton, c);
        
        return panel;
    }
    
    private void setControlsEnabled(boolean enabled){
        mazeSizeSlider.setEnabled(enabled);
        solverSpeedSlider.setEnabled(enabled);
        algorithmComboBox.setEnabled(enabled);
    }
}