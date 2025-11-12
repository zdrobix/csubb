package org.example;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Convolution {
    private final int[][] matrice;
    private final int[][] kernel;
    private final int n, m, k;

    public Convolution(String inputFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            k = Integer.parseInt(br.readLine().trim());
            kernel = new int[k][k];
            for (int i = 0; i < k; i++) {
                String[] parts = br.readLine().trim().split("\\s+");
                for (int j = 0; j < k; j++) kernel[i][j] = Integer.parseInt(parts[j]);
            }
            String[] dims = br.readLine().trim().split("\\s+");
            n = Integer.parseInt(dims[0]);
            m = Integer.parseInt(dims[1]);
            matrice = new int[n][m];
            for (int i = 0; i < n; i++) {
                String[] parts = br.readLine().trim().split("\\s+");
                for (int j = 0; j < m; j++) matrice[i][j] = Integer.parseInt(parts[j]);
            }
        }
    }

    private int clamp(int i, int j) {
        i = Math.min(n - 1, Math.max(0, i));
        j = Math.min(m - 1, Math.max(0, j));
        return matrice[i][j];
    }

    private int convolutioneazaPix(int j, int[] a, int[] b, int[] c) {
        int sum = 0;
        for (int x = -(k / 2); x <= k / 2; x++) {
            for (int y = -(k / 2); y <= k / 2; y++) {
                int coef = kernel[x + k / 2][y + k / 2];
                int val = switch (x) {
                    case -1 -> a[j + y];
                    case 0 -> b[j + y];
                    case 1 -> c[j + y];
                    default -> 0;
                };
                sum += coef * val;
            }
        }
        return sum;
    }

    private void initializeVector(int[] a, int[] b, int[] c, int row) {
        for (int j = 0; j < m; j++) {
            a[j] = clamp(row - 1, j);
            b[j] = clamp(row, j);
            c[j] = clamp(row + 1, j);
        }

    }

    private void nextVec(int[] a, int[] b, int[] c, int nextRow) {
        System.arraycopy(b, 0, a, 0, m);
        System.arraycopy(c, 0, b, 0, m);
        for (int j = 0; j < m; j++)
            c[j] = clamp(nextRow, j);
    }

    public void sequential() throws IOException {
//        long start = System.nanoTime();

        int[] a = new int[m];
        int[] b = new int[m];
        int[] c = new int[m];
        initializeVector(a, b, c, 0);

        for (int i = 0; i < n; i++) {
            int[] temp = new int[m];
            for (int j = 0; j < m; j++)
                temp[j] = convolutioneazaPix(j, a, b, c);
            System.arraycopy(temp, 0, matrice[i], 0, m);
            nextVec(a, b, c, i + 2);
        }

//        long end = System.nanoTime();
//        System.out.println("\n" + n + " " + m + " " + k);
//        System.out.println("Timp secvențial: " + (end - start));
//        scrieRezultat("./data/result_secvential_" + n + "_" + m + "_" + k + ".txt");
    }

    public void parallel(int threads) throws IOException, InterruptedException {
//        long start = System.nanoTime();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        int chunk = n / threads;
        int rest = n % threads;

        List<Callable<Void>> tasks = new ArrayList<>();
        int startRow = 0;

        for (int t = 0; t < threads; t++) {
            int rows = chunk + (t < rest ? 1 : 0);
            int endRow = startRow + rows - 1;

            int finalStart = startRow;
            int finalEnd = endRow;

            tasks.add(() -> {
                int[] a = new int[m];
                int[] b = new int[m];
                int[] c = new int[m];
                initializeVector(a, b, c, finalStart);

                for (int i = finalStart; i <= finalEnd; i++) {
                    int[] temp = new int[m];
                    for (int j = 0; j < m; j++) temp[j] = convolutioneazaPix(j, a, b, c);
                    System.arraycopy(temp, 0, matrice[i], 0, m);
                    nextVec(a, b, c, i + 2);
                }
                return null;
            });

            startRow += rows;
        }

        executor.invokeAll(tasks);
        executor.shutdown();

//        long end = System.nanoTime();
//        System.out.println("\n" + n + " " + m + " " + k + " threads: " + threads);
//        System.out.println("Timp paralel(" + threads + " threads): " + (end - start));
//        scrieRezultat("./data/result_paralel_" + threads + "threads_" + n + "_" + m + "_" + k + ".txt");
    }

    private void scrieRezultat(String fileName) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) bw.write(matrice[i][j] + " ");
                bw.newLine();
            }
        }
    }

    static class Spec {
        int N, M;
        int k;
        int[] p;

        public Spec(int N, int M, int k, int[] p) {
            this.N = N;
            this.M = M;
            this.k = k;
            this.p = p;
        }
    }

    static Spec[] specifications = new Spec[]{
            new Spec(10, 10, 3, new int[]{2}),
            new Spec(1000, 1000, 3, new int[]{2, 4, 8, 16}),
            new Spec(10000, 10000, 3, new int[]{2, 4, 8, 16}),
    };

    public static void runConvolution() throws IOException, InterruptedException {
        System.out.println("### Rezultate SECVENTIAL ###");
        System.out.println("|No. |N |M |k |Timp(nano) |");
        System.out.println("|--|--|--|--|--|");
        int count = 1;
        for (var spec : specifications) {
            long time = 0;
            String inputFile = "./data/input-" + spec.N + "-" + spec.M + ".txt";
            for (int i = 0; i < 10; i++) {
                Convolution conv = new Convolution(inputFile);
                long start = System.nanoTime();
                conv.sequential();
                long end = System.nanoTime();
                time += (end - start);
                if (i == 0) {
                    conv.scrieRezultat("./data/result-seq-" + spec.N + "-" + spec.M + ".txt");
                }
            }
            System.out.println("|" + count + " |" + spec.N + " |" + spec.M + " |" + spec.k + " |" + time / 10 + " |");
            count++;
        }
        System.out.println("\n### Rezultate PARALEL ###");
        System.out.println("|No. |N |M |k |p |Timp(nano) |");
        System.out.println("|--|--|--|--|--|--|");
        count = 1;
        for (var spec : specifications) {
            for (int p : spec.p) {
                long time = 0;
                String inputFile = "./data/input_" + spec.N + "_" + spec.M + "_" + spec.k + ".txt";

                for (int i = 0; i < 10; i++) {
                    Convolution conv = new Convolution(inputFile);
                    long start = System.nanoTime();
                    conv.parallel(p);
                    long end = System.nanoTime();
                    time += (end - start);

                    if (i == 0) {
                        conv.scrieRezultat("./data/result-hor" + spec.N + "-" + spec.M + "-" + spec.k + "-" + p + "threads.txt");
                    }
                }
                System.out.println("|" + count + " |" + spec.N + " |" + spec.M + " |" + spec.k + " |" + p + " |" + time / 10 + " |");
                count++;
            }
        }
    }

}
