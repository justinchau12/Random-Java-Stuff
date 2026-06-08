public enum Algorithm {

    DFS("DFS (Depth First Search)"){
        @Override
        public void pathfind(Maze maze, int delay){
            Pathfinding.dfs(maze, delay);
        }
    },

    BFS("BFS (Breadth First Search)"){
        @Override
        public void pathfind(Maze maze, int delay){
            Pathfinding.bfs(maze, delay); 
        }
        
    },

    RANDOMSEARCH("Random Search (Unreliable)"){
        @Override
        public void pathfind(Maze maze, int delay){
            Pathfinding.rs(maze, delay);
        }
    };
    
    private final String algorithmName;
    
    Algorithm(String algorithmName){
        this.algorithmName = algorithmName;
    }
    
    @Override
    public String toString(){
        return algorithmName;
    }

    public abstract void pathfind(Maze maze, int delay);
}