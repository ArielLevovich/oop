package src;

public class ConcreatePlayer implements Player{

    public boolean isDef;
    private int winsCount;

    public ConcreatePlayer(boolean isOne)
    {
        if (isOne)
            this.isDef=true;
        else
            this.isDef=false;
        this.winsCount=0;
    }



    public boolean isPlayerOne()
    {
        if(isDef)
            return true;
        return false;
    }

    public void wins()
    {
        this.winsCount++;
    }

    @Override
    public int getWins()
    {
        return this.winsCount;
    }
}
