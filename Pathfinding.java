import java.awt.event.*;
import javax.swing.Timer; // We need swing timer, not util timer
import java.util.*;

public class Pathfinding 
{
    private static Timer timer;
    private static boolean isFinished = false;
    
    private Pathfinding(){} // No pathfinding object should be created
    
    public static void dfs(Maze maze, int delay){
        CellState[][] cells = maze.getCells();
        int sideCellCount = maze.getSideCellCount();
        boolean[][] visited = new boolean[sideCellCount][sideCellCount];
        Stack<int[]> coordsStack = new Stack<>();
        int[][][] backPointer = PathfindingHelper.backPointerInit(sideCellCount);
        Random rand = new Random();
        
        // 1. Get + select the start cell.
        int[] startCoords = PathfindingHelper.getStartCoords(cells, sideCellCount);
        int startX = startCoords[0];
        int startY = startCoords[1];
        
        // 2. Push the cell C onto a stack S.
        coordsStack.push(new int[]{startX, startY});
        
        // 3. Mark the cell C as visited. 
        visited[startX][startY] = true;
        
        timer = new Timer(delay, null);
        ActionListener taskPerformer = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                int[] current = coordsStack.peek();
                // 4c. If end cell is reached, stop.
                if (PathfindingHelper.isEnd(cells, current[0], current[1])){
                    end();
                    PathfindingHelper.reconstructCorrectPath(cells, backPointer, current[0], current[1]);
                    maze.repaint();
                    return;
                }
                if (PathfindingHelper.canModifyCellState(cells, current[0], current[1])){
                    cells[current[0]][current[1]] = CellState.VISITED;
                    maze.repaint();
                }
                
                ArrayList<int[]> unvisitedNeighbors = PathfindingHelper.getUnvisitedNeighbors(cells, visited, sideCellCount, current[0], current[1]);

                if (!unvisitedNeighbors.isEmpty()){
                    
                    // 4a. Randomly select an adjacent cell A of cell C that has not been visited.
                    int[] neighbor = unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));
                
                    // 6. Assign the value A to C.
                    coordsStack.push(new int[]{neighbor[0], neighbor[1]});
                    visited[neighbor[0]][neighbor[1]] = true;
                    backPointer[neighbor[0]][neighbor[1]][0] = current[0];
                    backPointer[neighbor[0]][neighbor[1]][1] = current[1];
                    if (PathfindingHelper.canModifyCellState(cells, neighbor[0], neighbor[1])){
                        cells[neighbor[0]][neighbor[1]] = CellState.CURRENT;
                        maze.repaint();
                    }
                } else {
                    // 4b. Continue to pop items off the stack S until a cell is encountered with at least one non-visited neighbor.
                    coordsStack.pop();
                    int[] backtracked = coordsStack.peek();
                    backPointer[current[0]][current[1]][0] = backtracked[0];
                    backPointer[current[0]][current[1]][1] = backtracked[1];
                    if (PathfindingHelper.canModifyCellState(cells, backtracked[0], backtracked[1])){
                        cells[backtracked[0]][backtracked[1]] = CellState.CURRENT;
                        maze.repaint();
                    }
                }
            }
        };
        timer.addActionListener(taskPerformer);
        timer.start();
    }

    public static void bfs(Maze maze, int delay){
        CellState[][] cells = maze.getCells();
        int sideCellCount = maze.getSideCellCount();
        boolean[][] visited = new boolean[sideCellCount][sideCellCount];
        Queue<int[]> coordsQueue = new LinkedList<>();
        int[][][] backPointer = PathfindingHelper.backPointerInit(sideCellCount);
        
        // 1. Get + select the start cell.
        int[] startCoords = PathfindingHelper.getStartCoords(cells, sideCellCount);
        int startX = startCoords[0];
        int startY = startCoords[1];
        
        // 2. Enqueue the cell C onto a Queue Q.
        coordsQueue.offer(new int[]{startX, startY});
        
        // 3. Mark the cell C as visited. 
        visited[startX][startY] = true;
        
        timer = new Timer(delay, null);
        ActionListener taskPerformer = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                if (coordsQueue.isEmpty()) return; // No solution
                int[] current = coordsQueue.poll();
                // 4b. If end cell is reached, stop.
                if (PathfindingHelper.isEnd(cells, current[0], current[1])){
                    end();
                    PathfindingHelper.reconstructCorrectPath(cells, backPointer, current[0], current[1]);
                    maze.repaint();
                    return;
                }
                if (PathfindingHelper.canModifyCellState(cells, current[0], current[1])){
                    cells[current[0]][current[1]] = CellState.VISITED;
                    maze.repaint();
                }

                ArrayList<int[]> unvisitedNeighbors = PathfindingHelper.getUnvisitedNeighbors(cells, visited, sideCellCount, current[0], current[1]);
                
                // 4a. Diverts + explores every path in a junction
                for (int[] neighbor : unvisitedNeighbors){
                if (!visited[neighbor[0]][neighbor[1]]){
                    coordsQueue.add(new int[]{neighbor[0], neighbor[1]});
                    backPointer[neighbor[0]][neighbor[1]][0] = current[0];
                    backPointer[neighbor[0]][neighbor[1]][1] = current[1];
                    visited[neighbor[0]][neighbor[1]] = true;
                    if (PathfindingHelper.canModifyCellState(cells, neighbor[0], neighbor[1])){
                        cells[neighbor[0]][neighbor[1]] = CellState.CURRENT;
                        maze.repaint();
                    }
                }
            }

            }
        };
        timer.addActionListener(taskPerformer);
        timer.start();
    }
    
    public static void rs(Maze maze, int delay){
        CellState[][] cells = maze.getCells();
        int sideCellCount = maze.getSideCellCount();
        Random rand = new Random();
        
        // 1. Get + select the start cell.
        int[] startCoords = PathfindingHelper.getStartCoords(cells, sideCellCount);
        int startX = startCoords[0];
        int startY = startCoords[1];
        
        int[] current = new int[]{startX, startY};
        
        timer = new Timer(delay, null);
        ActionListener taskPerformer = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                // 2b. If end cell is reached, stop.
                if (PathfindingHelper.isEnd(cells, current[0], current[1])){
                    end();
                    maze.repaint();
                    return;
                }
                if (PathfindingHelper.canModifyCellState(cells, current[0], current[1])){
                    cells[current[0]][current[1]] = CellState.VISITED;
                    maze.repaint();
                }

                ArrayList<int[]> neighbors = PathfindingHelper.getNeighbors(cells, sideCellCount, current[0], current[1]);
                if (neighbors.isEmpty()) return;
                
                // 2a. Pick a random neighbor, visited or not
                int[] randomNeighbor = neighbors.get(rand.nextInt(neighbors.size()));

                current[0] = randomNeighbor[0];
                current[1] = randomNeighbor[1];
                if (PathfindingHelper.canModifyCellState(cells, randomNeighbor[0], randomNeighbor[1])){
                    cells[randomNeighbor[0]][randomNeighbor[1]] = CellState.CURRENT;
                    maze.repaint();
                }
            }
        };
        timer.addActionListener(taskPerformer);
        timer.start();
    }
    
    // Timer related helper methods
    
    public static boolean hasStarted(){
        return timer != null;
    }
    
    public static boolean isFinished(){
        return isFinished;
    }
    
    public static boolean isRunning(){
        return hasStarted() && !isFinished() && timer.isRunning();
    }
    
    public static boolean isPaused(){
        return hasStarted() && !isFinished() && !timer.isRunning();
    }

    public static void pause(){
       if (isRunning()) {
            timer.stop();
       }
    }

    public static void resume(){
       if (isPaused()) {
            timer.start();
       }
    }
        
    public static void end(){ // Pathfinding ended
       if (hasStarted()) {
            timer.stop();
            timer = null;
            isFinished = true;
       }
    }
    
    public static void reset(){ // User resets the maze for another pathfinding
        end(); // Required, as pathfind may end prematurely
        isFinished = false;
    }
    
    public static void updateDelay(int newDelay){
        if (hasStarted()){
            timer.setDelay(newDelay);
        }
    }
}

