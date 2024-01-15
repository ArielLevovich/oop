package src;
import java.util.Comparator;
import java.util.List;


 public class ListSizeComparator implements Comparator<ConcreatePiece>{
    @Override
    public int compare(ConcreatePiece o1, ConcreatePiece o2) {
        List<Position> list1 = o1.getMovesHistory();
        List<Position> list2 = o2.getMovesHistory();
        return Integer.compare(list1.size(), list2.size());
    }
 }
