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
        final Ranks rank = Ranks.getRankByRankValue(input.charAt(0));
        final Suits suit = Suits.getSuitBySuitValue(input.charAt(1));

        if (rank == null) {
            throw new IllegalArgumentException("Invalid rank.");
        }
        if (suit == null) {
            throw new IllegalArgumentException("Invalid suit.");
        }

        return new Card(rank, suit);
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
