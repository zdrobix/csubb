package org.example;

import java.time.LocalDateTime;

public class Payment {
    private int id;
    private LocalDateTime date;
    private String cnp;
    private double amount;

    public Payment(int id, LocalDateTime date, String cnp, double amount) {
        this.id = id;
        this.date = date;
        this.cnp = cnp;
        this.amount = amount;
    }

    // Getters
    public int getId() { return id; }
    public LocalDateTime getDate() { return date; }
    public String getCnp() { return cnp; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return String.format("ID:%d, CNP:%s, Suma:%.2f, Data:%s",
                id, cnp, amount, date);
    }
}
