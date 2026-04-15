import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.geom.*;

public class ShapeGui extends JFrame{
    
    private JLabel title;
    private JPanel inputPanel;
    private JPanel outputPanel;
    
    private final static String[] shapes = {"Sphere", "Cube", "Cone", "Cylinder", "Tetrahedron"};
    private final JComboBox shapeSelection = new JComboBox(shapes); 
    private String currentChoice;
    private RGBColorConstants c;
    private String[] labels;
    private JTextField[] fields;
    private JButton calculateButton;
    private JButton colorButton;
    private JButton clearButton;
    
    private JLabel surfaceAreaLabel;
    private JLabel surfaceAreaValue;
    private JLabel volumeLabel;
    private JLabel volumeValue;
    
    public ShapeGui(){
        super("3D Shape Calculator");
        setSize(600, 300);
        setIconImage(new ImageIcon("shapes.png").getImage());
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createAndShowGui();
    }
    
    private void createAndShowGui(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        panel.setBackground(Color.DARK_GRAY);
        
        inputPanel = createInputPanel();
        outputPanel = createOutputPanel();
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(inputPanel, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(outputPanel, c);
        
        add(panel);
        pack();
        setVisible(true);
    }
    
    private JPanel createInputPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(195, 247, 148));
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(shapeSelection, c);
        
