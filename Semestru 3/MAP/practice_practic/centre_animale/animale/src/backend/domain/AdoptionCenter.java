package backend.domain;

public class AdoptionCenter {
    private int ID;
    private String Name;
    private String Address;
    private int Capacity;

    public AdoptionCenter (int id, String name, String address, int capacity) {
        this.ID = id;
        this.Name = name;
        this.Address = address;
        this.Capacity = capacity;
    }

    public int getID() {
        return this.ID;
    }

    public String getName() {
        return this.Name;
    }

    public String getAddress() {
        return this.Address;
    }

    public int getCapacity() {
        return this.Capacity;
    }
}