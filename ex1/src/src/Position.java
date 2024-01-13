package src;

public class Position {
    private int x;//row
    private int y;//col
    //protected int [][] board;

    public Position(int x, int y)
    {
        this.x=x;
        this.y=y;
    }

    public Position(Position pos)
    {
        this.x=pos.x;
        this.y=pos.y;
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


}
