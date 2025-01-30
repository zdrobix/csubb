package domain;

import java.util.ArrayList;
import java.util.List;

public class Person extends Entity{

    private String Name;
    private String Surname;
    private String Username;
    private String Password;
    private String City;
    private String Street;
    private String StreetNumber;
    private String PhoneNumber;

    public Person(long ID, String name, String surname, String username, String password, String city, String street, String streetNumber, String phoneNumber) {
        this.Name = name;
        this.Surname = surname;
        this.Username = username;
        this.Password = password;
        this.City = city;
        this.Street = street;
        this.StreetNumber = streetNumber;
        this.PhoneNumber = phoneNumber;
        super.SetID(ID);
    }

    public String GetName() {
        return Name;
    }

    public String GetSurname() {
        return Surname;
    }

    public String GetUsername() {
        return Username;
    }

    public String GetPassword() {
        return Password;
    }

    public String GetCity() {
        return City;
    }

    public String GetStreet() {
        return Street;
    }

    public String GetStreetNumber() {
        return StreetNumber;
    }

    public String GetPhoneNumber() {
        return PhoneNumber;
    }

    public List<String> GetParams() {
        var list = new ArrayList<String>();
        list.add("" + super.GetID());
        list.add(this.GetName());
        list.add(this.GetSurname());
        list.add(this.GetUsername());
        list.add(this.GetPassword());
        list.add(this.GetCity());
        list.add(this.GetStreet());
        list.add(this.GetStreetNumber());
        list.add(this.GetPhoneNumber());
        return list;
    }
}
