package src;

public abstract class ConcreatePiece implements Piece
{
    protected Player owner;
    protected String type;
    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public String getType() {
        return this.type;//no sure if we need type for the piece
    }
}
