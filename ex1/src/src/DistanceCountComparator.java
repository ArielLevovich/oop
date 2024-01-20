package src;

import java.util.Comparator;

class DistanceCountComparator extends BaseComparator implements Comparator<ConcreatePiece> {
    public DistanceCountComparator(boolean isDefenderWon) {
        super(isDefenderWon);
    }
    @Override
    public int compare(ConcreatePiece o1, ConcreatePiece o2) {
        if (o1 == o2) {
            return 0;
        }
        // First, compare by distance in descending order
        int distanceCompare = Integer.compare(o2.getDistance(), o1.getDistance());
        if (distanceCompare != 0) {
            return distanceCompare;
        }

        // If the distances are equal, compare by title number in ascending order
        int titleCompare = compareByTitleNumber(o1, o2);
        if (titleCompare != 0) {
            return titleCompare;
        }

        // If both distances and title numbers are equal, compare by the winning team (the winner first)
        return compareByWinningTeam(o1, o2);

    }
}
