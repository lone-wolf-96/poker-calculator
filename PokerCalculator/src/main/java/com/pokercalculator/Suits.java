package com.pokercalculator;

import java.util.HashMap;
import java.util.Map;

public enum Suits {
    SPADES('S'), HEARTS('H'), CLUBS('C'), DIAMONDS('D');

    private final char _suit;

    private static final Map<Character, Suits> SUIT_BY_VALUE = new HashMap<Character, Suits>();

    static {
        for (Suits suit : Suits.values()) {
            SUIT_BY_VALUE.put(suit.getSuitValue(), suit);
        }
    }

    public char getSuitValue() {
        return _suit;
    }

    public static Suits getSuitBySuitValue(char suitValue) {
        return SUIT_BY_VALUE.get(suitValue);
    }

    private Suits(char suit) {
        this._suit = suit;
    }
}
