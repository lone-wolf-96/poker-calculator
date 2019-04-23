package com.pokercalculator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public final class HandTest {
        @Test
        public void testHand() {
                new Hand(new Card[] { new Card(Ranks.JACK, Suits.SPADES), new Card(Ranks.FOUR, Suits.HEARTS),
                                new Card(Ranks.FIVE, Suits.DIAMONDS), new Card(Ranks.NINE, Suits.DIAMONDS),
                                new Card(Ranks.JACK, Suits.CLUBS) });
        }

        @Test
        public void testGetCards() {
                final Card[] cards1 = new Card[] { new Card(Ranks.JACK, Suits.SPADES),
                                new Card(Ranks.FOUR, Suits.HEARTS), new Card(Ranks.FIVE, Suits.DIAMONDS),
                                new Card(Ranks.NINE, Suits.DIAMONDS), new Card(Ranks.JACK, Suits.CLUBS) };
                assertArrayEquals(cards1, new Hand(cards1).getCards());

                final Card[] cards2 = new Card[] { new Card(Ranks.QUEEN, Suits.CLUBS), new Card(Ranks.TEN, Suits.CLUBS),
                                new Card(Ranks.SIX, Suits.HEARTS), new Card(Ranks.FIVE, Suits.SPADES),
                                new Card(Ranks.THREE, Suits.HEARTS) };
                assertArrayEquals(cards2, new Hand(cards2).getCards());
        }

        @Test
        public void testGetHandRank() {
                final Card[] cards1 = new Card[] { new Card(Ranks.JACK, Suits.SPADES),
                                new Card(Ranks.FOUR, Suits.HEARTS), new Card(Ranks.FIVE, Suits.DIAMONDS),
                                new Card(Ranks.NINE, Suits.DIAMONDS), new Card(Ranks.JACK, Suits.CLUBS) };
                assertEquals(HandRanks.ONE_PAIR, new Hand(cards1).getHandRank());

                final Card[] cards2 = new Card[] { new Card(Ranks.QUEEN, Suits.CLUBS), new Card(Ranks.TEN, Suits.CLUBS),
                                new Card(Ranks.SIX, Suits.HEARTS), new Card(Ranks.FIVE, Suits.SPADES),
                                new Card(Ranks.THREE, Suits.HEARTS) };
                assertEquals(HandRanks.HIGH_CARD, new Hand(cards2).getHandRank());
        }

        @Test
        public void testFromString() {
                final Card[] cards = Hand.fromString("6C 3D TH KC 4S");

                final Card sixOfClubs = cards[0];
                final Card threeOfDiamonds = cards[1];
                final Card tenOfHearts = cards[2];
                final Card kingOfClubs = cards[3];
                final Card fourOfSpades = cards[4];

                assertEquals(Ranks.SIX, sixOfClubs.getRank());
                assertEquals(Suits.CLUBS, sixOfClubs.getSuit());

                assertEquals(Ranks.THREE, threeOfDiamonds.getRank());
                assertEquals(Suits.DIAMONDS, threeOfDiamonds.getSuit());

                assertEquals(Ranks.TEN, tenOfHearts.getRank());
                assertEquals(Suits.HEARTS, tenOfHearts.getSuit());

                assertEquals(Ranks.KING, kingOfClubs.getRank());
                assertEquals(Suits.CLUBS, kingOfClubs.getSuit());

                assertEquals(Ranks.FOUR, fourOfSpades.getRank());
                assertEquals(Suits.SPADES, fourOfSpades.getSuit());
        }

        @Test
        public void testToString() {
                final Card[] cards1 = new Card[] { new Card(Ranks.JACK, Suits.SPADES),
                                new Card(Ranks.FOUR, Suits.HEARTS), new Card(Ranks.FIVE, Suits.DIAMONDS),
                                new Card(Ranks.NINE, Suits.DIAMONDS), new Card(Ranks.JACK, Suits.CLUBS) };
                assertEquals("JS 4H 5D 9D JC", new Hand(cards1).toString());

                final Card[] cards2 = new Card[] { new Card(Ranks.QUEEN, Suits.CLUBS), new Card(Ranks.TEN, Suits.CLUBS),
                                new Card(Ranks.SIX, Suits.HEARTS), new Card(Ranks.FIVE, Suits.SPADES),
                                new Card(Ranks.THREE, Suits.HEARTS) };
                assertEquals("QC TC 6H 5S 3H", new Hand(cards2).toString());
        }

        @Test
        public void testToStringName() {
                final Card[] cards1 = new Card[] { new Card(Ranks.JACK, Suits.SPADES),
                                new Card(Ranks.FOUR, Suits.HEARTS), new Card(Ranks.FIVE, Suits.DIAMONDS),
                                new Card(Ranks.NINE, Suits.DIAMONDS), new Card(Ranks.JACK, Suits.CLUBS) };
                assertEquals("JACK OF SPADES\nFOUR OF HEARTS\nFIVE OF DIAMONDS\nNINE OF DIAMONDS\nJACK OF CLUBS",
                                new Hand(cards1).toStringName());

                final Card[] cards2 = new Card[] { new Card(Ranks.QUEEN, Suits.CLUBS), new Card(Ranks.TEN, Suits.CLUBS),
                                new Card(Ranks.SIX, Suits.HEARTS), new Card(Ranks.FIVE, Suits.SPADES),
                                new Card(Ranks.THREE, Suits.HEARTS) };
                assertEquals("QUEEN OF CLUBS\nTEN OF CLUBS\nSIX OF HEARTS\nFIVE OF SPADES\nTHREE OF HEARTS",
                                new Hand(cards2).toStringName());
        }
}
