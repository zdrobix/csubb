package org.example;

public class RunParalel {
    public static long RunHorizontal (int p, int[][] Matrix, int[][] Kernel) throws InterruptedException {
        long t0 = System.nanoTime();

        ParalelHorizontalConvolution[] threads = new ParalelHorizontalConvolution[p];

        int resultNrRow = Matrix.length - Kernel.length + 1;
        int rowsPerThread = resultNrRow / p;
        int rest = resultNrRow % p;
        int startRow = 0;

        if (Matrix.length < Kernel.length || Matrix[0].length < Kernel.length) {
            throw new IllegalArgumentException("Matrix and Kernel invalid size");
        }

        for (int id = 0; id < p; id++) {
            int endRow = startRow + rowsPerThread - 1;
            if (id < rest) endRow ++;

            endRow = Math.min(endRow, Matrix.length - 1);

            threads[id] = new ParalelHorizontalConvolution(
                    startRow,
                    endRow,
                    Matrix,
                    Kernel
            );
            threads[id].start();
            startRow = endRow + 1;
        }

        for (int id = 0; id < p; id++) {
            threads[id].join();
        }
        long t1 = System.nanoTime();
        return t1 - t0;
    }

    public static long RunVertical (int p, int[][] Matrix, int[][] Kernel) throws InterruptedException {
        long t0 = System.nanoTime();

        ParalelVerticalConvolution[] threads = new ParalelVerticalConvolution[p];

        int resultNrCol = Matrix[0].length - Kernel.length + 1;
        int colsPerThread = resultNrCol / p;
        int rest = resultNrCol % p;

        int startCol = 0;

        if (Matrix.length < Kernel.length || Matrix[0].length < Kernel.length) {
            throw new IllegalArgumentException("Matrix and Kernel invalid size");
        }

        for (int id = 0; id < p; id++) {
            int endCol = startCol + colsPerThread - 1;
            if (id < rest) endCol ++;

            threads[id] = new ParalelVerticalConvolution(
                    startCol,
                    endCol + Kernel.length - 1,
                    Matrix,
                    Kernel
            );
            threads[id].start();
            startCol = endCol + 1;
        }

        for (int id = 0; id < p; id++) {
            threads[id].join();
        }
        long t1 = System.nanoTime();
        return t1 - t0;
    }
}


