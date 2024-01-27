package src;

public class Position {
    private int x;//row
    private int y;//col
    private boolean undo; // this was added only to test "undo the last move" functionality.

    public Position(int x, int y)
    {
        this.x=x;
        this.y=y;
        this.undo = false;
    }
    public Position(int x, int y, boolean undo)
    {
        this.x=x;
        this.y=y;
        this.undo = undo;
    }

    public Position(Position pos)
    {
        this.x=pos.x;
        this.y=pos.y;
        this.undo = pos.undo;
    }

    public void setPos(int x,int y)
    {
        this.x=x;
        this.y=y;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    @Override
    public String toString()
    {
        return "("+this.x+","+this.y+")";
    }

    // this was added only to test "undo the last move" functionality.
    public boolean isUndo() {
        return undo;
    }
}
