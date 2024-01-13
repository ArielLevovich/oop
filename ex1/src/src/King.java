package src;

public class King extends ConcreatePiece {

    //private String type="♔";

    public King(Player defPlayer)
    {
        this.owner = defPlayer;
    }

    public String getType()
    {
        return "♔";
    }
}
