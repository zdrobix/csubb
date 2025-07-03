package org.example.services;

import org.example.domain.Child;
import org.example.domain.Event;
import org.example.domain.Signup;
import org.example.domain.LoginInfo;

import java.io.Serializable;

public interface IService extends Serializable {
    Iterable<Child> GetAllChildren();
    Child GetChildById(int id);
    Child AddChild (String name, String cnp);
    Child UpdateChild (Child child);
    Iterable<Child> GetChildByAge (int age);
    Iterable<Event> GetAllEvents();
    Event GetEventById(int id);
    Event AddEvent(String name, int minAge, int maxAge);
    Event UpdateEvent(Event event);
    LoginInfo GetByUsername (String username);
    Iterable<Signup> GetAllSignups();
    Signup AddSignup (Child child, Event event);
    Signup GetSignupById (int childId, int eventId);
    Iterable<Signup> GetSignupByEvent (int eventId);
    Iterable<Signup> GetSignupByChild (int childId);
    Iterable<Signup> GetAllSignupsMapped (IServiceChild serviceChild, IServiceEvent serviceEvent);
    Signup DeleteSignup (int childId, int eventId);
    IServiceChild getServiceChild();
    IServiceEvent getServiceEvent();
    IServiceSignup getServiceSignup();
    IServiceLogin getServiceLogin();
    boolean login(LoginInfo login, IObserver client);
    boolean logout(LoginInfo login, IObserver client);
}
