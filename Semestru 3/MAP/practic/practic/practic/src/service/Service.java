package service;

import repo.Repository;
import utils.Observable;
import utils.Observer;
import utils.EventChange;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domain.*;

public class Service implements Observable<EventChange>{
    private Repository Repo;
    private List<Observer<EventChange>> observers = new ArrayList<>();

    public Service (Repository repo) {
        this.Repo = repo;
    }

    public List<User> getUsers() {
        try {
            return this.Repo.getUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Show> getShowsOnPage (int pageNumber) {
        try {
            return this.Repo.getShows(pageNumber, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SeatReservation> getSeats () {
        try {
            return this.Repo.getSeatsReservations();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getNumberOfShows () {
        try {
            return this.Repo.getShows(0, true).size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Show addShow (String name, int duration, LocalDateTime startDate, int numberOfSeats) {
        try {
            var isValid = this.Repo.getShows(0, true).stream().filter(
                show -> {
                    if (show.getStartDate().isAfter(startDate.plusMinutes(duration)))
                        return false;
                    if (show.getStartDate().plusMinutes(show.getDurationMinutes()).isBefore(startDate))
                        return false;
                    return true;
                }
            ).collect(Collectors.toList());
            if (!isValid.isEmpty())
                return isValid.get(0);
            var show = new Show(0, name, duration, startDate, numberOfSeats, LocalDateTime.now());
            this.Repo.addShow(show);
            this.notifyObservers(new EventChange(show, "addshow"));
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addReservation (int showId, User user) {
        try {
            var reservation = new SeatReservation(showId, user.getEmail());
            this.Repo.addReservation(showId, user);
            this.notifyObservers(new EventChange(reservation, "addreservation"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addObserver(Observer<EventChange> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<EventChange> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(EventChange t) {
        for (Observer<EventChange> observer : observers) {
            observer.update(t);
        }
    }
}
