package org.example.services;

import org.example.domain.Event;

public interface IServiceEvent {
    Iterable<Event> GetAll();
    Event GetById(int id);
    Event Add(String name, int minAge, int maxAge);
    Event UpdateEvent(Event event);
}
