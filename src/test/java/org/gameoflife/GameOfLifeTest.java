package org.gameoflife;

import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {

    private static final String INPUT_FILE = "test_input.txt";

    @Test
    void testReadField() throws IOException {
        String input = "3\n8 5\n........\n..x.....\n..x.....\n..x.....\n........";

        Path path = Paths.get(INPUT_FILE);
        Files.write(path, input.getBytes());

        // Зчитування даних з файлу
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));

        // Зчитування поколінь
        int generations = Integer.parseInt(br.readLine());

        // Зчитування розмірів поля
        String[] inputParams = br.readLine().split(" ");
        int cols = Integer.parseInt(inputParams[0]);
        int rows = Integer.parseInt(inputParams[1]);

        // Зчитування початкового поля
        char[][] field = GameOfLife.readField(br, rows, cols);
        assertEquals(3, generations);
        assertEquals(8, cols);
        assertEquals(5, rows);
        assertArrayEquals(new char[][]{
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', 'x', '.', '.', '.', '.', '.'},
                {'.', '.', 'x', '.', '.', '.', '.', '.'},
                {'.', '.', 'x', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
        }, field);
        br.close();
        Files.deleteIfExists(path);
    }

    @Test
    void testCountNeighbors() {
        char[][] field = {
                {'x', '.', '.', '.', '.', 'x', '.', 'x'},
                {'x', '.', 'x', '.', '.', '.', '.', 'x'},
                {'x', '.', 'x', '.', 'x', '.', 'x', '.'},
                {'.', '.', 'x', '.', '.', '.', '.', 'x'},
                {'x', '.', '.', '.', '.', 'x', '.', 'x'},
        };
        assertEquals(5, GameOfLife.countNeighbors(field, 0, 0));
        assertEquals(1, GameOfLife.countNeighbors(field, 0, 2));
        assertEquals(3, GameOfLife.countNeighbors(field, 2, 0));
        assertEquals(2, GameOfLife.countNeighbors(field, 4, 4));
        assertEquals(4, GameOfLife.countNeighbors(field, 3, 7));
        assertEquals(4, GameOfLife.countNeighbors(field, 4, 7));
    }

    @Test
    void testGetNextCellState() {
        char[][] field = {
                {'x', '.', '.', '.', '.', 'x', '.', 'x'},
                {'x', '.', 'x', '.', '.', '.', '.', 'x'},
                {'x', '.', 'x', '.', 'x', '.', 'x', '.'},
                {'.', '.', 'x', '.', '.', '.', '.', 'x'},
                {'x', '.', '.', '.', '.', 'x', '.', 'x'},
        };
        assertEquals('.', GameOfLife.getNextCellState(field, 0, 0)); // Dead cell 5 > 3 neighbours
        assertEquals('.', GameOfLife.getNextCellState(field, 0, 2)); // Dead cell 1 < 2 neighbours
        assertEquals('x', GameOfLife.getNextCellState(field, 2, 0)); // Alive cell - 3
        assertEquals('.', GameOfLife.getNextCellState(field, 0, 1)); // Dead cell with 4 alive neighbors
        assertEquals('x', GameOfLife.getNextCellState(field, 3, 3)); // New Life
        assertEquals('.', GameOfLife.getNextCellState(field, 4, 4)); // Dead cell with 2 alive neighbors
        assertEquals('.', GameOfLife.getNextCellState(field, 3, 7)); // Dead cell 4 > 3 neighbours
        assertEquals('.', GameOfLife.getNextCellState(field, 4, 7)); // Dead cell 4 > 3 neighbours
    }

    @Test
    void testNextGeneration() {
        char[][] field = {
                {'.', '.', '.', '.', '.'},
                {'.', '.', 'x', '.', '.'},
                {'.', 'x', 'x', 'x', '.'},
                {'.', '.', '.', '.', '.'}
        };
        char[][] nextGen1 = GameOfLife.nextGeneration(field);
        assertArrayEquals(new char[][] {
                {'.', '.', '.', '.', '.'},
                {'.', 'x', 'x', 'x', '.'},
                {'.', 'x', 'x', 'x', '.'},
                {'.', '.', 'x', '.', '.'}
        }, nextGen1);

        char[][] nextGen2 = GameOfLife.nextGeneration(nextGen1);
        assertArrayEquals(new char[][] {
                {'.', 'x', '.', 'x', '.'},
                {'.', 'x', '.', 'x', '.'},
                {'.', '.', '.', '.', '.'},
                {'.', 'x', 'x', 'x', '.'}
        }, nextGen2);

        char[][] nextGen3 = GameOfLife.nextGeneration(nextGen2);
        assertArrayEquals(new char[][] {
                {'x', 'x', '.', 'x', 'x'},
                {'.', '.', '.', '.', '.'},
                {'.', 'x', '.', 'x', '.'},
                {'.', 'x', '.', 'x', '.'}
        }, nextGen3);
    }

    @Test
    void testPlayGame() {
        char[][] field = {
                {'.', '.', '.', '.', '.'},
                {'.', '.', 'x', '.', '.'},
                {'.', 'x', 'x', 'x', '.'},
                {'.', '.', '.', '.', '.'}
        };
        char[][] finalField = GameOfLife.playGame(field, 3);
        assertArrayEquals(new char[][] {
                {'x', 'x', '.', 'x', 'x'},
                {'.', '.', '.', '.', '.'},
                {'.', 'x', '.', 'x', '.'},
                {'.', 'x', '.', 'x', '.'}
        }, finalField);
        char[][] field1 = {
                {'x', '.', '.', '.', '.', 'x', '.', 'x'},
                {'x', '.', 'x', '.', '.', '.', '.', 'x'},
                {'x', '.', 'x', '.', 'x', '.', 'x', '.'},
                {'.', '.', 'x', '.', '.', '.', '.', 'x'},
                {'x', '.', '.', '.', '.', 'x', '.', 'x'},
        };
        char[][] finalField1 = GameOfLife.playGame(field1, 1);
        assertArrayEquals(new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'x', '.', 'x', '.', '.'},
                {'x', '.', 'x', '.', '.', '.', 'x', '.'},
                {'.', '.', '.', 'x', '.', 'x', '.', '.'},
                {'.', 'x', '.', '.', '.', '.', '.', '.'},
        }, finalField1);
    }
}