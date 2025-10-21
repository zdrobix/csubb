package org.example;

import java.util.Random;

public class CreateMatrix {
    public static int[][] Get(int sizeN, int sizeM, int max) {
        int[][] matrix = new int[sizeN][sizeM];
        for (int i = 0; i < sizeN; i++) {
            for (int j = 0; j < sizeM; j++) {
                matrix[i][j] = new Random().nextInt(max);
            }
        }
        return matrix;
    }
}
