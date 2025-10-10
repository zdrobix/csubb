package org.example;

import java.util.Random;

import static java.lang.Math.min;

public class Main {

    // 1. ciclic: 1 = id, id + p, id + 2p
    public static class Cyclic extends Thread {
        // id -index thread
        // p - numarul total de threads
        // n - dimensiunea vectorilor
        // A, B, C

        private int id, p, n;
        private int[] A, B, C;

        public Cyclic(int id, int p, int n, int[] a, int[] b, int[] c) {
            this.id = id;
            this.p = p;
            this.n = n;
            this.A = a;
            this.B = b;
            this.C = c;
        }

        public void run() {
            for (int i = this.id; i < this.n; i += this.p) {
                this.C[i] = this.A[i] + this.B[i];
            }
        }

    }
    //2. Blocuri
    public static class Block extends Thread {
        private int start, end;
        private int[] A, B, C;

        public Block(int start, int end, int[] a, int[] b, int[] c) {
            this.start = start;
            this.end = end;
            this.A = a;
            this.B = b;
            this.C = c;
        }

        public void run() {
            for (int i = this.start; i < this.end; i ++) {
                this.C[i] = this.A[i] + this.B[i];
            }
        }
    }

    public static int[] generator(int n, int max) {
        int[] v = new int[n];
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            v[i] = r.nextInt(max);
        }
        return v;
    }

    static long Sequential (int[] A, int[] B, int[] C) {
        long t0 = System.nanoTime();
        for (int i = 0; i < A.length; i++) {
            C[i] = A[i] + B[i];
        }
        long t1 = System.nanoTime();
        return t1 - t0;
    }

    static long runCyclic (int[] A, int[] B, int[] C, int p) throws InterruptedException {
        Cyclic[] threads = new Cyclic[p];
        long t0 = System.nanoTime();
        for (int id = 0; id < p; id++) {
            threads[id] = new Cyclic(id, p, A.length, A, B, C);
            threads[id].start();
        }
        for (int id = 0; id < p; id++) {
            threads[id].join();
        }
        long t1 = System.nanoTime();
        return t1 - t0;
    }

    static long runBlock (int[] A, int[] B, int[] C, int p) throws InterruptedException {
        Block[] threads = new Block[p];
        int n = A.length;
        long t0 = System.nanoTime();
        for (int id = 0; id < p; id++) {
            int start = id * n;
            int end = min((id + 1) * n / p, n);
            threads[id] = new Block(start, end, A, B, C);
            threads[id].start();
        }
        for (int id = 0; id < p; id++) {
            threads[id].join();
        }
        long t1 = System.nanoTime();
        return t1 - t0;
    }

    public static void main(String[] args) throws InterruptedException {
        int n = 1_000_000;
        int max = 50_000;
        int p = 5;

        int[] A = generator(n, max);
        int[] B = generator(n, max);

        int[] C1 = new int[n];
        long tsec = Sequential(A, B, C1);
        System.out.println("Sequential: " + tsec);

        int[] C2 = new int[n];
        long tcic = runCyclic(A, B, C2, p);
        System.out.println("Cyclic: " + tcic);

        int[] C3 = new int[n];
        long tblo = runBlock(A, B, C3, p);
        System.out.println("Block: " + tblo);
    }
}