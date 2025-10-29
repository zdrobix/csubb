package org.example;

import java.io.BufferedWriter;
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

    static Spec[] specifications = new Spec[]{
            new Spec(10, 10, 3, new int[]{4}),
            new Spec(1000, 1000, 5, new int[]{2, 4, 8, 16}),
            new Spec(10, 10000, 5, new int[]{2, 4, 8, 16}),
            new Spec(10000, 10, 5, new int[]{2, 4, 8, 16}),
            new Spec(10000, 10000, 5, new int[]{2, 4, 8, 16}),
    };

    public static void main(String[] args) throws IOException, InterruptedException {
//        run_sequential();
//        run_horizontal();
        run_vertical();
    }




    public static void generate_data() {


        for (var spec : specifications) {
            var matrix = CreateMatrix.Get(spec.N, spec.M, 999_999_999);
            var kernel = CreateMatrix.Get(spec.k, spec.k, 10);

            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter("./input/data-" + spec.N + "-" + spec.M + ".txt"))) {
                StringBuilder sb = new StringBuilder();
                sb.append(spec.N).append(' ').append(spec.M).append(' ').append(spec.k).append('\n');
                sb.append(MatrixToString.Run(matrix));
                sb.append(MatrixToString.Run(kernel));
                fileWriter.write(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void run_sequential() throws IOException {
        System.out.println("|No. |N |M |k |Time |\n|--|--|--|--|--|");
        int count = 1;
        for (var spec : specifications) {
            long time = 0;
            var data = ReadMatrixFile.Read("./input/data-" + spec.N + "-" + spec.M + ".txt");
            for (int i = 0; i < 10; i ++) {
                var Seq = new SequentialConvolution();
                time += Seq.Convolute(data.matrix, data.kernel);
                if (i == 0)
                    try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter("./output/result-seq-" + spec.N + "-" + spec.M + ".txt"))) {
                        fileWriter.write(MatrixToString.Run(Seq.Result));
                    }
            }
            System.out.println("|" + count + " |" + spec.N + " |" + spec.M + " |" + spec.k + " |" + time / 10 + " |" );
            count ++;
        }
    }

    public static void run_horizontal() throws IOException, InterruptedException {
        System.out.println("|No. |N |M |k |p |Time(nano) |\n|--|--|--|--|--|--|");
        int count = 1;
        for (var spec : specifications) {
            for (var x : spec.p) {

                long time = 0;
                var data = ReadMatrixFile.Read("./input/data-" + spec.N + "-" + spec.M + ".txt");
                for (int i = 0; i < 10; i ++) {
                    time += RunParalel.RunHorizontal(x, data.matrix, data.kernel);
                    if (i == 0)
                        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter("./output/result-hor-" + spec.N + "-" + spec.M + "-" + x + "-threads.txt"))) {
                            fileWriter.write(MatrixToString.Run(RunParalel.Result));
                        }
                }
                System.out.println("|" + count + " |" + spec.N + " |" + spec.M + " |" + spec.k + " |" + x +  " |" + time / 10 + " |" );
                count ++;
            }
        }
    }

    public static void run_vertical() throws IOException, InterruptedException {
        System.out.println("|No. |N |M |k |p |Time(nano) |\n|--|--|--|--|--|--|");
        int count = 1;
        for (var spec : specifications) {
            for (var x : spec.p) {

                long time = 0;
                var data = ReadMatrixFile.Read("./input/data-" + spec.N + "-" + spec.M + ".txt");
                for (int i = 0; i < 10; i ++) {
                    time += RunParalel.RunVertical(x, data.matrix, data.kernel);
                    if (i == 0)
                        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter("./output/result-ver-" + spec.N + "-" + spec.M + "-" + x + "-threads.txt"))) {
                            fileWriter.write(MatrixToString.Run(RunParalel.Result));
                        }
                }
                System.out.println("|" + count + " |" + spec.N + " |" + spec.M + " |" + spec.k + " |" + x +  " |" + time / 10 + " |" );
                count ++;
            }
        }
    }
}
