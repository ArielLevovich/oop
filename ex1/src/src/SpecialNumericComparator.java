package src;

import java.util.Comparator;

class SpecialNumericComparator implements Comparator<ConcreatePiece> {
    @Override
    public int compare(ConcreatePiece o1, ConcreatePiece o2) {
        String s1 = o1.getTitle();
        String s2 = o2.getTitle();
        // Compare the first characters
        int firstCharComparison = Character.compare(s1.charAt(0), s2.charAt(0));
        if (firstCharComparison != 0) {
            return firstCharComparison;
        }

        // Extract numeric parts
        int num1 = Integer.parseInt(s1.substring(1));
        int num2 = Integer.parseInt(s2.substring(1));

        // Single-digit vs multi-digit comparison
        boolean isSingleDigit1 = (num1 >= 1 && num1 <= 9);
        boolean isSingleDigit2 = (num2 >= 1 && num2 <= 9);

        if (isSingleDigit1 && !isSingleDigit2) {
            return -1; // Single-digit numbers are considered smaller
        } else if (!isSingleDigit1 && isSingleDigit2) {
            return 1; // Multi-digit numbers are considered larger
        } else {
            // Both numbers are of the same type, compare numerically
            return Integer.compare(num1, num2);
        }
    }
}