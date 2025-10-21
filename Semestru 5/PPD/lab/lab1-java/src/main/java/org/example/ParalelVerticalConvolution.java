package org.example;

public class ParalelVerticalConvolution extends Thread{
    private int FirstCol;
    private int LastCol;
    private int[][] Matrix;
    private int[][] Kernel;
    public int[][] Result;

    public ParalelVerticalConvolution(int firstCol, int lastCol, int[][] matrix, int[][] kernel) {
        FirstCol = firstCol;
        LastCol = lastCol;
        Matrix = matrix;
        Kernel = kernel;
    }

    @Override
    public void run() {
        int N = Matrix.length;
        int K = Kernel.length;
        int M = LastCol - FirstCol + 1 - (K - 1);

        if (N < K || M < K) {
            return;
        }

        Result = new int[N - K + 1][M];
        for (int i = 0; i <= N - K; i ++) {
            for (int j = 0; j < M; j ++) {
                int sum = 0;
                for (int ki = 0; ki < K; ki ++) {
                    for (int kj = 0; kj < K; kj ++) {
                        sum += Matrix[i + ki][FirstCol + j + kj] * Kernel[ki][kj];
                    }
                }
                Result[i][j] = sum;
            }
        }
    }
}
