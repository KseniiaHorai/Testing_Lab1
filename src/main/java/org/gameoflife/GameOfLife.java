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
    public static void getNextCellState() {
        //TODO
    }

    // Метод для обчислення наступного покоління
    public static void nextGeneration() {
        //TODO
    }

    // Основний метод гри
    public static void playGame() {
        //TODO
    }

    // Метод для запису поля у файл
    public static void writeField() {
        //TODO
    }

    public static void main(String[] args) throws IOException {
        // Зчитування даних з файлу
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        int generations = Integer.parseInt(br.readLine());

        System.out.println(generations);
        System.out.println("generations");

        String[] inputParams = br.readLine().split(" ");

        int cols = Integer.parseInt(inputParams[0]);
        int rows = Integer.parseInt(inputParams[1]);
        System.out.println(Arrays.toString(inputParams));
        System.out.println("inputParams");

        char[][] field = readField(br, rows, cols);
        System.out.println(Arrays.deepToString(field));
        System.out.println("field");

        br.close();
    }
}
