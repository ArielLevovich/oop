package src;

public class King extends ConcreatePiece {
    public King(int serialNUmber)
    {
        // king's owner is always the defender.
        super(new ConcreatePlayer(true), "♔", "K" + serialNUmber);
    }
}
