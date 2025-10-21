package org.example;

public class MatrixToString {
    public static String Run(int[][] matrix) {
        String result = "";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result += matrix[i][j];
                result += " ";
            }
            result += "\n";
        }
        return result;
    }
}
