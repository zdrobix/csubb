package org.example.services;

import org.example.domain.Event;
import org.example.repository.interf.IEventRepository;

public class ServiceEvent implements IServiceEvent {
    private IEventRepository Repo;

    public ServiceEvent(IEventRepository repo) {
        this.Repo = repo;
    }

    public Iterable<Event> GetAll() {
        return this.Repo.FindAll();
    }

    public Event GetById(int id) {
        return this.Repo.FindById(id);
    }

    public Event Add(String name, int minAge, int maxAge) {
        return this.Repo.Save(new Event(name, minAge, maxAge));
    }

    public Event UpdateEvent(Event event) {
        return this.Repo.Update(event);
    }
}