class PathfindingHelper{ // Class dedicated to reduce redundancy in Pathfinding & increase readability
    
    private PathfindingHelper(){} // No PathfindingHelper object should be created
    
    protected static int[] getStartCoords(CellState[][] cells, int sideCellCount){
        int startX = -1;
        int startY = -1;
        for (int i = 0; i < sideCellCount; i++){
            for (int j = 0; j < sideCellCount; j++){
                if (cells[i][j] != CellState.START) continue;
                startX = i;
                startY = j;
                break;
            }
        }
        
        if (startX == -1 || startY == -1) {
            throw new IllegalStateException("ERROR: No cell with CellState.START state found!");
        }
        
        return new int[]{startX, startY};
    }    
    
    protected static int[][][] backPointerInit(int sideCellCount){
        int[][][] backPointer = new int[sideCellCount][sideCellCount][2]; // backPointer[x][y] = {prevX, prevY}
        for (int i = 0; i < sideCellCount; i++) {
            for (int j = 0; j < sideCellCount; j++) {
                backPointer[i][j][0] = -1;
                backPointer[i][j][1] = -1;
            }
        }
        return backPointer;
    }
    protected static ArrayList<int[]> getUnvisitedNeighbors(CellState[][] cells, boolean visited[][], int sideCellCount, int x, int y) {
        ArrayList<int[]> neighbors = new ArrayList<>();

        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // Up, Left, Down, Right

        for (int[] d : directions) {
            int newX = x + d[0];
            int newY = y + d[1];
    
            if (newX >= 0 && newX < sideCellCount &&
                newY >= 0 && newY < sideCellCount &&
                !visited[newX][newY] && cells[newX][newY] != CellState.WALL){
                neighbors.add(new int[]{newX, newY});
            }
        }
    
        return neighbors;
    }
    
    protected static ArrayList<int[]> getNeighbors(CellState[][] cells, int sideCellCount, int x, int y) {
        ArrayList<int[]> neighbors = new ArrayList<>();

        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // Up, Left, Down, Right

        for (int[] d : directions) {
            int newX = x + d[0];
            int newY = y + d[1];
    
            if (newX >= 0 && newX < sideCellCount &&
                newY >= 0 && newY < sideCellCount
                && cells[newX][newY] != CellState.WALL){
                neighbors.add(new int[]{newX, newY});
            }
        }
    
        return neighbors;
    }
    
    protected static boolean isEnd(CellState cells[][], int x, int y){
        return cells[x][y] == CellState.END;
    }
    
    protected static boolean canModifyCellState(CellState cells[][], int x, int y){
        return cells[x][y] != CellState.START && cells[x][y] != CellState.END;
    }
    
    protected static void reconstructCorrectPath(CellState cells[][], int[][][] backPointer, int endX, int endY){
        int x = endX;
        int y = endY;

        while (x != -1 && y != -1) {
            if (canModifyCellState(cells, x, y)){
                cells[x][y] = CellState.CORRECTPATH;
            }

            int prevX = backPointer[x][y][0];
            int prevY = backPointer[x][y][1];

            x = prevX;
            y = prevY;
        }
    }
}