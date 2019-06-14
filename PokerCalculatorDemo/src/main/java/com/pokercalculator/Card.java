package com.pokercalculator;

public final class Card {
    private final Ranks rank;
    private final Suits suit;

    public Card(Ranks rank, Suits suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Ranks getRank() {
        return rank;
    }

    public Suits getSuit() {
        return suit;
    }

    public static Card fromString(String input) {
        final char rank = input.charAt(0);
        final char suit = input.charAt(1);

        final Ranks rankValue = Ranks.getRankByRankValue(rank);
        final Suits suitValue = Suits.getSuitBySuitValue(suit);

        if (rankValue == null) {
            throw new IllegalArgumentException("Invalid rank.");
        }
        if (suitValue == null) {
            throw new IllegalArgumentException("Invalid suit.");
        }

        return new Card(rankValue, suitValue);
    }

    public String toStringName() {
        final String rankName = getRank().toString();
        final String suitName = getSuit().toString();

        return rankName + " OF " + suitName;
    }

    @Override
    public String toString() {
        final char rankChar = getRank().getRankValue();
        final char suitChar = getSuit().getSuitValue();

        return rankChar + "" + suitChar;
    }
}
