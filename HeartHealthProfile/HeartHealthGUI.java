import javax.swing.*;
import java.awt.*;
import java.awt.event.*; // Somehow need this, do not remove

public class HeartHealthGUI extends JFrame {
    
    private HeartHealthProfile guest;
    private JPanel pane;
    private JPanel leftPane;
    private JPanel rightPane;
    
    private JLabel title;
    
    private static final String[] labels = {"Name", "Age","Weight (kilogram)", "Height (meter)"};
    private JTextField[] fields;

    private JTextArea outputField;
    private JButton calculateButton;
    private JButton clearButton;
    private JButton exitButton;
    
    public HeartHealthGUI(){
        super("Heart Health Profile");
        setSize(600, 400);
        addComponentListener(new ComponentAdapter(){
            public void componentResized(ComponentEvent e){
                pack();
            }
        });
        createAndShowGUI();
    }
    
    private JPanel createLeftPane(){
        JPanel panel = new JPanel();
        panel.setBackground(new Color(206, 236, 250));
        
        fields = new JTextField[labels.length];
        panel.setLayout(new GridLayout(0, 2, 5, 5));
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new JTextField(10);
            panel.add(new JLabel(labels[i]));
            panel.add(fields[i]);
        }
        
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Input"));

        return panel;
    }
    
    private JPanel createRightPane(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(87, 191, 91));
        
        outputField = new JTextArea(10, 20);
        outputField.setEditable(false);
        outputField.setFont(new java.awt.Font("Times New Roman", Font.PLAIN, 14));
        outputField.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(outputField);
        
        calculateButton = new JButton("Calculate");
        calculateButton.setBackground(Color.YELLOW);
        calculateButton.setForeground(Color.BLUE);
        calculateButton.setFont(new java.awt.Font("Monospaced", Font.BOLD, 14));
        calculateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        calculateButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                for (JTextField field : fields){
                    if (field.getText().trim().isEmpty()){
                        outputField.setText("Error: please fill out all the cells.");
                        pack();
                        return;
                    }
                }
                try {
                    guest = new HeartHealthProfile(fields[0].getText(), Integer.valueOf(fields[1].getText()), Double.valueOf(fields[2].getText()), Double.valueOf(fields[3].getText()));
                } catch (NumberFormatException ex){
                    outputField.setText("Error: Age, weight, and height must be a number.");
                        pack();
                        return;
                }
                outputField.setText(guest.returnReport());
                pack();
            }
        });
        
        clearButton = new JButton("Clear");
        clearButton.setBackground(Color.LIGHT_GRAY);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new java.awt.Font("Monospaced", Font.BOLD, 14));
        clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                for (JTextField field : fields){
                    field.setText("");
                }
                outputField.setText("");
                pack();
            }
        });
        
        exitButton = new JButton("Exit");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.BLACK);
        exitButton.setFont(new java.awt.Font("Monospaced", Font.BOLD, 14));
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                System.exit(0);
            }
        });
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(scrollPane, gbc);
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(calculateButton, gbc);
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(clearButton, gbc);
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(exitButton, gbc);
        
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(87, 99, 91)), "Results"));

        return panel;

    }
    
    private JLabel createTitle(){
        JLabel label = new JLabel("Heart Health Calculator");
        label.setFont(new Font("Monospaced", Font.BOLD, 26));
        label.setForeground(Color.WHITE);
        return label;
    }
    
    private void createAndShowGUI(){
        pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        pane.setBackground(new Color(15, 128, 228));
        GridBagConstraints gbc = new GridBagConstraints();
        title = createTitle();
        leftPane = createLeftPane();
        rightPane = createRightPane();
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        pane.add(title, gbc);
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        pane.add(leftPane, gbc);
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        pane.add(rightPane, gbc);
        
        add(pane);
        pack();
        setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Probably dont need this but keep it just in case
    }
}