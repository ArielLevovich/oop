package src;

public class Pawn extends ConcreatePiece {
    private int killsCount;
    public Pawn(Player owner, int serialNumber, Position position)
    {
        // 'D' stands for the defender, 'A' stands for the attacker.
        super(owner,
            owner.isPlayerOne() ? "♙" : "♟",
            owner.isPlayerOne() ? "D" + serialNumber : "A" + serialNumber, position);
        this.killsCount = 0;
    }

    public int getKillsCount() {
        return this.killsCount;
    }
    public void increaseKills() {
        this.killsCount++;
    }
}
