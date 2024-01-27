package src;

import java.util.ArrayList;

public abstract class ConcreatePiece implements Piece
{
    private final Player owner;
    private final String type;
    private final String title;
    private final String titlePrefix;
    private final int titleNumber;
    private int distance;
    private final ArrayList<Position> movesHistory;
    private int killsCount;

    ConcreatePiece(Player owner, String type, String titlePrefix, int titleNumber, Position position) {
        this.owner = owner;
        this.type = type;
        this.titlePrefix = titlePrefix;
        this.titleNumber = titleNumber;
        this.title = titlePrefix + titleNumber;
        this.movesHistory = new ArrayList<>();
        this.movesHistory.add(position);
        this.distance = 0;
        this.killsCount = 0;
    }
    public int getDistance(){return this.distance;}

    public void addDistance(Position p1, Position p2)
    {
        int x= (int) Math.sqrt(Math.pow(p2.getX()-p1.getX(),2)+Math.pow(p2.getY()-p1.getY(),2));
        this.distance+=x;
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

    public void setMovesHistory(ArrayList<Position> movesHistory) {
        this.movesHistory.clear();
        this.movesHistory.addAll(movesHistory);
    }
    public void addMove(Position move) {
        this.movesHistory.add(move);
    }

    public Position getFirstPosition() {
        return this.movesHistory.getFirst();
    }

    public String getTitlePrefix() {
        return titlePrefix;
    }

    public int getTitleNumber() {
        return titleNumber;
    }

    public int getKillsCount() {
        return this.killsCount;
    }
    public void setKillsCount(int killsCount) {
        this.killsCount = killsCount;
    }
    public void increaseKills() {
        this.killsCount++;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
