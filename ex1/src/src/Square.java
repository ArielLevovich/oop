package src;

import java.util.HashSet;

public class Square {
    HashSet<String> piecesTitles;
    int x;
    int y;

    public Square(int x, int y, HashSet<String> piecesTitles) {
        this.x = x;
        this.y = y;
        this.piecesTitles = piecesTitles;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public HashSet<String> getPiecesTitles() {
        return this.piecesTitles;
    }
}
