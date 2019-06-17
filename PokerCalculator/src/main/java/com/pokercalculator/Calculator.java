package com.pokercalculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class Calculator {
    private int[] winners;
    private int games;

    public Calculator(String filePath) {
        winners = new int[] { 0, 0, 0 };
        games = 0;
        calculate(filePath);
    }

    public int[] getWinners() {
        return winners;
    }

    public int getGames() {
        return games;
    }

    public boolean printResults(String filePath) {
        try (final PrintWriter printWriter = new PrintWriter(new FileWriter(filePath))) {
            int[] winners = getWinners();
            int games = getGames();

            printWriter.println("Total Games: " + games);

            for (int i = 0; i < 3; i++) {
                final String s = (i + 1) + ": " + winners[i];
                printWriter.println(s);
            }

            String p1Percentage = String.format("%.2f", ((float) winners[0] / games) * 100);
            String p2Percentage = String.format("%.2f", ((float) winners[1] / games) * 100);

            printWriter.println("4:");
            printWriter.println("--------- PLAYER 1 --------- | --------- PLAYER 2 ---------");
            printWriter.println(
                    "           " + p1Percentage + "%            |             " + p2Percentage + "%          ");
            printWriter.println("---------------------------- | ----------------------------");

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            printWriter.print("Date and Time: " + format.format(new Date()));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }

    private void calculate(String filePath) {
        final String pokerData = getPokerData(filePath);

        if (pokerData == null) {
            return;
        }

        final String[] pokerArray = pokerData.split("-");

        games = pokerArray.length;

        for (String game : pokerArray) {
            final String[] gameArray = game.split(" ");
            final int n = gameArray.length + 1;

            final String handString1 = String.join(" ", Arrays.copyOfRange(gameArray, 0, n / 2));
            final String handString2 = String.join(" ", Arrays.copyOfRange(gameArray, n / 2, gameArray.length));

            final Hand hand1 = new Hand(Hand.fromString(handString1));
            final Hand hand2 = new Hand(Hand.fromString(handString2));

            winners[checkWinner(hand1, hand2)]++;
        }
    }

    private String getPokerData(String filePath) {
        try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String[] lines = reader.lines().toArray(String[]::new);

            return String.join("-", lines);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }

    private int breakTie(Hand hand1, Hand hand2) {
        final HandRanks rank = hand1.getHandRank();

        if (rank == HandRanks.ROYAL_FLUSH) {
            return 2;
        }

        final Stream<Card> cardsInHand1 = Arrays.stream(hand1.getCards());
        final Stream<Card> cardsInHand2 = Arrays.stream(hand2.getCards());

        final int[] rankNumbers1 = cardsInHand1.mapToInt(card -> card.getRank().getRankNumber()).toArray();
        Arrays.sort(rankNumbers1);

        final int[] rankNumbers2 = cardsInHand2.mapToInt(card -> card.getRank().getRankNumber()).toArray();
        Arrays.sort(rankNumbers2);

        if (rank == HandRanks.STRAIGHT_FLUSH || rank == HandRanks.STRAIGHT) {
            return breakTieStraight(rankNumbers1, rankNumbers2);
        }

        if (rank == HandRanks.FLUSH || rank == HandRanks.HIGH_CARD) {
            return breakTieHighCard(rankNumbers1, rankNumbers2);
        }

        final int tieBreaker = breakTieRestHelper(rank);

        if (tieBreaker == -1) {
            return 2;
        }

        return breakTieRest(rankNumbers1, rankNumbers2, tieBreaker);
    }

    private int breakTieRestHelper(HandRanks rank) {
        switch (rank) {
        case FOUR_OF_A_KIND:
            return 4;
        case FULL_HOUSE:
        case THREE_OF_A_KIND:
            return 3;
        case TWO_PAIRS:
        case ONE_PAIR:
            return 2;
        default:
            return -1;
        }
    }

    private int breakTieRest(int[] rankNumbers1, int[] rankNumbers2, int tieBreaker) {
        final Map<Integer, Integer> frequencyMap1 = Utility.getFrequencyMap(rankNumbers1);
        final Map<Integer, Integer> frequencyMap2 = Utility.getFrequencyMap(rankNumbers2);

        final List<Integer> rankNumbersList1 = new LinkedList<Integer>(Utility.toListInteger(rankNumbers1));
        final List<Integer> rankNumbersList2 = new LinkedList<Integer>(Utility.toListInteger(rankNumbers2));

        final int frequentEqualN1 = frequencyMap1.keySet().stream().filter(key -> frequencyMap1.get(key) == tieBreaker)
                .findAny().get();
        final int frequentEqualN2 = frequencyMap2.keySet().stream().filter(key -> frequencyMap2.get(key) == tieBreaker)
                .findAny().get();

        final int winners = checkWinnerHelper(frequentEqualN1, frequentEqualN2);

        if (winners != 2) {
            return winners;
        }

        rankNumbersList1.removeAll(Arrays.asList(frequentEqualN1));
        rankNumbersList2.removeAll(Arrays.asList(frequentEqualN2));

        return breakTieHighCard(rankNumbersList1, rankNumbersList2);
    }

    private int breakTieStraight(int[] rankNumbers1, int[] rankNumbers2) {
        final List<Integer> ranksList1 = Utility.toListInteger(Utility.replaceAceForOneIf(rankNumbers1));
        final List<Integer> ranksList2 = Utility.toListInteger(Utility.replaceAceForOneIf(rankNumbers2));

        return checkWinnerHelper(Collections.max(ranksList1), Collections.max(ranksList2));
    }

    private int breakTieHighCard(int[] rankNumbers1, int[] rankNumbers2) {
        int winners = 2;
        for (int i = rankNumbers1.length - 1; i >= 0; i--) {
            winners = checkWinnerHelper(rankNumbers1[i], rankNumbers2[i]);
            if (winners != 2) {
                return winners;
            }
        }
        return winners;
    }

    private int breakTieHighCard(List<Integer> rankNumbersList1, List<Integer> rankNumbersList2) {
        final int[] rankNumbers1 = Utility.toIntArray(rankNumbersList1);
        final int[] rankNumbers2 = Utility.toIntArray(rankNumbersList2);

        return breakTieHighCard(rankNumbers1, rankNumbers2);
    }

    private int checkWinner(Hand hand1, Hand hand2) {
        final int hand1Rank = hand1.getHandRank().ordinal();
        final int hand2Rank = hand2.getHandRank().ordinal();

        int winners = checkWinnerHelper(hand1Rank, hand2Rank);

        return winners != 2 ? winners : breakTie(hand1, hand2);
    }

    private int checkWinnerHelper(int num1, int num2) {
        int resultComparer = Integer.compare(num1, num2);

        return (resultComparer + 2) % 3;
    }

    @Override
    public String toString() {
        final int[] winners = getWinners();
        final int games = getGames();

        return "Total Games: " + games + "\n" + "Player 1: " + winners[0] + "\n" + "Player 2: " + winners[1] + "\n"
                + "Tie: " + winners[2];
    }
}
