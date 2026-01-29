package org.example;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.*;

public class ClinicClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8888;
    private static final int REQUEST_INTERVAL = 2; // secunde

    private String clientName;
    private String cnp;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Random random;
    private volatile boolean running;
    private ScheduledExecutorService scheduler;

    public ClinicClient(String clientName, String cnp) {
        this.clientName = clientName;
        this.cnp = cnp;
        this.random = new Random();
        this.running = true;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void connect() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("[" + clientName + "] Conectat la server");

            // Programare cereri periodice
            scheduler.scheduleAtFixedRate(
                    this::makeAppointmentRequest,
                    0,
                    REQUEST_INTERVAL,
                    TimeUnit.SECONDS
            );

        } catch (IOException e) {
            System.err.println("[" + clientName + "] Eroare conectare: " + e.getMessage());
            running = false;
        }
    }

    private void makeAppointmentRequest() {
        if (!running) return;

        try {
            // Generare date aleatorii
            int location = random.nextInt(5); // 0-4
            int treatmentType = random.nextInt(5); // 0-4
            String time = generateRandomTime();

            // Trimitere cerere de programare
            String bookRequest = String.format("BOOK|%s|%s|%d|%d|%s",
                    clientName, cnp, location, treatmentType, time);

            out.println(bookRequest);
            String response = in.readLine();

            if (response == null) {
                System.out.println("[" + clientName + "] Server inchis");
                disconnect();
                return;
            }

            String[] parts = response.split("\\|");
            System.out.println("[" + clientName + "] Cerere programare - Locatie:" + (location + 1) +
                    ", Tratament:" + (treatmentType + 1) + ", Ora:" + time);

            if ("PROGRAMARE_REUSITA".equals(parts[0])) {
                int appointmentId = Integer.parseInt(parts[1]);
                double cost = Double.parseDouble(parts[2]);

                System.out.println("[" + clientName + "] PROGRAMARE REUSITA - ID:" + appointmentId +
                        ", Cost:" + cost + " RON");

                // Așteaptă puțin și trimite plata
                Thread.sleep(1000);
                makePayment(appointmentId);

            } else {
                System.out.println("[" + clientName + "] PROGRAMARE NEREUSITA - " + parts[1]);
            }

        } catch (IOException e) {
            if (running) {
                System.err.println("[" + clientName + "] Eroare comunicare: " + e.getMessage());
                disconnect();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void makePayment(int appointmentId) {
        try {
            String payRequest = String.format("PAY|%d|%s", appointmentId, cnp);
            out.println(payRequest);

            String response = in.readLine();
            if (response != null) {
                String[] parts = response.split("\\|");
                if ("PLATA_REUSITA".equals(parts[0])) {
                    System.out.println("[" + clientName + "] PLATA REUSITA - Suma:" + parts[1] + " RON");
                } else {
                    System.out.println("[" + clientName + "] PLATA NEREUSITA - " + parts[1]);
                }
            }
        } catch (IOException e) {
            if (running) {
                System.err.println("[" + clientName + "] Eroare plata: " + e.getMessage());
            }
        }
    }

    private String generateRandomTime() {
        // Program 10:00-18:00
        int hour = 10 + random.nextInt(8); // 10-17
        int minute = random.nextInt(4) * 15; // 0, 15, 30, 45
        return String.format("%02d:%02d", hour, minute);
    }

    public void disconnect() {
        running = false;
        scheduler.shutdown();

        try {
            if (out != null) {
                out.println("CLOSE");
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            System.out.println("[" + clientName + "] Deconectat");
        } catch (IOException e) {
            System.err.println("[" + clientName + "] Eroare deconectare: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Pornire Client Clinica Medicala\n");

        // Creare 10 clienți
        ClinicClient[] clients = new ClinicClient[10];

        for (int i = 0; i < 10; i++) {
            String name = "Client_" + (i + 1);
            String cnp = "1" + String.format("%012d", i + 1); // CNP simplificat
            clients[i] = new ClinicClient(name, cnp);

            // Conectare cu delay mic între clienți
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clients[i].connect();
        }

        // Așteaptă timp de rulare (3 minute + buffer)
        try {
            Thread.sleep(200000); // ~3.3 minute
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Deconectare clienți
        for (ClinicClient client : clients) {
            client.disconnect();
        }

        System.out.println("\nToti clientii deconectati");
    }
}
