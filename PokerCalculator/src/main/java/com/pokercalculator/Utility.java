package com.pokercalculator;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public final class Utility {
    public static int[] replaceAceForOneIf(int[] rankNumbers) {
        final boolean hasAceAndTwo = Arrays.binarySearch(rankNumbers, 14) > 0
                && Arrays.binarySearch(rankNumbers, 2) > 0;

        if (hasAceAndTwo) {
            rankNumbers = mapToIntArray(rankNumbers, (rankN -> (rankN == 14) ? 1 : rankN));
            Arrays.sort(rankNumbers);
        }

        return rankNumbers;
    }

    public static int[] mapToIntArray(int[] rankNumbers, ToIntFunction<? super Integer> intFunction) {
        return mapToIntArray(Arrays.stream(rankNumbers).boxed(), intFunction);
    }

    public static int[] mapToIntArray(Stream<Integer> streamNumbers, ToIntFunction<? super Integer> intFunction) {
        return streamNumbers.mapToInt(intFunction).toArray();
    }

    public static int[] mapToIntArray(Stream<Integer> streamNumbers) {
        return streamNumbers.mapToInt(i -> i).toArray();
    }

    public static int[] filterIntArray(int[] rankNumbers, Predicate<? super Integer> intPredicate) {
        return Arrays.stream(rankNumbers).boxed().filter(intPredicate).mapToInt(i -> i).toArray();
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
