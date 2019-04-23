package com.pokercalculator;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public final class CardTest {
    @Test
    public void testCard() {
        new Card(Ranks.JACK, Suits.SPADES);
    }

    @Test
    public void testGetRank() {
        assertEquals(Ranks.TWO, new Card(Ranks.TWO, Suits.DIAMONDS).getRank());
        assertEquals(Ranks.FIVE, new Card(Ranks.FIVE, Suits.SPADES).getRank());
        assertEquals(Ranks.TEN, new Card(Ranks.TEN, Suits.CLUBS).getRank());
        assertEquals(Ranks.SIX, new Card(Ranks.SIX, Suits.HEARTS).getRank());
        assertEquals(Ranks.SEVEN, new Card(Ranks.SEVEN, Suits.DIAMONDS).getRank());
    }

    @Test
    public void testGetSuit() {
        assertEquals(Suits.DIAMONDS, new Card(Ranks.SIX, Suits.DIAMONDS).getSuit());
        assertEquals(Suits.SPADES, new Card(Ranks.ACE, Suits.SPADES).getSuit());
        assertEquals(Suits.CLUBS, new Card(Ranks.QUEEN, Suits.CLUBS).getSuit());
        assertEquals(Suits.HEARTS, new Card(Ranks.THREE, Suits.HEARTS).getSuit());
    }

    @Test
    public void testFromString() {
        final Card tenOfClubs = Card.fromString("TC");
        assertEquals(Ranks.TEN, tenOfClubs.getRank());
        assertEquals(Suits.CLUBS, tenOfClubs.getSuit());

        final Card jackOfHearts = Card.fromString("JH");
        assertEquals(Ranks.JACK, jackOfHearts.getRank());
        assertEquals(Suits.HEARTS, jackOfHearts.getSuit());

        final Card eightOfDiamonds = Card.fromString("8D");
        assertEquals(Ranks.EIGHT, eightOfDiamonds.getRank());
        assertEquals(Suits.DIAMONDS, eightOfDiamonds.getSuit());
    }

    @Test
    public void testToString() {
        assertEquals("7D", new Card(Ranks.SEVEN, Suits.DIAMONDS).toString());
        assertEquals("KH", new Card(Ranks.KING, Suits.HEARTS).toString());
    }

    @Test
    public void testToStringName() {
        assertEquals("TWO OF CLUBS", new Card(Ranks.TWO, Suits.CLUBS).toStringName());
        assertEquals("ACE OF SPADES", new Card(Ranks.ACE, Suits.SPADES).toStringName());
    }
}
