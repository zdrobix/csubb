package org.example;

public class SequentialConvolution {
    public int[][] Result;

    public long Convolute (int[][] Matrix, int[][] Kernel) {
        long t0 = System.nanoTime();

        int N = Matrix.length;
        int M = Matrix[0].length;
        int K = Kernel.length;

        Result = new int[N - K + 1][M - K + 1];
        for (int i = 0; i <= N - K; i ++) {
            for (int j = 0; j <= M - K; j ++) {
                int sum = 0;
                for (int ki = 0; ki < K; ki ++) {
                    for (int kj = 0; kj < K; kj ++) {
                        sum += Matrix[i + ki][j + kj] * Kernel[ki][kj];
                    }
                }
                Result[i][j] = sum;
            }
        }
        long t1 = System.nanoTime();
        return t1 - t0;
    }


}
