package service;

import java.sql.SQLException;
import java.util.List;

import domain.*;
import repo.Repository;


public class Service {
    private Repository Repo;


    public Service (Repository repo) {
        this.Repo = repo;
    }

    public List<Client> getClients() {
        try {
            return this.Repo.getClients();
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Flight> getFlight() {
        try {
            return this.Repo.getFlights();
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Ticket> getTickets() {
        try {
            return this.Repo.getTickets();
        } catch (SQLException e) {
            return null;
        }
    }


}
