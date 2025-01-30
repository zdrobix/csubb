package service;

import domain.Person;

public class Context {
    private Person CurrentPerson;

    public Context() {};

    public Person GetPerson() { return this.CurrentPerson; }

    public void SetPerson (Person person) {this.CurrentPerson = person;}
}