        JPanel lowerPanel = new JPanel();
        labels = updateShapeFields();
        lowerPanel.setLayout(new GridLayout(labels.length, 2));
                         
        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { checkFieldsForCalculate(); }
            public void removeUpdate(DocumentEvent e) { checkFieldsForCalculate(); }
            public void changedUpdate(DocumentEvent e) { checkFieldsForCalculate(); }
        };
        
        fields = new JTextField[labels.length];
        for (int i = 0; i < fields.length; i++){
            fields[i] = new JTextField(10);
            lowerPanel.add(new JLabel(labels[i]));
            lowerPanel.add(fields[i]);
            fields[i].getDocument().addDocumentListener(dl);
        }
        
        shapeSelection.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                lowerPanel.removeAll();
                labels = updateShapeFields();
                lowerPanel.setLayout(new GridLayout(labels.length, 2));
                fields = new JTextField[labels.length];
                for (int i = 0; i < fields.length; i++){
                    fields[i] = new JTextField(10);
                    lowerPanel.add(new JLabel(labels[i]));
                    lowerPanel.add(fields[i]);
                    fields[i].getDocument().addDocumentListener(dl);
                }
                checkFieldsForCalculate();
                pack();
            }
        });
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(lowerPanel, c);
        
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN), "Input"));
        
        return panel;
    }

    private JPanel createOutputPanel(){
        JPanel panel = new JPanel();
        BoxLayout b = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(b);

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout(5, 5));
        upperPanel.setMinimumSize(new Dimension(250, 250));
        upperPanel.setPreferredSize(upperPanel.getMinimumSize());
        
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new GridLayout(4, 2, 5, 5));
                
        c = RGBColorConstants.RED;
        calculateButton = new JButton("Calculate");
        calculateButton.setBackground(Color.GREEN);
        calculateButton.setForeground(Color.BLACK);
        calculateButton.setFont(new java.awt.Font("SANS_SERIF", Font.BOLD, 14));
        calculateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        calculateButton.setEnabled(false);
        calculateButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                upperPanel.removeAll();
                String choice = (String) shapeSelection.getSelectedItem();
                currentChoice = choice;
                switch (choice) {
                    case "Sphere":
                        Sphere sphere = new Sphere(Double.valueOf(fields[0].getText()));
                        surfaceAreaValue.setText(String.format("%.2f", sphere.calculateSurfaceArea()));
                        volumeValue.setText(String.format("%.2f", sphere.calculateVolume()));
                        break;
                    case "Cube":
                        Cube cube = new Cube(Double.valueOf(fields[0].getText()));
                        surfaceAreaValue.setText(String.format("%.2f", cube.calculateSurfaceArea()));
                        volumeValue.setText(String.format("%.2f", cube.calculateVolume()));
                        break;
                    case "Cone":
                        Cone cone = new Cone(Double.valueOf(fields[0].getText()), Double.valueOf(fields[1].getText()));
                        surfaceAreaValue.setText(String.format("%.2f", cone.calculateSurfaceArea()));
                        volumeValue.setText(String.format("%.2f", cone.calculateVolume()));
                        break;
                    case "Cylinder":
                        Cylinder cylinder = new Cylinder(Double.valueOf(fields[0].getText()), Double.valueOf(fields[1].getText()));
                        surfaceAreaValue.setText(String.format("%.2f", cylinder.calculateSurfaceArea()));
                        volumeValue.setText(String.format("%.2f", cylinder.calculateVolume()));
                        break;
                    case "Tetrahedron":
                        Tetrahedron tetrahedron = new Tetrahedron(Double.valueOf(fields[0].getText()));
                        surfaceAreaValue.setText(String.format("%.2f", tetrahedron.calculateSurfaceArea()));
                        volumeValue.setText(String.format("%.2f", tetrahedron.calculateVolume()));
                        break;
                }
                upperPanel.add(new DrawShapePanel(upperPanel.getWidth(), upperPanel.getHeight(), choice, c.getRGB()), BorderLayout.CENTER);
                pack();
            }
        });
        
        colorButton = new JButton("Current color");
        colorButton.setBackground(c.getRGB());
        colorButton.setForeground(Color.WHITE);
        colorButton.setFont(new java.awt.Font("SANS_SERIF", Font.BOLD, 14));
        colorButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        colorButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                upperPanel.removeAll();
                c = c.nextRGB();
                colorButton.setBackground(c.getRGB());
                if (currentChoice == null){return;}
                upperPanel.add(new DrawShapePanel(upperPanel.getWidth(), upperPanel.getHeight(), currentChoice, c.getRGB()), BorderLayout.CENTER);
                pack();
            }
        });
        
        clearButton = new JButton("Clear");
        clearButton.setBackground(Color.RED);
        clearButton.setForeground(Color.BLACK);
        clearButton.setFont(new java.awt.Font("SANS_SERIF", Font.BOLD, 14));
        clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                upperPanel.removeAll();
                surfaceAreaValue.setText("");
                volumeValue.setText("");
                currentChoice = null;
                upperPanel.add(new DrawShapePanel(upperPanel.getWidth(), upperPanel.getHeight(), "", c.getRGB()), BorderLayout.CENTER);
                pack();
            }
        });
        
        surfaceAreaLabel = new JLabel("Surface Area = ");
        lowerPanel.add(surfaceAreaLabel);
        
        surfaceAreaValue = new JLabel();
        surfaceAreaValue.setPreferredSize(surfaceAreaValue.getPreferredSize());
        lowerPanel.add(surfaceAreaValue);
        
        volumeLabel = new JLabel("Volume = ");
        lowerPanel.add(volumeLabel);
        
        volumeValue = new JLabel();
        volumeValue.setPreferredSize(volumeValue.getPreferredSize());
        lowerPanel.add(volumeValue);

        lowerPanel.add(calculateButton);
        lowerPanel.add(colorButton);
        lowerPanel.add(clearButton);
        
        panel.add(upperPanel);
        panel.add(lowerPanel);
        
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Output"));
        
        return panel;
    }
    
    private void checkFieldsForCalculate() {
        boolean canCalculate = true;
        for (JTextField field : fields){
            if (field.getText().trim().isEmpty()){
                canCalculate = false;
                break;
            }
            try {
                if (Double.valueOf(field.getText()) < 0){
                    canCalculate = false;
                    break;
                }
            } catch (NumberFormatException ex){
                canCalculate = false;
                break;
            }
        }
        calculateButton.setEnabled(canCalculate);
    }
    
    private String[] updateShapeFields(){
        String choice = (String) shapeSelection.getSelectedItem();
        switch (choice) {
            case "Sphere":
                return Sphere.variableLabels;
            case "Cube":
                return Cube.variableLabels;
            case "Cone":
                return Cone.variableLabels;
            case "Cylinder":
                return Cylinder.variableLabels;
            case "Tetrahedron":
                return Cylinder.variableLabels;
            default:
                return new String[] {};
        }
    }
}

class DrawShapePanel extends JPanel{
    public DrawShapePanel(int width, int height, String shapeType, Color RGB){
        setLayout(new BorderLayout());
        add(new DrawShape(width, height, shapeType, RGB), BorderLayout.CENTER);
    }
    
    private class DrawShape extends JComponent{
        private int width;
        private int height;
        private String shapeType;
        private Color RGB;
    
