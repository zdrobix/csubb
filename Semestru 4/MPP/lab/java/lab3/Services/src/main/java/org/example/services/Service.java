package org.example.services;

import org.example.domain.Child;
import org.example.domain.Event;
import org.example.domain.LoginInfo;
import org.example.domain.Signup;

import java.util.HashMap;
import java.util.Map;

public class Service implements IService {
    private IServiceChild ServiceChild;
    private IServiceEvent ServiceEvent;
    private IServiceSignup ServiceSignup;
    private IServiceLogin ServiceLogin;
    private Map<Integer, IObserver> loggedUsers;

    public Service(IServiceChild serviceChild, IServiceEvent serviceEvent, IServiceSignup serviceSignup, IServiceLogin serviceLogin) {
        ServiceChild = serviceChild;
        ServiceEvent = serviceEvent;
        ServiceSignup = serviceSignup;
        ServiceLogin = serviceLogin;
        this.loggedUsers = new HashMap<>();
    }

    public IServiceChild getServiceChild() {
        return ServiceChild;
    }

    public IServiceEvent getServiceEvent() {
        return ServiceEvent;
    }

    public IServiceSignup getServiceSignup() {
        return ServiceSignup;
    }

    public IServiceLogin getServiceLogin() {
        return ServiceLogin;
    }

    @Override
    public synchronized Iterable<Child> GetAllChildren() {
        return ServiceChild.GetAll();
    }

    @Override
    public synchronized Child GetChildById(int id) {
        return ServiceChild.GetById(id);
    }

    @Override
    public synchronized Child AddChild(String name, String cnp) {
        var child = ServiceChild.AddChild(name, cnp);
        this.notifyChildAdded(child);
        return child;
    }

    @Override
    public synchronized Child UpdateChild(Child child) {
        return ServiceChild.UpdateChild(child);
    }

    @Override
    public synchronized Iterable<Child> GetChildByAge(int age) {
        return ServiceChild.GetByAge(age);
    }

    @Override
    public synchronized Iterable<Event> GetAllEvents() {
        return ServiceEvent.GetAll();
    }

    @Override
    public synchronized Event GetEventById(int id) {
        return ServiceEvent.GetById(id);
    }

    @Override
    public synchronized Event AddEvent(String name, int minAge, int maxAge) {
        var event =  ServiceEvent.Add(name, minAge, maxAge);
        this.notifyEventAdded(event);
        return event;
    }

    @Override
    public synchronized Event UpdateEvent(Event event) {
        return ServiceEvent.UpdateEvent(event);
    }

    @Override
    public synchronized LoginInfo GetByUsername(String username) {
        return ServiceLogin.GetByUsername(username);
    }

    @Override
    public synchronized Iterable<Signup> GetAllSignups() {
        return ServiceSignup.GetAll();
    }

    @Override
    public synchronized Signup AddSignup(Child child, Event event) {
        var signup =  ServiceSignup.AddSignup(child, event);
        notifySignupAdded(signup);
        return signup;
    }

    @Override
    public synchronized Signup GetSignupById(int childId, int eventId) {
        return ServiceSignup.GetById(childId, eventId);
    }

    @Override
    public synchronized Iterable<Signup> GetSignupByEvent(int eventId) {
        return ServiceSignup.GetByEvent(eventId);
    }

    @Override
    public synchronized Iterable<Signup> GetSignupByChild(int childId) {
        return ServiceSignup.GetByChild(childId);
    }

    @Override
    public synchronized Iterable<Signup> GetAllSignupsMapped(IServiceChild serviceChild, IServiceEvent serviceEvent) {
        return ServiceSignup.GetAllMapped(serviceChild, serviceEvent);
    }

    @Override
    public synchronized Signup DeleteSignup(int childId, int eventId) {
        return null;
    }

    @Override
    public synchronized boolean login(LoginInfo login, IObserver client) {
        System.out.println("Someone logged in.");
        this.loggedUsers.put(login.GetId(), client);
        return true;
    }

    @Override
    public synchronized boolean logout(LoginInfo login, IObserver client) {
        System.out.println("Someone logged out.");
        this.loggedUsers.remove(login.GetId());
        return true;
    }

    public void notifyChildAdded(Child child) {
        for (var client : loggedUsers.values()) {
            client.childAdded(child);
        }
    }

    public void notifyEventAdded(Event event) {
        for (var client : loggedUsers.values()) {
            client.eventAdded(event);
        }
    }

    public void notifySignupAdded(Signup signup) {
        for (var client : loggedUsers.values()) {
            client.signupAdded(signup);
        }
    }
}
