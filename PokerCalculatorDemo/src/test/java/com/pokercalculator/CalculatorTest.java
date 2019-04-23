package com.pokercalculator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class CalculatorTest {
    private final String FILE_PATH_SOURCE = System.getProperty("user.home") + "/Desktop/poker-calculator/pokerdata.txt";

    @Test
    public void testHand() {
        new Calculator(FILE_PATH_SOURCE);
    }

    @Test
    public void testGetWinners() {
        final int[] winners = { 376, 624, 0 };

        assertArrayEquals(winners, new Calculator(FILE_PATH_SOURCE).getWinners());
    }

    @Test
    public void testGetGames() {
        assertEquals(1000, new Calculator(FILE_PATH_SOURCE).getGames());
    }

    @Test
    public void testPrintResults() {
        final String filePathTarget = System.getProperty("user.home") + "/Desktop/poker_results.txt";

        assertTrue(new Calculator(FILE_PATH_SOURCE).printResults(filePathTarget));
    }

    @Test
    public void testToString() {
        assertEquals("Total Games: 1000\nPlayer 1: 376\nPlayer 2: 624\nTie: 0", new Calculator(FILE_PATH_SOURCE).toString());
    }
}
