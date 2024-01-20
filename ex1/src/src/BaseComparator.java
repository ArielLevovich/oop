package src;

public class BaseComparator {
    protected final boolean isDefenderWon; // true if defender won, false if attacker won.
    public BaseComparator(boolean isDefenderWon) {
        this.isDefenderWon = isDefenderWon;
    }

    public int compareByWinningTeam(ConcreatePiece o1, ConcreatePiece o2) {
        if (isDefenderWon && o1.getOwner().isPlayerOne() && !o2.getOwner().isPlayerOne()) {
            return -1;
        }
        else if (isDefenderWon && !o1.getOwner().isPlayerOne() && o2.getOwner().isPlayerOne()) {
            return 1;
        }
        else if (!isDefenderWon && !o1.getOwner().isPlayerOne() && o2.getOwner().isPlayerOne()) {
            return -1;
        }
        else if (!isDefenderWon && o1.getOwner().isPlayerOne() && !o2.getOwner().isPlayerOne()) {
            return 1;
        }
        return 0;
    }

    public int compareByTitleNumber(ConcreatePiece o1, ConcreatePiece o2) {
        return Integer.compare(o1.getTitleNumber(), o2.getTitleNumber());
    }
}
