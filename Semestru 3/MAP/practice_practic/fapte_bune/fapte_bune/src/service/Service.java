package service;

import java.time.LocalDateTime;
import java.util.List;

import domain.*;
import repo.RepositoryNeeds;
import repo.RepositoryPersons;

public class Service {
    private RepositoryNeeds RepoNeeds;
    private RepositoryPersons RepoPersons;

    public Service (RepositoryNeeds repoNeeds, RepositoryPersons repoPersons) {
        this.RepoNeeds = repoNeeds;
        this.RepoPersons = repoPersons;
    }

    public List<Need> GetNeeds() {
        return this.RepoNeeds.GetNeeds();
    }

    public List<Person> GetPersons() {
        return this.RepoPersons.GetPersons();
    }

    public Need GetNeed (long ID) {
        return this.GetNeeds().stream().filter(need -> need.GetID() == ID).findAny().orElse(null);
    }

    public Person GetPerson (long ID) {
        return this.GetPersons().stream().filter(person -> person.GetID() == ID).findAny().orElse(null);
    }

    public void AddPerson (String name, String surname, String username, String password, String city, String street, String streetNumber, String phoneNumber) {
        this.RepoPersons.AddPerson(
            new Person(RepoPersons.GetNewID(), name, surname, username, password, city, street, streetNumber, phoneNumber)
        );
    }

    public void AddNeed (String title, String description, LocalDateTime deadline, long personID, String status) {
        this.RepoNeeds.AddNeed(
            new Need(RepoNeeds.GetNewID(), title, description, deadline, personID, 0, status)
        );
    }
}
