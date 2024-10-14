package org.gameoflife;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {
    @Test
    void testCorrect1() {
        int sum = 4;
        assertEquals(2 + 2, sum);
    }

    @Test
    void testCorrect2() {
        int sum = 4;
        assertEquals(2 * 2, sum);
    }

}