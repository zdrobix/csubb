package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class Main {

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

    public static void main(String[] args) throws IOException, InterruptedException {

        long sumSeq = 0;

        for (int i = 0; i < 10; i ++)
            sumSeq += new SequentialConvolution().Convolute(CreateMatrix.Get(10000, 10000, 999_999_999), CreateMatrix.Get(5, 5, 10));

        System.out.println(sumSeq / 10);
    }

    public static void run_tables() {
        Spec specifications[] = new Spec[] {
                new Spec (10, 10, 3, new int[]{4}),
                new Spec (1000, 1000, 5, new int[] {2, 4, 8, 16}),
                new Spec (10, 10000, 5, new int[] {2, 4, 8, 16}),
                new Spec (10000, 10, 5, new int[] {2, 4, 8, 16}),
                new Spec (10000, 10000, 5, new int[] {2, 4, 8, 16}),
        };

        String tableHeadSeq = "|No. |N |M |k |Time |\n|--|--|--|--|--|--|";
        String tableHead = "|No. |N |M |k |p |Time |\n|--|--|--|--|--|--|";

        System.out.println(tableHead);

        for (var spec : specifications) {

            var matrix = CreateMatrix.Get(spec.N, spec.M, 999_999_999);
            var kernel = CreateMatrix.Get(spec.k, spec.k, 10);

            long sumSeq = 0;

            for (int i = 0; i < 10; i ++)
                sumSeq += new SequentialConvolution().Convolute(matrix, kernel);

            System.out.println("|" + 0 + "|" + spec.N + "|" + spec.M + "|" + spec.k + " |" + sumSeq / 10 + " |");

            for (int i = 0; i < spec.p.length; i ++) {
                long sumHor = 0;
                long sumVer = 0;
                try {
                    for (int j = 0; j < 10; j++) {
                        sumHor += new RunParalel().RunHorizontal(
                                spec.p[i],
                                matrix,
                                kernel
                        );
                    }
                } catch (Exception e) {
                    System.out.println("|" + spec.N + " |" + spec.M + "|" + spec.k + " |" + spec.p[i] + " |" + "impossible" + " |");
                } finally {
                    System.out.println("|" + spec.N + " |" + spec.M + "|" + spec.k + " |" + spec.p[i] + " |" + sumHor / 10 + " |");
                }

                try {
                    for (int j = 0; j < 10; j++) {
                        sumVer += new RunParalel().RunVertical(
                                spec.p[i],
                                matrix,
                                kernel
                        );
                    }
                } catch (Exception e) {
                    System.out.println("||" + spec.N + " |" + spec.M + "|" + spec.k + " |" + spec.p[i] + " |" + "impossible" + " |");
                } finally {
                    System.out.println("||" + spec.N + " |" + spec.M + "|" + spec.k + " |" + spec.p[i] + " |" + sumVer / 10 + " |");
                }
            }
        }
    }
}