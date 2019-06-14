package com.pokercalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.StringJoiner;

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

        final Card[] cards = parts.map(part -> Card.fromString(part)).toArray(Card[]::new);

        return cards;
    }

    public String toStringName() {
        final Card[] cards = getCards();

        final StringJoiner sj = new StringJoiner("\n");

        for (Card card : cards) {
            sj.add(card.toStringName());
        }

        return sj.toString();
    }

    private HandRanks evaluate() {
        final Supplier<Stream<Card>> cardsInHand = () -> Arrays.stream(getCards());

        final boolean isFlush = isFlush(cardsInHand.get());

        final int[] rankNumbers = cardsInHand.get().mapToInt(card -> card.getRank().getRankNumber()).toArray();
        Arrays.sort(rankNumbers);

        final boolean isStraight = isStraight(rankNumbers);

        if (isFlush && isStraight && isRoyal(cardsInHand.get())) {
            return HandRanks.ROYAL_FLUSH;
        }
        if (isFlush && isStraight) {
            return HandRanks.STRAIGHT_FLUSH;
        }

        final Map<Integer, Integer> frequencyMap = Utility.getFrequencyMap(rankNumbers);

        final boolean isFourOfAKind = isFourOfAKind(frequencyMap);

        if (isFourOfAKind) {
            return HandRanks.FOUR_OF_A_KIND;
        }

        final boolean isThreeOfAKind = isThreeOfAKind(frequencyMap);
        final boolean isOnePair = isOnePair(frequencyMap);

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

        final boolean isTwoPairs = isTwoPairs(frequencyMap);

        if (isTwoPairs) {
            return HandRanks.TWO_PAIRS;
        }
        if (isOnePair) {
            return HandRanks.ONE_PAIR;
        }

        return HandRanks.HIGH_CARD;
    }

    private boolean isTwoPairs(Map<Integer, Integer> frequencyMap) {
        final boolean isTwoPairs = Collections.frequency(frequencyMap.values(), 2) == 2;

        return isTwoPairs;
    }

    private boolean isOnePair(Map<Integer, Integer> frequencyMap) {
        final boolean isOnePair = frequencyMap.containsValue(2);

        return isOnePair;
    }

    private boolean isThreeOfAKind(Map<Integer, Integer> frequencyMap) {
        final boolean isThreeOfAKind = frequencyMap.containsValue(3);

        return isThreeOfAKind;
    }

    private boolean isFourOfAKind(Map<Integer, Integer> frequencyMap) {
        final boolean isFourOfAKind = frequencyMap.containsValue(4);

        return isFourOfAKind;
    }

    private boolean isFlush(Stream<Card> cardsInHand) {
        final Stream<Suits> suits = cardsInHand.map(card -> card.getSuit());

        final boolean isFlush = suits.distinct().count() == 1;

        return isFlush;
    }

    private boolean isStraight(int[] rankNumbers) {
        Utility.replaceAceForOneIf(rankNumbers);

        final int[] sequentialRanks = IntStream.range(rankNumbers[0], rankNumbers[0] + HAND_SIZE).toArray();

        final boolean isStraight = Arrays.equals(sequentialRanks, rankNumbers);

        return isStraight;
    }

    private boolean isRoyal(Stream<Card> cardsInHand) {
        final int ten = Ranks.TEN.getRankNumber();
        final int ace = Ranks.ACE.getRankNumber();

        final boolean isRoyal = cardsInHand.allMatch(card -> {
            final int cardNumber = card.getRank().getRankNumber();

            return ten <= cardNumber && cardNumber <= ace;
        });

        return isRoyal;
    }

    @Override
    public String toString() {
        final Card[] cards = getCards();

        final StringJoiner sj = new StringJoiner(" ");

        for (Card card : cards) {
            sj.add(card.toString());
        }

        return sj.toString();
    }
}
