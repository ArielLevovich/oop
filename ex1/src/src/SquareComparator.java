package src;

import java.util.Comparator;

public class SquareComparator implements Comparator<Square> {
    @Override
    public int compare(Square o1, Square o2) {
        if (o1 == o2) {
            return 0;
        }
        // compare by set size by descending order.
        int compareBySetSize = Integer.compare(o2.getPiecesTitles().size(), o1.getPiecesTitles().size());
        if (compareBySetSize != 0) {
            return compareBySetSize;
        }

        // compare by x coordinate by ascending order.
        int compareByX = Integer.compare(o1.getX(), o2.getX());
        if (compareByX != 0) {
            return compareByX;
        }
        // compare by y coordinate by ascending order.
        return Integer.compare(o1.getY(), o2.getY());
    }
}
