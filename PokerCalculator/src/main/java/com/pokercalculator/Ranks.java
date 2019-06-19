package com.pokercalculator;

import java.util.HashMap;
import java.util.Map;

public enum Ranks {
    TWO(2, '2'), THREE(3, '3'), FOUR(4, '4'), FIVE(5, '5'), SIX(6, '6'), SEVEN(7, '7'), EIGHT(8, '8'), NINE(9, '9'),
    TEN(10, 'T'), JACK(11, 'J'), QUEEN(12, 'Q'), KING(13, 'K'), ACE(14, 'A');

    private final int _rankNumber;
    private final char _rankValue;

    private static final Map<Character, Ranks> RANK_BY_VALUE = new HashMap<Character, Ranks>();

    static {
        for (Ranks rank : Ranks.values()) {
            RANK_BY_VALUE.put(rank.getRankValue(), rank);
        }
    }

    public char getRankValue() {
        return _rankValue;
    }

    public int getRankNumber() {
        return _rankNumber;
    }

    public static Ranks getRankByRankValue(char rankValue) {
        return RANK_BY_VALUE.get(rankValue);
    }

    private Ranks(int rankNumber, char rankValue) {
        this._rankNumber = rankNumber;
        this._rankValue = rankValue;
    }
}
