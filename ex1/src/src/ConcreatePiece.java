package src;

public abstract class ConcreatePiece implements Piece
{
    private final Player owner;
    private final String type;
    private final String title;
    private int killsCount;

    ConcreatePiece(Player owner, String type, String title) {
        this.owner = owner;
        this.type = type;
        this.title = title;
        this.killsCount = 0;
    }
    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public String getType() {
        return this.type;//no sure if we need type for the piece
    }

    public String getTitle() {
        return this.title;
    }
}
