package org.gameoflife;

import java.io.*;
import java.util.*;

class GameOfLife {

    // Метод для зчитування поля з файлу
    public static char[][] readField(BufferedReader br, int rows, int cols) throws IOException {
        char[][] field = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            String line = br.readLine();
            field[i] = line.toCharArray();
        }
        return field;
    }

    // Метод для підрахунку сусідів клітини
    public static int countNeighbors(char[][] field, int x, int y) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        int rows = field.length;
        int cols = field[0].length;
        int count = 0;

        for (int i = 0; i < 8; i++) {
            int nx = (x + dx[i] + rows) % rows;
            int ny = (y + dy[i] + cols) % cols;
            if (field[nx][ny] == 'x') {
                count++;
            }
        }

        return count;
    }

    // Метод для отримання стану клітини у наступному поколінні
    public static char getNextCellState(char[][] field, int x, int y) {
        int liveNeighbors = countNeighbors(field, x, y);
        if (field[x][y] == 'x') {
            if (liveNeighbors == 2 || liveNeighbors == 3) {
                return 'x'; // Клітина залишається живою
            } else {
                return '.'; // Клітина помирає
            }
        } else {
            if (liveNeighbors == 3) {
                return 'x'; // Народження нової клітини
            } else {
                return '.'; // Клітина залишається мертвою
            }
        }
    }

    // Метод для обчислення наступного покоління
    public static char[][] nextGeneration(char[][] field) {
        int rows = field.length;
        int cols = field[0].length;
        char[][] newField = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newField[i][j] = getNextCellState(field, i, j);
            }
        }

        return newField;
    }

    // Основний метод гри
    public static char[][] playGame(char[][] field, int generations) {
        for (int i = 0; i < generations; i++) {
            field = nextGeneration(field);
        }
        return field;
    }

    // Метод для запису поля у файл
    public static void writeField(PrintWriter pw, char[][] field) {
        for (char[] row : field) {
            pw.println(new String(row));
        }
    }

    public static void main(String[] args) throws IOException {

        // Зчитування даних з файлу
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        int generations = Integer.parseInt(br.readLine());
        
        String[] inputParams = br.readLine().split(" ");

        int cols = Integer.parseInt(inputParams[0]);
        int rows = Integer.parseInt(inputParams[1]);

        // Зчитування початкового поля
        char[][] field = readField(br, rows, cols);
        br.close();

        // Граємо в гру і отримуємо поле після заданої кількості поколінь
        char[][] finalField = playGame(field, generations);

        // Запис результатів у файл
        PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
        writeField(pw, finalField);
        pw.close();
    }
}
