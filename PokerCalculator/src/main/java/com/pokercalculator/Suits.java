package com.pokercalculator;

import java.util.HashMap;
import java.util.Map;

public enum Suits {
    SPADES('S'), HEARTS('H'), CLUBS('C'), DIAMONDS('D');

    private char suit;

    private static Map<Character, Suits> suitBySuitValue = new HashMap<Character, Suits>();

    static {
        for (Suits suit : Suits.values()) {
            suitBySuitValue.put(suit.getSuitValue(), suit);
        }
    }

    public char getSuitValue() {
        return suit;
    }

    public static Suits getSuitBySuitValue(char suitValue) {
        return suitBySuitValue.get(suitValue);
    }

    private Suits(char suit) {
        this.suit = suit;
    }
}
