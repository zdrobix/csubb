package org.example.domain;

public class Signup extends Entity<Tuple<Integer, Integer>>{
    private Child Child;
    private Event Event;

    public Signup (Child child, Event event) {
        this.Child = child;
        this.Event = event;
    }

    public Signup() {
        this.Child = new Child ("", "");
        this.Event = new Event("", 0, 0);
    };

    public Child GetChild() {
        return this.Child;
    }

    public Event GetEvent() {
        return this.Event;
    }

    @Override
    public String toString() {
        return this.Child.GetName() + " " + this.Event.GetName();
    }
}
