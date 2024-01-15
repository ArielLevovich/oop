package src;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class ConcreatePiece implements Piece
{
    private final Player owner;
    private final String type;
    private final String title;
    private int killsCount;
    private final ArrayList<Position> movesHistory;

    ConcreatePiece(Player owner, String type, String title, Position position) {
        this.owner = owner;
        this.type = type;
        this.title = title;
        this.killsCount = 0;
        this.movesHistory = new ArrayList<>();
        this.movesHistory.add(position);
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

    public ArrayList<Position> getMovesHistory() {
        return this.movesHistory;
    }

    public void addMove(Position move) {
        this.movesHistory.add(move);
    }

    public Position getFirstPosition() {
        return this.movesHistory.getFirst();
    }
}
