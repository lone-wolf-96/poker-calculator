package com.pokercalculator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class CalculatorTest {
    private static final String FILE_PATH_SOURCE = System.getProperty("user.dir") + "\\" + "pokerdata.txt";

    private static final Calculator CALCULATOR = new Calculator(FILE_PATH_SOURCE);

    @Test
    public void testCalculator() {
        new Calculator(FILE_PATH_SOURCE);
    }

    @Test
    public void testGetWinners() {
        final int[] winners = { 376, 624, 0 };
        assertArrayEquals(winners, CALCULATOR.getWinners());
    }

    @Test
    public void testGetGames() {
        assertEquals(1000, CALCULATOR.getGames());
    }

    @Test
    public void testPrintResults() {
        final String filePathTarget = System.getProperty("user.dir") + "\\";
        assertTrue(CALCULATOR.printResults(filePathTarget));
    }

    @Test
    public void testToString() {
        assertEquals("Total Games: 1000\nPlayer 1: 376\nPlayer 2: 624\nTie: 0", CALCULATOR.toString());
    }
}
