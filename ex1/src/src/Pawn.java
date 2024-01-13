package src;

public class Pawn extends ConcreatePiece {
    private int killsCount;

    public Pawn(Player owner)
    {
        this.owner = owner;
        this.killsCount = 0;
    }

    public String getType() {
        if(this.owner.isPlayerOne())
            return "♙";
        else
            return "♟";

    }
    public void counterKills() {
        this.killsCount++;
    }















}
