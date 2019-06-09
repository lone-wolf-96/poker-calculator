package com.pokercalculator;

import java.util.HashMap;
import java.util.Map;

public enum Ranks {
    TWO(2, '2'), THREE(3, '3'), FOUR(4, '4'), FIVE(5, '5'), SIX(6, '6'), SEVEN(7, '7'), EIGHT(8, '8'), NINE(9, '9'),
    TEN(10, 'T'), JACK(11, 'J'), QUEEN(12, 'Q'), KING(13, 'K'), ACE(14, 'A');

    private int rankNumber;
    private char rankValue;

    private static Map<Character, Ranks> rankByRankValue = new HashMap<Character, Ranks>();

    static {
        for (Ranks rank : Ranks.values()) {
            rankByRankValue.put(rank.getRankValue(), rank);
        }
    }

    private Ranks(int rankNumber, char rankValue) {
        this.rankNumber = rankNumber;
        this.rankValue = rankValue;
    }

    public char getRankValue() {
        return rankValue;
    }

    public int getRankNumber() {
        return rankNumber;
    }

    public static Ranks getRankByRankValue(char rankValue) {
        return rankByRankValue.get(rankValue);
    }
}
