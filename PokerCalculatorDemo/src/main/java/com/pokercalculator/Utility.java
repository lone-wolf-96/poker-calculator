package com.pokercalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.TreeMap;

public final class Utility {
    public static int[] replaceAceForOneIf(int[] rankNumbers) {
        final boolean hasAceAndTwo = Arrays.binarySearch(rankNumbers, 14) > 0
                && Arrays.binarySearch(rankNumbers, 2) > 0;

        if (hasAceAndTwo) {
            List<Integer> ranksList = toListInteger(rankNumbers);

            Collections.replaceAll(ranksList, 14, 1);

            rankNumbers = toIntArray(ranksList);

            Arrays.sort(rankNumbers);
        }

        return rankNumbers;
    }

    public static List<Integer> toListInteger(int[] array) {
        return toListInteger(Arrays.stream(array).boxed());
    }

    public static List<Integer> toListInteger(Stream<Integer> streamInteger) {
        return Arrays.asList(streamInteger.toArray(Integer[]::new));
    }

    public static int[] toIntArray(List<Integer> list) {
        return list.stream().mapToInt(i -> i).toArray();
    }

    public static Map<Integer, Integer> getFrequencyMap(int[] rankNumbers) {
        final Map<Integer, Integer> integersCount = new TreeMap<Integer, Integer>();

        for (int i : rankNumbers) {
            if (!integersCount.containsKey(i)) {
                integersCount.put(i, 1);
            } else {
                integersCount.put(i, integersCount.get(i) + 1);
            }
        }

        return integersCount;
    }
}
