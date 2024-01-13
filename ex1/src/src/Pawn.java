package src;

public class Pawn extends ConcreatePiece {
    public Pawn(Player owner, int serialNumber)
    {
        // 'D' stands for the defender, 'A' stands for the attacker.
        super(owner,
            owner.isPlayerOne() ? "♙" : "♟",
            owner.isPlayerOne() ? "D" + serialNumber : "A" + serialNumber);
    }
}
