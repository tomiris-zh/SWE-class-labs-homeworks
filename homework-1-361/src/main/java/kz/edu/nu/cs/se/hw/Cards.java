package kz.edu.nu.cs.se.hw;

import java.util.ArrayList;
import java.util.Arrays;

public class Cards implements Comparable {
    public int rank;
    public char suit;
    final ArrayList<String> ranks = new ArrayList<String>(Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"));

    public Cards(String card) {
        this.rank = ranks.indexOf(card.substring(0, card.length()-1));
        this.suit = card.charAt(card.length()-1);
    }

    @Override
    public int compareTo(Object o) {
        return this.rank - ((Cards) o).rank;
    }
}
