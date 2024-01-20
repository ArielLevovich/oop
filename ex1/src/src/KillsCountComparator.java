package src;

import java.util.Comparator;

class KillsCountComparator extends BaseComparator implements Comparator<ConcreatePiece> {
    public KillsCountComparator(boolean isDefenderWon)
    {
        super(isDefenderWon);
    }
    @Override
    public int compare(ConcreatePiece o1, ConcreatePiece o2) {
        if (o1 == o2) {
            return 0;
        }
        // First, compare by killsCount in descending order
        int killsCompare = Integer.compare(o2.getKillsCount(), o1.getKillsCount());
        if (killsCompare != 0) {
            return killsCompare;
        }

        // If killsCount is equal, compare by title number in ascending order
        int titleCompare = compareByTitleNumber(o1, o2);
        if (titleCompare != 0) {
            return titleCompare;
        }

        // If both killsCount and title numbers are equal, compare by the winning team (the winner first)
        return compareByWinningTeam(o1, o2);
    }
}