        public DrawShape(int width, int height, String shapeType, Color RGB){
            this.width = width;
            this.height = height;
            this.shapeType = shapeType;
            this.RGB = RGB;
        }
        
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            switch (this.shapeType){
                case "Sphere":{
                    int radius = this.width/2;
                    int x = this.width/4;
                    int y = this.width/4;
                    
                    Point2D center = new Point2D.Float(x, y);
                    float[] dist = {0.0f, 1.0f};
                    Color[] colors = {Color.WHITE, this.RGB};
                     RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
                    g2d.setPaint(p);
                    g2d.drawOval(x, y, radius, radius);
                    g2d.fillOval(x, y, radius, radius);
                    break;
                }
                case "Cube":{
                    int x = this.width/4;
                    int y = this.width/4;
                    int sideLength = this.width/2;
                    
                    g2d.setColor(this.RGB);
                    g2d.draw3DRect(x, y, sideLength, sideLength, true);
                    g2d.fill3DRect(x, y, sideLength, sideLength, true);
                    break;
                }
                case "Cone":{
                    int conex = this.width/4;
                    int coney = this.height/4;
                    int[] x = {};
                    int[] y = {};
                    int numPoints;
                    
                    int radius = this.width/2;
                    int xz = this.width/4;
                    int yz = this.width/4;
                    Point2D center = new Point2D.Float(xz, yz);
                    float[] dist = {0.0f, 1.0f};
                    Color[] colors = {Color.WHITE, this.RGB};
                     RadialGradientPaint p = new RadialGradientPaint(center,radius, dist, colors);
                    g2d.setPaint(p);
                    
                    g2d.drawArc(conex, coney, this.width/2, this.height/2, 0, -180);
                    g2d.fillArc(conex, coney, this.width/2, this.height/2, 0, -180);
                    // left, right, top
                    x = new int[]{this.width/4, this.width/4*3, this.width/2}; 
                    y = new int[]{this.height/2, this.height/2, this.height/8};
                    numPoints = 3;
                    g.drawPolygon(x, y, numPoints);
                    g.fillPolygon(x, y, numPoints);
                    break;
                } 
                case "Cylinder":{
                    int x = this.width/4;
                    int y = this.height/4;
                    int radiusx = this.width/2;
                    int radiusy = this.height/2;
                    //int COLOR_DIFF = 48;
                    
                    g2d.setColor(this.RGB.darker());
                    g2d.drawRect(x, y, x*2, y*2);
                    g2d.fillRect(x, y, x*2, y*2);
                    
                    g2d.setColor(this.RGB);
                    g2d.drawOval(x, y/5, radiusx, radiusy);
                    g2d.fillOval(x, y/5, radiusx, radiusy);
                    
                    g2d.setColor(this.RGB.darker());
                    g2d.drawArc(x, y+this.height/5, radiusx, radiusy, 180, 180);
                    g2d.fillArc(x, y+this.height/5, radiusx, radiusy, 180, 180);
                    break;
                }  
                case "Tetrahedron":{
                    // right, left, bottom
                    int centx = this.width/2;
                    int centy = this.height/2;
                    int displacex = this.width/3;
                    int displacey = this.height/3;
                    int[] x = {};
                    int[] y = {};
                    int numPoints;

                    //middle, top, bottom
                    g2d.setColor(this.RGB);
                    x = new int[]{centx, centx, centx + displacex};
                    y = new int[]{centy, centy - displacey, centy + displacey};
                    numPoints = 3;
                    g.drawPolygon(x, y, numPoints);
                    g.fillPolygon(x, y, numPoints);
                    
                    //middle, top, bottom (y-axis)
                    g2d.setColor(this.RGB.brighter());
                    x = new int[]{centx, centx, centx - displacex};
                    y = new int[]{centy, centy - displacey, centy  + displacey};
                    numPoints = 3;
                    g.drawPolygon(x, y, numPoints);
                    g.fillPolygon(x, y, numPoints);
                    
                    //middle, left, right (x-axis)
                    g2d.setColor(this.RGB.darker());
                    x = new int[]{centx, centx - displacex, centx + displacex};
                    y = new int[]{centy, centy + displacey, centy + displacey};
                    numPoints = 3;
                    g.drawPolygon(x, y, numPoints);
                    g.fillPolygon(x, y, numPoints);
                    break;
                }    
                default:
                    break;
            }
        }
    }
}

enum RGBColorConstants{ // RGBs aren't full 255 or 0 to allow lightness & darkness on the shape
    RED(new Color(220, 80, 80)),
    ORANGE(new Color(255, 165, 0)),
    GREEN(new Color(0, 128, 0)),
    YELLOW(new Color(250, 220, 100)),
    BLUE(new Color(80, 140, 220)),
    PINK(new Color(240, 140, 160)),
    CYAN(new Color(100, 200, 200)),
    PURPLE(new Color(128, 0, 128)),
    GRAY(new Color(128, 128, 128)),
    BROWN(new Color(150, 75, 0));
    
    private Color RGB;
    private static final RGBColorConstants[] VALS = values();
    
    private RGBColorConstants(Color RGB){
        this.RGB = RGB;
    }
    
    public Color getRGB(){
        return this.RGB;
    }
    
    public RGBColorConstants nextRGB(){
        return VALS[(this.ordinal() + 1) % VALS.length];
    }
}