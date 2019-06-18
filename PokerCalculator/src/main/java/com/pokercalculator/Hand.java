package com.pokercalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Hand {
    public static final int HAND_SIZE = 5;

    private final HandRanks handRank;
    private final Card[] cards;

    public Hand(Card[] cards) {
        this.cards = cards;
        this.handRank = evaluate();
    }

    public Card[] getCards() {
        return cards;
    }

    public HandRanks getHandRank() {
        return handRank;
    }

    public static Card[] fromString(String input) {
        final Stream<String> parts = Arrays.stream(input.split(" "));

        return parts.map(part -> Card.fromString(part)).toArray(Card[]::new);
    }

    public String toStringName() {
        final Stream<Card> cards = Arrays.stream(getCards());

        final String[] cardNames = cards.map(card -> card.toStringName()).toArray(String[]::new);

        return String.join("\n", cardNames);
    }

    private HandRanks evaluate() {
        final Supplier<Stream<Card>> cardsInHand = () -> Arrays.stream(getCards());

        final boolean isFlush = cardsInHand.get().map(card -> card.getSuit().getSuitValue()).distinct().count() == 1;

        final int[] rankNumbers = cardsInHand.get().mapToInt(card -> card.getRank().getRankNumber()).toArray();

        Arrays.sort(rankNumbers);

        final boolean isStraight = isStraight(rankNumbers);

        if (isFlush && isStraight) {
            if (isRoyal(cardsInHand.get())) {
                return HandRanks.ROYAL_FLUSH;
            }
            return HandRanks.STRAIGHT_FLUSH;
        }

        final Map<Integer, Integer> frequencyMap = Utility.getFrequencyMap(rankNumbers);

        if (frequencyMap.containsValue(4)) {
            return HandRanks.FOUR_OF_A_KIND;
        }

        final boolean isThreeOfAKind = frequencyMap.containsValue(3);
        final boolean isOnePair = frequencyMap.containsValue(2);

        if (isThreeOfAKind && isOnePair) {
            return HandRanks.FULL_HOUSE;
        }
        if (isFlush) {
            return HandRanks.FLUSH;
        }
        if (isStraight) {
            return HandRanks.STRAIGHT;
        }
        if (isThreeOfAKind) {
            return HandRanks.THREE_OF_A_KIND;
        }
        if (isOnePair) {
            if (Collections.frequency(frequencyMap.values(), 2) == 2) {
                return HandRanks.TWO_PAIRS;
            }
            return HandRanks.ONE_PAIR;
        }

        return HandRanks.HIGH_CARD;
    }

    private boolean isStraight(int[] rankNumbers) {
        rankNumbers = Utility.replaceAceForOneIf(rankNumbers);

        final int[] sequentialRanks = IntStream.range(rankNumbers[0], rankNumbers[0] + HAND_SIZE).toArray();

        return Arrays.equals(sequentialRanks, rankNumbers);
    }

    private boolean isRoyal(Stream<Card> cardsInHand) {
        final int ten = Ranks.TEN.getRankNumber();
        final int ace = Ranks.ACE.getRankNumber();

        return cardsInHand.allMatch(card -> {
            final int cardNumber = card.getRank().getRankNumber();

            return ten <= cardNumber && cardNumber <= ace;
        });
    }

    @Override
    public String toString() {
        final Stream<Card> cards = Arrays.stream(getCards());

        final String[] cardNames = cards.map(card -> card.toString()).toArray(String[]::new);

        return String.join(" ", cardNames);
    }
}
