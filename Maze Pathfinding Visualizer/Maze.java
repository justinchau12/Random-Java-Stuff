import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Maze extends JPanel implements ActionListener
{
    // Constants
    private final static int MIN_SIDE_CELL_COUNT = 5;
    private final static int MAX_SIDE_CELL_COUNT = 29;
    private final static int INIT_SIDE_CELL_COUNT = MIN_SIDE_CELL_COUNT;
    private final static int MIN_CELL_COUNT_BETWEEN_START_AND_END = 2;
    
    private CellState[][] cells; // x y coords
    private int sideCellCount;
    private int cellLength; 
    
    private boolean isMazeGenerated = false;

    public Maze(int width, int height){
        setPreferredSize(new Dimension(width, height));
        updateCellCount(INIT_SIDE_CELL_COUNT);
        updateCells(INIT_SIDE_CELL_COUNT);
    }
    
    public void generateMaze(){ // DFS Gen.
        Pathfinding.reset();
        boolean[][] visited = new boolean[sideCellCount][sideCellCount];
        Stack<int[]> coordsStack = new Stack<>();
        for (int i = 0; i < sideCellCount; i++){ // Turns every cells to wall to prep. for gen.
            for (int j = 0; j < sideCellCount; j++){
                cells[i][j] = CellState.WALL;
            }
        }
        
        // 1. Randomly select a cell C.
        Random rand = new Random();
        int startX, startY;
        startX = rand.nextInt(sideCellCount);
        startY = rand.nextInt(sideCellCount);

        // 2. Push the cell C onto a stack S.
        coordsStack.push(new int[]{startX, startY});
        
        // 3. Mark the cell C as visited. 
        visited[startX][startY] = true;
        cells[startX][startY] = CellState.EMPTY;
        
        // 4c. If no cells exist: stop.
        while (!coordsStack.isEmpty()) {            
            int[] current = coordsStack.peek(); 
            ArrayList<int[]> unvisitedNeighbors = getUnvisitedNeighbors(visited, current[0], current[1]);

            if (!unvisitedNeighbors.isEmpty()){
                // 4a. Randomly select an adjacent cell A of cell C that has not been visited.
                int[] neighbor = unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));
                int midX = (current[0] + neighbor[0]) / 2;
                int midY = (current[1] + neighbor[1]) / 2;
                
                // 5. Break the wall between C and A.
                cells[midX][midY] = CellState.EMPTY;
                
                // 6. Assign the value A to C.
                coordsStack.push(new int[]{neighbor[0], neighbor[1]});
                visited[neighbor[0]][neighbor[1]] = true;
                cells[neighbor[0]][neighbor[1]] = CellState.EMPTY;
            } else {
                // 4b. Continue to pop items off the stack S until a cell is encountered with at least one non-visited neighbor.
                coordsStack.pop();
            }
        }
        setMazeStartAndEnd();
        isMazeGenerated = true;
        repaint();
    } 
    
    private ArrayList<int[]> getUnvisitedNeighbors(boolean visited[][], int x, int y){

        ArrayList<int[]> neighbors = new ArrayList<>();

        int[][] directions = {{0, -2}, {0, 2}, {-2, 0}, {2, 0}}; // Up, Left, Down, Right

        for (int[] d : directions) {
            int newX = x + d[0];
            int newY = y + d[1];
    
            if (newX >= 0 && newX < sideCellCount &&
                newY >= 0 && newY < sideCellCount &&
                !visited[newX][newY]){
                neighbors.add(new int[]{newX, newY});
            }
        }
    
        return neighbors;
    }
    
    private void setMazeStartAndEnd(){
        Random rand = new Random();
        int startX, startY, endX, endY;
        do {
            startX = rand.nextInt(sideCellCount);
            startY = rand.nextInt(sideCellCount);
        } while (cells[startX][startY] != CellState.EMPTY);
        cells[startX][startY] = CellState.START;
        
        do {
            endX = rand.nextInt(sideCellCount);
            endY = rand.nextInt(sideCellCount);
        } while (cells[endX][endY] != CellState.EMPTY || getManhattanDistance(startX, startY, endX, endY) < MIN_CELL_COUNT_BETWEEN_START_AND_END);
        cells[endX][endY] = CellState.END;
    }
    
    private int getManhattanDistance(int x1, int y1, int x2, int y2){
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    // public methods for jbuttons actionlistener(Encapsulation)
    public void clearMaze(){
        Pathfinding.reset();
        for (int i = 0; i < sideCellCount; i++){
            for (int j = 0; j < sideCellCount; j++){
                if (!cells[i][j].isClearable()) continue;
                cells[i][j] = CellState.EMPTY;
            }
        }
        repaint();
    }
        
    public void resetMaze(){
        Pathfinding.reset();
        for (int i = 0; i < sideCellCount; i++){
            for (int j = 0; j < sideCellCount; j++){
                cells[i][j] = CellState.EMPTY;
            }
        }
        isMazeGenerated = false;
        repaint();
    }
    
    // public update / setter methods
    public void updateCellCount(int newSideCellCount){
        if ((MIN_SIDE_CELL_COUNT > newSideCellCount) || (MAX_SIDE_CELL_COUNT < newSideCellCount)){return;}
        sideCellCount = newSideCellCount;
        updateCells(newSideCellCount);
    }
    
    // private update / setter methods
    private void updateCells(int newSideCellCount){
        cells = new CellState[newSideCellCount][newSideCellCount];
        resetMaze();
    }
    
    private void updateCellLength(){
        cellLength = Math.min(getWidth() / sideCellCount, getHeight() / sideCellCount);
    }
    
    // public getter methods
    public CellState[][] getCells(){
        return cells;
    }
    
    public boolean getIsMazeGenerated(){
        return isMazeGenerated;
    }
        
    public int getSideCellCount(){
        return sideCellCount;
    }
    
    public static int getMinSideCellCount(){
        return MIN_SIDE_CELL_COUNT;
    }
    
    public static int getMaxSideCellCount(){
        return MAX_SIDE_CELL_COUNT;
    }
    
    public static int getInitSideCellCount(){
        return INIT_SIDE_CELL_COUNT;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Just in case
        
        updateCellLength();
        
        for (int i = 0; i < sideCellCount; i++){
            for (int j = 0; j < sideCellCount; j++){
                g2d.setColor(cells[i][j].getColor());
                g2d.fillRect(i*cellLength, j*cellLength, cellLength, cellLength);
            }
        }
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        int gridSize = sideCellCount * cellLength;
        for (int i = 0; i <= sideCellCount; i++) {
            int pos = i * cellLength;
            g2d.drawLine(pos, 0, pos, gridSize); // Vertical
            g2d.drawLine(0, pos, gridSize, pos); // Horizontal
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        repaint();
    }
}
