package src;

public class ConcreatePlayer implements Player{

    public boolean isDefender;
    private int winsCount;

    public ConcreatePlayer(boolean isOne)
    {
        this.isDefender = isOne;
        this.winsCount=0;
    }

    public boolean isPlayerOne()
    {
        return isDefender;
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
