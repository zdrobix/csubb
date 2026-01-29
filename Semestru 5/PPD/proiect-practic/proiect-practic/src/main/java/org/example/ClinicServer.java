package org.example;

import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class ClinicServer {
    private static final int PORT = 8888;
    private static final int MAX_THREADS = 10;
    private static final int VERIFICATION_INTERVAL = 5; // secunde
    private static final int PAYMENT_TIMEOUT = 30; // secunde
    private static final int SERVER_RUNTIME = 180; // 3 minute

    // Configurare tratamente
    private static final int NUM_LOCATIONS = 5;
    private static final int NUM_TREATMENTS = 5;
    private static final double[] TREATMENT_COSTS = {50, 20, 40, 100, 30};
    private static final int[] TREATMENT_DURATIONS = {120, 20, 30, 60, 30}; // minute
    private static final int[][] MAX_CLIENTS; // N(i,j)

    static {
        MAX_CLIENTS = new int[NUM_LOCATIONS][NUM_TREATMENTS];
        int[] baseCapacity = {3, 1, 1, 2, 1};
        for (int i = 0; i < NUM_LOCATIONS; i++) {
            for (int j = 0; j < NUM_TREATMENTS; j++) {
                MAX_CLIENTS[i][j] = baseCapacity[j] * (i + 1);
            }
        }
    }

    private ExecutorService threadPool;
    private List<Appointment> appointments;
    private List<Payment> payments;
    private AtomicInteger appointmentIdCounter;
    private AtomicInteger paymentIdCounter;
    private volatile boolean running;
    private ScheduledExecutorService verificationScheduler;
    private final Object appointmentLock = new Object();
    private final Object paymentLock = new Object();

    public ClinicServer() {
        this.threadPool = Executors.newFixedThreadPool(MAX_THREADS);
        this.appointments = Collections.synchronizedList(new ArrayList<>());
        this.payments = Collections.synchronizedList(new ArrayList<>());
        this.appointmentIdCounter = new AtomicInteger(1);
        this.paymentIdCounter = new AtomicInteger(1);
        this.running = true;
        this.verificationScheduler = Executors.newScheduledThreadPool(1);
    }

    public void start() {
        System.out.println("Server Clinica Medicala Pornit");
        System.out.println("Port: " + PORT);
        System.out.println("Max threaduri: " + MAX_THREADS);
        System.out.println("Interval verificare: " + VERIFICATION_INTERVAL + " secunde");
        System.out.println("Timpul de rulare: " + SERVER_RUNTIME + " secunde");
        System.out.println();

        // Programare verificari periodice
        verificationScheduler.scheduleAtFixedRate(
                this::performVerification,
                VERIFICATION_INTERVAL,
                VERIFICATION_INTERVAL,
                TimeUnit.SECONDS
        );

        // Programare verificare timeout plati
        verificationScheduler.scheduleAtFixedRate(
                this::checkPaymentTimeouts,
                5, 5, TimeUnit.SECONDS
        );

        // Programare oprire server
        verificationScheduler.schedule(() -> {
            System.out.println("\n=== Timpul de rulare expirat. Oprire server... ===");
            shutdown();
        }, SERVER_RUNTIME, TimeUnit.SECONDS);

        // Acceptare conexiuni clienti
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server asculta pe portul " + PORT + "...\n");

            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    threadPool.submit(new ClientHandler(clientSocket));
                } catch (IOException e) {
                    if (running) {
                        System.err.println("Eroare acceptare client: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare pornire server: " + e.getMessage());
        }
    }

    private void checkPaymentTimeouts() {
        synchronized (appointmentLock) {
            LocalDateTime now = LocalDateTime.now();
            List<Appointment> toRemove = new ArrayList<>();

            for (Appointment app : appointments) {
                if ("rezervare".equals(app.getStatus())) {
                    long secondsElapsed = Duration.between(app.getCreationTime(), now).getSeconds();
                    if (secondsElapsed > PAYMENT_TIMEOUT) {
                        toRemove.add(app);
                    }
                }
            }

            for (Appointment app : toRemove) {
                appointments.remove(app);
                System.out.println("Programare expirata (neplatita): ID " + app.getId());
            }
        }
    }

    private void performVerification() {
        System.out.println("\nVERIFICARE PERIODICA");
        System.out.println("Ora: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        try (PrintWriter writer = new PrintWriter(new FileWriter("verificari.txt", true))) {
            writer.println("\nVERIFICARE ");
            writer.println("Ora: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

            synchronized (appointmentLock) {
                synchronized (paymentLock) {
                    for (int loc = 0; loc < NUM_LOCATIONS; loc++) {
                        double totalLocation = calculateLocationTotal(loc);
                        List<Appointment> unpaid = getUnpaidAppointments(loc);
                        List<String> overlaps = checkOverlaps(loc);

                        System.out.println("\n--- Locatie " + (loc + 1) + " ---");
                        System.out.println("Sold total: " + totalLocation + " RON");

                        writer.println("\n--- Locatie " + (loc + 1) + " ---");
                        writer.println("Sold total: " + totalLocation + " RON");

                        if (!unpaid.isEmpty()) {
                            System.out.println("Programari neplatite: " + unpaid.size());
                            writer.println("Programari neplatite:");
                            for (Appointment app : unpaid) {
                                writer.println("  " + app);
                            }
                        }

                        if (!overlaps.isEmpty()) {
                            System.out.println("ATENTIE! Suprapuneri detectate:");
                            writer.println("ATENTIE! Suprapuneri detectate:");
                            for (String overlap : overlaps) {
                                System.out.println("  " + overlap);
                                writer.println("  " + overlap);
                            }
                        }
                    }
                }
            }

            writer.println("");
        } catch (IOException e) {
            System.err.println("Eroare salvare verificare: " + e.getMessage());
        }

        System.out.println("");
    }

    private double calculateLocationTotal(int location) {
        double total = 0;
        for (Payment payment : payments) {
            // Gasim programarea corespunzatoare
            for (Appointment app : appointments) {
                if (app.getId() == payment.getId() && app.getLocation() == location) {
                    total += payment.getAmount();
                    break;
                }
            }
        }
        return total;
    }

    private List<Appointment> getUnpaidAppointments(int location) {
        List<Appointment> unpaid = new ArrayList<>();
        for (Appointment app : appointments) {
            if (app.getLocation() == location && "rezervare".equals(app.getStatus())) {
                unpaid.add(app);
            }
        }
        return unpaid;
    }

    private List<String> checkOverlaps(int location) {
        List<String> overlaps = new ArrayList<>();
        Map<String, Integer> timeSlotCount = new HashMap<>();

        for (Appointment app : appointments) {
            if (app.getLocation() == location) {
                String key = app.getTreatmentTime() + "_" + app.getTreatmentType();
                timeSlotCount.put(key, timeSlotCount.getOrDefault(key, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : timeSlotCount.entrySet()) {
            String[] parts = entry.getKey().split("_");
            String time = parts[0];
            int treatmentType = Integer.parseInt(parts[1]);
            int count = entry.getValue();
            int maxAllowed = MAX_CLIENTS[location][treatmentType];

            if (count > maxAllowed) {
                overlaps.add(String.format("Tratament %d la ora %s: %d clienti (max: %d)",
                        treatmentType + 1, time, count, maxAllowed));
            }
        }

        return overlaps;
    }

    private void shutdown() {
        running = false;
        verificationScheduler.shutdown();
        threadPool.shutdown();

        try {
            if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
        }

        System.out.println("Server oprit.");
        System.exit(0);
    }

    class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String request;
                while ((request = in.readLine()) != null && running) {
                    if ("CLOSE".equals(request)) {
                        break;
                    }

                    String finalRequest = request;
                    CompletableFuture<String> future = CompletableFuture.supplyAsync(
                            () -> processRequest(finalRequest), threadPool);

                    String response = future.get();
                    out.println(response);
                }
            } catch (Exception e) {
                if (running) {
                    System.err.println("Eroare procesare client: " + e.getMessage());
                }
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String processRequest(String request) {
            String[] parts = request.split("\\|");
            String command = parts[0];

            switch (command) {
                case "BOOK":
                    return handleBooking(parts);
                case "PAY":
                    return handlePayment(parts);
                case "CANCEL":
                    return handleCancellation(parts);
                default:
                    return "EROARE|Comanda necunoscuta";
            }
        }

        private String handleBooking(String[] parts) {
            // Format: BOOK|name|cnp|location|treatmentType|time
            String name = parts[1];
            String cnp = parts[2];
            int location = Integer.parseInt(parts[3]);
            int treatmentType = Integer.parseInt(parts[4]);
            String time = parts[5];

            synchronized (appointmentLock) {
                // Verificare disponibilitate
                int currentCount = 0;
                for (Appointment app : appointments) {
                    if (app.getLocation() == location &&
                            app.getTreatmentType() == treatmentType &&
                            app.getTreatmentTime().equals(time)) {
                        currentCount++;
                    }
                }

                if (currentCount >= MAX_CLIENTS[location][treatmentType]) {
                    return "PROGRAMARE_NEREUSITA|Nu mai sunt locuri disponibile";
                }

                int id = appointmentIdCounter.getAndIncrement();
                Appointment appointment = new Appointment(
                        id, name, cnp, LocalDateTime.now(),
                        location, treatmentType, LocalDateTime.now(), time
                );
                appointments.add(appointment);

                System.out.println("Programare reusita: " + appointment);
                return "PROGRAMARE_REUSITA|" + id + "|" + TREATMENT_COSTS[treatmentType];
            }
        }

        private String handlePayment(String[] parts) {
            // Format: PAY|appointmentId|cnp
            int appointmentId = Integer.parseInt(parts[1]);
            String cnp = parts[2];

            synchronized (appointmentLock) {
                synchronized (paymentLock) {
                    Appointment appointment = null;
                    for (Appointment app : appointments) {
                        if (app.getId() == appointmentId && app.getCnp().equals(cnp)) {
                            appointment = app;
                            break;
                        }
                    }

                    if (appointment == null) {
                        return "PLATA_NEREUSITA|Programare negasita";
                    }

                    if ("platita".equals(appointment.getStatus())) {
                        return "PLATA_NEREUSITA|Programare deja platita";
                    }

                    double amount = TREATMENT_COSTS[appointment.getTreatmentType()];
                    Payment payment = new Payment(appointmentId, LocalDateTime.now(), cnp, amount);
                    payments.add(payment);
                    appointment.setStatus("platita");

                    System.out.println("Plata inregistrata: " + payment);
                    return "PLATA_REUSITA|" + amount;
                }
            }
        }

        private String handleCancellation(String[] parts) {
            // Format: CANCEL|appointmentId|cnp
            int appointmentId = Integer.parseInt(parts[1]);
            String cnp = parts[2];

            synchronized (appointmentLock) {
                synchronized (paymentLock) {
                    Appointment appointment = null;
                    for (Appointment app : appointments) {
                        if (app.getId() == appointmentId && app.getCnp().equals(cnp)) {
                            appointment = app;
                            break;
                        }
                    }

                    if (appointment == null) {
                        return "ANULARE_NEREUSITA|Programare negasita";
                    }

                    // Sterge plata
                    Payment paymentToRemove = null;
                    for (Payment p : payments) {
                        if (p.getId() == appointmentId) {
                            paymentToRemove = p;
                            break;
                        }
                    }

                    if (paymentToRemove != null) {
                        payments.remove(paymentToRemove);
                        // Adauga returnare (suma negativa)
                        Payment refund = new Payment(
                                paymentIdCounter.getAndIncrement(),
                                LocalDateTime.now(),
                                cnp,
                                -paymentToRemove.getAmount()
                        );
                        payments.add(refund);
                    }

                    appointments.remove(appointment);
                    System.out.println("Anulare reusita: ID " + appointmentId);
                    return "ANULARE_REUSITA|Returnare: " +
                            (paymentToRemove != null ? paymentToRemove.getAmount() : 0);
                }
            }
        }
    }

    public static void main(String[] args) {
        ClinicServer server = new ClinicServer();
        server.start();
    }
}