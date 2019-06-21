package com.pokercalculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Stream;

public final class Calculator {
    private int[] _winners;
    private int _games;

    private String[] _messages;

    public Calculator(String filePath) {
        this._winners = new int[] { 0, 0, 0 };
        this._games = 0;
        calculate(filePath);
    }

    public int[] getWinners() {
        return this._winners;
    }

    public int getGames() {
        return this._games;
    }

    public boolean printResults(String filePath) {
        try (final PrintWriter printWriterReport = new PrintWriter(
                new FileWriter(filePath + "poker_results_report.txt"));
                final PrintWriter printWriterResult = new PrintWriter(new FileWriter(filePath + "poker_results.txt"))) {
            final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            final String dateFormat = "Date and Time: " + format.format(new Date());

            final int[] winners = getWinners();
            final int games = getGames();

            for (int i = 0; i < 3; i++) {
                printWriterReport.println((i + 1) + ": " + winners[i]);
            }

            String p1Percentage = String.format("%.2f", ((float) winners[0] / games) * 100);
            String p2Percentage = String.format("%.2f", ((float) winners[1] / games) * 100);

            printWriterReport.println("4:");
            printWriterReport.println("--------- PLAYER 1 --------- | --------- PLAYER 2 ---------");
            printWriterReport.println(
                    "           " + p1Percentage + "%            |             " + p2Percentage + "%          ");
            printWriterReport.println("---------------------------- | ----------------------------");

            printWriterReport.println("Total Games: " + games);

            for (String message : _messages) {
                printWriterResult.println(message);
            }

            printWriterReport.print(dateFormat);
            printWriterResult.print(dateFormat);

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

        this._games = pokerArray.length;
        this._messages = new String[getGames()];

        for (int i = 0; i < this._games; i++) {
            final String[] gameArray = pokerArray[i].split(" ");
            final int n = (gameArray.length + 1) / 2;

            final String handString1 = String.join(" ", Arrays.copyOfRange(gameArray, 0, n));
            final String handString2 = String.join(" ", Arrays.copyOfRange(gameArray, n, gameArray.length));

            final Hand hand1 = new Hand(Hand.fromString(handString1));
            final Hand hand2 = new Hand(Hand.fromString(handString2));

            final int winnerIndex = checkWinner(hand1, hand2);
            final String winnerMessage = (winnerIndex < 2 ? "Winner " + (winnerIndex + 1) : "Draw");

            this._messages[i] = (winnerMessage + " : " + hand1.getHandRank().toString().replace('_', ' ') + " - "
                    + hand2.getHandRank().toString().replace('_', ' '));

            this._winners[winnerIndex]++;
        }
    }

    private String getPokerData(String filePath) {
        try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            final String[] lines = reader.lines().toArray(String[]::new);

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

        final int frequentEqualN1 = frequencyMap1.keySet().stream().filter(key -> frequencyMap1.get(key) == tieBreaker)
                .findAny().get();
        final int frequentEqualN2 = frequencyMap2.keySet().stream().filter(key -> frequencyMap2.get(key) == tieBreaker)
                .findAny().get();

        final int winners = checkWinnerHelper(frequentEqualN1, frequentEqualN2);

        if (winners != 2) {
            return winners;
        }

        final Stream<Integer> streamRanks1 = Arrays.stream(rankNumbers1).boxed().filter(rankN -> rankN != frequentEqualN1);
        final Stream<Integer> streamRanks2 = Arrays.stream(rankNumbers2).boxed().filter(rankN -> rankN != frequentEqualN1);

        rankNumbers1 = Utility.mapToIntArray(streamRanks1);
        rankNumbers2 = Utility.mapToIntArray(streamRanks2);

        return breakTieHighCard(rankNumbers1, rankNumbers2);
    }

    private int breakTieStraight(int[] rankNumbers1, int[] rankNumbers2) {
        int max1 = Arrays.stream(Utility.replaceAceForOneIf(rankNumbers1)).max().getAsInt();
        int max2 = Arrays.stream(Utility.replaceAceForOneIf(rankNumbers2)).max().getAsInt();

        return checkWinnerHelper(max1, max2);
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

    private int checkWinner(Hand hand1, Hand hand2) {
        final int hand1Rank = hand1.getHandRank().ordinal();
        final int hand2Rank = hand2.getHandRank().ordinal();

        final int winners = checkWinnerHelper(hand1Rank, hand2Rank);

        return winners != 2 ? winners : breakTie(hand1, hand2);
    }

    private int checkWinnerHelper(int num1, int num2) {
        final int resultComparer = Integer.compare(num1, num2);

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
