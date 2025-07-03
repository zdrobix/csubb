package org.example.services;

import org.example.domain.Child;
import org.example.domain.Event;
import org.example.domain.Signup;

import java.io.Serializable;

public interface IObserver {
    void childAdded(Child child);
    void eventAdded(Event event);
    void signupAdded(Signup signup);
}
