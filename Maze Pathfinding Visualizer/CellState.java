import java.awt.Color;

public enum CellState {
    EMPTY(Color.WHITE, true),
    VISITED(Color.CYAN, true),
    CORRECTPATH(Color.YELLOW, true),
    CURRENT(Color.BLUE, true),
    WALL(Color.BLACK, false),
    START(Color.GREEN, false),
    END(Color.RED, false);

    private final Color color;
    private final boolean isClearable;

    CellState(Color color, boolean isClearable){
        this.color = color;
        this.isClearable = isClearable;
    }

    public Color getColor(){
        return color;
    }
    
    public boolean isClearable(){
        return isClearable;
    }
}
