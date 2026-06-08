public class Solver
{
    // Constants
    private final static int MIN_SOLVER_SPEED = 3;
    private final static int MAX_SOLVER_SPEED = 10;
    private final static int INIT_SOLVER_SPEED = MIN_SOLVER_SPEED;
    
    private int solverSpeed;
    
    public Solver(){
        solverSpeed = INIT_SOLVER_SPEED;
    }
    
    public void updateSolverSpeed(int newSolverSpeed){
        if ((MIN_SOLVER_SPEED > newSolverSpeed) || (MAX_SOLVER_SPEED < newSolverSpeed)){return;}
        solverSpeed = newSolverSpeed;
        Pathfinding.updateDelay(getDelay());
    }
    
    public void toggleSolving(Maze maze, Algorithm selected){
        if (!maze.getIsMazeGenerated()) return;
        
        if (Pathfinding.isRunning()) { 
            Pathfinding.pause();
        } else {
            if (Pathfinding.isPaused()) {
                Pathfinding.resume();
            } else if (Pathfinding.isFinished()){
                return; // One must clear or reset first before another pathfind can occur
            } else if (!Pathfinding.hasStarted()){
                selected.pathfind(maze, getDelay());
            }
        }
    }
    
    private int getDelay(){
        return 1000 / solverSpeed; // ms
    }
    
    public boolean canSetNewInput(){
        if (Pathfinding.isFinished()){
            return false; // One must clear or reset first before enabling input
        }
        return !Pathfinding.isRunning();
    }
    
    public static int getMinSolverSpeed(){
        return MIN_SOLVER_SPEED;
    }
    
    public static int getMaxSolverSpeed(){
        return MAX_SOLVER_SPEED;
    }
    
    public static int getInitSolverSpeed(){
        return INIT_SOLVER_SPEED;
    }
}
