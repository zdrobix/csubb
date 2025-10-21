package org.example;

public class ParalelHorizontalConvolution extends Thread {
    private int FirstLine;
    private int LastLine;
    private int[][] Matrix;
    private int[][] Kernel;
    public int[][] Result;

    public ParalelHorizontalConvolution(int firstLine, int lastLine, int[][] matrix, int[][] kernel) {
        FirstLine = firstLine;
        LastLine = lastLine;
        Matrix = matrix;
        Kernel = kernel;
    }

    @Override
    public void run() {
        int N = LastLine - FirstLine + 1 ;
        int M = Matrix[0].length;
        int K = Kernel.length;

        if (N < K || M < K) {
            return;
        }

        Result = new int[N - K + 1][M - K + 1];
        for (int i = 0; i <= N - K; i ++) {
            for (int j = 0; j <= M - K; j ++) {
                int sum = 0;
                for (int ki = 0; ki < K; ki ++) {
                    for (int kj = 0; kj < K; kj ++) {
                        sum += Matrix[FirstLine + i + ki][j + kj] * Kernel[ki][kj];
                    }
                }
                Result[i][j] = sum;
            }
        }
    }
}


