package org.example.domain;

public class Event extends Entity<Integer>{
    public String Name;
    public int MinAge;
    public int MaxAge;

    public Event (String Name, int MinAge, int MaxAge) {
        this.Name = Name;
        this.MinAge = MinAge;
        this.MaxAge = MaxAge;
    }

    public String GetName() {
        return Name;
    }

    public int GetMinAge() {
        return this.MinAge;
    }

    public int GetMaxAge() {
        return this.MaxAge;
    }

    @Override
    public String toString() {
        return this.Name + ' ' + this.MinAge + ' ' + this.MaxAge;
    }
}