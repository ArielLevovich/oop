package src;
import java.util.Comparator;


public class MoveHistorySizeComparator extends BaseComparator implements Comparator<ConcreatePiece>{
    public MoveHistorySizeComparator(boolean isDefenderWon) {
        super(isDefenderWon);
    }
    @Override
    public int compare(ConcreatePiece o1, ConcreatePiece o2) {
        if (o1 == o2) {
            return 0;
        }
        // compare by the winning team (the winner first)
        int compareByWinningTeam = compareByWinningTeam(o1, o2);
        if (compareByWinningTeam != 0) {
            return compareByWinningTeam;
        }

        // if both are from the same team, we sort by history size by ascending order
        int movesHistoryCompare = Integer.compare(o1.getMovesHistory().size(), o2.getMovesHistory().size());
        if (movesHistoryCompare != 0) {
            return movesHistoryCompare;
        }

        // if moves history size is equal, we sort by title number by ascending order
        return compareByTitleNumber(o1, o2);
    }
 }
