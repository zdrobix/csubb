package org.example;

public class MatrixToString {
    public static String Run(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j]);
                sb.append(" ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
