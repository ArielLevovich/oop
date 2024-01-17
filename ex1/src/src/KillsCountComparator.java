package src;

 import java.util.Comparator;

 class KillsCountComp implements Comparator<ConcreatePiece> {
     @Override
     public int compare(ConcreatePiece o1, ConcreatePiece o2) {
         // First, compare by killsCount in descending order
         int killsCompare = Integer.compare(((Pawn)o2).getKillsCount(),((Pawn)o1).getKillsCount());
         if (killsCompare != 0) {
             return killsCompare;
         }

         // If killsCount is equal, compare by serial title
         int titleCompare = o1.getTitle().compareTo(o2.getTitle());
         if (titleCompare != 0) {
             return titleCompare;
         }

         // If both killsCount and serial title are equal, compare by winning team
         //return extractWin();
         return 1;
     }
 }
