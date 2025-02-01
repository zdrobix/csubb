package backend.domain;

public class Animal {
    private int ID;
    private String Name;
    private int CenterID;
    private AnimalType Type;

    public Animal(int id, String name, int centerID, AnimalType type) {
        this.ID = id;
        this.Name = name;
        this.CenterID = centerID;
        this.Type = type;
    }

    public int getID() {
        return this.ID;
    }

    public String getName() {
        return this.Name;
    }

    public int getCenterID() {
        return this.CenterID;
    }

    public AnimalType getType() {
        return this.Type;
    }
}
