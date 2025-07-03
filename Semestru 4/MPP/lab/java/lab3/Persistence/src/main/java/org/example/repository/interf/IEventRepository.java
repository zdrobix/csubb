package org.example.repository.interf;

import org.example.domain.Event;

public interface IEventRepository extends IRepository<Integer, Event>{

    Iterable<Event> GetAllByMinAge (int minAge);
    Iterable<Event> GetAllByMaxAge (int maxAge);
}
