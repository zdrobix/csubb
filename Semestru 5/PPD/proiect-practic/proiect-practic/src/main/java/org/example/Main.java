package org.example;

import java.util.concurrent.*;

public class Main {

    // Parametri de testare
    private static final int P = 10;  // Număr threaduri server
    private static final int NUM_CLIENTS = 10;  // Număr clienți
    private static final int N = 5;  // Număr locații
    private static final int M = 5;  // Număr tipuri tratamente

    // Costuri tratamente
    private static final double[] COSTS = {50, 20, 40, 100, 30};

    // Durate tratamente (minute)
    private static final int[] DURATIONS = {120, 20, 30, 60, 30};

    // Capacitate maximă clienți simultan
    // N(1,1)=3, N(1,2)=1, N(1,3)=1, N(1,4)=2, N(1,5)=1
    // N(i,j) = N(1,j)*(i-1) pentru toți i>1 și 0<j<=5
    private static final int[][] MAX_CAPACITY = calculateCapacity();

    private static final int SERVER_RUNTIME_MINUTES = 3;  // Server rulează 3 minute

    private static int[][] calculateCapacity() {
        int[][] capacity = new int[N][M];
        int[] baseCapacity = {3, 1, 1, 2, 1};  // N(1,j)

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (i == 0) {
                    capacity[i][j] = baseCapacity[j];
                } else {
                    capacity[i][j] = baseCapacity[j] * i;  // N(i,j) = N(1,j) * (i-1) dar i începe de la 0
                }
            }
        }

        return capacity;
    }

    public static void main(String[] args) {
        System.out.println("Sistem programari clinica");

        // Afișare parametri testare
        displayTestParameters();

        System.out.println("PORNIRE APLICATIE\n");

        // Thread pentru server
        Thread serverThread = new Thread(() -> {
            try {
                System.out.println("Main: pornire server\n");
                ClinicServer server = new ClinicServer();
                server.start();
            } catch (Exception e) {
                System.err.println("Main: eroare server: " + e.getMessage());
                e.printStackTrace();
            }
        });

        serverThread.setDaemon(false);
        serverThread.start();

        // Așteaptă ca serverul să pornească complet
        try {
            System.out.println("Main: asteptare pornire serve\n");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Pornire clienți
        System.out.println("[Main: pornire " + NUM_CLIENTS + " clienti\n");

        ExecutorService clientExecutor = Executors.newFixedThreadPool(NUM_CLIENTS);
        ClinicClient[] clients = new ClinicClient[NUM_CLIENTS];

        for (int i = 0; i < NUM_CLIENTS; i++) {
            final int clientIndex = i;
            String clientName = "Client_" + (i + 1);
            String cnp = generateCNP(i + 1);

            clients[i] = new ClinicClient(clientName, cnp);

            // Pornire client cu delay mic
            try {
                Thread.sleep(200);  // 200ms între porniri clienți
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clientExecutor.submit(() -> {
                clients[clientIndex].connect();
            });
        }

        System.out.println("Main: toti clientii au fost porniti!");
        System.out.println("Main: serverul va rula " + SERVER_RUNTIME_MINUTES + " minute\n");

        // Așteaptă timpul de rulare + buffer pentru închidere
        try {
            int waitTime = (SERVER_RUNTIME_MINUTES * 60 + 10) * 1000;  // +10 secunde buffer
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Închidere clienți
        System.out.println("\n" + "=".repeat(70));
        System.out.println("inchidere aplicatie");
        System.out.println("=".repeat(70) + "\n");

        System.out.println("Main: deconectare clienti");
        for (ClinicClient client : clients) {
            if (client != null) {
                client.disconnect();
            }
        }

        clientExecutor.shutdown();
        try {
            if (!clientExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                clientExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            clientExecutor.shutdownNow();
        }

        System.out.println("\n[main:  aplicatie inchisa cu succes!");

        // Forțează închiderea după ce totul este gata
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    private static void displayTestParameters() {
        System.out.println("PARAMETRI TESTARE:");
        System.out.println("numar threaduri server: " + P);
        System.out.println("numar clienti:" + NUM_CLIENTS);
        System.out.println("numar locatii (n):" + N);
        System.out.println("numar tipuri tratamente (m):" + M);
        System.out.println("timp rulare server " + SERVER_RUNTIME_MINUTES + " minute");
        System.out.println();

        System.out.println("TRATAMENTE:");
        for (int i = 0; i < M; i++) {
            System.out.printf("  Tratament %d: Cost=%6.2f RON, Durata=%3d minute%n",
                    (i + 1), COSTS[i], DURATIONS[i]);
        }
        System.out.println();

        System.out.println("CAPACITATE MAXIMA (Număr clienți simultan N(i,j)):");
        System.out.print("           ");
        for (int j = 0; j < M; j++) {
            System.out.printf("T%-3d ", (j + 1));
        }
        System.out.println();

        for (int i = 0; i < N; i++) {
            System.out.printf("Locatia %d: ", (i + 1));
            for (int j = 0; j < M; j++) {
                System.out.printf("%-4d ", MAX_CAPACITY[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("FORMULA CAPACITATE:");
        System.out.println("  N(1,1)=3, N(1,2)=1, N(1,3)=1, N(1,4)=2, N(1,5)=1");
        System.out.println("  N(i,j) = N(1,j) * (i-1) pentru toti i>1 și 0<j<=5");
        System.out.println();

        System.out.println("COMPORTAMENT CLIENTI:");
        System.out.println("-Fiecare client genereaza cereri la fiecare 2 secunde");
        System.out.println("-Date generate aleatoriu: locatie, tip tratament, ora");
        System.out.println("-Plata se face automat după programare reusita");
        System.out.println("-Programul functioneaza intre orele 10:00-18:00");
        System.out.println();

        System.out.println("VERIFICARI PERIODICE:");
        System.out.println("-Interval verificare:  10 secunde");
        System.out.println("-Verificare suprapuneri: DA");
        System.out.println("-Verificare plati:   DA");
        System.out.println("-Timeout plati: 30 secunde");
        System.out.println("-Salvare rezultate: verificari.txt");
    }

    private static String generateCNP(int index) {
        return String.format("1000101%02d000%d",
                Math.min(index, 28),
                index % 10);
    }
}