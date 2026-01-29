package org.example;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private String name;
    private String cnp;
    private LocalDateTime reservationDate;
    private int location;
    private int treatmentType;
    private LocalDateTime treatmentDate;
    private String treatmentTime;
    private String status; // "rezervare", "platita"
    private LocalDateTime creationTime;

    public Appointment(int id, String name, String cnp, LocalDateTime reservationDate,
                       int location, int treatmentType, LocalDateTime treatmentDate,
                       String treatmentTime) {
        this.id = id;
        this.name = name;
        this.cnp = cnp;
        this.reservationDate = reservationDate;
        this.location = location;
        this.treatmentType = treatmentType;
        this.treatmentDate = treatmentDate;
        this.treatmentTime = treatmentTime;
        this.status = "rezervare";
        this.creationTime = LocalDateTime.now();
    }

    // Getters și Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCnp() { return cnp; }
    public LocalDateTime getReservationDate() { return reservationDate; }
    public int getLocation() { return location; }
    public int getTreatmentType() { return treatmentType; }
    public LocalDateTime getTreatmentDate() { return treatmentDate; }
    public String getTreatmentTime() { return treatmentTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreationTime() { return creationTime; }

    @Override
    public String toString() {
        return String.format("ID:%d, CNP:%s, Locatie:%d, Tratament:%d, Ora:%s, Status:%s",
                id, cnp, location, treatmentType, treatmentTime, status);
    }
}