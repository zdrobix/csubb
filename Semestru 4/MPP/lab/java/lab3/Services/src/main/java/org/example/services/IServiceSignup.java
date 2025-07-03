package org.example.services;

import org.example.domain.Child;
import org.example.domain.Event;
import org.example.domain.Signup;
import org.example.domain.Tuple;

public interface IServiceSignup {
    Iterable<Signup> GetAll();
    Signup AddSignup (Child child, Event event);
    Signup GetById (int childId, int eventId);
    Iterable<Signup> GetByEvent (int eventId);
    Iterable<Signup> GetByChild (int childId);
    Signup Delete (int childId, int eventId);
    Iterable<Signup> GetAllMapped(IServiceChild serviceChild, IServiceEvent serviceEvent);
}
