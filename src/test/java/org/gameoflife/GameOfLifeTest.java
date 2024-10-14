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
        assertArrayEquals(new char[][] {
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', 'x', '.', '.', '.', '.', '.'},
                {'.', '.', 'x', '.', '.', '.', '.', '.'},
                {'.', '.', 'x', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
        }, field);
        br.close();
        Files.deleteIfExists(path);
    }

}
